package aicluster.common.api.qna.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import aicluster.common.api.qna.dto.QnaQuestAddParam;
import aicluster.common.api.qna.dto.QnaQuestListParam;
import aicluster.common.api.qna.dto.QnaQuestModifyParam;
import aicluster.common.common.dao.CmmtCodeDao;
import aicluster.common.common.dao.CmmtInsiderDao;
import aicluster.common.common.dao.CmmtMemberDao;
import aicluster.common.common.dao.CmmtQnaDao;
import aicluster.common.common.dao.CmmtQnaQuestDao;
import aicluster.common.common.entity.CmmtCode;
import aicluster.common.common.entity.CmmtInsider;
import aicluster.common.common.entity.CmmtMember;
import aicluster.common.common.entity.CmmtQna;
import aicluster.common.common.entity.CmmtQnaQuest;
import aicluster.common.common.util.CodeExt;
import aicluster.common.common.util.CodeExt.validateMessageExt;
import aicluster.common.common.util.QnaUtils;
import aicluster.framework.common.entity.BnMember;
import aicluster.framework.common.entity.CmmtAttachment;
import aicluster.framework.common.entity.CmmtAttachmentGroup;
import aicluster.framework.common.service.AttachmentService;
import aicluster.framework.config.EnvConfig;
import aicluster.framework.security.SecurityUtils;
import bnet.library.exception.InvalidationException;
import bnet.library.exception.InvalidationsException;
import bnet.library.util.CoreUtils.array;
import bnet.library.util.CoreUtils.date;
import bnet.library.util.CoreUtils.string;
import bnet.library.util.pagination.CorePagination;
import bnet.library.util.pagination.CorePaginationInfo;
import bnet.library.util.pagination.CorePaginationParam;

@Service
public class QnaQuestService {

	@Autowired
	private CmmtQnaDao cmmtQnaDao;

	@Autowired
	private CmmtCodeDao cmmtCodeDao;

	@Autowired
	private CmmtQnaQuestDao cmmtQnaQuestDao;

	@Autowired
	private CmmtMemberDao cmmtMemberDao;

	@Autowired
	private CmmtInsiderDao cmmtInsiderDao;

	@Autowired
	private AttachmentService attachmentService;

	@Autowired
	private QnaUtils qnaUtils;

	@Autowired
	private EnvConfig config;

	/**
	 * 질의응답 게시글 목록 조회
	 *
	 * @param qnaId : 질의게시판ID
	 * @param param : 검색조건 (questStatus:질의상태코드, categoryCd:카테고리코드, title:질의제목, memberNm:질의자명)
	 * @param pageParam : 페이징
	 * @return CorePagination<CmmtQnaQuest> : 질의게시글 목록
	 */
	public CorePagination<CmmtQnaQuest> getList(String qnaId, QnaQuestListParam param, CorePaginationParam pageParam) {
		BnMember worker = SecurityUtils.getCurrentMember();
		if (worker == null) {
			throw new InvalidationException("권한이 없습니다.");
		}

		// 권한검사. 답변자이면 모든 글 조회. 그렇지 않은 경우에는 자신의 글만 조회해야 한다.
		String questionerId = null;
		boolean canAnswer = qnaUtils.canAnswer(qnaId, worker.getMemberId());
		if (!canAnswer) {
			questionerId = worker.getMemberId();
			param.setMemberNm(null);
		}

		CmmtQna cmmtQna = cmmtQnaDao.select(qnaId);
		if (cmmtQna == null) {
			throw new InvalidationException("게시판이 존재하지 않습니다.");
		}

		// 카테고리를 사용하지 않는 게시판인 경우, 카테고리 검색어를 삭제한다.
		if (!cmmtQna.getCategory()) {
			param.setCategoryCd(null);
		}

		// 접수일 기간 검증
		if (string.isNotBlank(param.getQuestBeginDay()) && string.isNotBlank(param.getQuestEndDay())) {
			if (date.truncatedCompareTo(string.toDate(param.getQuestBeginDay()), string.toDate(param.getQuestEndDay()), Calendar.DATE) > 0) {
				throw new InvalidationException(String.format(validateMessageExt.일시_큰비교오류, "접수일 시작일", "접수일 종료일", "날짜"));
			}
		}

		long totalItems = cmmtQnaQuestDao.selectList_count(qnaId, param, questionerId);
		CorePaginationInfo info = new CorePaginationInfo(pageParam.getPage(), pageParam.getItemsPerPage(), totalItems);
		List<CmmtQnaQuest> list = cmmtQnaQuestDao.selectList(qnaId, param, questionerId, info.getBeginRowNum(), info.getItemsPerPage(), totalItems);

		CorePagination<CmmtQnaQuest> dto = new CorePagination<>(info, list);
		return dto;
	}

	public CmmtQnaQuest add(QnaQuestAddParam param, List<MultipartFile> files) {
		Date now = new Date();
		BnMember worker = SecurityUtils.getCurrentMember();
		if (worker == null) {
			throw new InvalidationException("권한이 없습니다.");
		}

		if (param == null) {
			throw new InvalidationException("입력이 없습니다.");
		}

		CmmtQna cmmtQna = cmmtQnaDao.select(param.getQnaId());
		if (cmmtQna == null) {
			throw new InvalidationException("게시판이 존재하지 않습니다.");
		}

		/*
		 * 입력검사
		 */
		InvalidationsException exs = new InvalidationsException();
		if (cmmtQna.getCategory()) {
			if (string.isBlank(param.getCategoryCd())) {
				exs.add("categoryCd", "카테고리를 입력하세요.");
			}
			else {
				CmmtCode cmmtCode = cmmtCodeDao.select(cmmtQna.getCategoryCodeGroup(), param.getCategoryCd());
				if (cmmtCode == null) {
					exs.add("categoryCd", "카테고리코드가 존재하지 않습니다.");
				}
			}
		}
		else if (string.isNotBlank(param.getCategoryCd())) {
			exs.add("categoryCd", "카테고리를 사용하지 않는 게시판입니다.");
		}

		if (string.isBlank(param.getTitle())) {
			exs.add("title", "제목을 입력하세요.");
		}

		if (string.isBlank(param.getQuestion())) {
			exs.add("question", "질문내용을 입력하세요.");
		}

		if (exs.size() > 0) {
			throw exs;
		}

		/*
		 * 파일 저장
		 */
		String attachmentGroupId = null;
		if (cmmtQna.getAttachable()) {
			if (files != null && files.size() > 0) {
				String[] exts = cmmtQna.getAttachmentExt().split("/");
				long size = cmmtQna.getAttachmentSize() * 1024 * 1024;
				CmmtAttachmentGroup attGroup = attachmentService.uploadFiles_toNewGroup(config.getDir().getUpload(), files, exts, size);
				attachmentGroupId = attGroup.getAttachmentGroupId();
			}
		}

		/*
		 * 입력
		 */
		CmmtQnaQuest cmmtQnaQuest = CmmtQnaQuest.builder()
				.questId(string.getNewId("quest-"))
				.qnaId(param.getQnaId())
				.categoryCd(param.getCategoryCd())
				.questSt(CodeExt.questSt.요청)
				.questStDt(now)
				.title(param.getTitle())
				.question(param.getQuestion())
				.questAttachmentGroupId(attachmentGroupId)
				.questionerId(worker.getMemberId())
				.questCreatedDt(now)
				.questUpdatedDt(now)
				.build();
		cmmtQnaQuestDao.insert(cmmtQnaQuest);

		/*
		 * 출력
		 */
		cmmtQnaQuest = cmmtQnaQuestDao.select(cmmtQnaQuest.getQuestId());
		List<CmmtAttachment> fileList = attachmentService.getFileInfos_group(cmmtQnaQuest.getQuestAttachmentGroupId());
		cmmtQnaQuest.setQuestAttachmentList(fileList);
		return cmmtQnaQuest;
	}

	public CmmtQnaQuest get(String qnaId, String questId) {
		Date now = new Date();
		BnMember worker = SecurityUtils.getCurrentMember();
		if (worker == null) {
			throw new InvalidationException("권한이 없습니다.");
		}

		CmmtQna cmmtQna = cmmtQnaDao.select(qnaId);
		if (cmmtQna == null) {
			throw new InvalidationException("게시판이 존재하지 않습니다.");
		}

		CmmtQnaQuest cmmtQnaQuest = cmmtQnaQuestDao.select(questId);
		if (cmmtQnaQuest == null) {
			throw new InvalidationException("글이 존재하지 않습니다.");
		}

		// 권한검사. 자신이 작성한 글이거나, 답변자이어야 한다.
		boolean canRead = qnaUtils.canRead(qnaId, cmmtQnaQuest, worker.getMemberId());
		if (!canRead) {
			throw new InvalidationException("권한이 없습니다.");
		}

		// 요청상태이고, 답변자이면, 상태를 접수로 변경한다.
		boolean canAnswer = qnaUtils.canAnswer(qnaId, worker.getMemberId());
		if (string.equals(cmmtQnaQuest.getQuestSt(), CodeExt.questSt.요청) && canAnswer) {
			cmmtQnaQuest.setQuestSt(CodeExt.questSt.접수);
			cmmtQnaQuest.setQuestStDt(now);
			cmmtQnaQuest.setAnswerCreatedDt(now);
			cmmtQnaQuest.setAnswerCreatorId(worker.getMemberId());
			cmmtQnaQuest.setAnswerUpdatedDt(now);
			cmmtQnaQuest.setAnswerUpdaterId(worker.getMemberId());
			cmmtQnaQuestDao.update(cmmtQnaQuest);
		}

		List<CmmtAttachment> questAttachmentList = attachmentService.getFileInfos_group(cmmtQnaQuest.getQuestAttachmentGroupId());
		List<CmmtAttachment> answerAttachmentList = attachmentService.getFileInfos_group(cmmtQnaQuest.getAnswerAttachmentGroupId());
		cmmtQnaQuest.setQuestAttachmentList(questAttachmentList);
		cmmtQnaQuest.setAnswerAttachmentList(answerAttachmentList);
		CmmtMember questioner = cmmtMemberDao.select(cmmtQnaQuest.getQuestionerId());
		CmmtInsider answerer = cmmtInsiderDao.select(cmmtQnaQuest.getAnswerUpdaterId());
		cmmtQnaQuest.setQuestioner(questioner);
		cmmtQnaQuest.setAnswerer(answerer);
		return cmmtQnaQuest;
	}

	public CmmtQnaQuest modify(QnaQuestModifyParam param, List<MultipartFile> files) {
		Date now = new Date();
		BnMember worker = SecurityUtils.getCurrentMember();
		if (worker == null) {
			throw new InvalidationException("권한이 없습니다.");
		}

		CmmtQna cmmtQna = cmmtQnaDao.select(param.getQnaId());
		if (cmmtQna == null) {
			throw new InvalidationException("게시판이 존재하지 않습니다.");
		}

		CmmtQnaQuest cmmtQnaQuest = cmmtQnaQuestDao.select(param.getQuestId());
		if (cmmtQnaQuest == null) {
			throw new InvalidationException("글이 존재하지 않습니다.");
		}

		if (!string.equals(param.getQnaId(), cmmtQnaQuest.getQnaId())) {
			throw new InvalidationException("글이 해당 게시판의 글이 아닙니다.");
		}

		// 권한검사: 자신이 등록한 질문이어야 한다.
		boolean canModify = qnaUtils.canModify(cmmtQnaQuest, worker.getMemberId());
		if (!canModify) {
			throw new InvalidationException("권한이 없습니다.");
		}

		// 상태검사: 요청상태인 경우만 수정할 수 있다.
		if (!string.equals(cmmtQnaQuest.getQuestSt(), CodeExt.questSt.요청)) {
			throw new InvalidationException("요청 상태인 경우만 수정할 수 있습니다.");
		}

		/*
		 * 입력검사
		 */
		InvalidationsException exs = new InvalidationsException();
		if (cmmtQna.getCategory()) {
			if (string.isBlank(param.getCategoryCd())) {
				exs.add("categoryCd", "카테고리를 입력하세요.");
			}
			else {
				CmmtCode cmmtCode = cmmtCodeDao.select(cmmtQna.getCategoryCodeGroup(), param.getCategoryCd());
				if (cmmtCode == null) {
					exs.add("categoryCd", "카테고리코드가 존재하지 않습니다.");
				}
			}
		}
		else if (string.isNotBlank(param.getCategoryCd())) {
			exs.add("categoryCd", "카테고리를 사용하지 않는 게시판입니다.");
		}

		if (string.isBlank(param.getTitle())) {
			exs.add("title", "제목을 입력하세요.");
		}

		if (string.isBlank(param.getQuestion())) {
			exs.add("question", "질문내용을 입력하세요.");
		}

		if (exs.size() > 0) {
			throw exs;
		}

		/*
		 * 파일 저장
		 */
		String attachmentGroupId = cmmtQnaQuest.getQuestAttachmentGroupId();
		if (cmmtQna.getAttachable()) {
			if (files != null && files.size() > 0) {
				String[] exts = cmmtQna.getAttachmentExt().split("/");
				long size = cmmtQna.getAttachmentSize() * 1024 * 1024;
				attachmentService.uploadFiles_toGroup(config.getDir().getUpload(), attachmentGroupId, files, exts, size);
			}
		}

		/*
		 * 수정
		 */
		cmmtQnaQuest.setCategoryCd(param.getCategoryCd());
		cmmtQnaQuest.setTitle(param.getTitle());
		cmmtQnaQuest.setQuestion(param.getQuestion());
		cmmtQnaQuest.setQuestUpdatedDt(now);
		cmmtQnaQuestDao.update(cmmtQnaQuest);

		/*
		 *  출력
		 */
		cmmtQnaQuest = cmmtQnaQuestDao.select(cmmtQnaQuest.getQuestId());

		List<CmmtAttachment> questAttachmentList = attachmentService.getFileInfos_group(cmmtQnaQuest.getQuestAttachmentGroupId());
		List<CmmtAttachment> answerAttachmentList = attachmentService.getFileInfos_group(cmmtQnaQuest.getAnswerAttachmentGroupId());
		cmmtQnaQuest.setQuestAttachmentList(questAttachmentList);
		cmmtQnaQuest.setAnswerAttachmentList(answerAttachmentList);
		CmmtMember questioner = cmmtMemberDao.select(cmmtQnaQuest.getQuestionerId());
		CmmtInsider answerer = cmmtInsiderDao.select(cmmtQnaQuest.getAnswerUpdaterId());
		cmmtQnaQuest.setQuestioner(questioner);
		cmmtQnaQuest.setAnswerer(answerer);
		return cmmtQnaQuest;
	}

	public void remove(String qnaId, String questId) {
		BnMember worker = SecurityUtils.getCurrentMember();
		if (worker == null) {
			throw new InvalidationException("권한이 없습니다.");
		}

		CmmtQna cmmtQna = cmmtQnaDao.select(qnaId);
		if (cmmtQna == null) {
			throw new InvalidationException("게시판이 존재하지 않습니다.");
		}

		CmmtQnaQuest cmmtQnaQuest = cmmtQnaQuestDao.select(questId);
		if (cmmtQnaQuest == null) {
			throw new InvalidationException("글이 존재하지 않습니다.");
		}

		if (!string.equals(qnaId, cmmtQnaQuest.getQnaId())) {
			throw new InvalidationException("글이 해당 게시판의 글이 아닙니다.");
		}

		// 권한 검사: 요청 상태인 경우만 삭제할 수 있다.
		if (!string.equals(cmmtQnaQuest.getQuestSt(), CodeExt.questSt.요청)) {
			throw new InvalidationException("요청 상태인 경우만 삭제할 수 있습니다.");
		}

		// 권한 검사: 자신의 글만 삭제가능
		boolean canModify = qnaUtils.canModify(cmmtQnaQuest, worker.getMemberId());
		if (!canModify) {
			throw new InvalidationException("권한이 없습니다.");
		}

		// 글 삭제
		cmmtQnaQuestDao.delete(questId);

		// 질문용 첨부파일 삭제
		if (string.isNotBlank(cmmtQnaQuest.getQuestAttachmentGroupId())) {
			attachmentService.removeFiles_group(config.getDir().getUpload(), cmmtQnaQuest.getQuestAttachmentGroupId());
		}

		// 답변용 첨부파일 삭제(없겠지만)
		if (string.isNotBlank(cmmtQnaQuest.getAnswerAttachmentGroupId())) {
			attachmentService.removeFiles_group(config.getDir().getUpload(), cmmtQnaQuest.getAnswerAttachmentGroupId());
		}
	}

	public CmmtQnaQuest answer(String qnaId, String questId, String answer) {
		Date now = new Date();
		BnMember worker = SecurityUtils.getCurrentMember();
		if (worker == null) {
			throw new InvalidationException("권한이 없습니다.");
		}

		// 게시판 조회
		CmmtQna cmmtQna = cmmtQnaDao.select(qnaId);
		if (cmmtQna == null || !cmmtQna.getEnabled()) {
			throw new InvalidationException("게시판이 존재하지 않습니다.");
		}

		// 게시글 조회
		CmmtQnaQuest cmmtQnaQuest = cmmtQnaQuestDao.select(questId);
		if (cmmtQnaQuest == null) {
			throw new InvalidationException("글이 존재하지 않습니다.");
		}

		// 권한검사: 답변권한
		boolean canAnswer = qnaUtils.canAnswer(qnaId, worker.getMemberId());
		if (!canAnswer) {
			throw new InvalidationException("권한이 없습니다.");
		}

		// 요청/접수 상태인 경우에만 답변을 달 수 있다.
		String[] allowedQuestSts = {CodeExt.questSt.요청, CodeExt.questSt.접수};
		if (!array.contains(allowedQuestSts, cmmtQnaQuest.getQuestSt())) {
			throw new InvalidationException("답변을 달 수 있는 상태가 아닙니다.");
		}

		/*
		 * 입력검사
		 */
		if (string.isBlank(answer)) {
			throw new InvalidationException("답변을 입력하세요.");
		}

		/*
		 * 답변 달기
		 */
		cmmtQnaQuest.setQuestSt(CodeExt.questSt.답변);
		cmmtQnaQuest.setQuestStDt(now);
		cmmtQnaQuest.setAnswer(answer);
		cmmtQnaQuest.setAnswerCreatedDt(now);
		cmmtQnaQuest.setAnswerCreatorId(worker.getMemberId());
		cmmtQnaQuest.setAnswerUpdatedDt(now);
		cmmtQnaQuest.setAnswerUpdaterId(worker.getMemberId());

		cmmtQnaQuestDao.update(cmmtQnaQuest);

		/*
		 * 결과 출력
		 */
		List<CmmtAttachment> questAttachmentList = attachmentService.getFileInfos_group(cmmtQnaQuest.getQuestAttachmentGroupId());
		List<CmmtAttachment> answerAttachmentList = attachmentService.getFileInfos_group(cmmtQnaQuest.getAnswerAttachmentGroupId());
		cmmtQnaQuest.setQuestAttachmentList(questAttachmentList);
		cmmtQnaQuest.setAnswerAttachmentList(answerAttachmentList);
		CmmtMember questioner = cmmtMemberDao.select(cmmtQnaQuest.getQuestionerId());
		CmmtInsider answerer = cmmtInsiderDao.select(cmmtQnaQuest.getAnswerUpdaterId());
		cmmtQnaQuest.setQuestioner(questioner);
		cmmtQnaQuest.setAnswerer(answerer);
		return cmmtQnaQuest;
	}
}
