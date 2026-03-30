package aicluster.common.api.survey.dto;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionSaveParam implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -8403004261666803382L;
	private String surveyId;
	private List<Question> questionList;
}
