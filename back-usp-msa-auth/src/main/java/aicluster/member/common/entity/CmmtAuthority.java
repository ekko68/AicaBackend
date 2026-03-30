package aicluster.member.common.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import bnet.library.util.CoreUtils.string;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CmmtAuthority implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -3266351146272101251L;
	private String authorityId;		/** 권한ID */
	private String authorityNm;		/** 권한이름 */
	private String purpose;			/** 용도(G:AUTHORITY_PURPOSE) */
	private String creatorId;		/** 생성자ID */
	private Date createdDt;			/** 생성일시 */
	private String updaterId;		/** 수정자ID */
	private Date updatedDt;			/** 수정일시 */
	@JsonIgnore
	private String roleIds;			/** 역할ID(","로 묶음) */
	/**
	 * 역할ID를 array로 만들어 리턴한다.
	 *
	 * @return	역할ID Array
	 */
	public String[] getRoles() {
		String[] roles = string.split(roleIds, ',');
		if (roles == null) {
			roles = new String[] {};
		}
		return roles;
	}
}
