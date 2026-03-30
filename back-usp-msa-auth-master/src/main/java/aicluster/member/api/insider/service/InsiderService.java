package aicluster.member.api.insider.service;

import java.util.ArrayList;
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
import aicluster.member.api.insider.dto.InsiderListParam;
import aicluster.member.api.insider.dto.InsiderMemberDto;
import aicluster.member.api.insider.dto.SrchPicItemDto;
import aicluster.member.api.insider.dto.SrchPicParam;
import aicluster.member.common.dao.CmmtAuthorityDao;
import aicluster.member.common.dao.CmmtInsiderDao;
import aicluster.member.common.dao.CmmtInsiderHistDao;
import aicluster.member.common.dao.LogtIndvdlinfoLogDao;
import aicluster.member.common.dto.InsiderDto;
import aicluster.member.common.dto.InsiderHistDto;
import aicluster.member.common.dto.VerifyLoginIdResultDto;
import aicluster.member.common.entity.CmmtAuthority;
import aicluster.member.common.entity.CmmtInsider;
import aicluster.member.common.entity.CmmtInsiderHist;
import aicluster.member.common.entity.LogtIndvdlinfoLog;
import aicluster.member.common.util.CodeExt;
import aicluster.member.common.util.CodeExt.histMessage;
import aicluster.member.common.util.CodeExt.validateMessageExt;
import bnet.library.exception.InvalidationException;
import bnet.library.exception.InvalidationsException;
import bnet.library.util.CoreUtils;
import bnet.library.util.CoreUtils.array;
import bnet.library.util.CoreUtils.password;
import bnet.library.util.CoreUtils.string;
import bnet.library.util.CoreUtils.webutils;
import bnet.library.util.dto.JsonList;
import bnet.library.util.pagination.CorePagination;
import bnet.library.util.pagination.CorePaginationInfo;
import bnet.library.util.pagination.CorePaginationParam;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class InsiderService {

	@Autowired
	private CmmtInsiderDao cmmtInsiderDao;

	@Autowired
	private CmmtInsiderHistDao cmmtInsiderHistDao;

	@Autowired
	private CmmtAuthorityDao cmmtAuthorityDao;

	@Autowired
	private LogtIndvdlinfoLogDao logtIndvdlinfoLogDao;

	@Autowired
	private EmailUtils emailUtils;

	@Autowired(required = false)
	private HttpServletRequest request;

	public CorePagination<InsiderDto> getList(InsiderListParam param, CorePaginationParam pageParam)
	{
		// 로그인 여부 검사, 내부사용자 여부 검사
		SecurityUtils.checkWorkerIsInsider();

		// 전체 건수 확인
		long totalItems = cmmtInsiderDao.selectCount(param);

		// 조회할 페이지 구간 정보 확인
		CorePaginationInfo info = new CorePaginationInfo(pageParam.getPage(), pageParam.getItemsPerPage(), totalItems);

		// 페이지 목록 조회
		List<InsiderDto> list = cmmtInsiderDao.selectList(param, info.getBeginRowNum(), info.getItemsPerPage());

		CorePagination<InsiderDto> pagination = new CorePagination<>(info, list);

		// 목록 조회
		return pagination;
	}

	public CmmtInsider addInsider(InsiderMemberDto dto) {

		Date now = new Date();
		InvalidationsException inputValidateErrs = new InvalidationsException();

		// 로그인 여부 검사, 내부사용자 여부 검사
		BnMember worker = SecurityUtils.checkWorkerIsInsider();

		log.debug(dto.toString());
		// 입력값 검증
		if (string.isBlank(dto.getLoginId())) {
			inputValidateErrs.add("loginId", String.format(validateMessageExt.입력없음, "로그인 ID"));
		}

		if (string.isBlank(dto.getMemberNm())) {
			inputValidateErrs.add("memberNm", String.format(validateMessageExt.입력없음, "회원이름"));
		}

		if (string.isBlank(dto.getDeptNm())) {
			inputValidateErrs.add("deptNm", String.format(validateMessageExt.입력없음, "부서명"));
		}

		if (string.isBlank(dto.getAuthorityId())) {
			inputValidateErrs.add("authorityId", String.format(validateMessageExt.입력없음, "권한"));
		}

		if (string.isBlank(dto.getEmail())) {
			inputValidateErrs.add("email", String.format(validateMessageExt.입력없음, "email"));
		}

		if (string.isBlank(dto.getTelNo())) {
			inputValidateErrs.add("telNo", String.format(validateMessageExt.입력없음, "전화번호"));
		}

		if (string.isBlank(dto.getMobileNo())) {
			inputValidateErrs.add("mobileNo", String.format(validateMessageExt.입력없음, "휴대폰번호"));
		}

		if (string.isNotBlank(dto.getMemberIps())) {
			dto.setMemberIps(string.validateIpStrings(dto.getMemberIps(), '/', '*'));
		}

		if (inputValidateErrs.size() > 0) {
			throw inputValidateErrs;
		}

		// ID 중복 조회
		dto.setLoginId(string.lowerCase(dto.getLoginId()));
		CmmtInsider cmmtInsider = cmmtInsiderDao.selectByLoginId(dto.getLoginId());
		if (cmmtInsider != null) {
			throw new InvalidationException(String.format(validateMessageExt.중복오류, "ID", dto.getLoginId()));
		}

		String authorityId = string.upperCase(dto.getAuthorityId());
		CmmtAuthority cmmtauthority = cmmtAuthorityDao.select(authorityId);
		if (cmmtauthority == null) {
			throw new InvalidationException(String.format(validateMessageExt.입력오류, "권한"));
		}

		// 입력값 세팅
		//String memberId = string.getNewId("insider-");
//		String encMobileNo = aes256.encrypt(dto.getMobileNo(), memberId);
//		String encEmail = aes256.encrypt(dto.getEmail(), memberId);

		dto.setMemberId(string.getNewId("insider-"));
		String passwd = password.getRandomPassword();
		String encPasswd = password.encode(passwd);

//		String memberIps = null;
//		if (string.isNotBlank(memberIps)) {
//			memberIps = CoreUtils.string.validateIpStrings(dto.getMemberIps(), '/', '*');
//		}

		cmmtInsider = CmmtInsider.builder()
				.memberId(dto.getMemberId())
				.loginId(dto.getLoginId())
				.encPasswd(encPasswd)
				.passwdDt(now)
				.passwdInit(true)
				.memberNm(dto.getMemberNm())
				.deptNm(dto.getDeptNm())
				.positionNm(dto.getPositionNm())
				.authorityId(authorityId)
				.memberSt(CodeExt.memberSt.정상)
				.memberStDt(now)
				.encEmail(dto.getEncEmail())
				.encTelNo(dto.getEncTelNo())
				.encMobileNo(dto.getEncMobileNo())
				.memberIps(dto.getMemberIps())
				.createdDt(now)
				.creatorId(worker.getMemberId())
				.updatedDt(now)
				.updaterId(worker.getMemberId())
				.build();

		// 내부자 정보 입력
		cmmtInsiderDao.insert(cmmtInsider);

		// 내부자 정보 이력 입력
		CmmtInsiderHist cmmtInsiderHist = new CmmtInsiderHist();
		CoreUtils.property.copyProperties(cmmtInsiderHist, cmmtInsider);
		cmmtInsiderHist = CmmtInsiderHist.builder()
				.histId(string.getNewId(CodeExt.prefix.이력ID))
				.histDt(now)
				.memberId(dto.getMemberId())
				.workerId(worker.getMemberId())
				.workCn(histMessage.신규사용자)
				.build();

		cmmtInsiderHistDao.insert(cmmtInsiderHist);

		// 이메일 발송
		String emailCn = CoreUtils.file.readFileString("/form/email/email-tmp-createAcc.html");
		if (string.isBlank(emailCn)) {
			throw new InvalidationException("이메일 발송 템플릿을 읽을 수 없습니다.");
		}
		EmailSendParam sendParam = new EmailSendParam();
		sendParam.setContentHtml(true);
		sendParam.setEmailCn(emailCn);
		Map<String, String> tmpParam = new HashMap<>();
		tmpParam.put("memberNm", dto.getMemberNm());
		tmpParam.put("loginId", dto.getLoginId());
		tmpParam.put("passwd", passwd);
		sendParam.addRecipient(dto.getEmail(), dto.getMemberNm(), tmpParam);
		sendParam.setTitle("AICA 계정이 생성되었습니다.");

		EmailResult er = emailUtils.send(sendParam);
		if (er.getSuccessCnt() == 0) {
			throw new InvalidationException("임시 비밀번호 이메일 발송에 오류가 발생하여 작업을 중단하였습니다.");
		}

		cmmtInsider = cmmtInsiderDao.select(cmmtInsider.getMemberId());

		// 입력된 정보 조회
		return cmmtInsider;
	}

	public CmmtInsider getInsider(String memberId) {

		Date now = new Date();

		// 로그인 여부 검사, 내부사용자 여부 검사
		BnMember worker = SecurityUtils.checkWorkerIsInsider();

		// 입력값 검증
		if (string.isBlank(memberId)) {
			throw new InvalidationException(String.format(validateMessageExt.입력없음, "회원ID"));
		}

		CmmtInsider cmmtInsider = cmmtInsiderDao.select(memberId);
		if (cmmtInsider == null) {
			throw new InvalidationException(String.format(validateMessageExt.조회결과없음, "회원ID"));
		}

		// log 기록
		String memberIp = null;
		if (request != null) {
			memberIp = webutils.getRemoteIp(request);
		}

		if (!string.equals(worker.getMemberId(), memberId)) {
			LogtIndvdlinfoLog log = LogtIndvdlinfoLog.builder()
					.logId(string.getNewId(CodeExt.prefix.개인정보조회로그ID))
					.logDt(now)
					.memberId(worker.getMemberId())
					.memberIp(memberIp)
					.workTypeNm("조회")
					.workCn("관리자 관리 상세페이지 조회 업무")
					.trgterId(memberId)
					.build();

			logtIndvdlinfoLogDao.insert(log);
		}

		// 내부자 정보 조회
		return cmmtInsider;
	}

	public CmmtInsider modifyInsider(InsiderMemberDto dto) {

		Date now = new Date();
		InvalidationsException inputValidateErrs = new InvalidationsException();

		// 로그인 여부 검사, 내부사용자 여부 검사
		BnMember worker = SecurityUtils.checkWorkerIsInsider();

		// 입력값 검증
		if (string.isBlank(dto.getMemberId())) {
			inputValidateErrs.add("memberId", String.format(validateMessageExt.입력없음, "회원 ID"));
		}

		if (string.isBlank(dto.getMemberNm())) {
			inputValidateErrs.add("memberNm", String.format(validateMessageExt.입력없음, "회원이름"));
		}

		if (string.isBlank(dto.getDeptNm())) {
			inputValidateErrs.add("deptNm", String.format(validateMessageExt.입력없음, "부서명"));
		}

		if (string.isBlank(dto.getAuthorityId())) {
			inputValidateErrs.add("authorityId", String.format(validateMessageExt.입력없음, "권한"));
		}

		if (string.isBlank(dto.getEmail())) {
			inputValidateErrs.add("email", String.format(validateMessageExt.입력없음, "email"));
		}

		if (string.isBlank(dto.getTelNo())) {
			inputValidateErrs.add("telNo", String.format(validateMessageExt.입력없음, "전화번호"));
		}

		if (string.isBlank(dto.getMobileNo())) {
			inputValidateErrs.add("mobileNo", String.format(validateMessageExt.입력없음, "휴대폰번호"));
		}

		if (string.isNotBlank(dto.getMemberIps())) {
			dto.setMemberIps(string.validateIpStrings(dto.getMemberIps(), '/', '*'));
		}

		if (inputValidateErrs.size() > 0) {
			throw inputValidateErrs;
		}

		CmmtInsider cmmtInsider = cmmtInsiderDao.select(dto.getMemberId());
		if (cmmtInsider == null) {
			throw new InvalidationException(String.format(validateMessageExt.조회결과없음, "사용자 정보"));
		}

		String memberSt = string.upperCase(dto.getMemberSt());
		if (string.isBlank(memberSt)) {
			memberSt = cmmtInsider.getMemberSt();
		}
		else {
			// 내부사용자의 상태는 정상 또는 중지만 해당한다.
			String[] arrMemberSt = {CodeExt.memberSt.정상, CodeExt.memberSt.중지};
			if ( !array.contains(arrMemberSt, dto.getMemberSt()) ) {
				throw new InvalidationException(String.format(validateMessageExt.유효오류, "상태"));
			}
		}

		String authorityId = string.upperCase(dto.getAuthorityId());
		CmmtAuthority cmmtAuthority = cmmtAuthorityDao.select(authorityId);
		if (cmmtAuthority == null) {
			throw new InvalidationException(String.format(validateMessageExt.입력오류, "권한"));
		}

		// 수정 전 정보 복사
		CmmtInsider cmmtInsiderNew = new CmmtInsider();
		CoreUtils.property.copyProperties(cmmtInsiderNew, cmmtInsider);

		// 입력된 정보 세팅
		cmmtInsiderNew.setMemberNm(dto.getMemberNm());
		cmmtInsiderNew.setDeptNm(dto.getDeptNm());
		cmmtInsiderNew.setPositionNm(dto.getPositionNm());
		cmmtInsiderNew.setAuthorityId(authorityId);
		cmmtInsiderNew.setMemberSt(memberSt);
		cmmtInsiderNew.setMemberStDt(now);
		cmmtInsiderNew.setEncEmail(dto.getEncEmail());
		cmmtInsiderNew.setEncTelNo(dto.getEncTelNo());
		cmmtInsiderNew.setEncMobileNo(dto.getEncMobileNo());
		cmmtInsiderNew.setMemberIps(dto.getMemberIps());
		cmmtInsiderNew.setUpdatedDt(now);
		cmmtInsiderNew.setUpdaterId(worker.getMemberId());

		// 복사본에 입력된 정보 입력
		cmmtInsiderDao.update(cmmtInsiderNew);

		// 원본과 수정본 비교 후 수정 이력 업데이트
		CmmtInsiderHist cmmtInsiderHist = new CmmtInsiderHist();

		int cnt = 0;
		String[] message = new String[5];

		if (!cmmtInsider.getMemberSt().equals(cmmtInsiderNew.getMemberSt())) {
			message[cnt] = String.format(histMessage.상태변경, cmmtInsider.getMemberSt(), cmmtInsiderNew.getMemberSt());
			cnt++;
		}

		if (!cmmtInsider.getAuthorityId().equals(cmmtInsiderNew.getAuthorityId())) {
			message[cnt] = String.format(histMessage.권한변경, cmmtInsider.getAuthorityId(), cmmtInsiderNew.getAuthorityId());
			cnt++;
		}

		if (string.isBlank(cmmtInsider.getMemberIps()) && string.isNotBlank(cmmtInsiderNew.getMemberIps())) {
			message[cnt] = String.format(histMessage.IP등록, cmmtInsiderNew.getMemberIps());
			cnt++;
		}else if (!cmmtInsider.getMemberIps().equals(cmmtInsiderNew.getMemberIps())) {
			message[cnt] = String.format(histMessage.IP변경, cmmtInsider.getMemberIps(), cmmtInsiderNew.getMemberIps());
			cnt++;
		}

		List<CmmtInsiderHist> uplist = new ArrayList<>();
		for (int i = 0; i < cnt; i++) {
			cmmtInsiderHist = CmmtInsiderHist.builder()
					.histId(string.getNewId(CodeExt.prefix.이력ID))
					.histDt(now)
					.memberId(dto.getMemberId())
					.workerId(worker.getMemberId())
					.workCn(message[i])
					.build();
			uplist.add(cmmtInsiderHist);
		}

		if (uplist.size() > 0) {
			cmmtInsiderHistDao.insertList(uplist);
		}

		// log 기록
		String memberIp = null;
		if (request != null) {
			memberIp = webutils.getRemoteIp(request);
		}

		if (!string.equals(worker.getMemberId(), dto.getMemberId())) {
			LogtIndvdlinfoLog log = LogtIndvdlinfoLog.builder()
					.logId(string.getNewId(CodeExt.prefix.개인정보조회로그ID))
					.logDt(now)
					.memberId(worker.getMemberId())
					.memberIp(memberIp)
					.workTypeNm("변경")
					.workCn("관리자 관리 정보 변경 업무")
					.trgterId(dto.getMemberId())
					.build();

			logtIndvdlinfoLogDao.insert(log);
		}

		cmmtInsider = cmmtInsiderDao.select(cmmtInsider.getMemberId());

		// 입력 정보 조회
		return cmmtInsider;
	}

	// 수정 이력 목록 조회
	public JsonList<InsiderHistDto> getHistList(String memberId) {

		// 로그인 여부 검사, 내부사용자 여부 검사
		SecurityUtils.checkWorkerIsInsider();

		// 입력값 검증
		if (string.isBlank(memberId)) {
			throw new InvalidationException(String.format(validateMessageExt.입력없음, "회원 ID"));
		}

		CmmtInsider cmmtInsider = cmmtInsiderDao.select(memberId);
		if (cmmtInsider == null) {
			throw new InvalidationException(String.format(validateMessageExt.조회결과없음, "사용자 정보"));
		}

		// 페이지 목록 조회
		List<InsiderHistDto> list = cmmtInsiderHistDao.selectList(memberId);

		// 목록 조회
		return new JsonList<>(list);
	}

	/**
	 * 내부사용자 담당자 검색
	 * 
	 * @param param
	 * @param pageParam
	 * @return
	 */
	public CorePagination<SrchPicItemDto> getSrchPic(SrchPicParam param, CorePaginationParam pageParam) 
	{
		// 로그인 여부 검사, 내부사용자 여부 검사
		SecurityUtils.checkWorkerIsInsider();

		// 전체 건수 확인
		long totalItems = cmmtInsiderDao.selectSrchPicCount(param);

		// 조회할 페이지 구간 정보 확인
		CorePaginationInfo info = new CorePaginationInfo(pageParam.getPage(), pageParam.getItemsPerPage(), totalItems);

		// 페이지 목록 조회
		List<SrchPicItemDto> list = cmmtInsiderDao.selectSrchPicList(param, info.getBeginRowNum(), info.getItemsPerPage());

		CorePagination<SrchPicItemDto> pagination = new CorePagination<>(info, list);

		// 목록 조회
		return pagination;
	}

	public void passwdInit(String memberId) {
		BnMember worker = SecurityUtils.checkWorkerIsInsider();

		CmmtInsider cmmtInsider = cmmtInsiderDao.select(memberId);
		if (cmmtInsider == null) {
			throw new InvalidationException(String.format(validateMessageExt.조회결과없음, "사용자 정보"));
		}

		if (string.isBlank(cmmtInsider.getEmail())) {
			throw new InvalidationException("이메일이 없어 비밀번호를 초기화할 수 없습니다.");
		}

		// 임시비밀번호 생성
		String tmpPasswd = password.getRandomPassword();

		/*
		 * 이메일 발송
		 */
		String emailCn = CoreUtils.file.readFileString("/form/email/email-tmp-passwd.html");
		if (string.isBlank(emailCn)) {
			throw new InvalidationException("이메일 발송 템플릿을 읽을 수 없습니다.");
		}
		EmailSendParam ep = new EmailSendParam();
		ep.setContentHtml(true);
		ep.setEmailCn(emailCn);
		Map<String, String> templateParam = new HashMap<>();
		templateParam.put("tmpPasswd", tmpPasswd);
		ep.addRecipient(cmmtInsider.getEmail(), cmmtInsider.getMemberNm(), templateParam);
		ep.setTitle("(AICA) 임시비밀번호");

		EmailResult er = emailUtils.send(ep);
		if (er.getResultCode() != 0) {
			throw new InvalidationException("임시비밀번호를 이메일로 발송하는데 실패하였습니다.");
		}

		/*
		 * 임시 비밀번호 저장
		 */
		Date now = new Date();
		String encPasswd = CoreUtils.password.encode(tmpPasswd);
		cmmtInsider.setEncPasswd(encPasswd);
		cmmtInsider.setPasswdInit(true);
		cmmtInsider.setPasswdDt(now);
		cmmtInsider.setUpdatedDt(now);
		cmmtInsider.setUpdaterId(worker.getMemberId());

		cmmtInsiderDao.update(cmmtInsider);
	}

    /**
     * 로그인ID 중복 검증
     *
     * @param loginId
     * @return
     */
	public VerifyLoginIdResultDto verifyLoginId(String loginId)
	{
		SecurityUtils.checkWorkerIsInsider();

		// 입력값 검증
        if (string.isBlank(loginId)) {
            throw new InvalidationException(String.format(validateMessageExt.입력없음, "로그인ID"));
        }

        // 로그인ID 건수 조회
        InsiderListParam param = InsiderListParam.builder().loginId(loginId).build();
        long duplicateCnt = cmmtInsiderDao.selectCount(param);

        // 출력 DTO 정의
        VerifyLoginIdResultDto verifyDto = new VerifyLoginIdResultDto();
        verifyDto.setLoginId(loginId);

        // 로그인ID 건수에 따른 중복여부 세팅
        if (duplicateCnt > 0) {
            verifyDto.setDuplicateYn(true);
        }
        else {
            verifyDto.setDuplicateYn(false);
        }

        return verifyDto;
	}

}
