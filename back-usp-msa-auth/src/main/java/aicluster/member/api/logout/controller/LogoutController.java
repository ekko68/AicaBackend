package aicluster.member.api.logout.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import aicluster.framework.security.JwtUtils;
import aicluster.member.api.logout.service.LogoutService;
import bnet.library.util.CoreUtils;

//@Slf4j
@RestController
@RequestMapping("/api/logout")
public class LogoutController {
	@Autowired
	private LogoutService logoutService;

	@PostMapping("/member")
    public void logout_member(HttpServletRequest request, HttpServletResponse response) {
    	logoutService.logout_member(request);
    	Cookie cookie = CoreUtils.webutils.getCookie(request, JwtUtils.cookieRefreshToken.회원용);
    	if (cookie != null) {
    		cookie.setValue(null);
    		cookie.setMaxAge(0);
    		response.addCookie(cookie);
    	}

    	response.setHeader("Set-AccessToken", "removed");
		response.setHeader("Set-RefreshTokenExpiresIn", "0");
    }

    @PostMapping("/insider")
    public void logout_insider(HttpServletRequest request, HttpServletResponse response) {
    	logoutService.logout_insider(request);
    	Cookie cookie = CoreUtils.webutils.getCookie(request, JwtUtils.cookieRefreshToken.내부사용자용);
    	if (cookie != null) {
    		cookie.setValue(null);
    		cookie.setMaxAge(0);
    		response.addCookie(cookie);
    	}

    	response.setHeader("Set-AccessToken", "removed");
		response.setHeader("Set-RefreshTokenExpiresIn", "0");
    }

}
