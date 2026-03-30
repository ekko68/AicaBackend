package aicluster.pms.api.expertClfc.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aicluster.framework.common.entity.BnMember;
import aicluster.framework.security.SecurityUtils;
import aicluster.pms.api.expertClfc.dto.ExpertClfcMapngParam;
import aicluster.pms.common.dao.UsptExpertClDao;
import aicluster.pms.common.dao.UsptExpertClMapngDao;
import aicluster.pms.common.entity.UsptExpertCl;
import aicluster.pms.common.entity.UsptExpertClMapng;
import aicluster.pms.common.util.Code;
import bnet.library.exception.InvalidationException;
import bnet.library.util.CoreUtils;
import bnet.library.util.pagination.CorePagination;
import bnet.library.util.pagination.CorePaginationInfo;


@Service
public class ExpertClfcService {

	public static final long ITEMS_PER_PAGE = 10L;

	@Autowired
	private UsptExpertClDao usptExpertClDao;
	@Autowired
	private UsptExpertClMapngDao usptExpertClMapngDao;

	/**
	 * 전문가단 트리 목록 조회
	 * @param
	 * @return List<ExpertClDto>
	 */
	public List<UsptExpertCl> getExpertClfcTreeList() {
		SecurityUtils.checkWorkerIsInsider();
		return usptExpertClDao.selectExpertClfcTreeList();
	}

	/**
	 * 전문가단 목록 조회
	 * @param expertClId
	 * @return List<ExpertClDto>
	 */
	public List<UsptExpertCl> getList(String expertClId) {
		SecurityUtils.checkWorkerIsInsider();
		return usptExpertClDao.selectList(expertClId);
	}

	/**
	 * 전문가단 등록 ,수정
	 * @param List<ExpertClfcParam>
	 * @return
	 */
	public void modifyExpertClfc(List<UsptExpertCl> usptExpertClList ) {
		BnMember worker = SecurityUtils.checkWorkerIsInsider();

		if(usptExpertClList.size() <= 0) {
			throw new InvalidationException(String.format(Code.validateMessage.입력없음, "AICA 전문가단"));
		}

		Date now = new Date();

		/** 전문가단 저장 */
		for( UsptExpertCl inputParam : usptExpertClList) {

			inputParam.setCreatedDt(now);
			inputParam.setCreatorId(worker.getMemberId());
			inputParam.setUpdatedDt(now);
			inputParam.setUpdaterId(worker.getMemberId());

			if(CoreUtils.string.equals(Code.flag.등록, inputParam.getFlag())){
				inputParam.setExpertClId(CoreUtils.string.getNewId(Code.prefix.전문가분류));
				usptExpertClDao.insertExpertCl(inputParam);
			} else if(CoreUtils.string.equals(Code.flag.수정, inputParam.getFlag())){
				if(usptExpertClDao.updateExpertCl(inputParam) != 1)
					throw new InvalidationException(String.format(Code.validateMessage.DB오류, "수정"));
			} else if(CoreUtils.string.equals(Code.flag.삭제, inputParam.getFlag())){
				if(usptExpertClDao.deleteExpertCl(inputParam) != 1) {
					throw new InvalidationException(String.format(Code.validateMessage.DB오류, "삭제"));
				}
				usptExpertClDao.deleteExpertClParnts(inputParam);
			} else {
				throw new InvalidationException(String.format(Code.validateMessage.입력없음, "변경 flag"));
			}
		}
	}

	/**
	 * 전문가단 삭제
	 * @param List<ExpertClfcParam>
	 * @return
	 */
	public void deleteExpertClfc(List<UsptExpertCl> usptExpertClList ) {

		SecurityUtils.checkWorkerIsInsider();
		if(usptExpertClList.size() <= 0) {
			throw new InvalidationException(String.format(Code.validateMessage.입력없음, "AICA 전문가단"));
		}

		/** 전문가단 저장 */
		for( UsptExpertCl inputParam : usptExpertClList) {

			if(CoreUtils.string.equals(Code.flag.삭제, inputParam.getFlag())){
				if(usptExpertClDao.deleteExpertCl(inputParam) != 1) {
					throw new InvalidationException(String.format(Code.validateMessage.DB오류, "삭제"));
				}
				usptExpertClDao.deleteExpertClParnts(inputParam);
			} else {
				throw new InvalidationException(String.format(Code.validateMessage.입력없음, "변경 flag"));
			}
		}
	}


	/**
	 * 전문가단 담당자 목록 조회
	 * @param parentBsnsClId
	 * @return
	 */
	public List<UsptExpertClMapng> getMapngList(String expertClId) {
		SecurityUtils.checkWorkerIsInsider();
		return usptExpertClMapngDao.selectMapngList(expertClId);
	}

	/**
	 * 전문가단 담당자 등록
	 * @param List<ExpertClfcParam>
	 * @return
	 */
	public void addExpertClfcMapng(List<UsptExpertClMapng> usptExpertClMapngList) {
		BnMember worker = SecurityUtils.checkWorkerIsInsider();

		if(usptExpertClMapngList.size() <= 0) {
			throw new InvalidationException(String.format(Code.validateMessage.입력없음, "AICA 전문가단"));
		}

		Date now = new Date();

		/** 전문가단 담당자 등록 */
		for( UsptExpertClMapng inputParam : usptExpertClMapngList) {
			inputParam.setCreatedDt(now);
			inputParam.setCreatorId(worker.getMemberId());

			if(CoreUtils.string.equals(Code.flag.등록, inputParam.getFlag())){
				usptExpertClMapngDao.insertExpertClMapng(inputParam);
			}else{
				throw new InvalidationException(String.format(Code.validateMessage.입력없음, "변경 flag"));
			}
		}
	}

	/**
	 * 전문가단 담당자 삭제
	 * @param List<ExpertClfcParam>
	 * @return
	 */
	public void deleteExpertClfcMapng(List<UsptExpertClMapng> usptExpertClMapngList) {
		SecurityUtils.checkWorkerIsInsider();

		if(usptExpertClMapngList.size() <= 0) {
			throw new InvalidationException(String.format(Code.validateMessage.입력없음, "AICA 전문가단"));
		}

		/** 전문가단 담당자 삭제 */
		for( UsptExpertClMapng inputParam : usptExpertClMapngList) {

			if(CoreUtils.string.equals(Code.flag.삭제, inputParam.getFlag())){
				if(usptExpertClMapngDao.deleteExpertClMapng(inputParam) != 1)
					throw new InvalidationException(String.format(Code.validateMessage.DB오류, "삭제"));
			} else {
				throw new InvalidationException(String.format(Code.validateMessage.입력없음, "변경 flag"));
			}
		}
	}


	/**
	 * 전문가단 담당자 추가 목록 조회(팝업)
	 * @param parentBsnsClId
	 * @return
	 */
	public CorePagination<UsptExpertClMapng> getExpertList(ExpertClfcMapngParam inputParam, Long page) {
		SecurityUtils.checkWorkerIsInsider();
		if(page == null) {
			page = 1L;
		}
		if(inputParam.getItemsPerPage() == null) {
			inputParam.setItemsPerPage(ITEMS_PER_PAGE);
		}
		Long itemsPerPage = inputParam.getItemsPerPage();

		// 전체 건수 확인
		int totalItems = usptExpertClMapngDao.selectExpertCount(inputParam);

		// 조회할 페이지 구간 정보 확인
		CorePaginationInfo info = new CorePaginationInfo(page, itemsPerPage, Long.valueOf(totalItems));

		inputParam.setBeginRowNum(info.getBeginRowNum());
		inputParam.setItemsPerPage(itemsPerPage);

		// 페이지 목록 조회
		List<UsptExpertClMapng> list = usptExpertClMapngDao.selectExpertList(inputParam);

		CorePagination<UsptExpertClMapng> pagination = new CorePagination<>(info, list);

		// 목록 조회
		return pagination;
	}

}