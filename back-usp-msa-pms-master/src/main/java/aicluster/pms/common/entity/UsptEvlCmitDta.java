package aicluster.pms.common.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsptEvlCmitDta implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1948177796263925018L;

	/** 평가계획ID */
	String  evlPlanId;
	/** 평가위원회ID */
	String  evlCmitId;
	/** 분과ID */
	String  sectId;
	/** 분과명 */
	String  sectNm;
	/** 평가단계ID */
	String  evlStepId;
	/** 평가단계명 */
	String  evlStepNm;
	/** 평가표ID */
	String  evlTableId;
	/** 첨부파일그룹ID */
	String  attachmentGroupId;

	@JsonIgnore
	/** 생성자 ID */
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
}
