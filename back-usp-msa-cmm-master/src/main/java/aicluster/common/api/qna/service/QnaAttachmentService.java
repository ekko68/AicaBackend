package aicluster.common.api.qna.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aicluster.common.common.dao.CmmtQnaDao;
import aicluster.common.common.dao.CmmtQnaQuestDao;
import aicluster.common.common.entity.CmmtQna;
import aicluster.common.common.entity.CmmtQnaQuest;
import aicluster.common.common.util.QnaUtils;
import aicluster.framework.common.entity.BnMember;
import aicluster.framework.common.entity.CmmtAttachment;
import aicluster.framework.common.service.AttachmentService;
import aicluster.framework.config.EnvConfig;
import aicluster.framework.security.SecurityUtils;
import bnet.library.exception.InvalidationException;
import bnet.library.util.CoreUtils.string;
import bnet.library.util.dto.JsonList;

@Service
public class QnaAttachmentService {

	@Autowired
	private CmmtQnaDao cmmtQnaDao;

	@Autowired
	private CmmtQnaQuestDao cmmtQnaQuestDao;

	@Autowired
	private QnaUtils qnaUtils;

	@Autowired
	private AttachmentService attachmentService;

	@Autowired
	private EnvConfig envConfig;

	public JsonList<CmmtAttachment> getList(String qnaId, String questId) {
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

		/*
		 * 권한검사
		 */
		boolean canRead = qnaUtils.canRead(qnaId, cmmtQnaQuest, worker.getMemberId());
		if (!canRead) {
			throw new InvalidationException("권한이 없습니다.");
		}

		List<CmmtAttachment> list = new ArrayList<>();
		if (string.isNotBlank(cmmtQnaQuest.getQuestAttachmentGroupId())) {
			list = attachmentService.getFileInfos_group(cmmtQnaQuest.getQuestAttachmentGroupId());
		}
		return new JsonList<>(list);
	}

	public CmmtAttachment get(String qnaId, String questId, String attachmentId) {
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

		/*
		 * 권한검사
		 */
		boolean canRead = qnaUtils.canRead(qnaId, cmmtQnaQuest, worker.getMemberId());
		if (!canRead) {
			throw new InvalidationException("권한이 없습니다.");
		}

		CmmtAttachment att = attachmentService.getFileInfo(attachmentId);
		if (!string.equals(cmmtQnaQuest.getQuestAttachmentGroupId(), att.getAttachmentGroupId())) {
			throw new InvalidationException("올바르지 않은 접근입니다.");
		}

		return att;
	}

	public void remove(String qnaId, String questId, String attachmentId) {
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

		/*
		 * 권한
		 */
		boolean canModify = qnaUtils.canModify(cmmtQnaQuest, worker.getMemberId());
		if (!canModify) {
			throw new InvalidationException("권한이 없습니다.");
		}

		// 파일 삭제
		attachmentService.removeFile(envConfig.getDir().getUpload(), attachmentId);
	}

}
