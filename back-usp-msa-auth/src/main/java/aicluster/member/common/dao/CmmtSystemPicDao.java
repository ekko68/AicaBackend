package aicluster.member.common.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import aicluster.member.common.entity.CmmtSystemPic;

@Repository
public interface CmmtSystemPicDao {
	List<CmmtSystemPic> selectList();
	void insertList(@Param("list") List<CmmtSystemPic> list);
	void deleteList();
}
