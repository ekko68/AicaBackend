package aicluster.pms.api.evl.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aicluster.framework.common.entity.BnMember;
import aicluster.framework.common.service.AttachmentService;
import aicluster.framework.notification.EmailUtils;
import aicluster.framework.notification.SmsUtils;
import aicluster.framework.notification.dto.EmailSendParam;
import aicluster.framework.notification.dto.SmsSendParam;
import aicluster.framework.notification.nhn.email.EmailReceiverResult;
import aicluster.framework.notification.nhn.email.EmailResult;
import aicluster.framework.notification.nhn.sms.SmsRecipientResult;
import aicluster.framework.notification.nhn.sms.SmsResult;
import aicluster.framework.security.SecurityUtils;
import aicluster.pms.api.evl.dto.EvasResnCnDto;
import aicluster.pms.api.evl.dto.EvlCharmnOpinionDto;
import aicluster.pms.api.evl.dto.EvlResultDto;
import aicluster.pms.api.evl.dto.EvlTableDto;
import aicluster.pms.api.evl.dto.EvlTableParam;
import aicluster.pms.api.evl.dto.EvlTargetListParam;
import aicluster.pms.api.evl.dto.EvlTrgetIdParam;
import aicluster.pms.api.evl.dto.PointListDto;
import aicluster.pms.api.evl.dto.PointListParam;
import aicluster.pms.api.evl.dto.PointParam;
import aicluster.pms.common.dao.UsptBsnsPblancApplcntDao;
import aicluster.pms.common.dao.UsptEvlCmitDao;
import aicluster.pms.common.dao.UsptEvlIemDao;
import aicluster.pms.common.dao.UsptEvlIemResultAdsbtrDao;
import aicluster.pms.common.dao.UsptEvlIemResultDao;
import aicluster.pms.common.dao.UsptEvlMfcmmDao;
import aicluster.pms.common.dao.UsptEvlMfcmmResultDao;
import aicluster.pms.common.dao.UsptEvlOnlineDao;
import aicluster.pms.common.dao.UsptEvlTrgetDao;
import aicluster.pms.common.dao.UsptExpertDao;
import aicluster.pms.common.dto.EvlAtendListDto;
import aicluster.pms.common.dto.EvlIemByCmitDto;
import aicluster.pms.common.dto.EvlPointListDto;
import aicluster.pms.common.dto.EvlResultAddAllotBasicDto;
import aicluster.pms.common.dto.EvlResultBasicInfoDto;
import aicluster.pms.common.dto.EvlResultByMfcmmListDto;
import aicluster.pms.common.dto.EvlResultByTargetListDto;
import aicluster.pms.common.dto.EvlResultDetailByIemDto;
import aicluster.pms.common.dto.EvlResultGnrlzDto;
import aicluster.pms.common.dto.EvlResutlDspthInfoDto;
import aicluster.pms.common.dto.EvlResutlSndngTrgetDto;
import aicluster.pms.common.dto.EvlSttusListDto;
import aicluster.pms.common.dto.EvlTableListDto;
import aicluster.pms.common.dto.EvlTargetListDto;
import aicluster.pms.common.dto.OnlineEvlResutlAgreDto;
import aicluster.pms.common.dto.OnlineEvlResutlDto;
import aicluster.pms.common.dto.OnlineEvlResutlGnrlzDto;
import aicluster.pms.common.entity.UsptBsnsPblancApplcnt;
import aicluster.pms.common.entity.UsptEvlCmit;
import aicluster.pms.common.entity.UsptEvlCmitExtrcTarget;
import aicluster.pms.common.entity.UsptEvlIemResult;
import aicluster.pms.common.entity.UsptEvlIemResultAdsbtr;
import aicluster.pms.common.entity.UsptEvlMfcmm;
import aicluster.pms.common.entity.UsptEvlMfcmmResult;
import aicluster.pms.common.entity.UsptEvlTrget;
import aicluster.pms.common.entity.UsptExpert;
import aicluster.pms.common.util.Code;
import bnet.library.exception.InvalidationException;
import bnet.library.exception.LoggableException;
import bnet.library.util.CoreUtils;
import bnet.library.util.CoreUtils.string;
import bnet.library.util.pagination.CorePagination;
import bnet.library.util.pagination.CorePaginationInfo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EvlOnlineService {

	public static final long ITEMS_PER_PAGE = 10L;
	@Autowired
	private UsptEvlCmitDao usptEvlCmitDao;
	@Autowired
	private UsptExpertDao usptExpertDao;
	@Autowired
	private UsptEvlOnlineDao usptEvlOnlineDao;
	@Autowired
	private UsptEvlMfcmmResultDao usptEvlMfcmmResultDao;
	@Autowired
	private UsptEvlIemResultDao usptEvlIemResultDao;
	@Autowired
	private UsptEvlTrgetDao usptEvlTrgetDao;
	@Autowired
	private UsptEvlIemDao usptEvlIemDao;
	@Autowired
	private UsptEvlMfcmmDao usptEvlMfcmmDao;
	@Autowired
	private EmailUtils emailUtils;
	@Autowired
	private SmsUtils smsUtils;
	@Autowired
	private UsptEvlIemResultAdsbtrDao usptEvlIemResultAdsbtrDao;
	@Autowired
	private UsptBsnsPblancApplcntDao usptBsnsPblancApplcntDao;
	@Autowired
	private AttachmentService attachmentService;

	/**
	 * 평가진행 목록 조회
	 * @param param
	 * @param page
	 * @return
	 */
	public CorePagination<EvlTargetListDto> getEvlTargetList(EvlTargetListParam param, Long page) {
		BnMember worker = SecurityUtils.checkWorkerIsInsider();
		if(page == null) {
			page = 1L;
		}
		if(param.getItemsPerPage() == null) {
			param.setItemsPerPage(ITEMS_PER_PAGE);
		}

		param.setInsiderId(worker.getMemberId());
		long totalItems = usptEvlCmitDao.selectEvlTargetListCount(param);
		CorePaginationInfo info = new CorePaginationInfo(page, param.getItemsPerPage(), totalItems);
		param.setBeginRowNum(info.getBeginRowNum());
		List<EvlTargetListDto> list = usptEvlCmitDao.selectEvlTargetList(param);
		CorePagination<EvlTargetListDto> pagination = new CorePagination<>(info, list);
		return pagination;
	}


	/**
	 * 평가진행 엑셀 다운로드 목록 조회
	 * @param param
	 * @return
	 */
	public List<EvlTargetListDto> getEvlTargetListExcel(EvlTargetListParam param) {
		BnMember worker = SecurityUtils.checkWorkerIsInsider();
		param.setInsiderId(worker.getMemberId());
		long totalItems = usptEvlCmitDao.selectEvlTargetListCount(param);
		CorePaginationInfo info = new CorePaginationInfo(1L, totalItems, totalItems);
		param.setBeginRowNum(info.getBeginRowNum());
		param.setItemsPerPage(totalItems);
		return usptEvlCmitDao.selectEvlTargetList(param);
	}

	/**
	 * 온라인평가 기본정보 조회
	 * @param evlCmitId
	 * @return
	 */
	public EvlTargetListDto getEvlBasicInfo(String evlCmitId) {
		BnMember worker = SecurityUtils.checkWorkerIsInsider();
		EvlTargetListParam param = new EvlTargetListParam();
		param.setInsiderId(worker.getMemberId());
		param.setEvlCmitId(evlCmitId);
		param.setBeginRowNum(1L);
		param.setItemsPerPage(ITEMS_PER_PAGE);
		List<EvlTargetListDto> list = usptEvlCmitDao.selectEvlTargetList(param);
		if(list == null || list.size() == 0) {
			throw new InvalidationException("요청하신 정보에 해당되는 평가위원회 정보가 없습니다.");
		}
		return list.get(0);
	}


	/**
	 * 평가위원 출석표 목록 조회
	 * @param evlCmitId
	 * @return
	 */
	public List<EvlAtendListDto> getEvlAtendList(String evlCmitId) {
		return usptEvlMfcmmDao.selectEvlAtendList(evlCmitId);
	}


	/**
	 * 위원장 설정
	 * @param uemInfo
	 */
	public void modifyCharmn(UsptEvlMfcmm uemInfo) {
		BnMember worker = SecurityUtils.checkWorkerIsInsider();
		UsptEvlMfcmm info = usptEvlMfcmmDao.select(uemInfo);
		if(info == null) {
			throw new InvalidationException("요청하신 정보에 해당되는 평가위원 정보가 없습니다.");
		}

		Date now = new Date();
		UsptEvlMfcmm orgCharmnInfo = usptEvlMfcmmDao.selectCharmn(uemInfo.getEvlCmitId());
		if(orgCharmnInfo != null) {
			orgCharmnInfo.setCharmn(false);
			orgCharmnInfo.setUpdatedDt(now);
			orgCharmnInfo.setUpdaterId(worker.getMemberId());
			usptEvlMfcmmDao.updateCharmn(orgCharmnInfo);
		}

		info.setCharmn(true);
		info.setUpdatedDt(now);
		info.setUpdaterId(worker.getMemberId());
		if(usptEvlMfcmmDao.updateCharmn(info) != 1) {
			throw new InvalidationException(String.format(Code.validateMessage.DB오류, "수정"));
		}
	}


	/**
	 * 출석상태 수정
	 * @param uemInfo
	 */
	public void modifyAtendSttus(UsptEvlMfcmm uemInfo) {
		BnMember worker = SecurityUtils.checkWorkerIsInsider();
		UsptEvlMfcmm info = usptEvlMfcmmDao.select(uemInfo);
		if(info == null) {
			throw new InvalidationException("요청하신 정보에 해당되는 평가위원 정보가 없습니다.");
		}

		if(CoreUtils.string.equals(Code.atendSttus.불참, uemInfo.getAtendSttusCd())) {
			if(!string.equals(info.getAtendSttusCd(), Code.atendSttus.대기중)) {
				throw new InvalidationException("대기중인 건만 불참 처리가 가능합니다.");
			}
			uemInfo.setEvasResnCn(info.getEvasResnCn());
		}

		if(CoreUtils.string.equals(Code.atendSttus.회피, uemInfo.getAtendSttusCd())) {
			if(CoreUtils.string.isBlank(uemInfo.getEvasResnCn())){
				throw new InvalidationException(String.format(Code.validateMessage.입력없음, "회피사유"));
			}
			if(info.getEvasAgreAt() == null || !info.getEvasAgreAt()) {
				throw new InvalidationException("회피 동의한 경우만 회피 처리가 가능합니다.");
			}
			if(!string.equals(info.getAtendSttusCd(), Code.atendSttus.대기중) &&  !string.equals(info.getAtendSttusCd(), Code.atendSttus.출석)){
				throw new InvalidationException("대기중 또는 출석인 건만 회피 처리가 가능합니다.");
			}
		}

		Date now = new Date();
		uemInfo.setUpdatedDt(now);
		uemInfo.setUpdaterId(worker.getMemberId());
		if(usptEvlMfcmmDao.updateAtendSttus(uemInfo) != 1) {
			throw new LoggableException(String.format(Code.validateMessage.DB오류, "수정"));
		}
	}


	/**
	 * 출석상태 취소 수정
	 * @param uemInfo
	 */
	public void modifyAtendSttusCancel(UsptEvlMfcmm uemInfo) {
		BnMember worker = SecurityUtils.checkWorkerIsInsider();
		UsptEvlMfcmm info = usptEvlMfcmmDao.select(uemInfo);
		if(info == null) {
			throw new InvalidationException("요청하신 정보에 해당되는 평가위원 정보가 없습니다.");
		}

		if(CoreUtils.string.equals(Code.atendSttus.불참, uemInfo.getAtendSttusCd())) {
			if(!string.equals(info.getAtendSttusCd(), Code.atendSttus.불참)) {
				throw new InvalidationException("불참 상태가 아닌 평가위원입니다.");
			}
			uemInfo.setEvasResnCn(info.getEvasResnCn());
		}

		if(CoreUtils.string.equals(Code.atendSttus.회피, uemInfo.getAtendSttusCd())) {
			if(!string.equals(info.getAtendSttusCd(), Code.atendSttus.회피)) {
				throw new InvalidationException("회피 상태가 아닌 평가위원입니다.");
			}
			uemInfo.setEvasResnCn("");
		}

		Date now = new Date();
		uemInfo.setUpdatedDt(now);
		uemInfo.setUpdaterId(worker.getMemberId());
		uemInfo.setAtendSttusCd(Code.atendSttus.대기중);
		if(usptEvlMfcmmDao.updateAtendSttus(uemInfo) != 1) {
			throw new LoggableException(String.format(Code.validateMessage.DB오류, "수정"));
		}
	}

	/**
	 * 회피사유 조회
	 * @param uemInfo
	 * @return
	 */
	public EvasResnCnDto getEvasResnCn(UsptEvlMfcmm uemInfo){
		SecurityUtils.checkWorkerIsInsider();
		UsptEvlMfcmm info = usptEvlMfcmmDao.select(uemInfo);
		if(info == null) {
			throw new InvalidationException("요청하신 정보에 해당되는 평가위원 정보가 없습니다.");
		}
		if(!string.equals(info.getAtendSttusCd(), Code.atendSttus.회피)) {
			throw new InvalidationException("회피 상태가 아닌 평가위원입니다.");
		}

		UsptExpert expertInfo = usptExpertDao.selectMfcmmDetail(info.getExtrcMfcmmId());
		EvasResnCnDto dto = new EvasResnCnDto();
		dto.setEvasResnCn(info.getEvasResnCn());
		dto.setEvlMfcmmNm(expertInfo.getExpertNm());
		return dto;
	}

	/**
	 *  회피사유 저장
	 * @param uemInfo
	 */
	public void modifyEvasResnCn(UsptEvlMfcmm uemInfo){
		BnMember worker = SecurityUtils.checkWorkerIsInsider();
		UsptEvlMfcmm info = usptEvlMfcmmDao.select(uemInfo);
		if(info == null) {
			throw new InvalidationException("요청하신 정보에 해당되는 평가위원 정보가 없습니다.");
		}
		if(string.isBlank(uemInfo.getEvasResnCn())) {
			throw new InvalidationException(String.format(Code.validateMessage.입력없음, "회피 사유"));
		}

		if(!string.equals(info.getAtendSttusCd(), Code.atendSttus.회피)) {
			throw new InvalidationException("회피 상태가 아니라서 회피사유를 저장할 수 없습니다.");
		}

		Date now = new Date();
		uemInfo.setUpdatedDt(now);
		uemInfo.setUpdaterId(worker.getMemberId());
		uemInfo.setAtendSttusCd(info.getAtendSttusCd());
		if(usptEvlMfcmmDao.updateAtendSttus(uemInfo) != 1) {
			throw new LoggableException(String.format(Code.validateMessage.DB오류, "수정"));
		}
	}


	/**
	 * 평가현황 목록 조회
	 * @param evlCmitId
	 * @return
	 */
	public List<EvlSttusListDto> getEvlSttusList(String evlCmitId) {
		SecurityUtils.checkWorkerIsInsider();
		EvlTargetListDto dto = this.getEvlBasicInfo(evlCmitId);
		if(dto == null) {
			throw new InvalidationException("요청하신 정보에 해당되는 평가위원회 정보가 없습니다.");
		}
		return usptEvlCmitDao.selectEvlSttusList(evlCmitId);
	}


	/**
	 * 가감점수 목록 조회
	 * @param param
	 * @return
	 */
	public PointListDto getPointList(PointListParam param) {
		SecurityUtils.checkWorkerIsInsider();
		EvlTargetListDto dto = this.getEvlBasicInfo(param.getEvlCmitId());
		if(dto == null) {
			throw new InvalidationException("요청하신 정보에 해당되는 평가위원회 정보가 없습니다.");
		}
		if(CoreUtils.string.isBlank(param.getEvlTrgetId())) {
			throw new InvalidationException("평가대상ID를 입력하세요.");
		}

		UsptEvlTrget usptEvlTrget = usptEvlTrgetDao.select(param.getEvlTrgetId());
		UsptBsnsPblancApplcnt applcnt = usptBsnsPblancApplcntDao.select(usptEvlTrget.getApplyId());

		EvlTableDto evltableDto = new EvlTableDto();
		evltableDto.setMemberNm(applcnt.getMemberNm());
		evltableDto.setReceiptNo(applcnt.getReceiptNo());

		PointListDto pointDto = new PointListDto();
		pointDto.setMemberNm(applcnt.getMemberNm());
		pointDto.setReceiptNo(applcnt.getReceiptNo());

		List<EvlPointListDto> list = usptEvlCmitDao.selectEvlPointList(param);
		pointDto.setPointList(list);
		if(list != null && list.size() > 0) {
			pointDto.setAdsbtrResnCn(list.get(0).getAdsbtrResnCn());
			pointDto.setEvlMthdCd(list.get(0).getEvlMthdCd());
		}
		return pointDto;
	}


	/**
	 * 가감점수 저장
	 * @param evlCmitId
	 * @param evlTrgetId
	 * @param param
	 */
	public void modifyPoint(String evlCmitId, String evlTrgetId, PointParam param) {
		BnMember worker = SecurityUtils.checkWorkerIsInsider();
		EvlTargetListDto dto = this.getEvlBasicInfo(evlCmitId);
		if(dto == null) {
			throw new InvalidationException("요청하신 정보에 해당되는 평가위원회 정보가 없습니다.");
		}

		if(!CoreUtils.string.equals(dto.getOrgnzrId(), worker.getMemberId())) {
			throw new InvalidationException("평가위원회의 간사만 저장할 수 있습니다.");
		}

		List<UsptEvlIemResultAdsbtr> resultList = param.getResultList();
		if(resultList == null || resultList.size() == 0) {
			throw new InvalidationException("정보가 입력되지 않았습니다.");
		}

		Date now = new Date();
		for(UsptEvlIemResultAdsbtr regInfo : resultList) {
			regInfo.setCreatedDt(now);
			regInfo.setCreatorId(worker.getMemberId());
			regInfo.setUpdatedDt(now);
			regInfo.setUpdaterId(worker.getMemberId());
			regInfo.setAdsbtrResnCn(param.getAdsbtrResnCn());
			regInfo.setEvlTrgetId(evlTrgetId);
			regInfo.setEvlCmitId(evlCmitId);

			if(CoreUtils.string.isBlank(regInfo.getEvlIemResultAdsbtrId())) {
				regInfo.setEvlIemResultAdsbtrId(CoreUtils.string.getNewId(Code.prefix.평가항목결과가감));
				usptEvlIemResultAdsbtrDao.insert(regInfo);
			} else {
				if(usptEvlIemResultAdsbtrDao.update(regInfo) != 1) {
					throw new InvalidationException(String.format(Code.validateMessage.DB오류, "수정"));
				}
			}
		}
	}


	/**
	 * 평가표 조회
	 * @param param
	 * @return
	 */
	public EvlTableDto getEvlTable(EvlTableParam param) {
		SecurityUtils.checkWorkerIsInsider();
		EvlTargetListDto dto = this.getEvlBasicInfo(param.getEvlCmitId());
		if(dto == null) {
			throw new InvalidationException("요청하신 정보에 해당되는 평가위원회 정보가 없습니다.");
		}

		if(CoreUtils.string.isBlank(param.getEvlTrgetId())) {
			throw new InvalidationException("평가대상ID를 입력해주세요.");
		}
		if(CoreUtils.string.isBlank(param.getEvlMfcmmId())) {
			throw new InvalidationException("평가위원ID를 입력해주세요.");
		}

		UsptEvlMfcmm evlMfcmm = new UsptEvlMfcmm();
		evlMfcmm.setEvlMfcmmId(param.getEvlMfcmmId());
		evlMfcmm = usptEvlMfcmmDao.select(evlMfcmm);
		UsptEvlTrget usptEvlTrget = usptEvlTrgetDao.select(param.getEvlTrgetId());
		UsptBsnsPblancApplcnt applcnt = usptBsnsPblancApplcntDao.select(usptEvlTrget.getApplyId());

		EvlTableDto evltableDto = new EvlTableDto();
		evltableDto.setMemberNm(applcnt.getMemberNm());
		evltableDto.setReceiptNo(applcnt.getReceiptNo());
		evltableDto.setEvlMfcmmNm(evlMfcmm.getEvlMfcmmNm());

		List<EvlTableListDto> list = usptEvlCmitDao.selectEvlTableList(param);
		evltableDto.setList(list);
		if(list != null && list.size() > 0) {
			evltableDto.setEvlMthdCd(list.get(0).getEvlMthdCd());
			evltableDto.setEvlOpinion(list.get(0).getEvlOpinion());
		}

		return evltableDto;
	}


	/**
	 * 위원장의견 조회
	 * @param evlCmitId
	 * @return
	 */
	public EvlCharmnOpinionDto getEvlCharmnOpinion(String evlCmitId) {
		SecurityUtils.checkWorkerIsInsider();
		EvlTargetListDto dto = this.getEvlBasicInfo(evlCmitId);
		if(dto == null) {
			throw new InvalidationException("요청하신 정보에 해당되는 평가위원회 정보가 없습니다.");
		}

		UsptEvlCmit evlCmit = usptEvlCmitDao.select(evlCmitId);
		EvlCharmnOpinionDto ecoDto = new EvlCharmnOpinionDto();
		ecoDto.setEvlCharmnOpinionCn(evlCmit.getEvlCharmnOpinionCn());
		return ecoDto;
	}



	/**
	 * 평가결과 조회
	 * @param evlCmitId
	 * @return
	 */
	public EvlResultDto getEvlResult(String evlCmitId) {
		SecurityUtils.checkWorkerIsInsider();
		EvlTargetListDto dto = this.getEvlBasicInfo(evlCmitId);
		if(dto == null) {
			throw new InvalidationException("요청하신 정보에 해당되는 평가위원회 정보가 없습니다.");
		}

		EvlResultDto resultDto = new EvlResultDto();
		resultDto.setResultList(usptEvlCmitDao.selectEvlResultList(evlCmitId));

		UsptEvlCmit evlCmit = usptEvlCmitDao.select(evlCmitId);
		if(CoreUtils.string.isBlank(evlCmit.getAttachmentGroupId())) {
			resultDto.setAttachmentList(attachmentService.getFileInfos_group(evlCmit.getAttachmentGroupId()));
		}

		return resultDto;
	}



	/**
	 * 선정/탈락 처리
	 * @param evlCmitId
	 * @param evlTrgetList
	 * @param slctn
	 */
	public void modifyEvlSlctn(String evlCmitId, List<EvlTrgetIdParam> evlTrgetList, Boolean slctn) {
		BnMember worker = SecurityUtils.checkWorkerIsInsider();
		EvlTargetListDto dto = this.getEvlBasicInfo(evlCmitId);
		if(dto == null) {
			throw new InvalidationException("요청하신 정보에 해당되는 평가위원회 정보가 없습니다.");
		}
		if(evlTrgetList == null || evlTrgetList.size() == 0) {
			throw new InvalidationException("평가대상 목록을 입력해주세요.");
		}
		Date now = new Date();
		UsptEvlTrget evlTrget = new UsptEvlTrget();
		evlTrget.setSlctn(slctn);
		evlTrget.setSlctnDt(now);
		evlTrget.setUpdatedDt(now);
		evlTrget.setUpdaterId(worker.getMemberId());
		for(EvlTrgetIdParam evlTrgetIdInfo : evlTrgetList) {
			evlTrget.setEvlTrgetId(evlTrgetIdInfo.getEvlTrgetId());
			if(usptEvlTrgetDao.updateSlctn(evlTrget) != 1) {
				throw new InvalidationException(String.format(Code.validateMessage.DB오류, "수정"));
			}
		}
	}


	public void modifyEvlCompt(String evlCmitId) {
		BnMember worker = SecurityUtils.checkWorkerIsInsider();
		EvlTargetListDto dto = this.getEvlBasicInfo(evlCmitId);
		if(dto == null) {
			throw new InvalidationException("요청하신 정보에 해당되는 평가위원회 정보가 없습니다.");
		}


		/*
		 * 	최초 평가완료 처리할 경우
		 *  1. 평가위원회 - 평가진행상태코드(G:EVL_STTUS) - 평가완료 처리
		 *  2. 평가단계 - 단계별평가진행상태코드(G:EVL_STEP_STTUS) - 평가완료 처리
		 *  3. 평가단계 - 다음단계의 평가단계에 선정된 대상자를 평가대상에 insert 처리
		 *  4. 평가단계가 마지막이었을경우 최종선정대상 테이블에 insert 처리
		 */






		/*
		 * 이미 평가완료 처리가 되어 있을 경우
		 * 1. 평가단계가 마지막이었을경우
		 *
		 *  - 다음단계의 평가단계의 상태가 대기중인지 확인
		 *   - 대기중이 아닐 경우 수정할 수 없음.
		 *   - 대기중일 경우
		 *     1. 평가단계

		*/




	}
















	//평가결과항목별상세(3가지 공통)
	public EvlResultBasicInfoDto getEvlResultIemDetail(String evlResultId){
		EvlResultBasicInfoDto returnInfo = usptEvlOnlineDao.selectEvlResultBasicInfo(evlResultId);//평가표 결과 상세 기본정보 조회

		if (returnInfo == null) {
			throw new InvalidationException("상세 결과 정보가 없습니다.");
		}

		List<EvlResultDetailByIemDto> detailList = usptEvlOnlineDao.selectEvlResultIemDetail(evlResultId);//항목 결과 리스트 조회

		if(detailList.size() > 0) {
			returnInfo.setEvlResultDetailByIemDtoList(detailList);
		}

		return returnInfo;
	}

	//평가결과항목별 결과 등록 - 오프라인용(3가지 공통)
	public String addEvlResultIemDetail(EvlResultBasicInfoDto evlResultBasicInfoDto){
		Date now = new Date();

		BnMember worker = SecurityUtils.checkWorkerIsInsider();

		//1.위원정보 확인
//		UsptEvlMfcmm curUsptEvlMfcmm = usptEvlOnlineDao.select(evlResultBasicInfoDto.getEvlMfcmmId()); //위원정보
//
//		if(curUsptEvlMfcmm == null) {
//			throw new InvalidationException(String.format(Code.validateMessage.조회결과없음, "위원 정보"));
//		}

		//2.대상자 정보 확인
		UsptEvlTrget usptEvlTrget = usptEvlTrgetDao.select(evlResultBasicInfoDto.getEvlTrgetId());

		if(usptEvlTrget == null) {
			throw new InvalidationException("대상자 정보가 없습니다.");
		}

		//3.평가결과 등록(평가위원별)
		UsptEvlMfcmmResult usptEvlMfcmmResult = new UsptEvlMfcmmResult();

		usptEvlMfcmmResult.setEvlMfcmmId(evlResultBasicInfoDto.getEvlMfcmmId());//위원id
		usptEvlMfcmmResult.setEvlTrgetId(evlResultBasicInfoDto.getEvlTrgetId());//대상id

		//기등록 확인
		List<UsptEvlMfcmmResult> regList  = usptEvlMfcmmResultDao.selectList(usptEvlMfcmmResult);

		if(regList.size() > 0) {
			throw new InvalidationException("이미 등록되었습니다.");
		}

		String evlResultKey  = string.getNewId("evlresult-");

		usptEvlMfcmmResult.setEvlResultId(evlResultKey); //키
		usptEvlMfcmmResult.setEvlOpinion(evlResultBasicInfoDto.getEvlOpinion());//위원별 평가의견

		usptEvlMfcmmResult.setMfcmmEvlSttusCd(Code.mfcmmEvlSttus.평가완료);//위원평가상태코드 평가완료
		usptEvlMfcmmResult.setMfcmmEvlSttusDt(now);

		usptEvlMfcmmResult.setCreatedDt(now);
		usptEvlMfcmmResult.setCreatorId(worker.getMemberId());

		int insertCnt = 0;

		try {
			insertCnt = usptEvlMfcmmResultDao.insert(usptEvlMfcmmResult);
        } catch (Exception e) {
            throw new InvalidationException(e.toString());
        } finally{
			if (insertCnt < 0) {
				throw new LoggableException(String.format(Code.validateMessage.DB오류, "저장"));
			}
		}

		//4.평가항목 결과 등록(항목별)
		List<EvlResultDetailByIemDto> evlResultDetailByIemDtoList = evlResultBasicInfoDto.getEvlResultDetailByIemDtoList();

		for(EvlResultDetailByIemDto insertDetailInfo : evlResultDetailByIemDtoList) {
			UsptEvlIemResult usptEvlIemResult = new UsptEvlIemResult();

			usptEvlIemResult.setEvlIemResultId(string.getNewId("evliemresult-")); //키셋팅
			usptEvlIemResult.setEvlResultId(evlResultKey); //평가결과키

			usptEvlIemResult.setEvlIemId(insertDetailInfo.getEvlIemId()); //평가결과키
			usptEvlIemResult.setEvlScore(insertDetailInfo.getEvlScore()); //평가점수
			usptEvlIemResult.setEvlCn(insertDetailInfo.getEvlCn()); //평가내용

			usptEvlIemResult.setCreatedDt(now);
			usptEvlIemResult.setCreatorId(worker.getMemberId());

			int insertCnt2 = usptEvlIemResultDao.insert(usptEvlIemResult);

			if (insertCnt2 < 0) {
				throw new LoggableException(String.format(Code.validateMessage.DB오류, "저장"));
			}
		}

		return evlResultKey;
	}

	//평가결과항목별 결과 수정 - 오프라인용(3가지 공통)
	public void modifyEvlResultIemDetail(EvlResultBasicInfoDto evlResultBasicInfoDto){
		Date now = new Date();

		BnMember worker = SecurityUtils.checkWorkerIsInsider();

		//1.평가결과(평가위원별 존재 여부 확인)
		UsptEvlMfcmmResult paramInfo = new UsptEvlMfcmmResult();

		paramInfo.setEvlResultId(evlResultBasicInfoDto.getEvlResultId());//평가결과 ID

		List<UsptEvlMfcmmResult> usptEvlMfcmmResultList = usptEvlMfcmmResultDao.selectList(paramInfo);

		if(usptEvlMfcmmResultList.size() < 1) {
			throw new InvalidationException(String.format(Code.validateMessage.조회결과없음, "수정할 평가 결과"));
		}

		//2.평가항목결과 존재여부 확인
		List<EvlResultDetailByIemDto> insertChkList = evlResultBasicInfoDto.getEvlResultDetailByIemDtoList();

		for(EvlResultDetailByIemDto insertInfo : insertChkList) {
			UsptEvlIemResult paramInfo2 = new UsptEvlIemResult();

			if(string.isBlank(insertInfo.getEvlIemResultId())){
				throw new InvalidationException(String.format(Code.validateMessage.조회결과없음, "수정할 평가 항목 결과"));
			}else {
				paramInfo2.setEvlIemResultId(insertInfo.getEvlIemResultId());

				List<UsptEvlIemResult> usptEvlIemResultList  = usptEvlIemResultDao.selectList(paramInfo2);

				if(usptEvlIemResultList.size() < 1) {
					throw new InvalidationException(String.format(Code.validateMessage.조회결과없음, "수정할 평가 항목 결과"));
				}
			}
		}

		//3.평가결과 수정(평가위원별)
		UsptEvlMfcmmResult usptEvlMfcmmResult = new UsptEvlMfcmmResult();

		usptEvlMfcmmResult.setEvlResultId(evlResultBasicInfoDto.getEvlResultId()); //키
		usptEvlMfcmmResult.setEvlOpinion(evlResultBasicInfoDto.getEvlOpinion());//위원별 평가의견
		usptEvlMfcmmResult.setMfcmmEvlSttusCd(Code.mfcmmEvlSttus.평가완료);//위원평가상태코드 평가완료
		usptEvlMfcmmResult.setMfcmmEvlSttusDt(now);

		usptEvlMfcmmResult.setUpdatedDt(now);
		usptEvlMfcmmResult.setUpdaterId(worker.getMemberId());

		int updateInt = 0;

		try {
			updateInt = usptEvlMfcmmResultDao.update(usptEvlMfcmmResult);
        } catch (Exception e) {
            throw new InvalidationException(e.toString());
        } finally{
			if (updateInt < 0) {
				throw new LoggableException(String.format(Code.validateMessage.DB오류, "저장"));
			}
		}

		//4.평가항목 결과 수정(항목별)
		List<EvlResultDetailByIemDto> insertList = evlResultBasicInfoDto.getEvlResultDetailByIemDtoList();

		for(EvlResultDetailByIemDto insertInfo : insertList) {
			UsptEvlIemResult usptEvlIemResult = new UsptEvlIemResult();

			usptEvlIemResult.setEvlIemResultId(insertInfo.getEvlIemResultId()); //평가항목결과키
			usptEvlIemResult.setEvlScore(insertInfo.getEvlScore()); //평가점수
			usptEvlIemResult.setEvlCn(insertInfo.getEvlCn()); //평가내용

			usptEvlIemResult.setUpdatedDt(now);
			usptEvlIemResult.setUpdaterId(worker.getMemberId());

			int updateCnt2 = usptEvlIemResultDao.update(usptEvlIemResult);

			if (updateCnt2 < 0) {
				throw new LoggableException(String.format(Code.validateMessage.DB오류, "저장"));
			}
		}

	}


	//평가결과 가감점부여 상세조회(3가지 공통)
	public EvlResultAddAllotBasicDto getEvlResultAddAllotDetail(EvlResultAddAllotBasicDto inputParam){
		EvlResultAddAllotBasicDto returnInfo = usptEvlOnlineDao.selectEvlResultAddAllotBasicInfo(inputParam);//평가표 결과 상세 기본정보 조회(가감점용)

		if (returnInfo == null) {
			throw new InvalidationException("상세 결과 정보가 없습니다.");
		}

		List<EvlResultDetailByIemDto> detailList = usptEvlOnlineDao.selectEvlResultAddAllotDetail(inputParam);//배점형 항목 결과 리스트 조회

		if(detailList.size() > 0) {
			returnInfo.setEvlResultDetailByIemDtoList(detailList);
		}

		return returnInfo;
	}


	//가감점 부여 수정(3가지 공통)
	public void modifyEvlResultAddAllotDetail(EvlResultAddAllotBasicDto paramDto){
		Date now = new Date();

		BnMember worker = SecurityUtils.checkWorkerIsInsider();

		//접근자가 해당 위원회의 간사인지 확인 체크
		if( !string.equals(usptEvlCmitDao.select(paramDto.getEvlCmitId()).getOrgnzrId(), worker.getMemberId()) ){
			log.debug("worker.getMemberId() = "+worker.getMemberId());
			throw new InvalidationException("해당 위원회의 간사가 아닙니다.");
		}

		EvlResultAddAllotBasicDto basicInfo = usptEvlOnlineDao.selectEvlResultAddAllotBasicInfo(paramDto);//평가표 결과 상세 기본정보 조회(가감점용)

		if (basicInfo == null) {
			throw new InvalidationException("저장할 대상 정보가 없습니다.");
		}

		String _flag;

		for(EvlResultDetailByIemDto inputInfo : paramDto.getEvlResultDetailByIemDtoList()) {
			_flag = inputInfo.getFlag();

			if (string.equals(_flag, "U") ) {
				String keyVal = inputInfo.getEvlIemResultAdsbtrId();

				if(string.isBlank(keyVal) ) {
					keyVal = string.getNewId("evlresultadsbtr-");
					inputInfo.setEvlIemResultAdsbtrId(keyVal);//key 값 세팅
				}else {
					if(usptEvlOnlineDao.selectValidKeyByAddAllot(keyVal) < 1) {
						throw new InvalidationException("잘못된 평가 항목 정보입니다.");
					}
				}

				inputInfo.setAdsbtrResnCn(paramDto.getAdsbtrResnCn()); //가감사유내용
				inputInfo.setEvlTrgetId(paramDto.getEvlTrgetId());
				inputInfo.setEvlCmitId(paramDto.getEvlCmitId());

				inputInfo.setUpdatedDt(now);
				inputInfo.setUpdaterId(worker.getMemberId());
				inputInfo.setCreatedDt(now);
				inputInfo.setCreatorId(worker.getMemberId());

				int updateCnt = usptEvlOnlineDao.updateEvlResultAddMinus(inputInfo);

				log.debug("updateCnt = {}", updateCnt);

				if(updateCnt < 1) { //TODO : (임병진) merge문으로 할경우 update 건수가 리턴되지 않음.
					//throw new LoggableException(String.format(Code.validateMessage.DB오류, "저장"));
				}
			}
		}

	}


	//온라인 평가진행-평가결과 상세
	public OnlineEvlResutlDto getEvlOnlineEvlResultDetail(String evlCmitId){
		UsptEvlCmitExtrcTarget basicInfo = usptEvlCmitDao.selectEvlCmitExtrcTarget(evlCmitId);//상단정보

		if (basicInfo == null) {
			throw new InvalidationException("위원회 정보가 없습니다.");
		}

		OnlineEvlResutlDto returnInfo = new OnlineEvlResutlDto();

		//상단기본정보 세팅
		returnInfo.setEvlCmitId(basicInfo.getEvlCmitId());
		returnInfo.setEvlCmitNm(basicInfo.getEvlCmitNm());
		returnInfo.setPblancNm(basicInfo.getPblancNm());
		returnInfo.setEvlTypeNm(basicInfo.getEvlTypeNm());
		returnInfo.setEvlStepNm(basicInfo.getEvlStepNm());
		returnInfo.setSectNm(basicInfo.getSectNm());
		returnInfo.setOrdtmRcritNm(basicInfo.getOrdtmRcritNm());
		returnInfo.setRceptOdr(basicInfo.getRceptOdr());


		//하단 점수형 평가결과 정보
		List<EvlResultByTargetListDto> targetList = usptEvlOnlineDao.selectEvlOnlineResultList(evlCmitId);

		for(EvlResultByTargetListDto targetInfo : targetList) {
			//TODO : (임병진) 쿼리에서 5점척도형인경우 총점 어떻게 구할지 확인 후 쿼리 수정 필요
			List<EvlResultByMfcmmListDto> detailInfoList = usptEvlOnlineDao.selectEvlResultByMfcmmList(targetInfo.getEvlTrgetId());//각 대상자별로 평가위원들의 평가점수 합계 구하기
			targetInfo.setEvlResultByMfcmmList(detailInfoList);
		}

		returnInfo.setEvlResultByTargetListDto(targetList);

		return returnInfo;
	}


	//온라인 평가진행-평가결과 동의서 팝업
	public OnlineEvlResutlAgreDto getEvlOnlineEvlReusltAgreDetail(String evlCmitId){
		UsptEvlCmitExtrcTarget basicInfo = usptEvlCmitDao.selectEvlCmitExtrcTarget(evlCmitId);//상단정보

		if (basicInfo == null) {
			throw new InvalidationException("위원회 정보가 없습니다.");
		}

		OnlineEvlResutlAgreDto returnInfo = new OnlineEvlResutlAgreDto();

		//1.상단기본정보 세팅
		returnInfo.setEvlCmitId(basicInfo.getEvlCmitId());
		returnInfo.setEvlCmitNm(basicInfo.getEvlCmitNm());
		returnInfo.setPblancNm(basicInfo.getPblancNm());
		returnInfo.setEvlTypeNm(basicInfo.getEvlTypeNm());
		returnInfo.setEvlStepNm(basicInfo.getEvlStepNm());
		returnInfo.setSectNm(basicInfo.getSectNm());
		returnInfo.setOrdtmRcritNm(basicInfo.getOrdtmRcritNm());
		returnInfo.setRceptOdr(basicInfo.getRceptOdr());
		returnInfo.setBsnsNm(basicInfo.getBsnsNm());

		//2.하단 점수형 평가결과 정보
		List<EvlResultByTargetListDto> targetList = usptEvlOnlineDao.selectEvlOnlineResultList(evlCmitId);

		for(EvlResultByTargetListDto targetInfo : targetList) {
			//TODO : (임병진) 쿼리에서 5점척도형인경우 총점 어떻게 구할지 확인 후 쿼리 수정 필요
			List<EvlResultByMfcmmListDto> detailInfoList = usptEvlOnlineDao.selectEvlResultByMfcmmList(targetInfo.getEvlTrgetId());//각 대상자별로 평가위원들의 평가점수 합계 구하기
			targetInfo.setEvlResultByMfcmmList(detailInfoList);
		}

		returnInfo.setEvlResultByTargetListDto(targetList);

		//3.평가위원 서명 리스트(구분, 이름, 소속기관)
		List<UsptEvlMfcmm> usptEvlMfcmmList = usptEvlMfcmmDao.selectList(evlCmitId);

		returnInfo.setUsptEvlMfcmmList(usptEvlMfcmmList);

		return returnInfo;
	}


	//탈락처리
	public void modifyEvlOnlineTrgetDrop(OnlineEvlResutlDto onlineEvlResutlDto){
		Date now = new Date();

		BnMember worker = SecurityUtils.checkWorkerIsInsider();

		UsptEvlCmit cmitInfo = usptEvlCmitDao.select(onlineEvlResutlDto.getEvlCmitId());

		if (cmitInfo == null) {
			throw new InvalidationException("위원회 정보가 없습니다.");
		}

		int updateCnt = 0;

		for(EvlResultByTargetListDto inputInfo : onlineEvlResutlDto.getEvlResultByTargetListDto()) {
			if (inputInfo.isCheck()) {
				if(usptEvlTrgetDao.select(inputInfo.getEvlTrgetId()) == null) {
					throw new InvalidationException("대상자 정보가 없습니다.");
				}

				inputInfo.setSlctn(false); //탈락
				inputInfo.setUpdatedDt(now);
				inputInfo.setUpdaterId(worker.getMemberId());
				inputInfo.setSlctnDt(now);

//				updateCnt = usptEvlTrgetDao.updateEvlOnlineTrgetDrop(inputInfo);
//
//				if(updateCnt < 1) {
//					throw new LoggableException(String.format(Code.validateMessage.DB오류, "처리"));
//				}
			}
		}
	}

	//탈락처리
	public void modifyEvlOnlineTrgetSltcn(OnlineEvlResutlDto onlineEvlResutlDto){
		Date now = new Date();

		BnMember worker = SecurityUtils.checkWorkerIsInsider();

		UsptEvlCmit cmitInfo = usptEvlCmitDao.select(onlineEvlResutlDto.getEvlCmitId());

		if (cmitInfo == null) {
			throw new InvalidationException("위원회 정보가 없습니다.");
		}

		int updateCnt = 0;

		for(EvlResultByTargetListDto inputInfo : onlineEvlResutlDto.getEvlResultByTargetListDto()) {
			if (inputInfo.isCheck()) {
				if(usptEvlTrgetDao.select(inputInfo.getEvlTrgetId()) == null) {
					throw new InvalidationException("대상자 정보가 없습니다.");
				}

				inputInfo.setSlctn(true); //선정
				inputInfo.setUpdatedDt(now);
				inputInfo.setUpdaterId(worker.getMemberId());
				inputInfo.setSlctnDt(now);

//				updateCnt = usptEvlTrgetDao.updateEvlOnlineTrgetDrop(inputInfo);
//
//				if(updateCnt < 1) {
//					throw new LoggableException(String.format(Code.validateMessage.DB오류, "처리"));
//				}
			}
		}
	}

	//온라인 평가진행-평가결과 상세
	public OnlineEvlResutlGnrlzDto getEvlOnlineEvlReusltGnrlz(String evlCmitId, String evlTrgetId){
		UsptEvlCmitExtrcTarget basicInfo = usptEvlCmitDao.selectEvlCmitExtrcTarget(evlCmitId);//상단정보

		if (basicInfo == null) {
			throw new InvalidationException("위원회 정보가 없습니다.");
		}

		OnlineEvlResutlGnrlzDto returnInfo = new OnlineEvlResutlGnrlzDto();

		//상단기본정보 세팅
		returnInfo.setEvlCmitId(basicInfo.getEvlCmitId());
		returnInfo.setEvlCmitNm(basicInfo.getEvlCmitNm());
		returnInfo.setPblancNm(basicInfo.getPblancNm());
		returnInfo.setEvlTypeNm(basicInfo.getEvlTypeNm());
		returnInfo.setEvlStepNm(basicInfo.getEvlStepNm());
		returnInfo.setSectNm(basicInfo.getSectNm());
		returnInfo.setOrdtmRcritNm(basicInfo.getOrdtmRcritNm());
		returnInfo.setRceptOdr(basicInfo.getRceptOdr());


		//하단 점수형 평가결과 정보

		List<EvlIemByCmitDto> iemList = usptEvlIemDao.selectByCmitList(evlCmitId);

		for(EvlIemByCmitDto iemInfo : iemList) {
			EvlResultGnrlzDto paramInfo = new EvlResultGnrlzDto();

			paramInfo.setEvlCmitId(evlCmitId);
			paramInfo.setEvlTrgetId(evlTrgetId);
			paramInfo.setEvlIemId(iemInfo.getEvlIemId());

			List<EvlResultGnrlzDto> detailInfoList = usptEvlOnlineDao.selectEvlOnlineEvlReusltGnrlz(paramInfo);//각 대상자별로 평가위원들의 평가점수 합계 구하기
			iemInfo.setEvlResultGnrlzDtoList(detailInfoList);
		}

		returnInfo.setEvlIemByCmitDtoList(iemList);

		return returnInfo;
	}


	//통보대상팝업 조회
	public EvlResutlDspthInfoDto getEvlReusltDspthInfo(String evlCmitId){
		UsptEvlCmit basicInfo = usptEvlCmitDao.select(evlCmitId);

		if (basicInfo == null) {
			throw new InvalidationException("위원회 정보가 없습니다.");
		}

		EvlResutlDspthInfoDto resultInfo = usptEvlOnlineDao.selectEvlReusltDspthInfo(evlCmitId);

		if (resultInfo == null) {
			throw new InvalidationException("선정된 통보대상자 없습니다.");
		}

		return resultInfo;
	}


	//통보처리
	public EvlResutlDspthInfoDto modifyEvlReusltDspthInfo(EvlResutlDspthInfoDto paramDto){
		String evlCmitId = paramDto.getEvlCmitId();

		UsptEvlCmit basicInfo = usptEvlCmitDao.select(evlCmitId);

		if (basicInfo == null) {
			throw new InvalidationException("위원회 정보가 없습니다.");
		}

		EvlResutlDspthInfoDto resultInfo = usptEvlOnlineDao.selectEvlReusltDspthInfo(evlCmitId);

		if (resultInfo == null) {
			throw new InvalidationException("선정된 통보대상자 없습니다.");
		}

		//임시 주석 처리
//		if (!string.equals(resultInfo.getEvlSttusCd(), Code.evlSttus.평가완료)) {
//			throw new InvalidationException("평가완료 상태의 건만 통보처리가 가능합니다.");
//		}

		//위원회에 해당하는 선정대상자 select
		List<EvlResutlSndngTrgetDto> sndngTrgetList = usptEvlOnlineDao.selectSndngTrgetList(evlCmitId);

		EmailSendParam esp = new EmailSendParam();
		//발송 정보 업데이트
		UsptEvlCmit usptEvlCmit = new UsptEvlCmit();

		//실패사유
		StringBuilder resultMsg = new StringBuilder();

		if( string.equals(paramDto.getSndngMth(), Code.sndngMth.이메일) ) {
			for(EvlResutlSndngTrgetDto sndngTrgetInfo : sndngTrgetList) {
				if(!string.isEmpty(sndngTrgetInfo.getEncEmail())){
					String email = string.trim(sndngTrgetInfo.getEmail());
					log.debug("email = " + email);
					if(string.isBlank(email)) {
						email = "bjim@dadfa.ddd"; //임시로 처리
					}else {
						email = "bjim@brainednet.com"; //임시로 처리
					}

					String emailFilePath = "/form/email/email-common.html";
					String emailCn = CoreUtils.file.readFileString(emailFilePath);

					if (emailCn == null) {
						throw new LoggableException("이메일 본문파일을 읽을 수 없습니다.");
					}

					esp.setContentHtml(true);
					esp.setEmailCn(emailCn);
					esp.setTitle("선정공고 안내");
					Map<String, String> templateParam = new HashMap<>();
					templateParam.put("memNm", sndngTrgetInfo.getMemberNm());//회원명
					templateParam.put("cnVal", paramDto.getSndngCn());//발송내용
					esp.addRecipient(email, sndngTrgetInfo.getMemberNm(), templateParam);
//					esp.addRecipient("0207-1219@daum.net", sndngTrgetInfo.getMemberNm(), templateParam);
				}
			}

	    	EmailResult er = emailUtils.send(esp);

	    	if (er.getFailCnt() == esp.getRecipientList().size()) {
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

//	    	usptEvlCmit.setEmailId(er.getEmailId());

			log.debug("returnVal email = "+er.toString());

		} else if( string.equals(paramDto.getSndngMth(), Code.sndngMth.SMS) ) {
			SmsSendParam param = new SmsSendParam();

			for(EvlResutlSndngTrgetDto sndngTrgetInfo : sndngTrgetList) {
				if(!string.isEmpty(sndngTrgetInfo.getEncMobileNo())){
					String mobileNo = string.trim(sndngTrgetInfo.getMobileNo());
					log.debug("mobileNo = " + mobileNo);
					if(string.isBlank(mobileNo)) {
						mobileNo = "010-8937111111";
					}else {
						mobileNo = "010-8937-7106";//임시로 처리
					}
					log.debug("mobileNo = " + mobileNo);
					String cnVal = paramDto.getSndngCn();//발송내용

					param.setSmsCn("AICA 선정공고 안내\n[##cnVal##]");

					// template Parameter 설정
					Map<String, String> tp = new HashMap<>();
					tp.put("cnVal", cnVal);
					param.addRecipient(mobileNo, tp);

				}
			}

			// SMS 발송
	        SmsResult sr = smsUtils.send(param);
	        List<SmsRecipientResult> recipientList = sr.getRecipientList();

	        // 모두 발송실패일 경우, 오류처리
	        if (sr.getFailCnt() == param.getRecipientList().size()) {
	        	throw new InvalidationException("SMS발송 실패");
	        }

	        //실패 사유를 메세지로 전송하기 위함
	        if(sr.getFailCnt() > 0) {
		        for(SmsRecipientResult recipientInfo : recipientList) {
		        	if(!recipientInfo.getIsSuccessful()) {
		        		resultMsg.append("수신번호 : ").append(recipientInfo.getRecipientNo()).append("는 발송 실패되었습니다.\n 실패사유 :").append(recipientInfo.getResultMessage()).append("\n");
		        	}
		        }
	        }

//	        usptEvlCmit.setSmsId(sr.getSmsId());

	        log.info(sr.toString());
		}

		//실패 사유 세팅
		resultInfo.setResultMsg(resultMsg.toString());

		usptEvlCmit.setEvlCmitId(evlCmitId);
//		usptEvlCmit.setSndngCn(paramDto.getSndngCn());
//		usptEvlCmit.setSndngMth(paramDto.getSndngMth());

		usptEvlCmitDao.updateSndngInfo(usptEvlCmit);

		return resultInfo;
	}

}
