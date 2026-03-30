package aicluster.common.common.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CmmtSurvey implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -2668199074568235300L;
	private String surveyId;
	private String surveyNm;
	private String systemId;
	private String beginDay;
	private String endDay;
	private String remark;
	private Boolean enabled;
	private Boolean duplicated;
	@JsonIgnore
	private String creatorId;
	@JsonIgnore
	private Date createdDt;
	@JsonIgnore
	private String updaterId;
	@JsonIgnore
	private Date updatedDt;

	private List<CmmtSurveyQuestion> questionList;
}
