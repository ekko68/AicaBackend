package aicluster.member.api.logout.service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aicluster.framework.common.dao.FwInsiderDao;
import aicluster.framework.common.dao.FwMemberDao;
import aicluster.framework.common.dao.FwUsptEvlMfcmmDao;
import aicluster.framework.common.entity.BnMember;
import aicluster.framework.security.JwtUtils;
import aicluster.framework.security.SecurityUtils;
import aicluster.member.common.dao.CmmtInsiderDao;
import aicluster.member.common.dao.CmmtMemberDao;
import aicluster.member.common.entity.CmmtInsider;
import aicluster.member.common.entity.CmmtMember;
import aicluster.member.common.util.CodeExt;
import bnet.library.exception.UnauthorizedException;
import bnet.library.util.CoreUtils;

@Service
public class LogoutService {

	@Autowired
	private CmmtMemberDao cmmtMemberDao;
	@Autowired
	private CmmtInsiderDao cmmtInsiderDao;
	@Autowired
	private FwUsptEvlMfcmmDao fwUsptEvlMfcmmDao;
	@Autowired
	private FwMemberDao fwMemberDao;
	@Autowired
	private FwInsiderDao fwInsiderDao;

	public void logout_member(HttpServletRequest request) {
		/*
		 * 1. AccessToken 또는 RefreshToken으로 사용자 정보 조회
		 * 2. RefreshToken 삭제
		 */

		// AccessToken으로 사용자 정보 조회
		BnMember worker = SecurityUtils.getCurrentMember();
		if (worker == null) {
			// Refresh token으로 사용자 정보 조회
			Cookie cookie = CoreUtils.webutils.getCookie(request, JwtUtils.cookieRefreshToken.회원용);
	    	if (cookie == null) {
	    		return;
	    	}

	    	String refreshToken = cookie.getValue();
	    	worker = fwMemberDao.selectBnMember_refreshToken(refreshToken);
	        if (worker == null) {
	        	// 평가위원인지 확인
	        	worker = fwUsptEvlMfcmmDao.selectBnMember_refreshToken(refreshToken);
	        	if (worker == null) {
	        		return;
	        	}
	        }
		}

		// 평가위원이면, 평가위원 로그아웃
		if ( CodeExt.memberTypeExt.isEvaluator(worker.getMemberType()) ) {
			logout_evaluator(worker.getMemberId());
			return;
		}

		// 일반회원이면, 일반위원 로그아웃
		CmmtMember member = cmmtMemberDao.select(worker.getMemberId());
		if (member == null) {
			return;
		}
		member.setRefreshToken(null);
		cmmtMemberDao.update(member);
	}

	public void logout_evaluator(String memberId) {
		fwUsptEvlMfcmmDao.updateRefreshToken(memberId, null);
	}

	public void logout_insider(HttpServletRequest request) {
		/*
		 * 1. AccessToken 또는 RefreshToken으로 사용자 정보 조회
		 * 2. RefreshToken 삭제
		 */

		// AccessToken으로 사용자정보 조회
		BnMember worker = SecurityUtils.getCurrentMember();
		if (worker == null) {
			Cookie cookie = CoreUtils.webutils.getCookie(request, JwtUtils.cookieRefreshToken.내부사용자용);
	    	if (cookie == null) {
	    		throw new UnauthorizedException();
	    	}

	    	String refreshToken = cookie.getValue();
	    	worker = fwInsiderDao.selectBnMember_refreshToken(refreshToken);
	        if (worker == null) {
	        	// 평가위원인지 확인
	        	worker = fwUsptEvlMfcmmDao.selectBnMember_refreshToken(refreshToken);
	        	if (worker == null) {
	        		return;
	        	}
	        }
		}

		//TODO : (유영민) 내부사용자 페이지에 평가위원이 있을 수 있나?
		// 평가위원이면, 평가위원 로그아웃
		if ( CodeExt.memberTypeExt.isEvaluator(worker.getMemberType()) ) {
			logout_evaluator(worker.getMemberId());
			return;
		}

		CmmtInsider insider = cmmtInsiderDao.select(worker.getMemberId());
		if (insider == null) {
			return;
		}
		insider.setRefreshToken(null);
		cmmtInsiderDao.update(insider);
	}
}
