package aicluster.pms.common.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class UsptBsnsPblancAppnTask implements Serializable {
	private static final long serialVersionUID = 3441381618716293866L;
	@JsonIgnore
	private String pblancId;
	private String appnTaskId;
	private String appnTaskNm;
	@JsonIgnore
	private String creatorId;		/** 생성자ID */
	@JsonIgnore
	private Date createdDt;			/** 생성일시 */
}
