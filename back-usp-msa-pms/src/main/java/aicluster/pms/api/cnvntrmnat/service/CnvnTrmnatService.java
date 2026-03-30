package aicluster.pms.api.cnvntrmnat.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import aicluster.framework.common.entity.BnMember;
import aicluster.framework.common.entity.CmmtAttachment;
import aicluster.framework.common.entity.CmmtAttachmentGroup;
import aicluster.framework.common.service.AttachmentService;
import aicluster.framework.config.EnvConfig;
import aicluster.framework.notification.EmailUtils;
import aicluster.framework.notification.SmsUtils;
import aicluster.framework.notification.dto.EmailSendParam;
import aicluster.framework.notification.dto.SmsSendParam;
import aicluster.framework.notification.nhn.email.EmailReceiverResult;
import aicluster.framework.notification.nhn.email.EmailResult;
import aicluster.framework.notification.nhn.sms.SmsResult;
import aicluster.framework.security.SecurityUtils;
import aicluster.pms.api.cnvncncls.dto.CnvnCnclsParam;
import aicluster.pms.api.cnvntrmnat.dto.CnvnTrmnatDto;
import aicluster.pms.api.cnvntrmnat.dto.CnvnTrmnatInputParam;
import aicluster.pms.api.cnvntrmnat.dto.CnvnTrmnatParam;
import aicluster.pms.common.dao.CmmtMemberDao;
import aicluster.pms.common.dao.UsptBsnsCnvnDao;
import aicluster.pms.common.dao.UsptBsnsCnvnPrtcmpnySignDao;
import aicluster.pms.common.dao.UsptBsnsCnvnTrmnatHistDao;
import aicluster.pms.common.entity.CmmtMember;
import aicluster.pms.common.entity.UsptBsnsCnvn;
import aicluster.pms.common.entity.UsptBsnsCnvnPrtcmpnySign;
import aicluster.pms.common.entity.UsptBsnsCnvnTrmnatHist;
import aicluster.pms.common.util.Code;
import bnet.library.exception.InvalidationException;
import bnet.library.util.CoreUtils;
import bnet.library.util.CoreUtils.string;
import bnet.library.util.pagination.CorePagination;
import bnet.library.util.pagination.CorePaginationInfo;


@Service
public class CnvnTrmnatService {

	public static final long ITEMS_PER_PAGE = 10L;
	@Autowired
	private EnvConfig config;
	@Autowired
	private AttachmentService attachmentService;		/*파일*/
	@Autowired
	private UsptBsnsCnvnDao usptBsnsCnvnDao;		/*사업협약*/
	@Autowired
	private UsptBsnsCnvnPrtcmpnySignDao usptBsnsCnvnPrtcmpnySignDao;		/*사업협약참여기업서명*/
	@Autowired
	private CmmtMemberDao cmmtMemberDao;	/*회원*/
	@Autowired
	private UsptBsnsCnvnTrmnatHistDao usptBsnsCnvnTrmnatHistDao;	/*사업협약해지이력*/


	@Autowired
	private SmsUtils smsUtils;
	@Autowired
	private EmailUtils emailUtils;

	/**
	 * 전자협약 해지 관리 목록조회
	 * @param cnvnCnclsParam(bsnsYear, bsnsNm, cnvnSttusCd, cnvnTrmnatDeStart, cnvnTrmnatDeEnd, taskNmKo, rspnberNm, memberNm, receiptNo)
	 * @param page
	 * @return
	 */
	public CorePagination<UsptBsnsCnvn> getList(CnvnTrmnatParam cnvnTrmnatParam,  Long page){
		SecurityUtils.checkWorkerIsInsider();

		if(page == null) {
			page = 1L;
		}
		if(cnvnTrmnatParam.getItemsPerPage() == null) {
			cnvnTrmnatParam.setItemsPerPage(ITEMS_PER_PAGE);
		}
		Long itemsPerPage = cnvnTrmnatParam.getItemsPerPage();
		//전체건수 조회
		long totalItems = usptBsnsCnvnDao.selectCnvnTrmnatListCnt(cnvnTrmnatParam);

		CorePaginationInfo info = new CorePaginationInfo(page, itemsPerPage, totalItems);
		cnvnTrmnatParam.setBeginRowNum(info.getBeginRowNum());
		cnvnTrmnatParam.setItemsPerPage(itemsPerPage);
		List<UsptBsnsCnvn> list = usptBsnsCnvnDao.selectCnvnTrmnatList(cnvnTrmnatParam);

		CorePagination<UsptBsnsCnvn> pagination = new CorePagination<>(info, list);
		return pagination;
	}


	/**
	 * 전자협약 관리 목록 엑셀 다운로드
	 @param cnvnCnclsParam(bsnsYear, bsnsNm, cnvnSttusCd, cnvnTrmnatDeStart, cnvnTrmnatDeEnd, taskNmKo, rspnberNm, memberNm, receiptNo)
	 * @return
	 */
	public List<UsptBsnsCnvn> getListExcel(CnvnTrmnatParam cnvnTrmnatParam){
		SecurityUtils.checkWorkerIsInsider();

		//전체건수 조회
		long totalItems = usptBsnsCnvnDao.selectCnvnTrmnatListCnt(cnvnTrmnatParam);

		CorePaginationInfo info = new CorePaginationInfo(1L, totalItems, totalItems);
		cnvnTrmnatParam.setBeginRowNum(info.getBeginRowNum());
		cnvnTrmnatParam.setItemsPerPage(totalItems);
		return  usptBsnsCnvnDao.selectCnvnTrmnatList(cnvnTrmnatParam);
	}


	/**
	 * 전자협약 해지 관리 과제검색 팝업
	 * @param cnvnCnclsParam(bsnsYear, bsnsNm, cnvnSttusCd, cnvnTrmnatDeStart, cnvnTrmnatDeEnd, taskNmKo, rspnberNm, memberNm, receiptNo)
	 * @param page
	 * @return
	 */
	public CorePagination<UsptBsnsCnvn> getCnvnTrmnatTask(CnvnTrmnatParam cnvnTrmnatParam, Long page){
		SecurityUtils.checkWorkerIsInsider();

		if(page == null) {
			page = 1L;
		}
		if(cnvnTrmnatParam.getItemsPerPage() == null) {
			cnvnTrmnatParam.setItemsPerPage(ITEMS_PER_PAGE);
		}
		cnvnTrmnatParam.setCnvnSttusCd(Code.cnvnSttus.협약완료);
		Long itemsPerPage = cnvnTrmnatParam.getItemsPerPage();
		//전체건수 조회
		long totalItems = usptBsnsCnvnDao.selectCnvnTrmnatListCnt(cnvnTrmnatParam);

		CorePaginationInfo info = new CorePaginationInfo(page, itemsPerPage, totalItems);
		cnvnTrmnatParam.setBeginRowNum(info.getBeginRowNum());
		cnvnTrmnatParam.setItemsPerPage(itemsPerPage);
		List<UsptBsnsCnvn> list = usptBsnsCnvnDao.selectCnvnTrmnatList(cnvnTrmnatParam);

		CorePagination<UsptBsnsCnvn> pagination = new CorePagination<>(info, list);
		return pagination;
	}


	/**
	 * 전자협약 해지 등록_저장
	 * @param CnvnCnclsParam
	 * @return
	 */
	public void modifyCnvnTrmnat(CnvnTrmnatInputParam cnvnTrmnatInputParam, List<MultipartFile> fileList) {

		/** 현재 시간*/
		Date now = new Date();
		/** 로그인 회원ID **/
		BnMember worker = SecurityUtils.checkWorkerIsInsider();
		String strMemberId = worker.getMemberId();

		String bsnsCnvnId = cnvnTrmnatInputParam.getBsnsCnvnId();
		String cnvnSttusCdInput= cnvnTrmnatInputParam.getCnvnSttusCd();	/**협약상태 코드_input */
		String cnvnTrmnatProcessDivCd ="";	/* 협약해지처리구분코드 */
		String resnCn  				= "";			/* 해지이력 사유내용 */
		String memberId 			= "";			/* 회원ID(주관업체)*/
		String taskNmKo			= "";			/* 과제명(국문) */
		String cnvnSttusCdNow 	= "";			/*현재협약태*/
		String cnvnTrmnatDiv =Code.cnvnTrmnatDiv.협약;										/** 협약해지구분 코드 */

		/** 협약상태코드
		 * 협약해지 		=  "CNST06";
		 * 저장 			= "SAVE";
		 * */
		/**기본정보 조회 cnvnTrmnatParam.getBsnsCnvnId() cnvnTrmnatParam.getApplyId()*/
		UsptBsnsCnvn usptBsnsCnvnDetail =usptBsnsCnvnDao.selectCnvnTrmnatDetail(cnvnTrmnatInputParam);
		//기본정보 셋팅
		cnvnSttusCdNow = usptBsnsCnvnDetail.getCnvnSttusCd();
		memberId = usptBsnsCnvnDetail.getMemberId();
		taskNmKo = usptBsnsCnvnDetail.getTaskNmKo();

		//협약해지
		if(CoreUtils.string.equals( Code.cnvnSttus.협약해지, cnvnSttusCdInput)){
			if(!CoreUtils.string.equals( Code.cnvnSttus.협약완료, cnvnSttusCdNow)){
				throw new InvalidationException("협약상태가 <협약완료> 경우에만 해지 등록이 가능합니다.");
			}
			cnvnTrmnatDiv =Code.cnvnTrmnatDiv.해지;
			//이력
			cnvnTrmnatProcessDivCd=  Code.cnvnTrmnatProcessDiv.해지등록;
			resnCn = "해지등록 처리되었습니다.";
		//저장
		}else if(CoreUtils.string.equals( "SAVE", cnvnSttusCdInput)){
			cnvnSttusCdInput = cnvnSttusCdNow;								//현재 상태코드
			cnvnTrmnatDiv =usptBsnsCnvnDetail.getCnvnTrmnatDivCd();
			//이력
			cnvnTrmnatProcessDivCd =  Code.cnvnTrmnatProcessDiv.저장;
			resnCn = "저장 처리되었습니다.";
		}

		/**해지첨부파일*/
		UsptBsnsCnvn usptBsnsCnvn = new UsptBsnsCnvn();

		/*해지첨부파일그룹 ID 조회*/
		String cnvnTrmnatFileGroupId = usptBsnsCnvnDetail.getCnvnTrmnatFileGroupId();/* 해지파일그룹ID */
		/* 첨부파일 삭제 */
		List<CmmtAttachment> attachFileDeleteList = cnvnTrmnatInputParam.getAttachFileDeleteList();
		if( attachFileDeleteList !=null && attachFileDeleteList.size() >0) {
			for(CmmtAttachment attachInfo : attachFileDeleteList) {
				boolean groupFileExists = 	attachmentService.removeFile( config.getDir().getUpload(), attachInfo.getAttachmentId());
				if(!groupFileExists) {
					cnvnTrmnatFileGroupId = null;
				}
			}
		}
		/* 협약해지 첨부파일 등록*/
		if(fileList != null && fileList.size() > 0) {
			if(CoreUtils.string.isNotBlank(cnvnTrmnatFileGroupId)) {
				attachmentService.uploadFiles_toGroup(config.getDir().getUpload(), cnvnTrmnatFileGroupId, fileList, null, 0);
			} else {
				CmmtAttachmentGroup fileGroupInfo = attachmentService.uploadFiles_toNewGroup(config.getDir().getUpload(), fileList, null, 0);
				 /** 협약해지첨부파일그룹ID */
				cnvnTrmnatFileGroupId = fileGroupInfo.getAttachmentGroupId();
			}
		}

		usptBsnsCnvn.setBsnsCnvnId(bsnsCnvnId);						/*사업협약ID*/
		usptBsnsCnvn.setApplyId(cnvnTrmnatInputParam.getApplyId());								/*신청ID*/
		usptBsnsCnvn.setCnvnSttusCd(cnvnSttusCdInput);										//협약상태 코드
		usptBsnsCnvn.setCnvnTrmnatDe(cnvnTrmnatInputParam.getCnvnTrmnatDe());				/*협약해지일*/
		usptBsnsCnvn.setCnvnTrmnatDivCd(cnvnTrmnatDiv);										/*협약해지구분코드*/
		usptBsnsCnvn.setCnvnTrmnatResnCn(cnvnTrmnatInputParam.getCnvnTrmnatResnCn());/*해지사유내용*/
		usptBsnsCnvn.setRedempTrgamt(cnvnTrmnatInputParam.getRedempTrgamt());			/*환수대상금액*/
		usptBsnsCnvn.setRedempComptAmount(cnvnTrmnatInputParam.getRedempComptAmount());	/*환수완료금액*/
		usptBsnsCnvn.setRedempDe(cnvnTrmnatInputParam.getRedempDe());						/*환수일*/
		usptBsnsCnvn.setCnvnTrmnatFileGroupId(cnvnTrmnatFileGroupId);					/*협약해지 첨부파일*/
		usptBsnsCnvn.setUpdatedDt(now);
		usptBsnsCnvn.setUpdaterId(strMemberId);
		//변경
		usptBsnsCnvnDao.updateCnvnTrmnat(usptBsnsCnvn);

		/**사업협약해지이력 등록*/
		UsptBsnsCnvnTrmnatHist usptBsnsCnvnTrmnatHist = new UsptBsnsCnvnTrmnatHist();
		String  bsnsCnvnTrmnatHistId =  CoreUtils.string.getNewId(Code.prefix.사업협약해지이력);
		usptBsnsCnvnTrmnatHist.setBsnsCnvnTrmnatHistId(bsnsCnvnTrmnatHistId);
		usptBsnsCnvnTrmnatHist.setBsnsCnvnId(bsnsCnvnId);						/*사업협약ID*/
		usptBsnsCnvnTrmnatHist.setCnvnTrmnatProcessDivCd(cnvnTrmnatProcessDivCd);
		usptBsnsCnvnTrmnatHist.setResnCn(resnCn);
		usptBsnsCnvnTrmnatHist.setCreatedDt(now);
		usptBsnsCnvnTrmnatHist.setCreatorId(strMemberId);
		//등록
		usptBsnsCnvnTrmnatHistDao.insert(usptBsnsCnvnTrmnatHist);

		/** email, sms 알림 전송*/
		if(CoreUtils.string.equals( Code.cnvnSttus.협약해지, cnvnTrmnatInputParam.getCnvnSttusCd())){
			/**************주관업체*/
			/*알림 전송*/
			CmmtMember memberInfoMain = cmmtMemberDao.select(memberId);	//회원정보 조회
			CnvnCnclsParam sendSmsEmailMain = new CnvnCnclsParam();
			sendSmsEmailMain.setMemberId(memberId);
			sendSmsEmailMain.setMemberNm(memberInfoMain.getMemberNm());
			sendSmsEmailMain.setEmail(memberInfoMain.getEmail());
			sendSmsEmailMain.setMbtlnum(memberInfoMain.getMobileNo());
			sendSmsEmailMain.setTaskNmKo(taskNmKo);
			sendMsg(sendSmsEmailMain);

			/**************참여기업*/
			List <UsptBsnsCnvnPrtcmpnySign> selectList= usptBsnsCnvnPrtcmpnySignDao.selectList(bsnsCnvnId);
			for( UsptBsnsCnvnPrtcmpnySign param :  selectList) {
				/*알림 전송*/
				CmmtMember memberInfo = cmmtMemberDao.select(param.getMemberId());		//회원정보 조회
				CnvnCnclsParam sendSmsEmail = new CnvnCnclsParam();
				sendSmsEmail.setMemberId(memberInfo.getMemberId());
				sendSmsEmail.setMemberNm(memberInfo.getMemberNm());
				sendSmsEmail.setEmail(memberInfo.getEmail());
				sendSmsEmail.setMbtlnum(memberInfo.getMobileNo());
				sendSmsEmail.setTaskNmKo(taskNmKo);
				sendMsg(sendSmsEmail);
			}
		}
	}


	/**
	 * 전자협약 해지 취소--> 협약완료로 변경
	 * @param CnvnCnclsParam
	 * @return
	 */
	public void modifyCnvnTrmnatCancel(CnvnTrmnatParam cnvnTrmnatParam) {

		/** 현재 시간*/
		Date now = new Date();
		/** 로그인 회원ID **/
		BnMember worker = SecurityUtils.checkWorkerIsInsider();
		String strMemberId = worker.getMemberId();

		/*협약해지취소하면 협약완료 상태로 한다.*/
		String cnvnSttusCdInput= Code.cnvnSttus.협약완료;										/** 협약상태 코드 */
		String cnvnTrmnatProcessDivCd =Code.cnvnTrmnatProcessDiv.해지취소;		/** 협약해지처리구분코드 */
		String resnCn  ="해지취소 처리되었습니다.";						 						/** 사유내용 */
		String cnvnTrmnatDiv =Code.cnvnTrmnatDiv.협약;										/** 협약해지구분 코드 */

		/** 협약상태코드
		 * 	협약해지취소	=  "CNST07";
		 * */
		//현재협약샹태 체크
		String cnvnSttusCdNow = usptBsnsCnvnDao.selectCnvnSttusCd(cnvnTrmnatParam.getBsnsCnvnId(), cnvnTrmnatParam.getApplyId());

		if(!CoreUtils.string.equals( Code.cnvnSttus.협약해지, cnvnSttusCdNow)){
			throw new InvalidationException("협약상태가 <협약해지> 경우에만 협약해지취소가 가능합니다.");
		}

		UsptBsnsCnvn usptBsnsCnvn = new UsptBsnsCnvn();

		usptBsnsCnvn.setBsnsCnvnId(cnvnTrmnatParam.getBsnsCnvnId());						/*사업협약ID*/
		usptBsnsCnvn.setApplyId(cnvnTrmnatParam.getApplyId());								/*신청ID*/
		usptBsnsCnvn.setCnvnSttusCd(cnvnSttusCdInput);										//협약상태 코드
		usptBsnsCnvn.setCnvnTrmnatDivCd(cnvnTrmnatDiv);										/** 협약해지구분 코드 */
		usptBsnsCnvn.setUpdatedDt(now);
		usptBsnsCnvn.setUpdaterId(strMemberId);
		// 협약 상태 변경_취소
		usptBsnsCnvnDao.updateCnvnSttusCancel(usptBsnsCnvn);

		/**사업협약해지이력 등록*/
		UsptBsnsCnvnTrmnatHist usptBsnsCnvnTrmnatHist = new UsptBsnsCnvnTrmnatHist();
		String  bsnsCnvnTrmnatHistId =  CoreUtils.string.getNewId(Code.prefix.사업협약해지이력);
		usptBsnsCnvnTrmnatHist.setBsnsCnvnTrmnatHistId(bsnsCnvnTrmnatHistId);
		usptBsnsCnvnTrmnatHist.setBsnsCnvnId(cnvnTrmnatParam.getBsnsCnvnId());						/*사업협약ID*/
		usptBsnsCnvnTrmnatHist.setCnvnTrmnatProcessDivCd(cnvnTrmnatProcessDivCd);
		usptBsnsCnvnTrmnatHist.setResnCn(resnCn);
		usptBsnsCnvnTrmnatHist.setCreatedDt(now);
		usptBsnsCnvnTrmnatHist.setCreatorId(strMemberId);
		//등록
		usptBsnsCnvnTrmnatHistDao.insert(usptBsnsCnvnTrmnatHist);
	}

	/**
	 * 전자협약 해지 관리 상세 조회
	 * @param cnvnCnclsParam(bsnsCnvnId, applyId)
	 * @return
	 */
	public CnvnTrmnatDto getCnvnTrmnatInfo(CnvnTrmnatParam cnvnTrmnatParam){
		SecurityUtils.checkWorkerIsInsider();

		/**조회 리턴**/
		CnvnTrmnatDto resultCnvnTrmnatDto = new CnvnTrmnatDto();

		/**기본정보 and 협약주체 조회*/
		UsptBsnsCnvn usptBsnsCnvnResult =usptBsnsCnvnDao.selectCnvnTrmnatDetail(cnvnTrmnatParam);

		//기본정보 리턴 셋팅
		resultCnvnTrmnatDto.setUsptBsnsCnvn(usptBsnsCnvnResult);

		/** 해지첨부파일 목록 **/
		if (string.isNotBlank(usptBsnsCnvnResult.getCnvnTrmnatFileGroupId())) {
			List<CmmtAttachment> list = attachmentService.getFileInfos_group(usptBsnsCnvnResult.getCnvnTrmnatFileGroupId());
			resultCnvnTrmnatDto.setAttachFileList(list);
		}

		/**사업협약해지이력 */
		List<UsptBsnsCnvnTrmnatHist> usptBsnsCnvnTrmnatHistList = usptBsnsCnvnTrmnatHistDao.selectList(usptBsnsCnvnResult.getBsnsCnvnId());
		resultCnvnTrmnatDto.setUsptBsnsCnvnTrmnatHistList(usptBsnsCnvnTrmnatHistList);

		return resultCnvnTrmnatDto;
	}

	/*******************************************************************************************************************************/
	/**
	 * sms or email 전송
	 * @param CnvnCnclsParam
	 * @return
	 */
	public String sendMsg(CnvnCnclsParam param) {
		SecurityUtils.checkWorkerIsInsider();
		StringBuffer sbMsg = new StringBuffer();
		//실패사유
		StringBuilder resultMsg = new StringBuilder();

		if(param == null) {
			throw new InvalidationException("발송대상이 설정되지 않았습니다.");
		}

		String title = "전자협약 해지처리 완료";
		String mainTitle = "전자협약 해지처리 완료";
		String memNm = param.getMemberNm();		//회원명
		String cnVal =  param.getTaskNmKo()+"의 해지처리 완료를 안내 드립니다.";			//발송내용


		//****************************이메일
		String emailCn = CoreUtils.file.readFileString("/form/email/email-bsnsPlan.html");//
		EmailSendParam esParam = new EmailSendParam();
		esParam.setContentHtml(true);
		esParam.setEmailCn(emailCn);
		esParam.setTitle(title);
		esParam.setContentHtml(true);

		if(CoreUtils.string.isNotEmpty(param.getEmail())){
			Map<String, String> templateParam = new HashMap<>();
			templateParam.put("mainTitle", mainTitle);
			templateParam.put("memNm", memNm);	//회원명
			templateParam.put("cnVal", cnVal);	//발송내용
			esParam.addRecipient(param.getEmail(), memNm, templateParam);
		} else {
			sbMsg.append("[" + memNm +"] 이메일 정보가 없습니다.\n");
		}

		EmailResult er = emailUtils.send(esParam);
		if (er.getFailCnt() >0) {
    		throw new InvalidationException("이메일 발송 실패");
    	}

    	List<EmailReceiverResult> receiverList = er.getReceiverList();

    	//실패 사유를 메세지로 전송하기 위함
    	if(er.getFailCnt() > 0) {
	        for(EmailReceiverResult receiverInfo : receiverList) {
	        	if(!receiverInfo.getIsSuccessful()) {
	        		resultMsg.append("수신자 : ").append(receiverInfo.getReceiveName()).append("(").append(receiverInfo.getReceiveMailAddr()).append(")는 발송 실패되었습니다.\n 실패사유 :").append(receiverInfo.getResultMessage()).append("\n");
	        	}
	        }
    	}

		//********************SMS
    	SmsSendParam smsParam = new SmsSendParam();
		if(CoreUtils.string.isNotEmpty(param.getMbtlnum())){

			smsParam.setSmsCn(mainTitle+"\n[##cnVal##]");

			Map<String, String> tp = new HashMap<>();
			tp.put("cnVal", cnVal);

			smsParam.addRecipient(param.getMbtlnum(),tp);
		} else {
			sbMsg.append("[" + param.getMbtlnum() +"] 핸드폰번호 정보가 없습니다.\n");
		}

		SmsResult sr = smsUtils.send(smsParam);
		 // 모두 발송실패일 경우, 오류처리
        if (sr.getFailCnt() >0) {
        	throw new InvalidationException("SMS발송 실패 :"+ sr.getRecipientList().get(0).getResultMessage());
        }

		return sbMsg.toString();
	}
}