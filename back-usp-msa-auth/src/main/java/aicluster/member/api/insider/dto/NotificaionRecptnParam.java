package aicluster.member.api.insider.dto;

import java.io.Serializable;
import java.util.List;

import aicluster.member.common.entity.CmmtSystemPic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificaionRecptnParam implements Serializable {

	private static final long serialVersionUID = -2008983251863419988L;

	private List<CmmtSystemPic> list;
}
