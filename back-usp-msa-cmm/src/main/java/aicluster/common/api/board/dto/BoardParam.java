package aicluster.common.api.board.dto;

import java.io.Serializable;
import java.util.HashMap;

import aicluster.common.common.entity.CmmtBoard;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardParam implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 3526017103726951456L;
	private CmmtBoard board;
	private HashMap<String, String> authority;
}
