package aicluster.pms.common.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class UsptSlctnPblancDetail implements Serializable {
	private static final long serialVersionUID = 7923411824996241911L;
	/** 선정공고상세ID */
	private String slctnPblancDetailId;
	/** 선정공고ID */
	@JsonIgnore
	private String slctnPblancId;
	/** 제목 */
	private String sj;
	/** 상세내용 */
	private String detailCn;

	/** 변경 플래그(I,U,D) */
	String  flag;
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
