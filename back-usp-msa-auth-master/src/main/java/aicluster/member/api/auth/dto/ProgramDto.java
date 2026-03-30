package aicluster.member.api.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProgramDto {

	private String programId;
	private String programNm;
	private String systemId;
	private String httpMethod;
	private String urlPattern;
	private Long checkOrder;
	private String[] roles;

}
