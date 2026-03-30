package aicluster.pms.common.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class UsptEvlIemResultAdsbtr implements Serializable {
	private static final long serialVersionUID = 7367855948543065264L;
	/** 평가항목결과가감ID */
	private String evlIemResultAdsbtrId;
	/** 평가항목ID */
	private String evlIemId;
	/** 평가대상ID */
	private String evlTrgetId;
	/** 평가위원회ID */
	private String evlCmitId;
	/** 평가점수 */
	private Integer evlScore;
	/** 평가내용 */
	private String evlCn;
	/** 가감사유내용 */
	private String adsbtrResnCn;
	/** 생성자 ID */
	@JsonIgnore
	private String creatorId;
	/** 생성일시 */
	@JsonIgnore
	private Date createdDt;
	/** 수정자 ID */
	@JsonIgnore
	private String updaterId;
	/** 수정일시 */
	@JsonIgnore
	private Date updatedDt;
}
