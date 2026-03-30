package aicluster.pms.api.cnvncncls.dto;

import java.io.Serializable;
import java.util.List;

import aicluster.framework.common.entity.CmmtAttachment;
import aicluster.pms.common.entity.UsptBsnsCnvn;
import aicluster.pms.common.entity.UsptBsnsCnvnPrtcmpnySign;
import aicluster.pms.common.entity.UsptTaskReqstWct;
import lombok.Data;

@Data
public class CnvnCnclsDto implements Serializable{
	/**
	 *전자협약 관리_admin
	 */
	private static final long serialVersionUID = -9079984700632524247L;

	/** 사업협약 */
	private UsptBsnsCnvn usptBsnsCnvn;
	/** 과제신청사업비 */
	private List <UsptTaskReqstWct> usptTaskReqstWct;
	/** 사업협약참여기업서명 */
	private List <UsptBsnsCnvnPrtcmpnySign> usptBsnsCnvnPrtcmpnySign;
	/** 첨부파일 */
	private List<CmmtAttachment> attachFileList;
	/** 해지첨부파일 */
	private List<CmmtAttachment> trmnatAttachFileList;


}
