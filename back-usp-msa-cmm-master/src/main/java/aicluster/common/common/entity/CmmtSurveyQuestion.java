package aicluster.common.common.entity;

import java.io.Serializable;
import java.util.List;

import aicluster.common.common.dto.ResultAnswerDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CmmtSurveyQuestion implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -4078308762411950630L;
	private String surveyId;
	private String questionId;
	private Integer questionNo;
	private String questionType;
	private Boolean required;
	private String questionCn;

	private Integer answererCnt;
	private List<ResultAnswerDto> resultAnswerList;
	private List<CmmtSurveyAnswer> answerList;
}
