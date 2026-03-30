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
public class CmmtAuthority implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 3690642526794641160L;
	private String authorityId;
	private String authorityNm;
	private String purpose;
	private String creatorId;
	private Date createdDt;
	private String updaterId;
	private Date updatedDt;
}
