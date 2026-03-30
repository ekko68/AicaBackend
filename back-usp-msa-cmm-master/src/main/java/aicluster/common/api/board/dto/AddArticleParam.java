package aicluster.common.api.board.dto;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddArticleParam implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 580591200431838254L;
	private String boardId;
	private String title;
	private Boolean notice;
	private String article;
	private String categoryCd;
	private Boolean posting;
	private Boolean webEditor;
	private String sharedUrl;
	private String thumbnailAltCn;

	private List<BoardArticleCnParam> articleCnList;
	private List<BoardArticleUrlParam> articleUrlList;
}
