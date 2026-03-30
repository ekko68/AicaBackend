package aicluster.common.api.popup.dto;

import java.io.Serializable;
import java.util.Date;

import bnet.library.util.CoreUtils.date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PopupGetListParam implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 4023596074021454246L;

	private String systemId;
	private Boolean posting;
	private String title;
	private Date beginDt;
	private Date endDt;

	public String getBeginDt() {
		if (this.beginDt == null) {
			return null;
		}
		return date.format(this.beginDt, "yyyyMMdd");
	}

	public String getEndDt() {
		if (this.endDt == null) {
			return null;
		}
		return date.format(this.endDt, "yyyyMMdd");
	}
}
