package aicluster.pms.common.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import aicluster.pms.common.dto.EvlPlanPblancStepDto;
import aicluster.pms.common.entity.UsptEvlPlan;

@Repository
public interface UsptEvlPlanDao {

	List<UsptEvlPlan> selectList(UsptEvlPlan usptEvlPlan);

	Long selectListCount(UsptEvlPlan usptEvlPlan);

	UsptEvlPlan select(String evlPlanId);

	int insert(UsptEvlPlan usptEvlPlan);

	int update(UsptEvlPlan usptEvlPlan);

	int updateEnable(UsptEvlPlan usptEvlPlan);

	/**
	 * 선정공고대상 목록 조회
	 * @param pblancId
	 * @param rceptOdr
	 * @return
	 */
	List<EvlPlanPblancStepDto> selectPblancEvlStepList(
			@Param("pblancId") String pblancId
			, @Param("rceptOdr") Integer rceptOdr);

	/**
	 * 선정공고대상 평가단계 조회
	 * @param pblancId
	 * @param rceptOdr
	 * @param evlStepId
	 * @param sectId
	 * @return
	 */
	EvlPlanPblancStepDto selectEvlStep(
			@Param("pblancId") String pblancId
			, @Param("rceptOdr") Integer rceptOdr
			, @Param("evlStepId") String evlStepId
			, @Param("sectId") String sectId);

	/**
	 * 선정공고대상 평가최종선정 조회
	 * @param pblancId
	 * @param rceptOdr
	 * @param evlLastSlctnId
	 * @return
	 */
	EvlPlanPblancStepDto selectEvlLastSlctn(
			@Param("pblancId") String pblancId
			, @Param("rceptOdr") Integer rceptOdr
			, @Param("evlLastSlctnId") String evlLastSlctnId);
}
