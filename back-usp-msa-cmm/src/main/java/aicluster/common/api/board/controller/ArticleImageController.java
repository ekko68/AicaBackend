package aicluster.common.api.board.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import aicluster.common.api.board.service.BoardImageService;
import aicluster.framework.common.entity.CmmtAttachment;
import aicluster.framework.common.service.AttachmentService;
import aicluster.framework.config.EnvConfig;
import bnet.library.util.dto.JsonList;

@RestController
@RequestMapping("/api/boards/{boardId}/articles/{articleId}/images")
public class ArticleImageController {
	
	@Autowired
	private BoardImageService boardImageService;

	@Autowired
	private AttachmentService attachmentService;

	@Autowired
	private EnvConfig config;

	@GetMapping("")
	public JsonList<CmmtAttachment> getList(@PathVariable String boardId, @PathVariable String articleId) {
		return boardImageService.getList(boardId, articleId);
	}

	@DeleteMapping("/{attachmentId}")
	public void remove(@PathVariable String boardId, @PathVariable String articleId, @PathVariable String attachmentId) {
		boardImageService.remove(boardId, articleId, attachmentId);
	}

	@GetMapping("/{attachmentId}")
	public void download(HttpServletResponse response, @PathVariable String boardId, @PathVariable String articleId, @PathVariable String attachmentId) {
		CmmtAttachment att = boardImageService.get(boardId, articleId, attachmentId);
		attachmentService.downloadFile_contentType(response, config.getDir().getUpload(), att.getAttachmentId());
	}
}
