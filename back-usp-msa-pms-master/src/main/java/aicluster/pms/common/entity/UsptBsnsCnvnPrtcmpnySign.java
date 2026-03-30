package aicluster.pms.common.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class UsptBsnsCnvnPrtcmpnySign implements Serializable {
	/**
	 * 사업협약참여기업서명
	 */
	private static final long serialVersionUID = -2548553816163667847L;

	/** 사업협약서명ID */
	String bsnsCnvnPrtcmpnySignId;
	/**사업협약ID*/
	String bsnsCnvnId;
	/**회원ID :  참여기업회원ID */
	String memberId;
	/**사업자서명일시*/
	Date bsnmSignDt;

	/** char_사업자서명일시 */
	String charBsnmSignDt;
	/** 신청자명 */
	String memberNm;
	/** 대표자명 */
	String ceoNm;
	/** 사업자번호*/
	String bizrno;

	/** 생성자ID */
	@JsonIgnore
	private String creatorId;
	/** 생성일시 */
	@JsonIgnore
	private Date createdDt;
	/** 수정자ID */
	@JsonIgnore
	private String updaterId;
	/** 수정일시 */
	@JsonIgnore
	private Date updatedDt;
}
