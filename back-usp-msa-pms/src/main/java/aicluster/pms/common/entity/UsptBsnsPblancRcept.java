package aicluster.pms.common.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class UsptBsnsPblancRcept implements Serializable {
	private static final long serialVersionUID = 6136007917204480012L;

	/** 변경 플래그(I,U,D) */
	private String flag;
	/** 그리드 선택여부*/
	private Boolean check;
	/** 순번 */
	private int rn;

	/** 공고ID */
	private String pblancId;
	/** 접수차수 */
	private Integer rceptOdr;
	/** 시작일시 */
	private Date beginDt;
	/** 종료일시 */
	private Date endDt;
	/** 모집기간 */
	private String rcritPd;

	/** 페이징 처리 */
	@JsonIgnore
	private Long beginRowNum;
	@JsonIgnore
	private Long itemsPerPage;

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
