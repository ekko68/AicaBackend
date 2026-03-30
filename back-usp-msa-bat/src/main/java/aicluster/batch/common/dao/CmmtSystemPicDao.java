package aicluster.batch.common.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import aicluster.batch.common.entity.CmmtSystemPic;

@Repository
public interface CmmtSystemPicDao {
	List<CmmtSystemPic> selectList(String apiSystemId);

	List<CmmtSystemPic> selectAll();
}
