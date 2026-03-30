package aicluster.pms.api.cnvnchange.dto;

import java.io.Serializable;
import java.util.List;

import aicluster.framework.common.entity.CmmtAttachment;
import aicluster.pms.common.entity.UsptCnvnChangeReq;
import aicluster.pms.common.entity.UsptTaskPrtcmpnyHist;
import aicluster.pms.common.entity.UsptTaskPrtcmpnyInfoHist;
import lombok.Data;

@Data
public class UsptTaskPrtcmpnyInfoHistDto implements Serializable{

	/**
	 *협약변경관리_참여기업 변경이력
	 */
	private static final long serialVersionUID = 3671834787274590643L;
	/*협약변경요청ID*/
	String cnvnChangeReqId;
	/**신청자ID**/
	String applcntId;

	/** 협약변경요청 */
	private UsptCnvnChangeReq usptCnvnChangeReq;
	/** 과제참여기업정보변경이력 변경전*/
	private UsptTaskPrtcmpnyInfoHist usptTaskPrtcmpnyInfoHistBefore;
	/** 과제참여기업정보변경이력 변경 후*/
	private UsptTaskPrtcmpnyInfoHist usptTaskPrtcmpnyInfoHistAfter;

	/** 과제참여기업변경이력 변경전*/
	private List<UsptTaskPrtcmpnyHist> usptTaskPrtcmpnyHistBeforeList;
	/** 과제참여기업변경이력 변경 후*/
	private List<UsptTaskPrtcmpnyHist> usptTaskPrtcmpnyHistAfterList;


	/** 첨부파일 */
	private List<CmmtAttachment> attachFileList;
	/** 첨부파일삭제 List */
	private List<CmmtAttachment> attachFileDeleteList;
}
