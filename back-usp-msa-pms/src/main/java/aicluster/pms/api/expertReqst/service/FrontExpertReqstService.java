package aicluster.pms.api.expertReqst.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import aicluster.framework.common.entity.BnMember;
import aicluster.framework.common.entity.CmmtAttachmentGroup;
import aicluster.framework.common.service.AttachmentService;
import aicluster.framework.common.util.TermsUtils;
import aicluster.framework.config.EnvConfig;
import aicluster.framework.security.SecurityUtils;
import aicluster.pms.api.expertReqst.dto.ExpertClIdDto;
import aicluster.pms.api.expertReqst.dto.ExpertClIdParntsDto;
import aicluster.pms.api.expertReqst.dto.FrontExpertReqstDto;
import aicluster.pms.api.expertReqst.dto.FrontExpertReqstParam;
import aicluster.pms.common.dao.CmmtMemberDao;
import aicluster.pms.common.dao.UsptExpertAcdmcrDao;
import aicluster.pms.common.dao.UsptExpertCareerDao;
import aicluster.pms.common.dao.UsptExpertClDao;
import aicluster.pms.common.dao.UsptExpertClMapngDao;
import aicluster.pms.common.dao.UsptExpertCrqfcDao;
import aicluster.pms.common.dao.UsptExpertDao;
import aicluster.pms.common.dao.UsptExpertReqstHistDao;
import aicluster.pms.common.entity.CmmtMember;
import aicluster.pms.common.entity.UsptExpert;
import aicluster.pms.common.entity.UsptExpertAcdmcr;
import aicluster.pms.common.entity.UsptExpertCareer;
import aicluster.pms.common.entity.UsptExpertCl;
import aicluster.pms.common.entity.UsptExpertClMapng;
import aicluster.pms.common.entity.UsptExpertCrqfc;
import aicluster.pms.common.entity.UsptExpertReqstHist;
import aicluster.pms.common.util.Code;
import bnet.library.exception.InvalidationException;
import bnet.library.util.CoreUtils;
import bnet.library.util.CoreUtils.string;
import bnet.library.util.dto.JsonList;


@Service
public class FrontExpertReqstService {

	@Autowired
	private CmmtMemberDao cmmtMemberDao;
	@Autowired
	private AttachmentService attachmentService;
	@Autowired
	private EnvConfig config;
	@Autowired
	private UsptExpertDao usptExpertDao;
	@Autowired
	private UsptExpertReqstHistDao usptExpertReqstHistDao;
	@Autowired
	private UsptExpertClMapngDao usptExpertClMapngDao;
	@Autowired
	private UsptExpertCareerDao usptExpertCareerDao;
	@Autowired
	private UsptExpertAcdmcrDao usptExpertAcdmcrDao;
	@Autowired
	private UsptExpertCrqfcDao usptExpertCrqfcDao;
	@Autowired
	private UsptExpertClDao usptExpertClDao;
	@Autowired
    private TermsUtils termsUtils;

	/**
	 * 전문가 신청자정보 조회
	 * @param pblancId
	 * @return
	 */
	public FrontExpertReqstDto getExpertReqstInfo() {

		//회원정보 조회
		BnMember worker = SecurityUtils.checkWorkerIsMember();
		String strMemberId = worker.getMemberId();

		CmmtMember selectResult = cmmtMemberDao.select(strMemberId);

		FrontExpertReqstDto dto = new FrontExpertReqstDto();
		if(selectResult !=null ) {
			dto.setMemberId(selectResult.getMemberId());			/**회원ID**/
			dto.setMemberNm(selectResult.getMemberNm());		/**회원명(사업자명)**/
			dto.setGender(selectResult.getGender());					/**성별**/
			dto.setEncBirthday(selectResult.getBirthday());		/**생년월일**/
			dto.setEncMobileNo(selectResult.getMobileNo());	/**휴대폰번호**/
			dto.setEncEmail(selectResult.getEmail());				/**이메일**/
		}
		return dto;
	}

	/**
	 * 전문가 신청자 등록
	 * @param frontExpertReqstParam
	 * * @param fileList
	 * @return
	 */
	public FrontExpertReqstParam add( FrontExpertReqstParam frontExpertReqstParam,  List<MultipartFile> fileList) {

		/** 현재 시간*/
		Date now = new Date();
		/** 로그인 회원ID **/
		BnMember worker = SecurityUtils.checkWorkerIsMember();

		String strMemberId = worker.getMemberId();

		//회원정보 조회
		int cntExpert = usptExpertDao.selectExpertRegCnt(strMemberId);
		if( cntExpert> 0 ){
			throw new InvalidationException("이미 신청된 전문가 입니다.");
		}

		/********************************* 신청자 정보 등록 start**/
		/** 전문가ID 생성 */
		String newExpertId = CoreUtils.string.getNewId(Code.prefix.전문가);

		/** 신청자정보(전문가) 등록 */
		UsptExpert usptExpert = frontExpertReqstParam.getUsptExpert();

		if(CoreUtils.string.isBlank(usptExpert.getLastUnivNm())) {
			throw new InvalidationException(String.format(Code.validateMessage.입력없음, "최종대학명"));
		}
		if(CoreUtils.string.isBlank(usptExpert.getUnivDeptNm())) {
			throw new InvalidationException(String.format(Code.validateMessage.입력없음, "대학학부명"));
		}

		/** 전문가 분야정보 */
		List<UsptExpertClMapng> usptExpertClMapngListCheck = frontExpertReqstParam.getUsptExpertClMapng();
		if(usptExpertClMapngListCheck.size()<1) {
			throw new InvalidationException(String.format(Code.validateMessage.입력없음, "전문분야"));
		}else {
			for(UsptExpertClMapng inputParam : usptExpertClMapngListCheck ) {
				if(string.isBlank(inputParam.getExpertClId())){
					throw new InvalidationException("전문가분야를 선택하세요.");
				}
			}
		}

		/** 전문가 경력정보 */
		List<UsptExpertCareer> usptExpertCareerListCheck = frontExpertReqstParam.getUsptExpertCareer();
		if(usptExpertCareerListCheck.size()<1) {
			throw new InvalidationException(String.format(Code.validateMessage.입력없음, "경력정보"));
		}else {
			for(UsptExpertCareer inputParam : usptExpertCareerListCheck ) {
				if(string.isBlank(inputParam.getWorkBgnde())){
					throw new InvalidationException(String.format(Code.validateMessage.입력없음, "근무시작일"));
				}else if(string.isBlank(inputParam.getWorkEndde())){
						throw new InvalidationException(String.format(Code.validateMessage.입력없음, "근무종료일"));
				}else if(string.isBlank(inputParam.getWrcNm())){
					throw new InvalidationException(String.format(Code.validateMessage.입력없음, "직장명"));
				}else if(string.isBlank(inputParam.getDeptNm())){
					throw new InvalidationException(String.format(Code.validateMessage.입력없음, "부서명"));
				}else if(string.isBlank(inputParam.getOfcpsNm())){
					throw new InvalidationException(String.format(Code.validateMessage.입력없음, "직위"));
				}else if(string.isBlank(inputParam.getChrgJobNm())){
					throw new InvalidationException(String.format(Code.validateMessage.입력없음, "담당업무"));
				}
			}
		}

		/** 전문가 학력정보 */
		List<UsptExpertAcdmcr> usptExpertAcdmcrListCheck = frontExpertReqstParam.getUsptExpertAcdmcr();
		if(usptExpertAcdmcrListCheck.size()<1) {
			throw new InvalidationException(String.format(Code.validateMessage.입력없음, "학력정보"));
		}else {
			for(UsptExpertAcdmcr inputParam : usptExpertAcdmcrListCheck ) {
				if(string.isBlank(inputParam.getAcdmcrBgnde())){
					throw new InvalidationException(String.format(Code.validateMessage.입력없음, "학력시작일"));
				}else if(string.isBlank(inputParam.getAcdmcrEndde())){
						throw new InvalidationException(String.format(Code.validateMessage.입력없음, "학력종료일"));
				}else if(string.isBlank(inputParam.getDgriCd())){
					throw new InvalidationException(String.format(Code.validateMessage.입력없음, "학위"));
				}else if(string.isBlank(inputParam.getSchulNm())){
					throw new InvalidationException(String.format(Code.validateMessage.입력없음, "학교명"));
				}else if(string.isBlank(inputParam.getMajorNm())){
					throw new InvalidationException(String.format(Code.validateMessage.입력없음, "전공명"));
				}else if(string.isBlank(inputParam.getProfsrNm())){
					throw new InvalidationException(String.format(Code.validateMessage.입력없음, "지도교수명"));
				}else if(string.isBlank(inputParam.getGrdtnDivCd())){
					throw new InvalidationException(String.format(Code.validateMessage.입력없음, "졸업구분"));
				}
			}
		}

		//첨부파일 등록(첨부파일그룹ID)
		if(fileList != null && fileList.size() > 0) {
			CmmtAttachmentGroup fileGroupInfo = attachmentService.uploadFiles_toNewGroup(config.getDir().getUpload(), fileList, null, 0);
			usptExpert.setAttachmentGroupId(fileGroupInfo.getAttachmentGroupId());
		}

		String encBrthdy = CoreUtils.aes256.encrypt( usptExpert.getEncBrthdy(), newExpertId);	/*생년월일*/
		String encMbtlnum = CoreUtils.aes256.encrypt(usptExpert.getEncMbtlnum(), newExpertId);	/*휴대폰번호*/
		String encEmail = CoreUtils.aes256.encrypt(usptExpert.getEncEmail(), newExpertId);	/*이메일*/

		usptExpert.setExpertId(newExpertId);
		usptExpert.setMemberId(strMemberId);
		usptExpert.setEncBrthdy(encBrthdy);
		usptExpert.setEncMbtlnum(encMbtlnum);
		usptExpert.setEncEmail(encEmail);
		usptExpert.setExpertReqstProcessSttusCd(Code.rceptSttus.신청);
		usptExpert.setCreatedDt(now);
		usptExpert.setCreatorId(strMemberId);
		usptExpert.setUpdatedDt(now);
		usptExpert.setUpdaterId(strMemberId);

		usptExpertDao.insert(usptExpert);	//insert

		/**전문가신청처리이력**/
		UsptExpertReqstHist usptExpertReqstHist = new UsptExpertReqstHist();

		usptExpertReqstHist.setExpertReqstHistId(CoreUtils.string.getNewId(Code.prefix.전문가신청처리이력));/** 전문가신청처리이력ID 생성 */
		usptExpertReqstHist.setExpertId(newExpertId);
		usptExpertReqstHist.setExpertReqstProcessSttusCd(Code.rceptSttus.신청);
		usptExpertReqstHist.setCreatedDt(now);
		usptExpertReqstHist.setCreatorId(strMemberId);

		usptExpertReqstHistDao.insert(usptExpertReqstHist);	//insert

		/** 전문분야 */
		List<UsptExpertClMapng> usptExpertClMapngList =  frontExpertReqstParam.getUsptExpertClMapng();
		if(usptExpertClMapngList.size()>0) {
			for( UsptExpertClMapng input : usptExpertClMapngList) {
				if(CoreUtils.string.isBlank(input.getExpertClId())) {
					throw new InvalidationException(String.format(Code.validateMessage.입력없음, "전문가분류ID"));
				}

				input.setExpertId(newExpertId);
				input.setCreatedDt(now);
				input.setCreatorId(strMemberId);

				usptExpertClMapngDao.insertExpertClMapng(input);
			}
		}

		/** 전문가 경력정보 */
		List<UsptExpertCareer> usptExpertCareerList =  frontExpertReqstParam.getUsptExpertCareer();
		if(usptExpertCareerList.size()>0) {
			for( UsptExpertCareer input : usptExpertCareerList) {

				input.setExpertCareerId(CoreUtils.string.getNewId(Code.prefix.전문가경력));	/** 전문가경력ID 생성 */
				input.setExpertId(newExpertId);
				input.setCreatedDt(now);
				input.setCreatorId(strMemberId);
				input.setUpdatedDt(now);
				input.setUpdaterId(strMemberId);

				usptExpertCareerDao.insert(input);	//insert
			}
		}

		/** 전문가 학력정보 */
		List<UsptExpertAcdmcr> usptExpertAcdmcrList=  frontExpertReqstParam.getUsptExpertAcdmcr();
		if(usptExpertAcdmcrList.size()>0) {
			for( UsptExpertAcdmcr input : usptExpertAcdmcrList) {

				input.setExpertAcdmcrId(CoreUtils.string.getNewId(Code.prefix.전문가학력));	/** 전문가학력ID 생성 */
				input.setExpertId(newExpertId);
				input.setCreatedDt(now);
				input.setCreatorId(strMemberId);
				input.setUpdatedDt(now);
				input.setUpdaterId(strMemberId);

				usptExpertAcdmcrDao.insert(input);
			}
		}

		/** 전문가 자격증정보 */
		List<UsptExpertCrqfc> usptExpertCrqfcList=  frontExpertReqstParam.getUsptExpertCrqfc();
		if(usptExpertCrqfcList.size()>0) {
			for( UsptExpertCrqfc input : usptExpertCrqfcList) {

				input.setExpertCrqfcId(CoreUtils.string.getNewId(Code.prefix.전문가자격증));	/** 전문가자격증ID 생성 */
				input.setExpertId(newExpertId);
				input.setCreatedDt(now);
				input.setCreatorId(strMemberId);
				input.setUpdatedDt(now);
				input.setUpdaterId(strMemberId);

				usptExpertCrqfcDao.insert(input);
			}
			/*************************************** 신청자 정보 등록 end**/
		}

		// 약관동의정보 생성
		termsUtils.insertList(frontExpertReqstParam.getSessionId(), worker.getMemberId());
		return frontExpertReqstParam;
	}

	/**
	 * 전문가 분류조회_부모전문가분류 조회
	 * @param pblancId
	 * @return
	 */
	public JsonList<ExpertClIdParntsDto> selectParntsExpertClIdList() {

		List<ExpertClIdParntsDto> expertClIdParntsDtoList = new ArrayList <>();
		//부모전문가분류 조회
		List<UsptExpertCl> usptExpertClList= usptExpertClDao.selectParntsExpertClIdList();

		for(UsptExpertCl info : usptExpertClList) {
			ExpertClIdParntsDto expertClIdParntsDto = new ExpertClIdParntsDto();

			expertClIdParntsDto.setParntsExpertClId(info.getParntsExpertClId());
			expertClIdParntsDto.setExpertClNm(info.getExpertClNm());
			expertClIdParntsDtoList.add(expertClIdParntsDto);
		}
		return new JsonList<>(expertClIdParntsDtoList);
	}

	/**
	 * 전문가 분류조회_전문가분류보 조회
	 * @param pblancId
	 * @return
	 */
	public JsonList<ExpertClIdDto> selectExpertClIdList(String expertClId) {

		List<ExpertClIdDto> expertClIdDtoList = new ArrayList <>();
		//부모전문가분류 조회
		List<UsptExpertCl> usptExpertClList= usptExpertClDao.selectExpertClIdList(expertClId);
		for(UsptExpertCl info : usptExpertClList) {
			ExpertClIdDto expertClIdDto = new ExpertClIdDto();
			expertClIdDto.setExpertClId(info.getExpertClId());
			expertClIdDto.setExpertClNm(info.getExpertClNm());
			expertClIdDtoList.add(expertClIdDto);
		}
		return new JsonList<>(expertClIdDtoList);
	}
}