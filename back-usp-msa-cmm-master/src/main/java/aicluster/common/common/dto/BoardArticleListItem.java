package aicluster.common.common.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardArticleListItem implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -6430920546823680923L;
	private String articleId;
	private String boardId;
	private String title;
	private String article;  // 목록에서는 게시판 내용 목록을 병합하여 출력한다.
	private Boolean notice;
	private String attachmentGroupId;
	private String imageGroupId;
	private String categoryCd;
	private Boolean posting;
	private Boolean webEditor;
	private String sharedUrl;
	private String pcThumbnailFileId;
	private String mobileThumbnailFileId;
	private String thumbnailAltCn;
	private Long readCnt;
	private String creatorId;
	private String creatorNm;
	private Date createdDt;
	private String updaterId;
	private String updaterNm;
	private Date updatedDt;
	private Long rn;
}
