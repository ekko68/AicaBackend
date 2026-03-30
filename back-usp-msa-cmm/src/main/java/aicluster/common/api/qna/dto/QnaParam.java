package aicluster.common.api.qna.dto;

import java.io.Serializable;
import java.util.List;

import aicluster.common.common.entity.CmmtQna;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QnaParam implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -8541844816750796558L;
	private CmmtQna qna;
	private List<String> answererList;
}
