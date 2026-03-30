package aicluster.pms.common.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import bnet.library.util.CoreUtils.aes256;
import bnet.library.util.CoreUtils.string;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsptUdstdprcp implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -2543357889951144609L;

	/*이해관계자 entity*/
	/** 변경 플래그(I,U,D) */
	String  flag;

	/** 이해관계자ID */
	String  udstdprcpId;
	/** 평가계획ID */
	String  evlPlanId;
	/** 이름 */
	String  nm;
	/** 성별코드(G:GENDER) */
	String  genderCd;
	/** 성별명(G:GENDER) */
	String  genderNm;
	/** 기관명 */
	String  insttNm;
	/** 최종학교 */
	String  lastSchul;
	/** 학과 */
	String  subjct;
	/** 생년월일 */
	String  brthdy;
	/** 휴대폰번호 */
	String  mbtlnum;
	/** 암호화된 생년월일 */
	String  encBrthdy;
	/** 암호화된 휴대폰번호 */
	String  encMbtlnum;
	/** 사업자번호 */
	String  bizrno;

	/** 생성자 ID */
	@JsonIgnore
	private String creatorId;
	/** 생성일시 */
	@JsonIgnore
	private Date createdDt;
	/** 수정자 ID */
	@JsonIgnore
	private String updaterId;
	/** 수정일시 */
	@JsonIgnore
	private Date updatedDt;

	/**
	 * 복호화된 생년월일
	 *
	 * @return
	 */
	public String getBrthdy() {
		if (string.isBlank(this.encBrthdy)) {
			return null;
		}
		return aes256.decrypt(this.encBrthdy, this.udstdprcpId);
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
		return aes256.decrypt(this.encMbtlnum, this.udstdprcpId);
	}


}
