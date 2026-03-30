package aicluster.member.common.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -8111345651157538789L;
	private String memberId;		/** 회원 ID */
	private String loginId;			/** 로그인 ID */
	@JsonIgnore
	private String encPasswd;		/** 암호화된 비밀번호 */
	@JsonIgnore
	private Date passwdDt;			/** 비밀번호 변경일시 */
	@JsonIgnore
	private Boolean passwdInit;		/** 비밀번호 초기여부 */
	private String memberNm;		/** 회원 이름 */
	private String authorityId;		/** 권한 ID */
	private String authorityNm;		/** 권한이름 */
	private String memberSt;		/** 회원상태 */
	private String memberStNm;		/** 회원상태이름 */
	@JsonIgnore
	private Date memberStDt;		/** 회원상태변경일시 */
	private String memberType;		/** 회원구분 */
	private String memberTypeNm;	/** 회원구분이름 */
	@JsonIgnore
	private String ci;				/** 공인인증서 고유식별값 */
	@JsonIgnore
	private String encEmail;		/** 암호화된 이메일 */
	@JsonIgnore
	private String encBirthday;		/** 암호화된 생년월일(개인회원) */
	@JsonIgnore
	private String encMobileNo;		/** 암호화된 휴대폰번호 */
	@JsonIgnore
	private String bizrno;			/** 사업자번호(기업회원) */
	@JsonIgnore
	private String refreshToken;	/** Refresh token */
	@JsonIgnore
	private String creatorId;		/** 생성자 ID */
	@JsonIgnore
	private Date createdDt;			/** 생성일시 */
	@JsonIgnore
	private String updaterId;		/** 수정자 ID */
	@JsonIgnore
	private Date updatedDt;			/** 수정일시 */
	private Long rn;				/** Row 번호 */
}
