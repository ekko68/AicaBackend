package aicluster.pms.api.expertClfc.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class ExpertClfcMapngParam implements Serializable {

	/**
	 *전문가단 담당자
	 */
	private static final long serialVersionUID = -4011893564867944384L;

	String expertNm; 			/**전문가명**/
	String wrcNm; 				/**부서명**/

	/** 페이징 처리 */
	@JsonIgnore
	private Long beginRowNum;
	@JsonIgnore
	private Long itemsPerPage;


}
