package aicluster.common.common.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import aicluster.common.common.entity.CmmtBoardAuthority;

@Repository
public interface CmmtBoardAuthorityDao {

	void delete_board(String boardId);

	List<CmmtBoardAuthority> selectList(String boardId);

	void save(CmmtBoardAuthority cmmtBoardAuthority);

	void insertList(@Param("list") List<CmmtBoardAuthority> list);

	CmmtBoardAuthority select(
			@Param("boardId") String boardId,
			@Param("authorityId") String authorityId);

}
