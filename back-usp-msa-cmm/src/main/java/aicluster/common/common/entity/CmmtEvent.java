package aicluster.common.common.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
public class CmmtEvent implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 6892923799416157764L;
	private String eventId;
	private String eventNm;
	private Boolean posting;
	private String beginDay;
	private String endDay;
	private String pcThumbnailFileId;
	private String mobileThumbnailFileId;
	private String thumbnailAltCn;
	private String pcImageFileId;
	private String mobileImageFileId;
	private String imageAltCn;
	private String url;
	private Boolean newWindow;
	private String attachmentGroupId;
	@JsonIgnore
	private String creatorId;
	@JsonIgnore
	private Date createdDt;
	@JsonIgnore
	private String updaterId;
	@JsonIgnore
	private Date updatedDt;

	private Long readCnt;

	/*
	 * Helper
	 */
	private List<CmmtEventCn> eventCnList;

	public String getFmtBeginDay() {
		if (string.isBlank(this.beginDay)) {
			return null;
		}

		return date.format(string.toDate(this.beginDay), "yyyy-MM-dd");
	}

	public String getFmtEndDay() {
		if (string.isBlank(this.endDay)) {
			return null;
		}

		return date.format(string.toDate(this.endDay), "yyyy-MM-dd");
	}

	public String getFmtCreatedDay() {
		if (this.createdDt == null) {
			return null;
		}

		return date.format(this.createdDt, "yyyy-MM-dd");
	}
}
