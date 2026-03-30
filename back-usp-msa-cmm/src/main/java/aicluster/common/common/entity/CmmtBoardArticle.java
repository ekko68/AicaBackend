package aicluster.common.common.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import aicluster.framework.common.entity.CmmtAttachment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CmmtBoardArticle implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -5266077391452519813L;
	private String articleId;
	private String boardId;
	private String title;
	private String article;
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

	/*
	 * Helper property
	 */
	private CmmtBoard cmmtBoard;
	private List<CmmtBoardArticleCn> articleCnList;
	private List<CmmtBoardArticleUrl> articleUrlList;
	private List<CmmtAttachment> attachmentList;
	private List<CmmtAttachment> imageList;
}
