package aicluster.common.common.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CmmtBoardCmmnt implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 576346897499988264L;
	private String articleId;
	private String cmmntId;
	private String cmmnt;
	private String creatorId;
	private Date createdDt;
	private String updaterId;
	private Date updatedDt;
}
