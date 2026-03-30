package aicluster.pms.api.cnvnchange.dto;

import java.io.Serializable;
import java.util.List;

import aicluster.framework.common.entity.CmmtAttachment;
import aicluster.pms.common.entity.UsptCnvnChangeReq;
import aicluster.pms.common.entity.UsptCnvnTaskInfoHist;
import lombok.Data;

@Data
public class UsptCnvnTaskInfoHistDto implements Serializable{

	/**
	 *협약변경관리_협약과제정보변경이력
	 */
	private static final long serialVersionUID = 2885578045486410158L;

	/*협약변경요청ID*/
	String cnvnChangeReqId;
	/**신청자ID**/
	String applcntId;

	/** 협약변경요청 */
	private UsptCnvnChangeReq usptCnvnChangeReq;
	/** 협약과제정보변경이력 변경전*/
	private UsptCnvnTaskInfoHist usptCnvnTaskInfoHistBefore;
	/** 협약과제정보변경이력 변경 후*/
	private UsptCnvnTaskInfoHist usptCnvnTaskInfoHistAfter;

	/** 첨부파일 */
	private List<CmmtAttachment> attachFileList;
	/** 첨부파일삭제 List */
	private List<CmmtAttachment> attachFileDeleteList;
}
