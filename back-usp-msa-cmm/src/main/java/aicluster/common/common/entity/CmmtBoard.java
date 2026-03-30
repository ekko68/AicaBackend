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
@AllArgsConstructor
@NoArgsConstructor
public class CmmtBoard implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -5651152886391978552L;
	private String boardId;
	private String systemId;
	private String boardNm;
	private Long articleCnt;
	private Boolean enabled;
	@JsonIgnore
	private Boolean webEditor;
	private Boolean noticeAvailable;
	private Boolean commentable;
	private Boolean category;
	private String categoryCodeGroup;
	private Boolean attachable;
	private Long attachmentSize;
	private String attachmentExt;
	private Boolean useSharedUrl;
	private Boolean useThumbnail;
	private Boolean useForm;
	private Boolean allReadable;
	@JsonIgnore
	private String creatorId;
	@JsonIgnore
	private Date createdDt;
	@JsonIgnore
	private String updaterId;
	@JsonIgnore
	private Date updatedDt;

	private List<CmmtBoardAuthority> authorityList;
}
