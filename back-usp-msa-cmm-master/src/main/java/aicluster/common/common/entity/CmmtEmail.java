package aicluster.common.common.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CmmtEmail implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 7043357799066697843L;
	private String emailId;
	private String title;
	private String article;
	private Boolean html;
	private Boolean send;
	private Date sendDt;
	private Integer tryCnt;
	private String systemId;
	private Date createdDt;
}
