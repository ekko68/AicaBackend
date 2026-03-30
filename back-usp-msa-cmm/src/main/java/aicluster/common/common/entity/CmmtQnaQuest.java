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
public class CmmtQnaQuest implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 3968112303972794825L;
	private String questId;
	private String qnaId;
	private String categoryCd;
	private String questSt;
	private Date questStDt;
	private String title;
	private String question;
	private String questAttachmentGroupId;
	private String questionerId;
	private Date questCreatedDt;
	private Date questUpdatedDt;
	private String answer;
	private String answerAttachmentGroupId;
	private String answerCreatorId;
	private Date answerCreatedDt;
	private String answerUpdaterId;
	private Date answerUpdatedDt;
	private Long rn;

	/*
	 * Helper
	 */
	private String questionerNm;
	private String answererNm;

	private List<CmmtAttachment> questAttachmentList;
	private List<CmmtAttachment> answerAttachmentList;

	private CmmtMember questioner;
	private CmmtInsider answerer;
}
