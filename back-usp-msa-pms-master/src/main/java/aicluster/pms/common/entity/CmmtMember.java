package aicluster.pms.common.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import bnet.library.util.CoreUtils.aes256;
import bnet.library.util.CoreUtils.string;
import lombok.Data;

@Data
public class CmmtMember implements Serializable {
	private static final long serialVersionUID = -1092831500406196908L;
	private String memberId;
	private String loginId;
	@JsonIgnore
	private String encPasswd;
	@JsonIgnore
	private Date passwdDt;
	@JsonIgnore
	private Boolean passwdInit;
	private String memberNm;
	@JsonIgnore
	private String nickname;
	private String gender;
	@JsonIgnore
	private String authorityId;
	@JsonIgnore
	private String memberSt;
	@JsonIgnore
	private Date memberStDt;
	@JsonIgnore
	private String blackListBeginDay;
	@JsonIgnore
	private String blackListEndDay;
	@JsonIgnore
	private String blackListReason;
	@JsonIgnore
	private String memberType;
	@JsonIgnore
	private String ci;
	@JsonIgnore
	private String encEmail;
	@JsonIgnore
	private String encBirthday;
	@JsonIgnore
	private String encMobileNo;
	private String chargerNm;
	private String ceoNm;
	private String bizrno;
	@JsonIgnore
	private String jurirno;
	@JsonIgnore
	private Boolean instr; //강사
	@JsonIgnore
	private Boolean marketingReception;
	@JsonIgnore
	private String refreshToken;
	@JsonIgnore
	private String kakaoToken;
	@JsonIgnore
	private String naverToken;
	@JsonIgnore
	private String googleToken;
	@JsonIgnore
	private Date lastLoginDt;
	@JsonIgnore
	private String creatorId;
	@JsonIgnore
	private Date createdDt;
	@JsonIgnore
	private String updaterId;
	@JsonIgnore
	private Date updatedDt;

	/*
	 * formatting properties
	 */
	@JsonIgnore
	private String memberStNm;
	@JsonIgnore
	private String authorityNm;
	@JsonIgnore
	private String memberTypeNm;

	public String getEmail() {
		if (string.isBlank(this.encEmail)) {
			return null;
		}
		return aes256.decrypt(this.encEmail, this.memberId);
	}

	public String getBirthday() {
		if (string.isBlank(this.encBirthday)) {
			return null;
		}
		return aes256.decrypt(this.encBirthday, this.memberId);
	}

	public String getMobileNo() {
		if (string.isBlank(this.encMobileNo)) {
			return null;
		}
		String mobileNo = aes256.decrypt(this.encMobileNo, this.memberId);
		return mobileNo;
	}
}
