package aicluster.pms.common.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class UsptWctTaxitm implements Serializable {
	private static final long serialVersionUID = 2289810861743297515L;
	@JsonIgnore
	private String wctIoeId;		/** 사업비비목ID */
	private String wctTaxitmId;		/** 사업비세목ID */
	private String wctTaxitmNm;		/** 사업비세목명 */

	private String flag;			/** I:등록, U:수정, D:삭제 */
	@JsonIgnore
	private String creatorId;		/** 생성자ID */
	@JsonIgnore
	private Date createdDt;			/** 생성일시 */
	@JsonIgnore
	private String updaterId;		/** 수정자ID */
	@JsonIgnore
	private Date updatedDt;			/** 수정일시 */
}
