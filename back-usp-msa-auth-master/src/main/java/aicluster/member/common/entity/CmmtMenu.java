package aicluster.member.common.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CmmtMenu implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 5698280712332834062L;
	private String systemId;		/** 시스템ID */
	private String menuId;			/** 메뉴ID */
	private String menuNm;			/** 메뉴이름 */
	private String url;				/** URL */
	private Boolean newWindow;		/** 새창여부 */
	private String parentMenuId;	/** 부모메뉴 ID */
	private Long sortOrder;			/** 정렬순서 */
	private String remark;			/** 비고 */
	private String creatorId;		/** 생성자ID */
	private Date createdDt;			/** 생성일시 */
	private String updaterId;		/** 수정자ID */
	private Date updatedDt;			/** 수정일시 */
}
