package aicluster.common.api.event.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventGetListParam implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -6684758203058250464L;

	private Boolean posting;
	private String eventNm;
}
