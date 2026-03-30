package aicluster.common.api.survey.dto;

import java.io.Serializable;
import java.util.Date;

import bnet.library.util.CoreUtils.date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SurveyAddParam implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -5085660370866260647L;

	private String systemId;
	private String surveyNm;
	private Date beginDay;
	private Date endDay;
	private String remark;
	private Boolean enabled;
	private Boolean duplicated;

	public String getBeginDay() {
		if (this.beginDay == null) {
			return null;
		}

		return date.format(this.beginDay, "yyyyMMdd");
	}

	public String getEndDay() {
		if (this.endDay == null) {
			return null;
		}
		return date.format(this.endDay, "yyyyMMdd");
	}
}
