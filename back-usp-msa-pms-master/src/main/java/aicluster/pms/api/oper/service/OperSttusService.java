package aicluster.pms.api.oper.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aicluster.framework.security.SecurityUtils;
import aicluster.pms.common.dao.CmmtMemberDao;
import aicluster.pms.common.dto.BsnsPortListDto;
import aicluster.pms.common.dto.EntrprsSttusListDto;
import aicluster.pms.common.util.Code;
import bnet.library.exception.InvalidationException;
import bnet.library.util.CoreUtils;

@Service
public class OperSttusService {

	@Autowired
	private CmmtMemberDao cmmtMemberDao;

	/**
	 * 기업현황 목록 조회
	 * @param bsnsYear
	 * @return
	 */
	public List<EntrprsSttusListDto> getEntrprsSttusList(String bsnsYear) {
		SecurityUtils.checkWorkerIsInsider();
		if(CoreUtils.string.isEmpty(bsnsYear)) {
			throw new InvalidationException(String.format(Code.validateMessage.입력없음, "사업연도"));
		}
		return cmmtMemberDao.selectEntrprsSttusList(bsnsYear);
	}


	/**
	 * 사업지원 현황 목록 조회
	 * @param bsnsYear
	 * @return
	 */
	public List<BsnsPortListDto> getBsnsPortSttusList(String bsnsYear) {
		SecurityUtils.checkWorkerIsInsider();
		if(CoreUtils.string.isEmpty(bsnsYear)) {
			throw new InvalidationException(String.format(Code.validateMessage.입력없음, "사업연도"));
		}
		return cmmtMemberDao.selectBsnsPortSttusList(bsnsYear);
	}
}
