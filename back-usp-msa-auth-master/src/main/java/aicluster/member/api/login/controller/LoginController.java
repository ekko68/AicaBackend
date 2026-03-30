package aicluster.member.api.login.controller;

import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import aicluster.framework.common.entity.TokenDto;
import aicluster.framework.config.EnvConfig;
import aicluster.framework.security.JwtUtils;
import aicluster.member.api.login.dto.EvlLoginParam;
import aicluster.member.api.login.dto.KakaoLoginParam;
import aicluster.member.api.login.dto.LoginParam;
import aicluster.member.api.login.dto.LoginResult;
import aicluster.member.api.login.dto.SnsLoginParam;
import aicluster.member.api.login.service.LoginService;
import aicluster.member.common.dao.CmmtInsiderDao;
import aicluster.member.common.dao.CmmtLoginTryDao;
import aicluster.member.common.dao.CmmtMemberDao;
import aicluster.member.common.entity.CmmtInsider;
import aicluster.member.common.entity.CmmtLoginTry;
import aicluster.member.common.entity.CmmtMember;
import aicluster.member.common.util.CodeExt;
import bnet.library.exception.InvalidationException;
import bnet.library.util.CoreUtils;

//@Slf4j
@RestController
@RequestMapping("/api/login")
public class LoginController {
	private static final int MAX_LOGIN_FAIL_CNT = 5;

	@Autowired
	private LoginService loginService;
    @Autowired
    private EnvConfig envConfig;
    @Autowired
    private CmmtLoginTryDao cmmtLoginTryDao;
    @Autowired
    private CmmtMemberDao cmmtMemberDao;
    @Autowired
    private CmmtInsiderDao cmmtInsiderDao;

    @PostMapping("/member")
	public LoginResult memberLogin(@RequestBody LoginParam param, HttpServletRequest request, HttpServletResponse response) {
    	// 접속자 IP
    	String userIp = CoreUtils.webutils.getRemoteIp(request);

    	// 로그인 처리
    	LoginResult result = null;
    	try {
    		result = loginService.login_member(param, userIp);
    	} catch (AuthenticationException ex) {
    		/*
    		 * 비밀번호 연속 오류 처리
    		 */
    		CmmtMember cmmtMember = cmmtMemberDao.selectByLoginId(param.getLoginId());
    		if (cmmtMember != null) {
    			cmmtLoginTryDao.save(cmmtMember.getMemberId(), userIp);
    			CmmtLoginTry clt = cmmtLoginTryDao.select(cmmtMember.getMemberId(), userIp);
    			if (clt != null && clt.getTryCnt() >= MAX_LOGIN_FAIL_CNT) {
    				Date now = new Date();
    				cmmtMember.setMemberSt(CodeExt.memberSt.잠김);
    				cmmtMember.setMemberStDt(now);
    				cmmtMemberDao.update(cmmtMember);
    				throw new InvalidationException(String.format("비밀번호 %d회 연속 실패로 계정이 잠겼습니다.", MAX_LOGIN_FAIL_CNT));
    			}
    		}
    		throw ex;
    	}

        /*
         * Cookie 설정
         */
		Cookie cookie = new Cookie(JwtUtils.cookieRefreshToken.회원용, result.getRefreshToken());
    	cookie.setMaxAge(envConfig.getCookie().getMaxAge());
    	cookie.setSecure(envConfig.getCookie().getSecure());
    	cookie.setHttpOnly(envConfig.getCookie().getHttpOnly());
    	cookie.setDomain(envConfig.getCookie().getDomain());
    	cookie.setPath(envConfig.getCookie().getPath());
    	response.addCookie(cookie);

		response.setHeader("Set-AccessToken", result.getAccessToken());
		response.setHeader("Set-RefreshTokenExpiresIn", "" + result.getRefreshTokenExpiresIn());

		return result;
	}

	@PostMapping("/insider")
	public LoginResult insiderLogin(@RequestBody LoginParam param, HttpServletRequest request, HttpServletResponse response) {
		String userIp = CoreUtils.webutils.getRemoteIp(request);

    	LoginResult result = null;
    	try {
    		result = loginService.login_insider(param, userIp);
    	} catch (AuthenticationException ex) {
    		/*
    		 * 비밀번호 연속 오류 처리
    		 */
    		CmmtInsider cmmtInsider = cmmtInsiderDao.selectByLoginId(param.getLoginId());
    		if (cmmtInsider != null) {
    			cmmtLoginTryDao.save(cmmtInsider.getMemberId(), userIp);
    			CmmtLoginTry clt = cmmtLoginTryDao.select(cmmtInsider.getMemberId(), userIp);
    			if (clt != null && clt.getTryCnt() >= MAX_LOGIN_FAIL_CNT) {
    				Date now = new Date();
    				cmmtInsider.setMemberSt(CodeExt.memberSt.잠김);
    				cmmtInsider.setMemberStDt(now);
    				cmmtInsiderDao.update(cmmtInsider);
    				throw new InvalidationException(String.format("비밀번호 %d회 연속 실패로 계정이 잠겼습니다.", MAX_LOGIN_FAIL_CNT));
    			}
    		}
    		throw ex;
    	}

		Cookie cookie = new Cookie(JwtUtils.cookieRefreshToken.내부사용자용, result.getRefreshToken());
    	cookie.setMaxAge(envConfig.getCookie().getMaxAge());
    	cookie.setSecure(envConfig.getCookie().getSecure());
    	cookie.setHttpOnly(envConfig.getCookie().getHttpOnly());
    	cookie.setDomain(envConfig.getCookie().getDomain());
    	cookie.setPath(envConfig.getCookie().getPath());
    	response.addCookie(cookie);

		response.setHeader("Set-AccessToken", result.getAccessToken());
		response.setHeader("Set-RefreshTokenExpiresIn", "" + result.getRefreshTokenExpiresIn());

        return result;
	}

	@PostMapping("/evaluator")
	public LoginResult evaluatorLogin(@RequestBody EvlLoginParam param, HttpServletRequest request, HttpServletResponse response) {
    	String userIp = CoreUtils.webutils.getRemoteIp(request);
    	LoginResult result = loginService.login_evaluator(param, userIp);

		Cookie cookie = new Cookie(JwtUtils.cookieRefreshToken.회원용, result.getRefreshToken());
    	cookie.setMaxAge(envConfig.getCookie().getMaxAge());
    	cookie.setSecure(envConfig.getCookie().getSecure());
    	cookie.setHttpOnly(envConfig.getCookie().getHttpOnly());
    	cookie.setDomain(envConfig.getCookie().getDomain());
    	cookie.setPath(envConfig.getCookie().getPath());
    	response.addCookie(cookie);

		response.setHeader("Set-AccessToken", result.getAccessToken());
		response.setHeader("Set-RefreshTokenExpiresIn", "" + result.getRefreshTokenExpiresIn());

        return result;
	}

	@PostMapping("/sns/naver")
	public LoginResult naverLogin(@RequestBody SnsLoginParam param, HttpServletRequest request, HttpServletResponse response) {
    	// 접속자 IP
    	String userIp = CoreUtils.webutils.getRemoteIp(request);

    	// 로그인 처리
    	LoginResult result = loginService.login_memberNaver(param, userIp);

        /*
         * Cookie 설정
         */
		Cookie cookie = new Cookie(JwtUtils.cookieRefreshToken.회원용, result.getRefreshToken());
    	cookie.setMaxAge(envConfig.getCookie().getMaxAge());
    	cookie.setSecure(envConfig.getCookie().getSecure());
    	cookie.setHttpOnly(envConfig.getCookie().getHttpOnly());
    	cookie.setDomain(envConfig.getCookie().getDomain());
    	cookie.setPath(envConfig.getCookie().getPath());
    	response.addCookie(cookie);

		response.setHeader("Set-AccessToken", result.getAccessToken());
		response.setHeader("Set-RefreshTokenExpiresIn", "" + result.getRefreshTokenExpiresIn());

		return result;
	}

	@PostMapping("/sns/google")
	public LoginResult googleLogin(@RequestBody SnsLoginParam param, HttpServletRequest request, HttpServletResponse response) {
    	// 접속자 IP
    	String userIp = CoreUtils.webutils.getRemoteIp(request);

    	// 로그인 처리
    	LoginResult result = loginService.login_memberGoogle(param, userIp);

        /*
         * Cookie 설정
         */
		Cookie cookie = new Cookie(JwtUtils.cookieRefreshToken.회원용, result.getRefreshToken());
    	cookie.setMaxAge(envConfig.getCookie().getMaxAge());
    	cookie.setSecure(envConfig.getCookie().getSecure());
    	cookie.setHttpOnly(envConfig.getCookie().getHttpOnly());
    	cookie.setDomain(envConfig.getCookie().getDomain());
    	cookie.setPath(envConfig.getCookie().getPath());
    	response.addCookie(cookie);

		response.setHeader("Set-AccessToken", result.getAccessToken());
		response.setHeader("Set-RefreshTokenExpiresIn", "" + result.getRefreshTokenExpiresIn());

		return result;
	}

	@PostMapping("/sns/kakao")
	public LoginResult kakaoLogin(@RequestBody KakaoLoginParam param, HttpServletRequest request, HttpServletResponse response) {
    	// 접속자 IP
    	String userIp = CoreUtils.webutils.getRemoteIp(request);

    	// 로그인 처리
    	LoginResult result = loginService.login_memberKakao(param, userIp);

        /*
         * Cookie 설정
         */
		Cookie cookie = new Cookie(JwtUtils.cookieRefreshToken.회원용, result.getRefreshToken());
    	cookie.setMaxAge(envConfig.getCookie().getMaxAge());
    	cookie.setSecure(envConfig.getCookie().getSecure());
    	cookie.setHttpOnly(envConfig.getCookie().getHttpOnly());
    	cookie.setDomain(envConfig.getCookie().getDomain());
    	cookie.setPath(envConfig.getCookie().getPath());
    	response.addCookie(cookie);

		response.setHeader("Set-AccessToken", result.getAccessToken());
		response.setHeader("Set-RefreshTokenExpiresIn", "" + result.getRefreshTokenExpiresIn());

		return result;
	}

	@PostMapping("/refresh-token/member")
    public TokenDto refreshTokenMember(HttpServletRequest request, HttpServletResponse response) {
		return loginService.refreshToken_member(request, response);
    }

	@PostMapping("/refresh-token/insider")
    public TokenDto refreshTokenInsider(HttpServletRequest request, HttpServletResponse response) {
		return loginService.refreshToken_insider(request, response);
    }

	@PostMapping("/refresh-token/evaluator")
	public TokenDto refreshTokenEvaluator(HttpServletRequest request, HttpServletResponse response) {
		return loginService.refreshToken_evaluator(request, response);
	}
}
