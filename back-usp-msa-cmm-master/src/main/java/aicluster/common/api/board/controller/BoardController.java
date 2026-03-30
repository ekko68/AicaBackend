package aicluster.common.api.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import aicluster.common.api.board.dto.BoardParam;
import aicluster.common.api.board.service.BoardService;
import aicluster.common.common.entity.CmmtBoard;
import bnet.library.exception.InvalidationException;
import bnet.library.util.dto.JsonList;

@RestController
@RequestMapping("/api/boards")
public class BoardController {

	@Autowired
	private BoardService service;

	@GetMapping("")
	public JsonList<CmmtBoard> getList(String systemId, Boolean enabled, String boardId, String boardNm) {
		return service.getList(systemId, enabled, boardId, boardNm);
	}

	@PostMapping("")
	public CmmtBoard add(@RequestBody BoardParam param) {
		return service.add( param );
	}

	@GetMapping("/{boardId}")
	public CmmtBoard get(@PathVariable String boardId) {
		return service.get(boardId);
	}

	@PutMapping("/{boardId}")
	public CmmtBoard modify(@PathVariable String boardId, @RequestBody BoardParam param) {
		if (param == null) {
			throw new InvalidationException("입력이 없습니다.");
		}
		if (param.getBoard() == null) {
			throw new InvalidationException("입력이 없습니다.");
		}
		param.getBoard().setBoardId(boardId);
		if (param.getAuthority() == null) {
			throw new InvalidationException("게시판 권한 정보가 없습니다.");
		}
		return service.modify(param);
	}

	@DeleteMapping("/{boardId}")
	public void remove(@PathVariable String boardId) {
		service.remove(boardId);
	}
}
