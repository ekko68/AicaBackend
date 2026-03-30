package aicluster.member.common.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MenuEnabledDto implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 5030862136748303716L;
	private String systemId;		/** 시스템ID */
	private String menuId;			/** 메뉴ID */
	private String menuNm;			/** 메뉴이름 */
	private String url;				/** URL */
	private Boolean newWindow;		/** 새창여부 */
	private String parentMenuId;	/** 부모메뉴ID */
	private Long sortOrder;			/** 정렬순서 */
	private String remark;			/** 비고 */

	@JsonIgnore
	private String creatorId;		/** 생성자ID */
	@JsonIgnore
	private Date createdDt;			/** 생성일시 */
	@JsonIgnore
	private String updaterId;		/** 수정자ID */
	@JsonIgnore
	private Date updatedDt;			/** 수정일시 */

	private Boolean enabled;		/** 사용여부 */
}
