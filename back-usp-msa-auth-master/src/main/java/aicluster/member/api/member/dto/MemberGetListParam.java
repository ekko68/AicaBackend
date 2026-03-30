package aicluster.member.api.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberGetListParam {

	private String loginId;
	private String memberNm;
	private String memberSt;
	private String memberType;
	private Long page;
	private Long itemsPerPage;

}
