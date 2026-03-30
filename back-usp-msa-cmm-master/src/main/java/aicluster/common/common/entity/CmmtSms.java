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
public class CmmtSms implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -8905582418394634272L;
	private String smsId;
	private String mobileNo;
	private String message;
	private Boolean send;
	private Date sendDt;
	private Integer tryCnt;
	private String systemId;
	private Date createdDt;
}
