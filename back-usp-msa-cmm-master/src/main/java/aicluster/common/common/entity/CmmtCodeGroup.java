package aicluster.common.common.entity;

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
public class CmmtCodeGroup implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -2962826394518097918L;
	private String codeGroup;
	private String codeGroupNm;
	private String remark;
	private Boolean enabled;
	private String creatorId;
	private Date createdDt;
	private String updaterId;
	private Date updatedDt;
}
