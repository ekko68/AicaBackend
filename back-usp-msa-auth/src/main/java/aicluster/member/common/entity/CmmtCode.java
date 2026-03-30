package aicluster.member.common.entity;

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
	private static final long serialVersionUID = 8938367906514298837L;
	private String codeGroup;		/** 코드그 */
	private String code;			/** 코드 */
	private String codeNm;			/** 코드명 */
	private String remark;			/** 비고 */
	private String codeType;		/** 코드구분 */
	private Boolean enabled;		/** 사용여부 */
	private Long sortOrder;			/** 정렬순서 */
	@JsonIgnore
	private String creatorId;		/** 생성자ID */
	@JsonIgnore
	private Date createdDt;			/** 생성일시 */
	@JsonIgnore
	private String updaterId;		/** 수정자ID */
	@JsonIgnore
	private Date updatedDt;			/** 수정일시 */


}
