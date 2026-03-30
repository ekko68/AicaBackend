package aicluster.batch.api.holiday.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="body")
public class HolidayBody {

	@XmlElementWrapper(name="items")
	@XmlElement(name="item")
	private List<HolidayItem> items;

	private Integer numOfRows;
	private Integer pageNo;
	private Long totalCount;
}
