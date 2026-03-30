package aicluster.common.api.survey.dto;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SurveyAnsParam implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 6605606458542593699L;
	private String surveyId;
	private List<SurveyQuestionAns> questions;
}
