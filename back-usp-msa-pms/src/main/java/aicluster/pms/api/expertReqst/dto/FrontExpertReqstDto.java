package aicluster.pms.api.expertReqst.dto;

import java.io.Serializable;
import java.util.List;

import aicluster.pms.common.entity.UsptExpertCl;
import lombok.Data;

@Data
public class FrontExpertReqstDto implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = -7332892624338263902L;
	/**회원ID**/
	String MemberId;
	/**회원명(사업자명)**/
	String MemberNm;
	/**성별**/
	String Gender;
	/**생년월일**/
	String EncBirthday;
	/**휴대폰번호**/
	String EncMobileNo;
	/**이메일**/
	String EncEmail;
	/** 전문가분류ID */
	String  expertClId;
}
