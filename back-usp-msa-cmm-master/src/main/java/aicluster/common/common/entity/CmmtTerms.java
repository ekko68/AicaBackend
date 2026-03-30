package aicluster.common.common.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import bnet.library.util.CoreUtils.date;
import bnet.library.util.CoreUtils.string;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CmmtTerms implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -4724389249877474706L;
	private String termsType;
	private String termsTypeNm;
	private String beginDay;
	private boolean required;
	private String termsCn;
	private String possessTermCd;
	private String possessTermNm;
	@JsonIgnore
	private String creatorId;
	@JsonIgnore
	private Date createdDt;
	@JsonIgnore
	private String updaterId;
	@JsonIgnore
	private Date updatedDt;

	public String getFmtBeginDay() {
		if (string.isBlank(this.beginDay) || !date.isValidDate(this.beginDay, "yyyyMMdd")) {
			return null;
		}
		return date.format(string.toDate(this.beginDay), "yyyy-MM-dd");
	}
}
