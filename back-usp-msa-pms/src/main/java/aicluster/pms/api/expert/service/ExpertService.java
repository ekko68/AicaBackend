package aicluster.pms.api.expert.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;

import aicluster.framework.common.entity.BnMember;
import aicluster.framework.common.entity.CmmtAttachment;
import aicluster.framework.common.entity.CmmtAttachmentGroup;
import aicluster.framework.common.service.AttachmentService;
import aicluster.framework.config.EnvConfig;
import aicluster.framework.security.SecurityUtils;
import aicluster.pms.api.expert.dto.ExpertDto;
import aicluster.pms.api.expert.dto.ExpertListExcelDto;
import aicluster.pms.api.expert.dto.ExpertListParam;
import aicluster.pms.common.dao.UsptExpertAcdmcrDao;
import aicluster.pms.common.dao.UsptExpertCareerDao;
import aicluster.pms.common.dao.UsptExpertClDao;
import aicluster.pms.common.dao.UsptExpertClMapngDao;
import aicluster.pms.common.dao.UsptExpertCrqfcDao;
import aicluster.pms.common.dao.UsptExpertDao;
import aicluster.pms.common.dao.UsptExpertReqstHistDao;
import aicluster.pms.common.entity.UsptExpert;
import aicluster.pms.common.entity.UsptExpertAcdmcr;
import aicluster.pms.common.entity.UsptExpertCareer;
import aicluster.pms.common.entity.UsptExpertCl;
import aicluster.pms.common.entity.UsptExpertClMapng;
import aicluster.pms.common.entity.UsptExpertCrqfc;
import aicluster.pms.common.entity.UsptExpertReqstHist;
import aicluster.pms.common.util.Code;
import bnet.library.exception.InvalidationException;
import bnet.library.util.CoreUtils;
import bnet.library.util.CoreUtils.string;
import bnet.library.util.pagination.CorePagination;
import bnet.library.util.pagination.CorePaginationInfo;


@Service
public class ExpertService {

	public static final long ITEMS_PER_PAGE = 10L;

	@Autowired
	private AttachmentService attachmentService;
	@Autowired
	private EnvConfig config;
	@Autowired
	private UsptExpertDao usptExpertDao;
	@Autowired
	private UsptExpertReqstHistDao usptExpertReqstHistDao;
	@Autowired
	private UsptExpertClMapngDao usptExpertClMapngDao;
	@Autowired
	private UsptExpertCareerDao usptExpertCareerDao;
	@Autowired
	private UsptExpertAcdmcrDao usptExpertAcdmcrDao;
	@Autowired
	private UsptExpertCrqfcDao usptExpertCrqfcDao;
	@Autowired
	private UsptExpertClDao usptExpertClDao;




	/**
	 * 전문가 목록 조회
	 * @param expertListParam
	 * @return
	 */
	public CorePagination<UsptExpert> getExpertList(ExpertListParam expertListParam, Long page) {
		/**회원정보**/
		SecurityUtils.checkWorkerIsInsider();
		if(page == null) {
			page = 1L;
		}
		if(expertListParam.getItemsPerPage() == null) {
			expertListParam.setItemsPerPage(ITEMS_PER_PAGE);
		}
		Long itemsPerPage = expertListParam.getItemsPerPage();

		// 전체 건수 확인
		long totalItems = usptExpertDao.selectExpertListCnt(expertListParam);

		// 조회할 페이지 구간 정보 확인
		CorePaginationInfo info = new CorePaginationInfo(page, itemsPerPage, totalItems);

		expertListParam.setBeginRowNum(info.getBeginRowNum());
		expertListParam.setItemsPerPage(itemsPerPage);

		// 페이지 목록 조회
		List<UsptExpert> list = usptExpertDao.selectExpertList(expertListParam);

		CorePagination<UsptExpert> pagination = new CorePagination<>(info, list);

		// 목록 조회
		return pagination;
	}


	/**
	 * 전문가 목록 엑셀다운로드
	 * @param pblancId
	 * @return
	 */
	public List<UsptExpert> getExpertListExcel(ExpertListParam expertListParam) {

		SecurityUtils.checkWorkerIsInsider();
		// 전체 건수 확인
		long totalItems = usptExpertDao.selectExpertListCnt(expertListParam);
		CorePaginationInfo info = new CorePaginationInfo(1L, totalItems, totalItems);
		expertListParam.setBeginRowNum(info.getBeginRowNum());
		expertListParam.setItemsPerPage(totalItems);
		return usptExpertDao.selectList(expertListParam);
	}

	/**
	 * 전문가 상세정보
	 * @param expertId
	 * @return
	 */
	@GetMapping("/{expertId}")
	public ExpertDto getExpertReqsInfo(String expertId) {

		SecurityUtils.checkWorkerIsInsider();

		/**조회 리턴**/
		ExpertDto resultExpertDto = new ExpertDto();

		/**전문가 기본정보**/
		UsptExpert usptExpert = new UsptExpert();
		usptExpert = usptExpertDao.selectOneExpert(expertId);

		if(usptExpert == null) {
			throw new InvalidationException(String.format(Code.validateMessage.조회결과없음, "등독된 전문"));
		}
		resultExpertDto.setUsptExpert(usptExpert);

		/** 첨부파일 목록 **/
		if (string.isNotBlank(usptExpert.getAttachmentGroupId())) {
			List<CmmtAttachment> list = attachmentService.getFileInfos_group(usptExpert.getAttachmentGroupId());
			resultExpertDto.setAttachFileList(list);
		}

		/** 전문분야 */
		resultExpertDto.setUsptExpertClMapng(usptExpertClMapngDao.selectExpertReqsList(expertId));

		/** 전문가 경력정보 */
		UsptExpertCareer usptExpertCareer = new UsptExpertCareer();
		usptExpertCareer.setExpertId(expertId);
		resultExpertDto.setUsptExpertCareer(usptExpertCareerDao.selectList(usptExpertCareer));

		/** 전문가 학력정보 */
		UsptExpertAcdmcr usptExpertAcdmcr = new UsptExpertAcdmcr();
		usptExpertAcdmcr.setExpertId(expertId);
		resultExpertDto.setUsptExpertAcdmcr(usptExpertAcdmcrDao.selectList(usptExpertAcdmcr));

		/** 전문가 자격증정보 */
		UsptExpertCrqfc usptExpertCrqfc = new UsptExpertCrqfc();
		usptExpertCrqfc.setExpertId(expertId);
		resultExpertDto.setUsptExpertCrqfc(usptExpertCrqfcDao.selectList(usptExpertCrqfc));

		return resultExpertDto;
	}

	/**
	 * 전문가 변경
	 * @param frontExpertParam
	 * * @param fileList
	 * @return
	 */
	public ExpertDto modifyExpert(String expertId,  ExpertDto expertDto,  List<MultipartFile> fileList) {

		/** 현재 시간*/
		Date now = new Date();
		/** 로그인 회원ID **/
		BnMember worker = SecurityUtils.checkWorkerIsInsider();
		String strMemberId = worker.getMemberId();

		/********************************* 신청자 정보 승인 및 수정 start**/

		/** 신청자정보(전문가) 변경 */
		UsptExpert usptExpert = expertDto.getUsptExpert();

		if(CoreUtils.string.isBlank(usptExpert.getLastUnivNm())) {
			throw new InvalidationException(String.format(Code.validateMessage.입력없음, "최종대학명"));
		}
		if(CoreUtils.string.isBlank(usptExpert.getUnivDeptNm())) {
			throw new InvalidationException(String.format(Code.validateMessage.입력없음, "대학학부명"));
		}

		//첨부파일 등록(첨부파일그룹ID)
		if(fileList != null && fileList.size() > 0) {
			if(CoreUtils.string.isNotBlank(usptExpert.getAttachmentGroupId())) {
				attachmentService.uploadFiles_toGroup(config.getDir().getUpload(), usptExpert.getAttachmentGroupId(), fileList, null, 0);
			} else {
				CmmtAttachmentGroup fileGroupInfo = attachmentService.uploadFiles_toNewGroup(config.getDir().getUpload(), fileList, null, 0);
				usptExpert.setAttachmentGroupId(fileGroupInfo.getAttachmentGroupId());
			}
		}

		String encBrthdy = CoreUtils.aes256.encrypt( usptExpert.getEncBrthdy(), expertId);	/*생년월일*/
		String encMbtlnum = CoreUtils.aes256.encrypt(usptExpert.getEncMbtlnum(), expertId);	/*휴대폰번호*/
		String encEmail = CoreUtils.aes256.encrypt(usptExpert.getEncEmail(), expertId);	/*이메일*/

		usptExpert.setEncBrthdy(encBrthdy);
		usptExpert.setEncMbtlnum(encMbtlnum);
		usptExpert.setEncEmail(encEmail);
		usptExpert.setUpdatedDt(now);
		usptExpert.setUpdaterId(strMemberId);

		usptExpertDao.update(usptExpert);

		/**전문가신청처리이력**/
		UsptExpertReqstHist usptExpertReqstHist = new UsptExpertReqstHist();

		usptExpertReqstHist.setExpertReqstHistId(CoreUtils.string.getNewId(Code.prefix.전문가신청처리이력));/** 전문가신청처리이력ID 생성 */
		usptExpertReqstHist.setExpertId(expertId);
		usptExpertReqstHist.setExpertReqstProcessSttusCd(Code.rceptSttus.접수완료);
		usptExpertReqstHist.setCreatedDt(now);
		usptExpertReqstHist.setCreatorId(strMemberId);

		usptExpertReqstHistDao.insert(usptExpertReqstHist);	//insert

		/** 전문분야 */
		List<UsptExpertClMapng> usptExpertClMapngList =  expertDto.getUsptExpertClMapng();
		if(usptExpertClMapngList.size()>0) {
			for( UsptExpertClMapng input : usptExpertClMapngList) {

				if(CoreUtils.string.isBlank(input.getExpertClId())) {
					throw new InvalidationException(String.format(Code.validateMessage.입력없음, "전문가분류ID"));
				}
				if(CoreUtils.string.equals(Code.flag.등록, input.getFlag())){
					input.setCreatedDt(now);
					input.setCreatorId(strMemberId);

					usptExpertClMapngDao.insertExpertClMapng(input);
				}else if(CoreUtils.string.equals(Code.flag.수정, input.getFlag())){

					usptExpertClMapngDao.update(input);
				}else if(CoreUtils.string.equals(Code.flag.삭제, input.getFlag())){

					usptExpertClMapngDao.deleteExpertClMapng(input);
				}
			}
		}

		/** 전문가 경력정보 */
		List<UsptExpertCareer> usptExpertCareerList =  expertDto.getUsptExpertCareer();
		if(usptExpertCareerList.size()>0) {
			for( UsptExpertCareer input : usptExpertCareerList) {

				if(CoreUtils.string.equals(Code.flag.등록, input.getFlag())){
					input.setExpertCareerId(CoreUtils.string.getNewId(Code.prefix.전문가경력));	/** 전문가경력ID 생성 */
					input.setCreatedDt(now);
					input.setCreatorId(strMemberId);
					input.setUpdatedDt(now);
					input.setUpdaterId(strMemberId);

					usptExpertCareerDao.insert(input);
				}else if(CoreUtils.string.equals(Code.flag.수정, input.getFlag())){
					input.setUpdatedDt(now);
					input.setUpdaterId(strMemberId);

					usptExpertCareerDao.update(input);
				}else if(CoreUtils.string.equals(Code.flag.삭제, input.getFlag())){

					usptExpertCareerDao.delete(input);
				}
			}
		}

		/** 전문가 학력정보 */
		List<UsptExpertAcdmcr> usptExpertAcdmcrList=  expertDto.getUsptExpertAcdmcr();
		if(usptExpertAcdmcrList.size()>0) {
			for( UsptExpertAcdmcr input : usptExpertAcdmcrList) {

				if(CoreUtils.string.equals(Code.flag.등록, input.getFlag())){
					input.setExpertAcdmcrId(CoreUtils.string.getNewId(Code.prefix.전문가학력));	/** 전문가학력ID 생성 */
					input.setCreatedDt(now);
					input.setCreatorId(strMemberId);
					input.setUpdatedDt(now);
					input.setUpdaterId(strMemberId);

					usptExpertAcdmcrDao.insert(input);
				}else if(CoreUtils.string.equals(Code.flag.수정, input.getFlag())){
					input.setUpdatedDt(now);
					input.setUpdaterId(strMemberId);

					usptExpertAcdmcrDao.update(input);
				}else if(CoreUtils.string.equals(Code.flag.삭제, input.getFlag())){

					usptExpertAcdmcrDao.delete(input);
				}
			}
		}

		/** 전문가 자격증정보 */
		List<UsptExpertCrqfc> usptExpertCrqfcList=  expertDto.getUsptExpertCrqfc();
		if(usptExpertCrqfcList.size()>0) {
			for( UsptExpertCrqfc input : usptExpertCrqfcList) {

				if(CoreUtils.string.equals(Code.flag.등록, input.getFlag())){
					input.setExpertCrqfcId(CoreUtils.string.getNewId(Code.prefix.전문가자격증));	/** 전문가자격증ID 생성 */
					input.setCreatedDt(now);
					input.setCreatorId(strMemberId);
					input.setUpdatedDt(now);
					input.setUpdaterId(strMemberId);

					usptExpertCrqfcDao.insert(input);
				}else if(CoreUtils.string.equals(Code.flag.수정, input.getFlag())){
					input.setUpdatedDt(now);
					input.setUpdaterId(strMemberId);

					usptExpertCrqfcDao.update(input);
				}else if(CoreUtils.string.equals(Code.flag.삭제, input.getFlag())){

					usptExpertCrqfcDao.delete(input);
				}
			}
			/*************************************** 신청자 정보 등록 end**/
		}
		return expertDto;
	}

	/**
	 * 전문가 삭제
	 * @param expertId
	 * @param expertListParam
	 * @return
	 */
	public void deleteExpert(ExpertListParam expertListParam){
		/**회원정보**/
		SecurityUtils.checkWorkerIsInsider();

		int deleteCnt = 0;

		String expertId = expertListParam.getExpertId();
		String attachmentGroupId = expertListParam.getAttachmentGroupId();
		/**전문가신청처리이력**/
		deleteCnt += usptExpertReqstHistDao.deleteExpert(expertId);
		/** 전문분야 */
		deleteCnt += usptExpertClMapngDao.deleteExpert(expertId);
		/** 전문가 경력정보 */
		deleteCnt += usptExpertCareerDao.deleteExpert(expertId);
		/** 전문가 학력정보 */
		deleteCnt += usptExpertAcdmcrDao.deleteExpert(expertId);
		/** 전문가 자격증정보 */
		deleteCnt += usptExpertCrqfcDao.deleteExpert(expertId);
		/** 전문가 삭제 */
		deleteCnt = usptExpertDao.delete(expertId);
		/** 첨부파일 목록 삭제 **/
		if (string.isNotBlank(attachmentGroupId)) {
			attachmentService.removeFiles_group(config.getDir().getUpload(), attachmentGroupId);
		}

		if(deleteCnt == 0) {
			throw new InvalidationException(String.format(Code.validateMessage.DB오류, "삭제"));
		}
	}

	/**
	 * 전문가 목록 조회
	 * @param expertListParam
	 * @return
	 */
	public CorePagination<UsptExpert> getExpertMatchHistList(ExpertListParam expertListParam, Long page) {
		/**회원정보**/
		SecurityUtils.checkWorkerIsInsider();

		if(page == null) {
			page = 1L;
		}
		if(expertListParam.getItemsPerPage() == null) {
			expertListParam.setItemsPerPage(ITEMS_PER_PAGE);
		}
		Long itemsPerPage = expertListParam.getItemsPerPage();

		// 전체 건수 확인
		long totalItems = usptExpertDao.selectExpertMatchHistListCnt(expertListParam);

		// 조회할 페이지 구간 정보 확인
		CorePaginationInfo info = new CorePaginationInfo(page, itemsPerPage, totalItems);

		expertListParam.setBeginRowNum(info.getBeginRowNum());
		expertListParam.setItemsPerPage(itemsPerPage);

		// 페이지 목록 조회
		List<UsptExpert> list = usptExpertDao.selectExpertMatchHistList(expertListParam);

		CorePagination<UsptExpert> pagination = new CorePagination<>(info, list);

		// 목록 조회
		return pagination;
	}

	/**
	 * 전문가 목록 엑셀다운로드
	 * @param expertListParam
	 * @return
	 */
	public List<UsptExpert> getExpertMatchHistListExcel(ExpertListParam expertListParam) {

		SecurityUtils.checkWorkerIsInsider();
		// 전체 건수 확인
		long totalItems = usptExpertDao.selectExpertMatchHistListCnt(expertListParam);
		CorePaginationInfo info = new CorePaginationInfo(1L, totalItems, totalItems);
		expertListParam.setBeginRowNum(info.getBeginRowNum());
		expertListParam.setItemsPerPage(totalItems);

		return usptExpertDao.selectExpertMatchHistList(expertListParam);
	}

	/**
	 * 전문가 excel 등록
	 * @param frontExpertParam
	 * * @param fileList
	 * @return
	 */
	public List<ExpertListExcelDto> modifyExpertExcel( List<ExpertListExcelDto> expertListExcelDto) {

		/** 현재 시간*/
		Date now = new Date();
		/** 로그인 회원ID **/
		BnMember worker = SecurityUtils.checkWorkerIsInsider();
		String strMemberId = worker.getMemberId();

		/************************ 체크 밸류 start*/
		if(expertListExcelDto.size() <=0) {
			throw new InvalidationException(String.format(Code.validateMessage.입력없음, "전문가정보"));
		}
		//checking
		List<ExpertListExcelDto> expertListExcelDtoCheck=  getCheckValueExpertExcel(expertListExcelDto);
		/************************ 체크 밸류 end*/

		ExpertListParam expertListParam = new ExpertListParam();
		//*** 조회 조건없이 전체 조회하여 생년월일, 휴대폰 번호, 이메일 모두 일치하면 중복건으로 판단
		List<UsptExpert> resultCompareList = usptExpertDao.selectList(expertListParam);

		//전문가 정보
		for( ExpertListExcelDto checkInputParam : expertListExcelDtoCheck) {
			/** 전문가ID 생성 */
			String newExpertId ="";
			String encBrthdy ="";
			String encMbtlnum ="";
			String encEmail ="";
			String expertId ="";
			String successYn = checkInputParam.getSuccessYn();

			//value 체크 후 정상적인 데이터만 등록
			if("Y".equals(successYn)) {
					/**
					 * 	생년월일, 휴대폰 번호, 이메일 모두 일치하면 중복건으로 판단
					 */
					int updateCnt = 0;
					/** 전문가 */
					UsptExpert usptExpert = new UsptExpert();

					for(UsptExpert resultCompareInfo : resultCompareList) {
						//조회된 전체 건수화 비교 하여 update or insert건수로 구분.
						if( string.equals(resultCompareInfo.getEmail(), checkInputParam.getEncEmail())
								&& string.equals(resultCompareInfo.getMbtlnum(), checkInputParam.getEncMbtlnum())
								&& string.equals(resultCompareInfo.getBrthdy(), checkInputParam.getEncBrthdy()) ){
							updateCnt ++;
							//********* update에서 사용하기 위해 셋팅  */
							usptExpert = resultCompareInfo;
						}
					}	//resultCompareList for end

					/**==================================================== 등록 및 수정 start*/
					if(updateCnt>0) {
						/** 전문가 */
						encBrthdy = CoreUtils.aes256.encrypt( checkInputParam.getEncBrthdy(), usptExpert.getExpertId());	/*생년월일*/
						encMbtlnum = CoreUtils.aes256.encrypt(checkInputParam.getEncMbtlnum(), usptExpert.getExpertId());	/*휴대폰번호*/
						encEmail = CoreUtils.aes256.encrypt(checkInputParam.getEncEmail(), usptExpert.getExpertId());	/*이메일*/

						usptExpert.setExpertNm(checkInputParam.getExpertNm());	 /** 전문가명 */
						usptExpert.setGenderCd(checkInputParam.getGenderCd());	 /** 성별코드(G:GENDER) */
						usptExpert.setNativeYn(checkInputParam.getNativeYn());		 /** 내국인여부 */
						usptExpert.setEncBrthdy(encBrthdy);				   		/** 암호화된 생년월일 */
						usptExpert.setEncMbtlnum(encMbtlnum);		 		/** 암호화된 휴대폰번호 */
						usptExpert.setEncEmail(encEmail);					 		/** 암호화된 이메일 */
						usptExpert.setWrcNm(checkInputParam.getWrcNm());	 	/** 직장명 */
						usptExpert.setOfcpsNm(checkInputParam.getOfcpsNm());	 /** 직위명 */
						usptExpert.setExpertReqstProcessSttusCd("COMPT");	//접수완료
						usptExpert.setUpdatedDt(now);
						usptExpert.setUpdaterId(strMemberId);
						//수정
						usptExpertDao.update(usptExpert);

						//전문가신청처리이력용 ID 셋팅
						expertId = usptExpert.getExpertId();

					}else{
						/** 전문가ID 생성 */
						newExpertId = CoreUtils.string.getNewId(Code.prefix.전문가);
						encBrthdy = CoreUtils.aes256.encrypt( checkInputParam.getEncBrthdy(), newExpertId);	/*생년월일*/
						encMbtlnum = CoreUtils.aes256.encrypt(checkInputParam.getEncMbtlnum(), newExpertId);	/*휴대폰번호*/
						encEmail = CoreUtils.aes256.encrypt(checkInputParam.getEncEmail(), newExpertId);	/*이메일*/

						/** 전문가 */
						UsptExpert usptExpertInsert = new UsptExpert();

						usptExpertInsert.setExpertId(newExpertId);				 		/** 전문가ID */
						usptExpertInsert.setExpertNm(checkInputParam.getExpertNm());	 /** 전문가명 */
						usptExpertInsert.setGenderCd(checkInputParam.getGenderCd());	 /** 성별코드(G:GENDER) */
						usptExpertInsert.setNativeYn(checkInputParam.getNativeYn());		 /** 내국인여부 */
						usptExpertInsert.setEncBrthdy(encBrthdy);				   		/** 암호화된 생년월일 */
						usptExpertInsert.setEncMbtlnum(encMbtlnum);		 			/** 암호화된 휴대폰번호 */
						usptExpertInsert.setEncEmail(encEmail);					 		/** 암호화된 이메일 */
						usptExpertInsert.setWrcNm(checkInputParam.getWrcNm());	 		/** 직장명 */
						usptExpertInsert.setOfcpsNm(checkInputParam.getOfcpsNm());	 	/** 직위명 */
						usptExpertInsert.setExpertReqstProcessSttusCd("COMPT");	//접수완료
						usptExpertInsert.setCreatedDt(now);
						usptExpertInsert.setCreatorId(strMemberId);
						usptExpertInsert.setUpdatedDt(now);
						usptExpertInsert.setUpdaterId(strMemberId);
						//등록
						usptExpertDao.insert(usptExpertInsert);

						//전문가신청처리이력용 ID 셋팅
						expertId =newExpertId;
					}

					/** 전문분야 */
					UsptExpertClMapng usptExpertClMapng = new UsptExpertClMapng();
					usptExpertClMapng.setExpertId(expertId);				 		/** 전문가ID */
					usptExpertClMapng.setExpertClId(checkInputParam.getExpertClId());	/**전문가분류ID**/

					int dupCnt = usptExpertClMapngDao.selectCheckCnt(usptExpertClMapng);

					if(dupCnt <=0) {
						usptExpertClMapng.setCreatedDt(now);
						usptExpertClMapng.setCreatorId(strMemberId);
						//등록
						usptExpertClMapngDao.insertExpertClMapng(usptExpertClMapng);
					}

					/**전문가신청처리이력**/
					UsptExpertReqstHist usptExpertReqstHist = new UsptExpertReqstHist();
					usptExpertReqstHist.setExpertReqstHistId(CoreUtils.string.getNewId(Code.prefix.전문가신청처리이력));/** 전문가신청처리이력ID 생성 */
					usptExpertReqstHist.setExpertId(expertId);
					usptExpertReqstHist.setExpertReqstProcessSttusCd("COMPT");	//접수완료
					usptExpertReqstHist.setResnCn("");
					usptExpertReqstHist.setCreatedDt(now);
					usptExpertReqstHist.setCreatorId(strMemberId);
					//등록
					usptExpertReqstHistDao.insert(usptExpertReqstHist);	//insert
					/**==================================================== 등록 및 수정 end*/
			}	//successYn("Y") end
		}	//checkInputParam for end

		return expertListExcelDtoCheck;
	}

	/**
	 * 전문가 excel 등록 value check
	 * @param expertListExcelDto
	 * @return List<ExpertListExcelDto>
	 */
	public List<ExpertListExcelDto> getCheckValueExpertExcel( List<ExpertListExcelDto> expertListExcelDto) {
		SecurityUtils.checkWorkerIsInsider();
		int index=0;
		for(ExpertListExcelDto inputParam : expertListExcelDto) {

			String expertNm 	= inputParam.getExpertNm();                  /** 전문가명 */
			String  genderCd = inputParam.getGenderCd();					   /** 성별코드(G:GENDER) */
			Boolean nativeYn = inputParam.getNativeYn();                 	 /** 내국인여부 */
			String encBrthdy 	= inputParam.getEncBrthdy();                  /** 암호화된 생년월일 */
			String encMbtlnum = inputParam.getEncMbtlnum();              /** 암호화된 휴대폰번호 */
			String encEmail 	= inputParam.getEncEmail();                   /** 암호화된 이메일 */
			String  wrcNm		= inputParam.getWrcNm();                     /** 직장명 */
			String  ofcpsNm	= inputParam.getOfcpsNm();                   /** 직위명 */
			String expertClId	= inputParam.getExpertClId();          			/** 전문가분류ID */

			//기본값 셋팅
			inputParam.setSuccessYn("Y");

			if(string.isBlank(expertNm) || string.isBlank(genderCd) || nativeYn ==null || string.isBlank(encBrthdy) || string.isBlank(encMbtlnum)
					|| string.isBlank(encEmail)|| string.isBlank(wrcNm)|| string.isBlank(ofcpsNm)|| string.isBlank(expertClId) ) {
				inputParam.setSuccessYn("N");
			}
			//생년월일
			if(string.length(encBrthdy) != 8){
				inputParam.setSuccessYn("N");
			}

			expertListExcelDto.set(index, inputParam);
			index++;
		}
		return expertListExcelDto;
	}

	/**
	 * 전문가 등록 템플릿 엑셀 다운로드 전문가분류 조회
	 * @param
	 * @return
	 */
	public List<UsptExpertCl> selectExpertClfcTreeList() {

		return usptExpertClDao.selectExpertClfcTreeList();
	}

}