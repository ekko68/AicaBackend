package aicluster.common.common.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import aicluster.common.common.entity.CmmtBoardArticleUrl;

@Repository
public interface CmmtBoardArticleUrlDao {

	void insertList(@Param("list") List<CmmtBoardArticleUrl> list);

	List<CmmtBoardArticleUrl> selectList(String articleId);

	void deleteList(String articleId);
}
