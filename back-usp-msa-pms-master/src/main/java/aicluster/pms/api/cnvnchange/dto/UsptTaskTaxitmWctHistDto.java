package aicluster.pms.api.cnvnchange.dto;

import java.io.Serializable;
import java.util.List;

import aicluster.framework.common.entity.CmmtAttachment;
import aicluster.pms.common.entity.UsptCnvnChangeReq;
import aicluster.pms.common.entity.UsptTaskTaxitmWctHist;
import lombok.Data;

@Data
public class UsptTaskTaxitmWctHistDto implements Serializable{

	/**
	 * 협약변경관리_과제세목별사업비변경이력
	 */
	private static final long serialVersionUID = 8716786490635930727L;

	/*협약변경요청ID*/
	String cnvnChangeReqId;
	/**신청자ID**/
	String applcntId;

	/** 협약변경요청 */
	private UsptCnvnChangeReq usptCnvnChangeReq;

	/** 과제세목별사업비변경이력 변경전*/
	private List<UsptTaskTaxitmWctHist> usptTaskTaxitmWctHistBeforeList;
	/** 과제세목별사업비변경이력 변경 후*/
	private List<UsptTaskTaxitmWctHist> usptTaskTaxitmWctHistAfterList;

	/** 첨부파일 */
	private List<CmmtAttachment> attachFileList;
	/** 첨부파일삭제 List */
	private List<CmmtAttachment> attachFileDeleteList;
}
