package aicluster.common.common.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import aicluster.common.common.entity.CmmtBoard;

@Repository
public interface CmmtBoardDao {

	List<CmmtBoard> selectList(
			@Param("systemId") String systemId,
			@Param("enabled") Boolean enabled,
			@Param("boardId") String boardId,
			@Param("boardNm") String boardNm);

	CmmtBoard select(String boardId);

	void insert(CmmtBoard board);

	void update(CmmtBoard board);

	void delete(String boardId);

	void updateArticleCnt(String boardId);

}
