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
@AllArgsConstructor
@NoArgsConstructor
public class UsptEvlIemResult implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 140880896091342229L;

	String  evlIemResultId;                 /** 평가항목결과ID */
	String  evlResultId;                    /** 평가결과ID */
	String  evlIemId;                       /** 평가항목ID */
	Integer evlScore;                       /** 평가점수 */
	String  evlCn;                          /** 평가내용 */

	@JsonIgnore
	private String creatorId;		/** 생성자 ID */
	@JsonIgnore
	private Date createdDt;			/** 생성일시 */
	@JsonIgnore
	private String updaterId;		/** 수정자 ID */
	@JsonIgnore
	private Date updatedDt;			/** 수정일시 */
}
