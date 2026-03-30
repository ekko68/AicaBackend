package aicluster.member.common.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CmmtAuthorityRole implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -307492116598859122L;
	private String authorityId;		/** 권한ID */
	private String roleId;			/** 역할ID */
	private String creatorId;		/** 생성자ID */
	private Date createdDt;			/** 생성일시 */
}
