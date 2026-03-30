package aicluster.common.api.holiday.dto;

import java.util.List;

import aicluster.common.common.dto.HldySmmryDto;
import aicluster.common.common.entity.CmmtHldy;
import aicluster.common.common.entity.CmmtHldyExcl;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class YearHolidaysDto {

	private List<HldySmmryDto> summary;
	private List<CmmtHldy> holidays;
	private List<CmmtHldyExcl> exclHolidays;

}
