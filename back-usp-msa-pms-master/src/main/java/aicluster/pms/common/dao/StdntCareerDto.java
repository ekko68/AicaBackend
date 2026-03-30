package aicluster.pms.common.dao;

import java.io.Serializable;
import java.util.List;

import aicluster.pms.common.entity.UsptAcdmcr;
import aicluster.pms.common.entity.UsptCrqfc;
import aicluster.pms.common.entity.UsptEtcCareer;
import aicluster.pms.common.entity.UsptJobCareer;
import aicluster.pms.common.entity.UsptMsvc;
import aicluster.pms.common.entity.UsptProgrm;
import aicluster.pms.common.entity.UsptWnpz;
import lombok.Data;

@Data
public class StdntCareerDto implements Serializable {
	private static final long serialVersionUID = 9066965947895054552L;
	/** 신청ID */
	private String applyId;
	/** 접수번호 */
	private String receiptNo;
	/** 희망직무 */
	private String hopeDty;
	/** 이름 */
	private String memberNm;
	/** 매칭여부 */
	private String mtchAt;
	/** 매칭기관 */
	private String mtchInstt;
	/** 사업연도 */
	private String bsnsYear;
	/** 공고명 */
	private String pblancNm;
	/** 이메일 */
	private String email;
	/** 휴대폰번호 */
	private String mobileNo;

	private UsptMsvc msvcInfo;
	private List<UsptAcdmcr> acdmcrList;
	private List<UsptCrqfc> crqfcList;
	private List<UsptJobCareer> jobCareerList;
	private List<UsptEtcCareer> etcCareerList;
	private List<UsptWnpz> wnpzList;
	private List<UsptProgrm> progrmList;
}
