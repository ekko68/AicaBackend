package aicluster.pms.api.expertReqst.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class ExpertClIdParntsDto implements Serializable{

	/**
	 * 전문가 분류코드조회-부모코드
	 */
	private static final long serialVersionUID = 7324848125769374862L;

	/** 부모전문가분류ID */
	String  parntsExpertClId;
	/** 전문가분류명 */
	String  expertClNm;
}
