package aicluster.pms.api.cnvnchange.dto;

import java.io.Serializable;
import java.util.List;

import aicluster.framework.common.entity.CmmtAttachment;
import aicluster.pms.common.entity.UsptCnvnChangeReq;
import aicluster.pms.common.entity.UsptTaskRspnberHist;
import lombok.Data;

@Data
public class UsptTaskRspnberHistDto implements Serializable{

	/**
	 * 협약변경관리_과제책임자변경이력
	 */
	private static final long serialVersionUID = -7368978600283376813L;

	/*협약변경요청ID*/
	String cnvnChangeReqId;
	/**신청자ID**/
	String applcntId;

	/** 협약변경요청 */
	private UsptCnvnChangeReq usptCnvnChangeReq;
	/** 과제책임자변경이력 변경전*/
	private UsptTaskRspnberHist usptTaskRspnberHistBefore;
	/** 과제책임자변경이력 변경 후*/
	private UsptTaskRspnberHist usptTaskRspnberHistAfter;

	/** 첨부파일 */
	private List<CmmtAttachment> attachFileList;
	/** 첨부파일삭제 List */
	private List<CmmtAttachment> attachFileDeleteList;
}
