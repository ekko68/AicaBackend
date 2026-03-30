package aicluster.pms.common.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import aicluster.pms.api.expert.dto.ExpertListParam;
import aicluster.pms.api.expertReqst.dto.ExpertReqstListParam;
import aicluster.pms.common.entity.UsptEvlMfcmmExtrc;
import aicluster.pms.common.entity.UsptExpert;
import aicluster.pms.common.entity.UsptExpertParam;

@Repository
public interface UsptExpertDao {
	List<UsptExpert> selectList(UsptExpertParam usptExpert);

	long selectListCount(UsptExpertParam usptExpert);

	List<UsptExpert> selectListByExtrc(UsptEvlMfcmmExtrc usptEvlMfcmmExtrc);

	List<UsptExpert> selectListByExcl(UsptEvlMfcmmExtrc usptEvlMfcmmExtrc);

	UsptExpert selectMfcmmDetail(String extrcMfcmmId);

	int insertList(@Param("list") List<UsptExpert> usptExpertList);

	int insert(UsptExpert usptExpert);

	//********************************************전문가 신청 front**//
	int selectExpertRegCnt(String memberId);

	//********************************************전문가 신청 admin**//
	long selectExpertListCnt(ExpertReqstListParam expertReqstListParam);
	/*목록*/
	List<UsptExpert> selectExpertList(ExpertReqstListParam expertReqstListParam);
	/*excel*/
	List<UsptExpert> selectList(ExpertReqstListParam expertReqstListParam);
	/*단건조회*/
	UsptExpert selectOneExpert(String expertId);
	/*상태값 update*/
	int updateExpertReturn(ExpertReqstListParam expertReqstListParam);
	/*전문가 정보 업데이트*/
	int update(UsptExpert usptExpert);

	//********************************************전문가 관리 admin**//
	long selectExpertListCnt(ExpertListParam expertListParam);
	/*목록*/
	List<UsptExpert> selectExpertList(ExpertListParam expertListParam);
	/*excel*/
	List<UsptExpert> selectList(ExpertListParam expertListParam);
	/*삭제*/
	int delete(String expertId);

	/*매칭이력*/
	long selectExpertMatchHistListCnt(ExpertListParam expertListParam);
	List<UsptExpert> selectExpertMatchHistList(ExpertListParam expertListParam);

}
