package aicluster.pms.common.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import bnet.library.util.CoreUtils.date;
import lombok.Data;

@Data
public class RsltHistPresentnDto implements Serializable {
	private static final long serialVersionUID = 5938526484420323956L;
	/** 성과이력ID */
	private String rsltHistId;
	/** 처리자명 */
	private String opetrNm;
	@JsonIgnore
	private Date presentnDt;
	public String getPresentnDate() {
		if(this.presentnDt == null) {
			return "";
		}
		return date.format(this.presentnDt, "yyyy-MM-dd");
	}
}
