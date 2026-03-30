package aicluster.pms.api.bsns.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import aicluster.pms.common.entity.UsptStdrApplyRealm;
import aicluster.pms.common.entity.UsptStdrAppnTask;
import lombok.Data;

@Data
public class StdrBsnsTaskDto implements Serializable {
	private static final long serialVersionUID = -3751090720019054905L;
	/** 기준사업코드 */
	private String stdrBsnsCd;
	/** 과제유형코드 */
	private String taskTypeCd;
	/** 지원분야 */
	private List<UsptStdrApplyRealm> stdrApplyRealmList;
	/** 지정과제 */
	private List<UsptStdrAppnTask> stdrAppnTaskList;
	/** 수정자ID */
	@JsonIgnore
	private String updaterId;
	/** 수정일시 */
	@JsonIgnore
	private Date updatedDt;

}
