package aicluster.common.api.board.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aicluster.common.common.dao.CmmtBoardArticleDao;
import aicluster.common.common.dao.CmmtBoardDao;
import aicluster.common.common.entity.CmmtBoard;
import aicluster.common.common.entity.CmmtBoardArticle;
import aicluster.common.common.util.BoardUtils;
import aicluster.framework.common.entity.BnMember;
import aicluster.framework.common.entity.CmmtAttachment;
import aicluster.framework.common.service.AttachmentService;
import aicluster.framework.config.EnvConfig;
import aicluster.framework.security.SecurityUtils;
import bnet.library.exception.InvalidationException;
import bnet.library.util.CoreUtils.string;
import bnet.library.util.dto.JsonList;

@Service
public class BoardAttachmentService {

	@Autowired
	private CmmtBoardDao cmmtBoardDao;

	@Autowired
	private CmmtBoardArticleDao cmmtBoardArticleDao;

	@Autowired
	private AttachmentService attachmentService;

	@Autowired
	private EnvConfig config;

	@Autowired
	private BoardUtils boardUtils;

	public JsonList<CmmtAttachment> getList(String boardId, String articleId) {
		CmmtBoard cmmtBoard = cmmtBoardDao.select(boardId);
		if (cmmtBoard == null) {
			throw new InvalidationException("게시판이 존재하지 않습니다.");
		}

		CmmtBoardArticle cmmtBoardArticle = cmmtBoardArticleDao.select(articleId);
		if (cmmtBoardArticle == null || !string.equals(boardId, cmmtBoardArticle.getBoardId())) {
			throw new InvalidationException("게시글이 존재하지 않습니다.");
		}

		BnMember worker = SecurityUtils.getCurrentMember();

		// 권한검사
		if (!boardUtils.canReadArticle(cmmtBoard, worker)) {
			throw new InvalidationException("권한이 없습니다.");
		}

		if (!cmmtBoard.getAttachable()) {
			return new JsonList<>(new ArrayList<CmmtAttachment>());
		}
		if (string.isBlank(cmmtBoardArticle.getAttachmentGroupId())) {
			return new JsonList<>(new ArrayList<CmmtAttachment>());
		}

		List<CmmtAttachment> list =  attachmentService.getFileInfos_group(cmmtBoardArticle.getAttachmentGroupId());

		return new JsonList<>(list);
	}

	public void remove(String boardId, String articleId, String attachmentId) {
		CmmtBoard cmmtBoard = cmmtBoardDao.select(boardId);
		if (cmmtBoard == null) {
			throw new InvalidationException("게시판이 존재하지 않습니다.");
		}

		CmmtBoardArticle cmmtBoardArticle = cmmtBoardArticleDao.select(articleId);
		if (cmmtBoardArticle == null) {
			throw new InvalidationException("게시글이 존재하지 않습니다.");
		}

		BnMember worker = SecurityUtils.getCurrentMember();

		// 권한검사
		if (!boardUtils.canRemoveArticle(boardId, cmmtBoardArticle, worker)) {
			throw new InvalidationException("권한이 없습니다.");
		}

		// 첨부파일을 사용할 수 없는 게시판이라면...
		if (!cmmtBoard.getAttachable()) {
			throw new InvalidationException("파일이 없습니다.");
		}

		// 첨부파일 조회
		CmmtAttachment att = attachmentService.getFileInfo(attachmentId);
		if (att == null || !string.equals(cmmtBoardArticle.getAttachmentGroupId(), att.getAttachmentGroupId())) {
			throw new InvalidationException("파일이 없습니다.");
		}

		attachmentService.removeFile(config.getDir().getUpload(), attachmentId);
	}

	public CmmtAttachment get(String boardId, String articleId, String attachmentId) {
		CmmtBoard cmmtBoard = cmmtBoardDao.select(boardId);
		if (cmmtBoard == null) {
			throw new InvalidationException("게시판이 존재하지 않습니다.");
		}

		CmmtBoardArticle cmmtBoardArticle = cmmtBoardArticleDao.select(articleId);
		if (cmmtBoardArticle == null) {
			throw new InvalidationException("게시글이 존재하지 않습니다.");
		}

		BnMember worker = SecurityUtils.getCurrentMember();

		// 권한검사
		if (!boardUtils.canReadArticle(cmmtBoard, worker)) {
			throw new InvalidationException("권한이 없습니다.");
		}

		// 첨부파일을 사용할 수 없는 게시판이라면...
		if (!cmmtBoard.getAttachable()) {
			throw new InvalidationException("파일이 없습니다.");
		}

		// 파일 조회
		CmmtAttachment att = attachmentService.getFileInfo(attachmentId);
		if (att == null || !string.equals(cmmtBoardArticle.getAttachmentGroupId(), att.getAttachmentGroupId())) {
			throw new InvalidationException("파일이 없습니다.");
		}

		return att;
	}

}
