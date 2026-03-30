package aicluster.pms.api.cnvncncls.dto;

import java.io.Serializable;
import java.util.List;

import aicluster.framework.common.entity.CmmtAttachment;
import lombok.Data;

@Data
public class FrontCnvnCnclsInputParam implements Serializable{
	/**
	 *전자협약 관리_front_등록
	 */
	private static final long serialVersionUID = 3774813760245724343L;
	/** 사업협약ID */
	String  bsnsCnvnId;
	/** 신청ID */
	String  applyId;
	/** 협약상태코드(G:CNVN_STTUS) */
	String  cnvnSttusCd;
	/**회원ID :  참여기업회원ID */
	String memberId;

	/** 첨부파일삭제 List */
	private List<CmmtAttachment> attachFileDeleteList;
}
