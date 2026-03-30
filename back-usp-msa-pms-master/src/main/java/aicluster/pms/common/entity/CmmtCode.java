package aicluster.pms.common.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CmmtCode implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 5996994470066215274L;

	/** 코드그굽 */
	private String codeGroup;
	/** 코드 */
	private String code;
	/** 코드명 */
	private String codeNm;
	/** 비고 */
	private String remark;
	/** 코드구분 */
	private String codeType;
	/** 사용여부 */
	private Boolean enabled;
	/** 정렬순서 */
	private Long sortOrder;
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
