package aicluster.pms.common.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import bnet.library.util.CoreUtils.aes256;
import bnet.library.util.CoreUtils.string;
import lombok.Data;

@Data
public class UsptCnvnApplcntHist implements Serializable {
	/**
	 * 협약신청자정보변경이력
	 */
	private static final long serialVersionUID = -1514189826299691105L;
	/** 협약신청자정보변경이력ID */
	String  cnvnApplcntHistId;
	/** 협약변경요청ID */
	String  cnvnChangeReqId;
	/** 승인협약신청자정보변경이력ID */
	String  confmCnvnApplcntHistId;
	/** 신청자명 */
	String  applcntNm;
	/** 성별코드(G:GENDER) */
	String  genderCd;
	/** 암호화된 휴대폰번호 */
	String  encMbtlnum;
	/** 암호화된 생년월일 */
	String  encBrthdy;
	/** 내국인여부 */
	Boolean nativeYn;
	/** 암호화된 이메일 */
	String  encEmail;
	/** 개인사업자구분코드(INDVDL_BSNM_DIV) */
	String indvdlBsnmDivCd;
	/** 담당자명 */
	 String chargerNm;
	 /** 대표자명 */
	 String ceoNm;
	 /** 사업자번호(기업회원) */
	 String bizrno;
	 /*담당자휴대폰번호*/
	String chargerMbtlnum;
	 /*담당자이메일*/
	String chargerEmail;
	 /*변경일자*/
	String changeDe;
	 /*변경전후*/
	String changeBeAf;

	@JsonIgnore
	private String creatorId;		/** 생성자 ID */
	@JsonIgnore
	private Date createdDt;			/** 생성일시 */

	/**
	 * 복호화된 생년월일
	 *
	 * @return
	 */
	public String getBrthdy() {
		if (string.isBlank(this.encBrthdy)) {
			return null;
		}
		return aes256.decrypt(this.encBrthdy, this.cnvnApplcntHistId);
	}

	/**
	 * 복호화된 휴대폰번호
	 *
	 * @return
	 */
	public String getMbtlnum() {
		if (string.isBlank(this.encMbtlnum)) {
			return null;
		}
		return aes256.decrypt(this.encMbtlnum, this.cnvnApplcntHistId);
	}

	/**
	 * 복호화된 이메일
	 *
	 * @return
	 */
	public String getEmail() {
		if (string.isBlank(this.encEmail)) {
			return null;
		}
		return aes256.decrypt(this.encEmail, this.cnvnApplcntHistId);
	}
	/**
	 * 복호화된 담당자 휴대폰번호
	 *
	 * @return
	 */
	public String getDecChargerMbtlnum() {
		if (string.isBlank(this.chargerMbtlnum)) {
			return null;
		}
		return aes256.decrypt(this.chargerMbtlnum, this.cnvnApplcntHistId);
	}

	/**
	 * 복호화된 담당자 이메일
	 *
	 * @return
	 */
	public String getDecChargerEmail() {
		if (string.isBlank(this.chargerEmail)) {
			return null;
		}
		return aes256.decrypt(this.chargerEmail, this.cnvnApplcntHistId);
	}
}
