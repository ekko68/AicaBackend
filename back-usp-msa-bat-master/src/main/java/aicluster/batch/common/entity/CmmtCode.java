package aicluster.batch.common.entity;

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
public class CmmtCode implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 3704800633487444140L;
	private String codeGroup;
	private String code;
	private String codeNm;
	private String remark;
	private String codeType;
	private Boolean enabled;
	private Long sortOrder;
	private String creatorId;
	private Date createdDt;
	private String updaterId;
	private Date updatedDt;
}
