package aicluster.pms.api.bsnsapply.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aicluster.framework.common.entity.BnMember;
import aicluster.framework.common.service.AttachmentService;
import aicluster.framework.config.EnvConfig;
import aicluster.framework.security.SecurityUtils;
import aicluster.pms.api.bsnsapply.dto.BsnsApplyBhExmntDto;
import aicluster.pms.api.bsnsapply.dto.BsnsApplyBhExmntParam;
import aicluster.pms.api.bsnsapply.dto.BsnsApplyListParam;
import aicluster.pms.common.dao.CmmtMemberDao;
import aicluster.pms.common.dao.LogtIndvdlinfoLogDao;
import aicluster.pms.common.dao.UsptBsnsDao;
import aicluster.pms.common.dao.UsptBsnsPblancApplcntDao;
import aicluster.pms.common.dao.UsptBsnsPblancApplcntEntDao;
import aicluster.pms.common.dao.UsptBsnsPblancApplcntHistDao;
import aicluster.pms.common.dao.UsptBsnsPblancApplyAttachDao;
import aicluster.pms.common.dao.UsptBsnsPblancApplyBhExmntDao;
import aicluster.pms.common.dao.UsptBsnsPblancApplyChklstDao;
import aicluster.pms.common.dao.UsptBsnsPblancApplyTaskDao;
import aicluster.pms.common.dao.UsptBsnsPblancApplyTaskPartcptsDao;
import aicluster.pms.common.dao.UsptBsnsPblancDao;
import aicluster.pms.common.dto.ApplyAttachDto;
import aicluster.pms.common.dto.BsnsApplyDto;
import aicluster.pms.common.dto.BsnsApplyListDto;
import aicluster.pms.common.dto.BsnsBasicDto;
import aicluster.pms.common.entity.CmmtMember;
import aicluster.pms.common.entity.LogtIndvdlinfoLog;
import aicluster.pms.common.entity.UsptBsnsPblanc;
import aicluster.pms.common.entity.UsptBsnsPblancApplcnt;
import aicluster.pms.common.entity.UsptBsnsPblancApplcntEnt;
import aicluster.pms.common.entity.UsptBsnsPblancApplcntHist;
import aicluster.pms.common.entity.UsptBsnsPblancApplyBhExmnt;
import aicluster.pms.common.entity.UsptBsnsPblancApplyTask;
import aicluster.pms.common.entity.UsptBsnsPblancApplyTaskPartcpts;
import aicluster.pms.common.util.Code;
import bnet.library.exception.InvalidationException;
import bnet.library.util.CoreUtils;
import bnet.library.util.CoreUtils.date;
import bnet.library.util.CoreUtils.string;
import bnet.library.util.CoreUtils.webutils;
import bnet.library.util.dto.JsonList;
import bnet.library.util.pagination.CorePagination;
import bnet.library.util.pagination.CorePaginationInfo;

@Service
public class BsnsApplyService {
	public static final long ITEMS_PER_PAGE = 10L;
	@Autowired
	private EnvConfig config;
	@Autowired
	private AttachmentService attachmentService;
	@Autowired
	private CmmtMemberDao cmmtMemberDao;
	@Autowired
	private LogtIndvdlinfoLogDao logtIndvdlinfoLogDao;
	@Autowired
	private UsptBsnsDao usptBsnsDao;
	@Autowired
	private UsptBsnsPblancDao usptBsnsPblancDao;
	@Autowired
	private UsptBsnsPblancApplcntDao usptBsnsPblancApplcntDao;
	@Autowired
	private UsptBsnsPblancApplyTaskDao usptBsnsPblancApplyTaskDao;
	@Autowired
	private UsptBsnsPblancApplcntEntDao usptBsnsPblancApplcntEntDao;
	@Autowired
	private UsptBsnsPblancApplyChklstDao usptBsnsPblancApplyChklstDao;
	@Autowired
	private UsptBsnsPblancApplyAttachDao usptBsnsPblancApplyAttachDao;
	@Autowired
	private UsptBsnsPblancApplyTaskPartcptsDao usptBsnsPblancApplyTaskPartcptsDao;
	@Autowired
	private UsptBsnsPblancApplcntHistDao usptBsnsPblancApplcntHistDao;
	@Autowired
	private UsptBsnsPblancApplyBhExmntDao usptBsnsPblancApplyBhExmntDao;

	public CorePagination<BsnsApplyListDto> getList(BsnsApplyListParam bsnsApplyListParam, Long page, Boolean ordtmRcrit){
		BnMember worker = SecurityUtils.checkWorkerIsInsider();
		if(page == null) {
			page = 1L;
		}
		if(bsnsApplyListParam.getItemsPerPage() == null) {
			bsnsApplyListParam.setItemsPerPage(ITEMS_PER_PAGE);
		}

		bsnsApplyListParam.setOrdtmRcrit(ordtmRcrit);
		bsnsApplyListParam.setInsiderId(worker.getMemberId());
		if(!CoreUtils.string.equalsAnyIgnoreCase(bsnsApplyListParam.getSortOrder(), "desc", "asc")) {
			bsnsApplyListParam.setSortOrder("desc");
		}

		long totalItems = usptBsnsPblancApplcntDao.selectListCount(bsnsApplyListParam);
		CorePaginationInfo info = new CorePaginationInfo(page, bsnsApplyListParam.getItemsPerPage(), totalItems);
		bsnsApplyListParam.setBeginRowNum(info.getBeginRowNum());
		List<BsnsApplyListDto> list = usptBsnsPblancApplcntDao.selectList(bsnsApplyListParam);
		CorePagination<BsnsApplyListDto> pagination = new CorePagination<>(info, list);
		return pagination;
	}

	public List<BsnsApplyListDto> getListExcelDwld(BsnsApplyListParam bsnsApplyListParam, Boolean ordtmRcrit){
		BnMember worker = SecurityUtils.checkWorkerIsInsider();
		bsnsApplyListParam.setOrdtmRcrit(ordtmRcrit);
		bsnsApplyListParam.setInsiderId(worker.getMemberId());
		if(!CoreUtils.string.equalsAnyIgnoreCase(bsnsApplyListParam.getSortOrder(), "desc", "asc")) {
			bsnsApplyListParam.setSortOrder("desc");
		}
		long totalItems = usptBsnsPblancApplcntDao.selectListCount(bsnsApplyListParam);
		CorePaginationInfo info = new CorePaginationInfo(1L, totalItems, totalItems);
		bsnsApplyListParam.setBeginRowNum(info.getBeginRowNum());
		bsnsApplyListParam.setItemsPerPage(totalItems);
		return usptBsnsPblancApplcntDao.selectList(bsnsApplyListParam);
	}

	public void modifyReceipt(List<UsptBsnsPblancApplcnt> applyIdList) {
		BnMember worker = SecurityUtils.checkWorkerIsInsider();

		if(applyIdList.size() == 0) {
			throw new InvalidationException("완료처리할 접수ID가 없습니다.");
		}

		Date now = new Date();
		List<UsptBsnsPblancApplcntHist> histList = new ArrayList<UsptBsnsPblancApplcntHist>();

		for(UsptBsnsPblancApplcnt applyInfo : applyIdList) {
			String chargerAuthorCd = usptBsnsPblancApplcntDao.selectChargerAuth(applyInfo.getApplyId(), worker.getMemberId());
			if(!CoreUtils.string.equals(Code.사업담당자_수정권한, chargerAuthorCd)) {
				throw new InvalidationException("권한이 없습니다.");
			}
			UsptBsnsPblancApplcnt applcntInfo = new UsptBsnsPblancApplcnt();
			applcntInfo.setApplyId(applyInfo.getApplyId());
			applcntInfo.setRceptSttusCd(Code.rceptSttus.접수완료);
			applcntInfo.setRceptSttusDt(now);
			applcntInfo.setUpdatedDt(now);
			applcntInfo.setUpdaterId(worker.getMemberId());

			if(usptBsnsPblancApplcntDao.updateRceptSttusCompt(applcntInfo) != 1) {
				throw new InvalidationException("접수완료 처리 중 오류가 발생했습니다.");
			}

			UsptBsnsPblancApplcntHist histInfo = new UsptBsnsPblancApplcntHist();
			histInfo.setApplyId(applyInfo.getApplyId());
			histInfo.setHistId(CoreUtils.string.getNewId(Code.prefix.전문가신청처리이력));
			histInfo.setCreatedDt(now);
			histInfo.setCreatorId(worker.getMemberId());
			histInfo.setOpetrId(worker.getMemberId());
			histInfo.setProcessCn(Code.histMessage.접수완료);
			histInfo.setRceptSttusCd(Code.rceptSttus.접수완료);
			histList.add(histInfo);
		}
		usptBsnsPblancApplcntHistDao.insertList(histList);
	}


	public BsnsApplyDto get(HttpServletRequest request, String applyId) {
		BnMember worker = SecurityUtils.checkWorkerIsInsider();

		UsptBsnsPblancApplcnt info = usptBsnsPblancApplcntDao.select(applyId);
		if(info == null) {
			throw new InvalidationException("해당되는 신청접수 내용이 없습니다.");
		}
		String chargerAuthorCd = usptBsnsPblancApplcntDao.selectChargerAuth(applyId, worker.getMemberId());
		if(CoreUtils.string.isBlank(chargerAuthorCd)) {
			throw new InvalidationException("권한이 없습니다.");
		}

		BsnsApplyDto dto = new BsnsApplyDto();
		UsptBsnsPblanc pblancInfo = usptBsnsPblancDao.select(info.getPblancId(), worker.getMemberId());
		BsnsBasicDto bsnsInfo = usptBsnsDao.select(pblancInfo.getBsnsCd());

		dto.setApplyId(info.getApplyId());
		dto.setReceiptNo(info.getReceiptNo());
		dto.setRceptSttus(info.getRceptSttus());
		dto.setRceptSttusCd(info.getRceptSttusCd());
		dto.setMemberType(info.getMemberType());
		dto.setBsnsNm(bsnsInfo.getBsnsNm());
		dto.setBsnsTypeCd(bsnsInfo.getBsnsTypeCd());
		dto.setBsnsTypeNm(bsnsInfo.getBsnsTypeNm());
		dto.setPblancId(info.getPblancId());
		dto.setPblancNm(pblancInfo.getPblancNm());
		dto.setPblancNo(pblancInfo.getPblancNo());

		Date bsnsBgndt = string.toDate(pblancInfo.getBsnsBgnde());
		Date bsnsEnddt = string.toDate(pblancInfo.getBsnsEndde());
		if (bsnsBgndt != null && bsnsEnddt != null ) {
			Date lastdt = string.toDate(date.getCurrentDate("yyyy") + "1231");
			dto.setBsnsPd("협약 체결일 ~ " + date.format(bsnsEnddt, "yyyy-MM-dd"));
			int allDiffMonths = CoreUtils.date.getDiffMonths(bsnsBgndt, bsnsEnddt);
			if(date.compareDay(bsnsEnddt, lastdt) == 1) {
				int diffMonths = CoreUtils.date.getDiffMonths(bsnsBgndt, lastdt);
				dto.setBsnsPdYw(date.format(bsnsBgndt, "yyyy-MM-dd") + " ~ " + date.format(lastdt, "yyyy-MM-dd") + "(" + diffMonths + "개월)");
			} else {
				dto.setBsnsPdYw(date.format(bsnsBgndt, "yyyy-MM-dd") + " ~ " + date.format(bsnsEnddt, "yyyy-MM-dd") + "(" + allDiffMonths + "개월)");
			}
			dto.setBsnsPdAll(date.format(bsnsBgndt, "yyyy-MM-dd") + " ~ " + date.format(bsnsEnddt, "yyyy-MM-dd") + "(" + allDiffMonths + "개월)");
		}

		/* 신청자 정보 */
		CmmtMember cmmtMember = cmmtMemberDao.select(info.getMemberId());
		dto.setCmmtMember(cmmtMember);
		if(!CoreUtils.string.equals(cmmtMember.getMemberType(), Code.memberType.개인사용자)) {
			UsptBsnsPblancApplcntEnt entInfo = usptBsnsPblancApplcntEntDao.select(applyId);
			if(entInfo != null) {
				entInfo.setFxnum(entInfo.getDecFxnum());
				entInfo.setCeoEmail(entInfo.getDecCeoEmail());
				entInfo.setCeoTelno(entInfo.getDecCeoTelno());
				entInfo.setReprsntTelno(entInfo.getDecReprsntTelno());
				dto.setApplcntEnt(entInfo);
			}
		}

		/* 과제정보 */
		UsptBsnsPblancApplyTask taskInfo = usptBsnsPblancApplyTaskDao.select(applyId);
		taskInfo.setFxnum(taskInfo.getDecFxnum());
		taskInfo.setTelno(taskInfo.getDecTelno());
		taskInfo.setBrthdy(taskInfo.getDecBrthdy());
		taskInfo.setMbtlnum(taskInfo.getDecMbtlnum());
		taskInfo.setEmail(taskInfo.getDecEmail());
		dto.setTaskInfo(taskInfo);

		/* 과제참여자 정보 */
		List<UsptBsnsPblancApplyTaskPartcpts> taskPartcptsList = usptBsnsPblancApplyTaskPartcptsDao.selectList(applyId);
		if(taskPartcptsList != null) {
			for(UsptBsnsPblancApplyTaskPartcpts partcptsInfo : taskPartcptsList) {
				partcptsInfo.setBrthdy(partcptsInfo.getDecBrthdy());
				partcptsInfo.setMbtlnum(partcptsInfo.getDecMbtlnum());
			}
		}
		dto.setPartcptslist(taskPartcptsList);

		/* 필수확인사항 목록 */
		dto.setChkList(usptBsnsPblancApplyChklstDao.selectList(applyId));
		/* 첨부파일 목록 */
		dto.setAtchmnflList(usptBsnsPblancApplyAttachDao.selectList(applyId, pblancInfo.getBsnsCd()));

		Date now = new Date();
		LogtIndvdlinfoLog log = LogtIndvdlinfoLog.builder()
				.logId(string.getNewId("ilog-"))
				.logDt(now)
				.memberId(worker.getMemberId())
				.memberIp(webutils.getRemoteIp(request))
				.workTypeNm("조회")
				.workCn("사업신청 접수관리 상세조회")
				.trgterId(info.getMemberId())
				.build();
		logtIndvdlinfoLogDao.insert(log);

		return dto;
	}


	/**
	 * 첨부파일 다운로드
	 * @param response
	 * @param applyId
	 * @param downloadType : ("ALL":전체 / "":단건)
	 * @param attachmentId
	 */
	public void getFileDownload(HttpServletResponse response, String applyId, String attachmentId, String downloadType) {
		BnMember worker = SecurityUtils.checkWorkerIsInsider();
		UsptBsnsPblancApplcnt info = usptBsnsPblancApplcntDao.select(applyId);
		if(info == null) {
			throw new InvalidationException("해당되는 신청접수 내용이 없습니다.");
		}
		UsptBsnsPblanc pblancInfo = usptBsnsPblancDao.select(info.getPblancId(), worker.getMemberId());
		if(pblancInfo == null) {
			throw new InvalidationException("권한이 없습니다.");
		}
		List<ApplyAttachDto> fileList = usptBsnsPblancApplyAttachDao.selectList(applyId, pblancInfo.getBsnsCd());

		if(CoreUtils.string.equals(downloadType, "ALL")) {
			String[] attachmentIds = new String[fileList.size()];
			for(int idx=0; idx<fileList.size(); idx++) {
				ApplyAttachDto fileDto = fileList.get(idx);
				attachmentIds[idx] = fileDto.getAttachmentId();
			}
			attachmentService.downloadFiles(response, config.getDir().getUpload(), attachmentIds, "신청접수_첨부파일");
		} else {
			Optional<ApplyAttachDto> opt = fileList.stream().filter(x->CoreUtils.string.equals(attachmentId, x.getAttachmentId())).findFirst();
			if(opt.isPresent()) {
				attachmentService.downloadFile(response, config.getDir().getUpload(), attachmentId);
			} else {
				throw new InvalidationException("요청한 첨부파일이 존재하지 않습니다.");
			}
		}
	}


	public void modifyRceptSttus(String applyId, String rceptSttusCd, String processCn) {
		BnMember worker = SecurityUtils.checkWorkerIsInsider();
		UsptBsnsPblancApplcnt info = usptBsnsPblancApplcntDao.select(applyId);
		if(info == null) {
			throw new InvalidationException("해당되는 신청접수 내용이 없습니다.");
		}

		String chargerAuthorCd = usptBsnsPblancApplcntDao.selectChargerAuth(applyId, worker.getMemberId());
		if(CoreUtils.string.isBlank(chargerAuthorCd)) {
			throw new InvalidationException("권한이 없습니다.");
		}

		Date now = new Date();
		info.setRceptSttusCd(rceptSttusCd);
		info.setRceptSttusDt(now);
		info.setCreatedDt(now);
		info.setCreatorId(worker.getMemberId());
		if(CoreUtils.string.equals(rceptSttusCd, Code.rceptSttus.반려)) {
			info.setRejectReasonCn(processCn);
		}
		if(CoreUtils.string.equals(rceptSttusCd, Code.rceptSttus.보완요청)) {
			info.setMakeupReqCn(processCn);
		}
		if(usptBsnsPblancApplcntDao.update(info) != 1) {
			throw new InvalidationException("신청접수 상태 수정 중 오류가 발생했습니다.");
		}

		UsptBsnsPblancApplcntHist histInfo = new UsptBsnsPblancApplcntHist();
		histInfo.setApplyId(applyId);
		histInfo.setHistId(CoreUtils.string.getNewId(Code.prefix.전문가신청처리이력));
		histInfo.setCreatedDt(now);
		histInfo.setCreatorId(worker.getMemberId());
		histInfo.setOpetrId(worker.getMemberId());
		histInfo.setProcessCn(processCn);
		histInfo.setRceptSttusCd(rceptSttusCd);
		usptBsnsPblancApplcntHistDao.insert(histInfo);
	}


	public BsnsApplyBhExmntDto getBhExmnt(String applyId) {
		BnMember worker = SecurityUtils.checkWorkerIsInsider();
		UsptBsnsPblancApplcnt info = usptBsnsPblancApplcntDao.select(applyId);
		if(info == null) {
			throw new InvalidationException("해당되는 신청접수 내용이 없습니다.");
		}
		BsnsApplyBhExmntDto dto = new BsnsApplyBhExmntDto();
		UsptBsnsPblanc pblancInfo = usptBsnsPblancDao.select(info.getPblancId(), worker.getMemberId());
		if(pblancInfo == null) {
			throw new InvalidationException("권한이 없습니다.");
		}
		dto.setMakeupOpinionCn(info.getMakeupOpinionCn());
		dto.setBhExmntList(usptBsnsPblancApplyBhExmntDao.selectList(info.getApplyId(), pblancInfo.getBsnsCd()));
		dto.setAtchmnflList(usptBsnsPblancApplyAttachDao.selectList(applyId, pblancInfo.getBsnsCd()));
		return dto;
	}


	public void modifyBhExmnt(String applyId, BsnsApplyBhExmntParam bsnsApplyBhExmntParam) {
		BnMember worker = SecurityUtils.checkWorkerIsInsider();
		UsptBsnsPblancApplcnt info = usptBsnsPblancApplcntDao.select(applyId);
		if(info == null) {
			throw new InvalidationException("해당되는 신청접수 내용이 없습니다.");
		}
		String chargerAuthorCd = usptBsnsPblancApplcntDao.selectChargerAuth(applyId, worker.getMemberId());
		if(CoreUtils.string.isBlank(chargerAuthorCd)) {
			throw new InvalidationException("권한이 없습니다.");
		}

		Date now = new Date();
		info.setMakeupOpinionCn(bsnsApplyBhExmntParam.getMakeupOpinionCn());
		info.setUpdatedDt(now);
		info.setUpdaterId(worker.getMemberId());

		if(usptBsnsPblancApplcntDao.update(info) != 1) {
			throw new InvalidationException("사전검토 저장 중 오류가 발생했습니다.");
		}

		List<UsptBsnsPblancApplyBhExmnt> bhExmntList = bsnsApplyBhExmntParam.getBhExmntList();
		if(bhExmntList.size() != 0) {
			for(UsptBsnsPblancApplyBhExmnt regInfo : bhExmntList) {
				regInfo.setApplyId(applyId);
				regInfo.setCreatedDt(now);
				regInfo.setCreatorId(worker.getMemberId());
				regInfo.setUpdatedDt(now);
				regInfo.setUpdaterId(worker.getMemberId());
				usptBsnsPblancApplyBhExmntDao.save(regInfo);
			}
		}
	}


	public JsonList<UsptBsnsPblancApplcntHist> getHist(String applyId) {
		BnMember worker = SecurityUtils.checkWorkerIsInsider();
		UsptBsnsPblancApplcnt info = usptBsnsPblancApplcntDao.select(applyId);
		if(info == null) {
			throw new InvalidationException("해당되는 신청접수 내용이 없습니다.");
		}
		String chargerAuthorCd = usptBsnsPblancApplcntDao.selectChargerAuth(applyId, worker.getMemberId());
		if(CoreUtils.string.isBlank(chargerAuthorCd)) {
			throw new InvalidationException("권한이 없습니다.");
		}

		return new JsonList<>(usptBsnsPblancApplcntHistDao.selectList(applyId));
	}

}
