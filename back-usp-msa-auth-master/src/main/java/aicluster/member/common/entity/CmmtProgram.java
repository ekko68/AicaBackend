package aicluster.member.common.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import bnet.library.util.CoreUtils.string;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CmmtProgram implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 9017874489387475194L;

	private String programId;
	private String programNm;
	private String systemId;
	private String httpMethod;
	private String urlPattern;
	private Long checkOrder;
	private String creatorId;
	private Date createdDt;
	private String updaterId;
	private Date updatedDt;


	@JsonIgnore
	private String roleIds;

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
