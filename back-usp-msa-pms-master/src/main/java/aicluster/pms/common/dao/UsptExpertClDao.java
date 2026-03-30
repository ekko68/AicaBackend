package aicluster.pms.common.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import aicluster.pms.common.entity.UsptExpertCl;

@Repository
public interface UsptExpertClDao {

	/**
	 *전문가분류
	 */

	/*전문가단 트리 목록 조회*/
	List<UsptExpertCl> selectExpertClfcTreeList();
	/*전문가단 목록 조회*/
	List<UsptExpertCl> selectList(@Param("expertClId") String expertClId);
	/*전문가단 등록*/
	int insertExpertCl(UsptExpertCl inputParam);
	/*전문가단 수정*/
	int updateExpertCl(UsptExpertCl inputParam);
	/*전문가단 삭제*/
	int deleteExpertCl(UsptExpertCl inputParam);
	/*전문가단 부모ID 삭제*/
	int deleteExpertClParnts(UsptExpertCl inputParam);

	/*부모전문가분류 조회*/
	List<UsptExpertCl> selectParntsExpertClIdList();
	/*전문가분류 조회*/
	List<UsptExpertCl> selectExpertClIdList(String expertClId);
}
