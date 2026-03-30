package aicluster.pms.api.selection.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class LastSelectionDto implements Serializable {
	private static final long serialVersionUID = 3949028750381010538L;
	/** 사업선정대상ID */
	private String bsnsSlctnId;
	/** 신청ID */
	private String applyId;
}
