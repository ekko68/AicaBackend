package aicluster.pms.common.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import aicluster.pms.common.dto.EvlAtendListDto;
import aicluster.pms.common.entity.UsptEvlMfcmm;

@Repository
public interface UsptEvlMfcmmDao {

	//평가위원 등록
	int insert(UsptEvlMfcmm usptEvlMfcmm);

	//평가위원 기본정보 조회
	UsptEvlMfcmm select(UsptEvlMfcmm usptEvlMfcmm);


	//평가위원 리스트(위원회에 해당하는)
	List<UsptEvlMfcmm> selectList(String evlCmitId);

	void deleteByCmitId(String evlCmitId);


	/**
	 * 평가위원 출석표
	 * @param evlCmitId
	 * @return
	 */
	List<EvlAtendListDto> selectEvlAtendList(String evlCmitId);
	/**
	 * 위원장 평가위원 조회
	 * @param evlCmitId
	 * @return
	 */
	UsptEvlMfcmm selectCharmn(String evlCmitId);

	/**
	 * 위원장여부 수정
	 * @param usptEvlMfcmm
	 * @return
	 */
	int updateCharmn(UsptEvlMfcmm usptEvlMfcmm);

	/**
	 * 출석상태 수정
	 * @param usptEvlMfcmm
	 * @return
	 */
	int updateAtendSttus(UsptEvlMfcmm usptEvlMfcmm);
}
