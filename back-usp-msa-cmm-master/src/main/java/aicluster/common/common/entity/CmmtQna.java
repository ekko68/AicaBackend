package aicluster.common.common.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CmmtQna implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1305010762425671956L;
	private String qnaId;
	private String qnaNm;
	private String systemId;
	private Long articleCnt;
	private Boolean category;
	private String categoryCodeGroup;
	private Boolean attachable;
	private Long attachmentSize;
	private String attachmentExt;
	private Boolean enabled;
	private String creatorId;
	private Date createdDt;
	private String updaterId;
	private Date updatedDt;

	/*
	 * Helper
	 */
	private List<CmmtQnaAnswerer> answererList;
}
