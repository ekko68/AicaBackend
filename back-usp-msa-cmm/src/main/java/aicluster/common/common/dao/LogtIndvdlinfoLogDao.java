package aicluster.common.common.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import aicluster.common.common.entity.LogtIndvdlinfoLog;

@Repository
public interface LogtIndvdlinfoLogDao {
	Long selectList_count(
			@Param("beginDay") String beginDay,
			@Param("endDay") String endDay,
			@Param("workerLoginId") String workerLoginId);

	List<LogtIndvdlinfoLog> selectList(
			@Param("beginDay") String beginDay,
			@Param("endDay") String endDay,
			@Param("workerLoginId") String workerLoginId,
			@Param("beginRowNum") Long beginRowNum,
			@Param("itemsPerPage") Long itemsPerPage,
			@Param("totalItems") Long totalItems);
}
