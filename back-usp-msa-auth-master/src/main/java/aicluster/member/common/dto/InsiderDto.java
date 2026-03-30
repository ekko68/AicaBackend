package aicluster.member.common.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InsiderDto implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 7086755507474294553L;
	private String memberId;		/** 회원ID */
	private String loginId;			/** 로그인ID */
	@JsonIgnore
	private String encPasswd;		/** 암호화된 비밀번호 */
	@JsonIgnore
	private Date passwdDt;			/** 비밀번호 변경일시 */
	@JsonIgnore
	private Boolean passwdInit;		/** 비밀번호 초기화여부 */
	private String memberNm;		/** 회원이름 */
	private String nickname;
	@JsonIgnore
	private String gender;
	private String deptNm;
	private String positionNm;
	private String authorityId;		/** 권한ID */
	private String authorityNm;		/** 권한명 */
	private String memberSt;		/** 회원상태 */
	private String memberStNm;		/** 회원상태명 */
	@JsonIgnore
	private Date memberStDt;		/** 회원상태 변경일시 */
	@JsonIgnore
	private String encTelNo;		/** 암호화된 전화번호 */
	@JsonIgnore
	private String encMobileNo;		/** 암호화된 휴대폰번호 */
	@JsonIgnore
	private String encEmail;		/** 암호화된 이메일 */
	@JsonIgnore
	private String memberIps;
	@JsonIgnore
	private String refreshToken;	/** Refresh token */
	@JsonIgnore
	private String creatorId;		/** 생성자ID */
	@JsonIgnore
	private Date createdDt;			/** 생성일시 */
	@JsonIgnore
	private String updaterId;		/** 수정자ID */
	@JsonIgnore
	private Date updatedDt;			/** 수정일시 */
	@JsonIgnore
	private Date lastLoginDt;
	private Long rn;				/** Row 번호 */
}
