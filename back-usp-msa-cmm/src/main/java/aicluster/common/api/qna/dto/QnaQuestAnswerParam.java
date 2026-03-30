package aicluster.common.api.qna.dto;

import java.io.Serializable;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QnaQuestAnswerParam implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -5708083016185137092L;
	private String qnaId;
	private String questId;
	private String categoryCd;
	private String title;
	private String question;
	private List<MultipartFile> fileList;
}
