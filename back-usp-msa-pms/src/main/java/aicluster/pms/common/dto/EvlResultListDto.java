package aicluster.pms.common.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class EvlResultListDto implements Serializable {
	private static final long serialVersionUID = -6509112203934498445L;
	/** 평가대상ID */
	private String evlTrgetId;
	/** 접수번호 */
	private String receiptNo;
	/** 평가방식코드 (G: EVL_MTH) */
	private String evlMthdCd;
	/** 선정여부 */
	private Boolean slctnAt;
	/** 회원명 */
	private String memberNm;
	/** 가점 */
	private Integer addScore;
	/** 가점 내용 */
	private String addCn;
	/** 감점 */
	private Integer subScore;
	/** 감정 내용 */
	private String subCn;
	/** 최고점수 */
	private Integer maxEvlScore;
	/** 최저점수 */
	private Integer minEvlScore;
	/** 총점 */
	private Integer sumEvlScore;
	/** 평균 */
	private Float avgEvlScore;
	/** 종합점수 */
	private Float totEvlScore;
	/** 순번 */
	private Long rn;
	/** 평가위원 목록 */
	private List<EvlSttusMfcmmListDto> evlSttusMfcmmList;
}
