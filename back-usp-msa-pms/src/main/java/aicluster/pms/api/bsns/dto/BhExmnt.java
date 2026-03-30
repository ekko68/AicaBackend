package aicluster.pms.api.bsns.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class BhExmnt {
	/** 사전검토ID */
	private String bhExmntId;
	/** 사전검토구분명 */
	private String bhExmntDivNm;
	/** I:등록, U:수정, D:삭제 */
	private String flag;
	/** 생성자ID */
	@JsonIgnore
	private String creatorId;
	/** 생성일시 */
	@JsonIgnore
	private Date createdDt;
	/** 수정자ID */
	@JsonIgnore
	private String updaterId;
	/** 수정일시 */
	@JsonIgnore
	private Date updatedDt;
	/** 사전검토 항목 목록 */
	List<BhExmntIem> bhExmntIemList;
}
