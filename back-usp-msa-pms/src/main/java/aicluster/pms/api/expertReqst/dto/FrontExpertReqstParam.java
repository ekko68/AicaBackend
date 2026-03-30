package aicluster.pms.api.expertReqst.dto;

import java.io.Serializable;
import java.util.List;

import aicluster.pms.common.entity.UsptExpert;
import aicluster.pms.common.entity.UsptExpertAcdmcr;
import aicluster.pms.common.entity.UsptExpertCareer;
import aicluster.pms.common.entity.UsptExpertCl;
import aicluster.pms.common.entity.UsptExpertClMapng;
import aicluster.pms.common.entity.UsptExpertCrqfc;
import lombok.Data;

@Data
public class FrontExpertReqstParam implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = -2196936911291179342L;

	/** 약관동의 세션ID */
	private String sessionId;
	/** 신청자 전문가정보 */
	private UsptExpert usptExpert;
	/** 전문가 분야정보 */
	private List<UsptExpertClMapng> usptExpertClMapng;
	/** 전문가 경력정보 */
	private List<UsptExpertCareer> usptExpertCareer;
	/** 전문가 학력정보 */
	private List<UsptExpertAcdmcr> usptExpertAcdmcr;
	/** 전문가 자격증정보 */
	private List<UsptExpertCrqfc> usptExpertCrqfc;
}
