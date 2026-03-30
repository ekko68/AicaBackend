package aicluster.pms.common.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class UsptBsnsPblancDetail implements Serializable {
	private static final long serialVersionUID = 2538472088051683118L;
	/** 공고ID */
	@JsonIgnore
	private String pblancId;
	/** 공고상세 ID */
	private String pblancDetailId;
	/** 제목 */
	private String sj;
	/** 상세내용 */
	private String detailCn;
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
}
