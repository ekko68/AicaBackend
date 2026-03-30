package aicluster.pms.common.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import aicluster.pms.api.bsns.dto.StdrBsnsListParam;
import aicluster.pms.api.bsns.dto.StdrBsnsTaskDto;
import aicluster.pms.common.dto.StdrBsnsListDto;
import aicluster.pms.common.entity.UsptStdrBsns;

@Repository
public interface UsptStdrBsnsDao {
	void insert(UsptStdrBsns info);
	int update(UsptStdrBsns info);
	UsptStdrBsns select(String stdrBsnsCd);
	/** 목록 건수 */
	Long selectListCount(StdrBsnsListParam param);
	/** 목록 조회 */
	List<StdrBsnsListDto> selectList(StdrBsnsListParam param);
	/**  과제 설정 */
	int updateTaskType(StdrBsnsTaskDto taskInfo);

	int updateUnable(UsptStdrBsns info);
}
