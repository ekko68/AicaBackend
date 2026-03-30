package aicluster.member.common.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CmmtMenuRole implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -6853893332842971861L;
	private String systemId;	/** 시스템ID */
	private String menuId;		/** 메뉴ID */
	private String roleId;		/** 역할ID */
	private String creatorId;	/** 생성자ID */
	private Date createdDt;		/** 생성일시 */

//	@JsonIgnore
//	private String roleIds;
//
//	/**
//	 * 역할ID를 array로 만들어 리턴한다.
//	 *
//	 * @return	역할ID Array
//	 */
//	public String[] getRoles() {
//		String[] roles = string.split(roleIds, ',');
//		if (roles == null) {
//			roles = new String[] {};
//		}
//		return roles;
//	}
}
