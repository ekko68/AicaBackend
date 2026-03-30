package aicluster.pms.common.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import bnet.library.util.CoreUtils.date;
import lombok.Data;

@Data
public class UsptRslt implements Serializable {
	private static final long serialVersionUID = 3708093286198073381L;
	/** 성과ID */
	private String rsltId;
	/** 신청ID */
	private String applyId;
	/** 성과상태코드(G:RSLT_STTUS) */
	private String rsltSttusCd;
	/** 성과상태 */
	private String rsltSttus;
	/** 보완요청일 */
	private Date makeupReqDe;
	/** 보완요청내용 */
	private String makeupReqCn;
	/** 보완첨부파일그룹ID */
	private String makeupAttachmentGroupId;
	/** 첨부파일그룹ID */
	private String attachmentGroupId;
	/** 제출일시 */
	private Date presentnDt;
	/** 발송방법 */
	private String sndngMth;
	/** 발송내용 */
	private String sndngCn;
	/** 최근발송일 */
	private Date recentSendDate;
	/** 성과년월 */
	private String rsltYm;
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

	@JsonIgnore
	public String getRsltYear() {
		if(this.createdDt == null) {
			return "";
		}
		return date.format(this.createdDt, "yyyy");
	}

}
