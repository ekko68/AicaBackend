package aicluster.member.api.member.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aicluster.framework.common.entity.BnMember;
import aicluster.framework.notification.EmailUtils;
import aicluster.framework.notification.dto.EmailSendParam;
import aicluster.framework.notification.nhn.email.EmailResult;
import aicluster.framework.security.SecurityUtils;
import aicluster.member.api.member.dto.BlackListDto;
import aicluster.member.api.member.dto.MemberGetListParam;
import aicluster.member.api.member.dto.MemberParam;
import aicluster.member.common.dao.CmmtAuthorityDao;
import aicluster.member.common.dao.CmmtCodeDao;
import aicluster.member.common.dao.CmmtMemberDao;
import aicluster.member.common.dao.CmmtMemberHistDao;
import aicluster.member.common.dao.LogtIndvdlinfoLogDao;
import aicluster.member.common.dto.MemberDto;
import aicluster.member.common.dto.MemberStatsDto;
import aicluster.member.common.entity.CmmtAuthority;
import aicluster.member.common.entity.CmmtCode;
import aicluster.member.common.entity.CmmtMember;
import aicluster.member.common.entity.CmmtMemberHist;
import aicluster.member.common.entity.LogtIndvdlinfoLog;
import aicluster.member.common.util.CodeExt;
import aicluster.member.common.util.CodeExt.validateMessageExt;
import aicluster.member.common.util.MemberHistUtils;
import bnet.library.exception.InvalidationException;
import bnet.library.exception.InvalidationsException;
import bnet.library.util.CoreUtils;
import bnet.library.util.CoreUtils.date;
import bnet.library.util.CoreUtils.string;
import bnet.library.util.CoreUtils.webutils;
import bnet.library.util.dto.JsonList;
import bnet.library.util.pagination.CorePagination;
import bnet.library.util.pagination.CorePaginationInfo;

@Service
public class MemberService {

	public static final long ITEMS_PER_PAGE = 20;

	@Autowired(required = false)
	private CmmtMemberDao cmmtMemberDao;
	@Autowired
	private CmmtAuthorityDao cmmtAuthorityDao;
	@Autowired
	private CmmtCodeDao cmmtCodeDao;
	@Autowired
	private CmmtMemberHistDao cmmtMemberHistDao;
	@Autowired
	private LogtIndvdlinfoLogDao logtIndvdlinfoLogDao;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private MemberHistUtils memberHistUtils;
	@Autowired
	private EmailUtils emailUtils;

	/**
	 * 회원 목록 조회
	 *
	 * @param param
	 * @return
	 */
	public CorePagination<MemberDto> getList(MemberGetListParam param) {

		SecurityUtils.checkWorkerIsInsider();

		if(param.getPage() == null) {
			param.setPage(1L);
		}
		if(param.getItemsPerPage() == null) {
			param.setItemsPerPage(ITEMS_PER_PAGE);
		}
		//전체 건수
		long totalItems = cmmtMemberDao.selectCount( param.getLoginId(), param.getMemberNm(), param.getMemberSt(), param.getMemberType());
		//조회할 페이지의 구간 정보
		CorePaginationInfo info = new CorePaginationInfo(param.getPage(), param.getItemsPerPage(), totalItems);
		//페이지의 목록 조회
		List<MemberDto> list = cmmtMemberDao.selectList(param.getLoginId(), param.getMemberNm(), param.getMemberSt(), param.getMemberType(), info.getBeginRowNum(), param.getItemsPerPage());
		//출력할 자료 생성 후 리턴
		CorePagination<MemberDto> pagination = new CorePagination<>(info, list);
		return pagination;
	}

	/**
	 * 회원정보 상세조회
	 *
	 * @param memberId
	 * @return
	 */
	public CmmtMember get(String memberId) {
		Date now = new Date();
		BnMember worker = 	SecurityUtils.checkWorkerIsInsider();
		if(string.isBlank(memberId)) {
			throw new InvalidationException(String.format(validateMessageExt.입력없음, "회원ID"));
		}
		//id 조회
		CmmtMember cmmtMember = cmmtMemberDao.select(memberId);
		if(cmmtMember == null) {
			throw new InvalidationException(String.format(validateMessageExt.조회결과없음, "사용자 정보"));
		}
		// log 정보생성
		if (!string.equals(worker.getMemberId(), memberId)) {
			LogtIndvdlinfoLog log = LogtIndvdlinfoLog.builder()
					.logId(string.getNewId("ilog-"))
					.logDt(now)
					.memberId(worker.getMemberId())
					.memberIp(webutils.getRemoteIp(request))
					.workTypeNm("조회")
					.workCn("회원관리 상세페이지 조회 업무")
					.trgterId(memberId)
					.build();

			logtIndvdlinfoLogDao.insert(log);
		}

		return cmmtMember;
	}

	/**
	 * 회원정보 수정
	 *
	 * @param param
	 * @return
	 */
	public CmmtMember modify(MemberParam param) {
		Date now = new Date();
		BnMember worker = 	SecurityUtils.checkWorkerIsInsider();

		if(string.isBlank(param.getMemberId())) {
			throw new InvalidationException(String.format(validateMessageExt.입력없음, "회원 ID"));
		}

		//대문자 변환
		String authorityId = string.upperCase(param.getAuthorityId());
		authorityId = string.removeWhitespace(authorityId);
		String memberSt = string.upperCase(param.getMemberSt());
		memberSt = string.removeWhitespace(memberSt);

		String memberId = param.getMemberId();

		//id 조회
		CmmtMember cmmtMember = cmmtMemberDao.select(memberId);
		if(cmmtMember == null) {
			throw new InvalidationException(String.format(validateMessageExt.조회결과없음, "사용자 정보"));
		}
		//authorityId,memberSt 존재 여부 검사
		CmmtAuthority cmmtAuthority = cmmtAuthorityDao.select(authorityId);
		if(cmmtAuthority == null) {
			throw new InvalidationException("권한이 올바르지 않습니다.");
		}
		CmmtCode cmmtCode = cmmtCodeDao.select("MEMBER_ST",memberSt);
		if(cmmtCode == null) {
			throw new InvalidationException("회원상태가 올바르지 않습니다.");
		}

		//cmmtMember를 cmmtMemberNew로 복사
		CmmtMember cmmtMemberNew = new CmmtMember();
		CoreUtils.property.copyProperties(cmmtMemberNew, cmmtMember);

		//cmmtMemberNew에 입력받은 정보 세팅
		cmmtMemberNew.setAuthorityId(authorityId);
		cmmtMemberNew.setMemberSt(memberSt);
		cmmtMemberNew.setBlackListBeginDay(param.getBlackListBeginDay());
		cmmtMemberNew.setBlackListEndDay(param.getBlackListEndDay());
		cmmtMemberNew.setBlackListReason(param.getBlackListReason());
		cmmtMemberNew.setUpdaterId(worker.getMemberId());
		cmmtMemberNew.setUpdatedDt(now);

		//CMMT_MEMBER update
		cmmtMemberDao.update(cmmtMemberNew);
		cmmtMemberNew = cmmtMemberDao.select(memberId);

		//변경이력 등록
		memberHistUtils.addChangeHist(worker.getMemberId(), cmmtMember, cmmtMemberNew);

		// log 정보생성
		if (!string.equals(worker.getMemberId(), memberId)) {
			LogtIndvdlinfoLog log = LogtIndvdlinfoLog.builder()
					.logId(string.getNewId("ilog-"))
					.logDt(now)
					.memberId(worker.getMemberId())
					.memberIp(webutils.getRemoteIp(request))
					.workTypeNm("변경")
					.workCn("회원관리 정보 변경 업무")
					.trgterId(memberId)
					.build();

			logtIndvdlinfoLogDao.insert(log);
		}

		return cmmtMemberNew;

	}

	/**
	 * 로그인 사용자 회원탈퇴
	 */
	public void secession()
	{
		Date now = new Date();
		BnMember worker = SecurityUtils.checkLogin();

		CmmtMember cmmtMember = cmmtMemberDao.select(worker.getMemberId());
		if(cmmtMember == null) {
			throw new InvalidationException(String.format(validateMessageExt.조회결과없음, "사용자 정보"));
		}

		if (string.equals(cmmtMember.getMemberSt(), CodeExt.memberSt.탈퇴)) {
			throw new InvalidationException("이미 탈퇴 계정입니다.");
		}
		cmmtMember.setMemberSt(CodeExt.memberSt.탈퇴);
		cmmtMember.setMemberStDt(now);
		cmmtMember.setUpdaterId(worker.getMemberId());
		cmmtMember.setUpdatedDt(now);

		cmmtMemberDao.update(cmmtMember);

		// 회원변경이력 등록
		memberHistUtils.addSecessionHist(worker.getMemberId());
	}

	/**
	 * 불량회원 설정
	 *
	 * @param blackListDto
	 * @return
	 */
	public CmmtMember black(BlackListDto blackListDto) {
		Date now = new Date();
		BnMember worker = 	SecurityUtils.checkWorkerIsInsider();
		InvalidationsException inputValidateErrs = new InvalidationsException();

		if(string.isBlank(blackListDto.getMemberId())) {
			inputValidateErrs.add("memberId",String.format(validateMessageExt.입력없음, "회원 ID"));
		}

		if(blackListDto.getRegisterReasons() == null) {
			inputValidateErrs.add("registerResonsList",String.format(validateMessageExt.체크없음, "등록사유"));
		}

		if(string.isBlank(blackListDto.getLimitBeginDt())) {
			inputValidateErrs.add("limitBeginDt","이용제한 시작일을 설정해주세요.");
		} else {
			Date beginDt = string.toDate(blackListDto.getLimitBeginDt());
			if (beginDt == null) {
				inputValidateErrs.add("limitBeginDt", "시작일이 유효하지 않습니다.");
			}
		}

		if(string.isBlank(blackListDto.getLimitEndDt())) {
			inputValidateErrs.add("limitEndDt","이용제한 종료일을 설정해주세요.");
		} else {
			Date beginDt = string.toDate(blackListDto.getLimitEndDt());
			if (beginDt == null) {
				inputValidateErrs.add("limitEndDt", "시작일이 유효하지 않습니다.");
			}
		}

		// 입력값 검증 결과 메시지 처리
		if (inputValidateErrs.size() > 0) {
			throw inputValidateErrs;
		}
		String memberId = blackListDto.getMemberId();
		String[] registerReasonsList = blackListDto.getRegisterReasons();
		String limitBeginDt = string.getNumberOnly(blackListDto.getLimitBeginDt());
		String limitEndDt = string.getNumberOnly(blackListDto.getLimitEndDt());
		String detailReason = blackListDto.getDetailReason();

		CmmtMember cmmtMember = cmmtMemberDao.select(memberId);
		if(cmmtMember == null) {
			throw new InvalidationException(String.format(validateMessageExt.조회결과없음, "사용자 정보"));
		}

		if(cmmtMember.getMemberSt().equals(CodeExt.memberSt.정상) == false ) {
			throw new InvalidationException("회원상태가 '정상'인 회원만 불량회원 등록이 가능합니다.");
		}

		boolean codeValidate = true;

		StringBuilder message = new StringBuilder();

		for (String reasonCode : registerReasonsList) {
			CmmtCode cmmtCode = cmmtCodeDao.select("BLACK_LIST_RI", reasonCode);
			if(cmmtCode == null) {
				codeValidate = false;
			}else {
				message.append(cmmtCode.getCodeNm()).append("\n");
			}
		}

		if(codeValidate == false) {
			throw new InvalidationException(String.format(validateMessageExt.포함불가, "등록사유 코드"));
		}

		if(CoreUtils.date.isValidDate(limitBeginDt, "yyyymmdd") == false) {
			throw new InvalidationException(String.format(validateMessageExt.유효오류, "시작일자"));
		}

		if(CoreUtils.date.isValidDate(limitEndDt, "yyyymmdd") == false) {
			throw new InvalidationException(String.format(validateMessageExt.유효오류, "종료일자"));
		}

		Date beginDt = CoreUtils.date.parseDateSilently(limitBeginDt, "yyyymmdd");
		Date endDt = CoreUtils.date.parseDateSilently(limitEndDt, "yyyymmdd");

		if(CoreUtils.date.compareDay(beginDt, endDt) >= 0 ) {
			throw new InvalidationException(String.format(validateMessageExt.크거나같은비교오류	, "시작일", "종료일"));
		}

		CmmtMember cmmtMemberNew = new CmmtMember();
		CoreUtils.property.copyProperties(cmmtMemberNew, cmmtMember);

		cmmtMemberNew.setMemberSt(CodeExt.memberSt.불량회원);
		cmmtMemberNew.setMemberStDt(now);
		cmmtMemberNew.setBlackListBeginDay(limitBeginDt);
		cmmtMemberNew.setBlackListEndDay(limitEndDt);
		cmmtMemberNew.setBlackListReason(detailReason);
		cmmtMemberNew.setUpdaterId(worker.getMemberId());
		cmmtMemberNew.setUpdatedDt(now);

		cmmtMemberDao.update(cmmtMemberNew);
		cmmtMemberNew = cmmtMemberDao.select(cmmtMember.getMemberId());

		// 변경이력 등록
		memberHistUtils.addChangeHist(worker.getMemberId(), cmmtMember, cmmtMemberNew);

		return cmmtMemberNew;
	}

	/**
	 * 불량회원 해제
	 *
	 * @param memberId
	 * @return
	 */
	public CmmtMember unblack(String memberId) {
		Date now = new Date();
		BnMember worker = 	SecurityUtils.checkWorkerIsInsider();

		if(string.isBlank(memberId)) {
			throw new InvalidationException(String.format(validateMessageExt.입력없음, "회원 ID"));
		}

		CmmtMember cmmtMember = cmmtMemberDao.select(memberId);
		if(cmmtMember == null) {
			throw new InvalidationException(String.format(validateMessageExt.조회결과없음, "사용자 정보"));
		}

		if(cmmtMember.getMemberSt().equals(CodeExt.memberSt.불량회원) == false) {
			throw new InvalidationException("회원상태가 '불량회원'인 회원만 불량회원 해제가 가능합니다.");
		}

		CmmtMember cmmtMemberNew = new CmmtMember();
		CoreUtils.property.copyProperties(cmmtMemberNew, cmmtMember);

		cmmtMemberNew.setMemberSt(CodeExt.memberSt.정상);
		cmmtMemberNew.setMemberStDt(new Date());
		cmmtMemberNew.setBlackListBeginDay(null);
		cmmtMemberNew.setBlackListEndDay(null);
		cmmtMemberNew.setBlackListReason(null);
		cmmtMemberNew.setUpdaterId(worker.getMemberId());
		cmmtMemberNew.setUpdatedDt(now);

		cmmtMemberDao.update(cmmtMember);
		cmmtMemberNew = cmmtMemberDao.select(cmmtMember.getMemberId());

		// 이력 등록
		memberHistUtils.addChangeHist(worker.getMemberId(), cmmtMember, cmmtMemberNew);

		return cmmtMemberNew;
	}

	/**
	 * 회원 이력 목록 조회
	 *
	 * @param memberId
	 * @return
	 */
	public JsonList<CmmtMemberHist> getHistList(String memberId) {
		SecurityUtils.checkWorkerIsInsider();

		//memberId로 cmmt_member를 조회한다.
		CmmtMember cmmtMember = cmmtMemberDao.select(memberId);
		if(cmmtMember == null) {
			throw new InvalidationException(String.format(validateMessageExt.조회결과없음, "자료"));
		}

		//cmmt_member_hist을 조회한다.

		List<CmmtMemberHist> list = cmmtMemberHistDao.selectListByMemberId(memberId);

		return new JsonList<>(list);
	}

	/**
	 * 회원 상태 목록 조회
	 *
	 * @param searchType
	 * @param memberType
	 * @param beginDay
	 * @param endDay
	 * @return
	 */
	public MemberStatsDto getStatsList(String searchType, String memberType, String beginDay, String endDay)
	{
		// 내부사용자 확인
		SecurityUtils.checkWorkerIsInsider();

		// 필수값 체크
		if (string.isBlank(searchType)) {
			throw new InvalidationException(String.format(CodeExt.validateMessageExt.입력없음, "검색구분"));
		}
		if (!date.isValidDate(beginDay, "yyyyMMdd")) {
			throw new InvalidationException(String.format(CodeExt.validateMessageExt.날짜없음, "조회기간(시작일)"));
		}
		if (!date.isValidDate(endDay, "yyyyMMdd")) {
			throw new InvalidationException(String.format(CodeExt.validateMessageExt.날짜없음, "조회기간(종료일)"));
		}

		// 기간 검증
		if (date.compareDay(string.toDate(beginDay), string.toDate(endDay)) > -1) {
			throw new InvalidationException(String.format(CodeExt.validateMessageExt.크거나같은비교오류, "조회기간(시작일)", "조회기간(종료일)"));
		}

		MemberStatsDto memberStats = cmmtMemberDao.selectCurrStats();

		if ("DAY".equals(searchType)) {
			memberStats.setStatsList(cmmtMemberDao.selectDayStatsList(memberType, beginDay, endDay));
		}
		else if ("MONTH".equals(searchType)) {
			memberStats.setStatsList(cmmtMemberDao.selectMonthStatsList(memberType, beginDay, endDay));
		}

		return memberStats;
	}

	/**
	 * 회원 비밀번호 초기화
	 *
	 * @param memberId
	 */
	public void passwdInit(String memberId) {
		Date now = new Date();
		BnMember worker = SecurityUtils.checkWorkerIsInsider();

		// 회원정보 조회
		CmmtMember cmmtMember = cmmtMemberDao.select(memberId);
		if (cmmtMember == null) {
			throw new InvalidationException(String.format(CodeExt.validateMessageExt.조회결과없음, "회원정보"));
		}

		// 이메일 주소 검사
		if (string.isBlank(cmmtMember.getEmail())) {
			throw new InvalidationException("이메일이 없어 비밀번호를 초기화할 수 없습니다.");
		}

		// 임시 비밀번호 생성
		String tmpPasswd = CoreUtils.password.getRandomPassword();
		String encTmpPasswd = CoreUtils.password.encode(tmpPasswd);

		// 이메일 발송
		String emailCn = CoreUtils.file.readFileString("/form/email/email-tmp-passwd.html");
		if (string.isBlank(emailCn)) {
			throw new InvalidationException("이메일 본문파일을 찾을 수 없습니다.");
		}
		EmailSendParam eparam = new EmailSendParam();
		eparam.setContentHtml(true);
		eparam.setEmailCn(emailCn);
		eparam.setTitle("(AICA) 비밀번호 초기화");
		Map<String, String> templateParam = new HashMap<>();
		templateParam.put("tmpPasswd", tmpPasswd);
		eparam.addRecipient(cmmtMember.getEmail(), cmmtMember.getMemberNm(), templateParam);

		EmailResult er = emailUtils.send(eparam);
		if (er.getFailCnt() > 0) {
			throw new InvalidationException("이메일 발송에 실패하여 비밀번호 초기화를 중단하였습니다.");
		}

		// 임시 비밀번호 Update
		cmmtMember.setEncPasswd(encTmpPasswd);
		cmmtMember.setPasswdDt(now);
		cmmtMember.setPasswdInit(true);
		cmmtMemberDao.update(cmmtMember);

		// 비밀번호 초기화 이력 생성
		memberHistUtils.addPasswdInitHist(worker.getMemberId(), memberId);

	}
}

