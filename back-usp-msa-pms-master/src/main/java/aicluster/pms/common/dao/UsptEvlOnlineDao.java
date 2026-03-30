package aicluster.pms.common.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import aicluster.pms.common.dto.EvlResultAddAllotBasicDto;
import aicluster.pms.common.dto.EvlResultBasicInfoDto;
import aicluster.pms.common.dto.EvlResultByMfcmmListDto;
import aicluster.pms.common.dto.EvlResultByTargetListDto;
import aicluster.pms.common.dto.EvlResultDetailByIemDto;
import aicluster.pms.common.dto.EvlResultGnrlzDto;
import aicluster.pms.common.dto.EvlResutlDspthInfoDto;
import aicluster.pms.common.dto.EvlResutlSndngTrgetDto;

@Repository
public interface UsptEvlOnlineDao {
	//등록된 대상 리스트
//	List<UsptEvlMfcmm> selectEvlOnlineAtendList(String evlCmitId);

	//단건 상세 조회
//	UsptEvlMfcmm select(String evlMfcmmId);

//	int updateCharmn(UsptEvlMfcmm usptEvlMfcmm);

//	int updateCharmnAllfalse(String evlCmitId);

//	int updateAttend(UsptEvlMfcmm usptEvlMfcmm);

//	int updateEvas(UsptEvlMfcmm usptEvlMfcmm);

//	List<EvlResultByTargetListDto> selectEvlOnlineSttusList(String evlCmitId);

	List<EvlResultByTargetListDto> selectEvlOnlineResultList(String evlCmitId);

	List<EvlResultByMfcmmListDto> selectEvlResultByMfcmmList(String evlTrgetId);

	EvlResultBasicInfoDto selectEvlResultBasicInfo(@Param("evlResultId") String evlResultId);

	List<EvlResultDetailByIemDto> selectEvlResultIemDetail(@Param("evlResultId") String evlResultId);

	EvlResultAddAllotBasicDto selectEvlResultAddAllotBasicInfo(EvlResultAddAllotBasicDto inputParam);

	List<EvlResultDetailByIemDto> selectEvlResultAddAllotDetail(EvlResultAddAllotBasicDto inputParam);

	int selectValidKeyByAddAllot(String keyVal);

	int updateEvlResultAddMinus(EvlResultDetailByIemDto inputParam);

	List<EvlResultGnrlzDto> selectEvlOnlineEvlReusltGnrlz(EvlResultGnrlzDto evlResultGnrlzDto);

	EvlResutlDspthInfoDto selectEvlReusltDspthInfo(String evlCmitId);

	List<EvlResutlSndngTrgetDto> selectSndngTrgetList(String evlCmitId);
}
