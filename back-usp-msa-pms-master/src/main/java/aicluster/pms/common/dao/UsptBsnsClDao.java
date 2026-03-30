package aicluster.pms.common.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import aicluster.pms.common.entity.UsptBsnsCl;

@Repository
public interface UsptBsnsClDao {

	List<UsptBsnsCl> selectList(String parentBsnsClId);
	void insert(UsptBsnsCl bcInfo);
	int update(UsptBsnsCl bcInfo);
	int delete(String bsnsClId);
	/**
	 * 산업분류 사용건수 조회
	 * @param bcInfo
	 * @return
	 */
	Integer selectUseCnt(String bsnsClId);

}
