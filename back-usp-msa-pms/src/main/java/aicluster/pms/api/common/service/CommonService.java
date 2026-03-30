package aicluster.pms.api.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aicluster.pms.common.dao.UsptBsnsDao;
import aicluster.pms.common.dao.UsptBsnsPblancAppnTaskDao;
import aicluster.pms.common.dto.BsnsNmDto;
import aicluster.pms.common.entity.UsptBsnsPblancAppnTask;

@Service
public class CommonService {

	@Autowired
	private UsptBsnsDao usptBsnsDao;
	@Autowired
	private UsptBsnsPblancAppnTaskDao usptBsnsPblancAppnTaskDao;

	/**
	 * 사업연도 목록 조회
	 * @return
	 */
	public List<String> getBsnsYearList(){
		return usptBsnsDao.selectBsnsYearList();
	}

	/**
	 * 사업명 목록 조회
	 * @param bsnsYear
	 * @return
	 */
	public List<BsnsNmDto> getBsnsNmList(String bsnsYear){
		return usptBsnsDao.selectBsnsNmList(bsnsYear);
	}

	/**
	 * 공고 지원분야 목록 조회
	 * @return
	 */
	public List<UsptBsnsPblancAppnTask> getAppnTaskList(){
		return usptBsnsPblancAppnTaskDao.selectAppnTaskList();
	}
}
