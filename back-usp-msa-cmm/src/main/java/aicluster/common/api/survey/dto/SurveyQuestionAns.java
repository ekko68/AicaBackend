package aicluster.common.api.survey.dto;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SurveyQuestionAns implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -3127165855845468789L;
	private String questionId;
	private List<SurveyAnswer> answers;
}
