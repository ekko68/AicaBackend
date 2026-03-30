package aicluster.common.api.qna.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import aicluster.common.api.qna.service.QnaAttachmentService;
import aicluster.framework.common.entity.CmmtAttachment;
import aicluster.framework.common.service.AttachmentService;
import aicluster.framework.config.EnvConfig;
import bnet.library.util.dto.JsonList;

@RestController
@RequestMapping("/api/qna/{qnaId}/quests/{questId}/attachments")
public class QnaAttachmentController {

	@Autowired
	private QnaAttachmentService service;

	@Autowired
	private AttachmentService attachmentService;

	@Autowired
	private EnvConfig envConfig;

	/**
	 * 질문파일 목록 조회
	 *
	 * @param qnaId
	 * @param questId
	 * @return
	 */
	@GetMapping("")
	public JsonList<CmmtAttachment> getList(@PathVariable String qnaId, @PathVariable String questId) {
		return service.getList(qnaId, questId);
	}

	/**
	 * 첨부파일 다운로드
	 *
	 * @param response
	 * @param qnaId
	 * @param questId
	 * @param attachmentId
	 */
	@GetMapping("{attachmentId}")
	public void download(HttpServletResponse response, @PathVariable String qnaId, @PathVariable String questId, @PathVariable String attachmentId) {
		CmmtAttachment att = service.get(qnaId, questId, attachmentId);
		attachmentService.downloadFile(response, envConfig.getDir().getUpload(), att.getAttachmentId());
	}

	/**
	 * 첨부파일 삭제
	 *
	 * @param qnaId
	 * @param questId
	 * @param attachmentId
	 */
	@DeleteMapping("{attachmentId}")
	public void remove(@PathVariable String qnaId, @PathVariable String questId, @PathVariable String attachmentId) {
		service.remove(qnaId, questId, attachmentId);
	}
}
