package aicluster.pms.common.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsptExpertParam implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -1778208862867675245L;

	/** 전문가ID */
	String  expertId;
	/** 전문가명 */
	String  expertNm;
	/** 직장명 */
	String  wrcNm;

	/** 엑셀 다운로드 여부 */
	@JsonIgnore
	private boolean isExcel;

	/** 페이징 처리 */
	@JsonIgnore
	private Long beginRowNum;
	@JsonIgnore
	private Long itemsPerPage;

}
