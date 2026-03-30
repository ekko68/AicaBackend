package aicluster.common.common.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CmmtSurveyMemberAns implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -3969935145306011788L;
	private String memberAnsId;
	private String surveyId;
	private String questionId;
	private String answerId;
	private String memberId;
	private String shortAnswer;
	private Date createdDt;
}
