package aicluster.common.api.survey.dto;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 4096841369613989575L;
	private String questionType;
	private Boolean required;
	private String questionCn;
	private List<String> answerCnList;
}
