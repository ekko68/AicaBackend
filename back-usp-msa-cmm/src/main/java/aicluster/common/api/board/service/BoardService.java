package aicluster.common.api.board.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aicluster.common.api.board.dto.BoardParam;
import aicluster.common.common.dao.CmmtAuthorityDao;
import aicluster.common.common.dao.CmmtBoardArticleDao;
import aicluster.common.common.dao.CmmtBoardAuthorityDao;
import aicluster.common.common.dao.CmmtBoardDao;
import aicluster.common.common.entity.CmmtAuthority;
import aicluster.common.common.entity.CmmtBoard;
import aicluster.common.common.entity.CmmtBoardAuthority;
import aicluster.common.common.util.BoardUtils;
import aicluster.framework.common.entity.BnMember;
import aicluster.framework.security.SecurityUtils;
import bnet.library.exception.InvalidationException;
import bnet.library.exception.InvalidationsException;
import bnet.library.util.dto.JsonList;

@Service
public class BoardService {

	@Autowired
	private CmmtBoardDao cmmtBoardDao;

	@Autowired
	private CmmtBoardArticleDao cmmtBoardArticleDao;

	@Autowired
	private CmmtBoardAuthorityDao cmmtBoardAuthorityDao;

	@Autowired
	private CmmtAuthorityDao cmmtAuthorityDao;

	@Autowired
	private BoardUtils boardUtils;

	public JsonList<CmmtBoard> getList(String systemId, Boolean enabled, String boardId, String boardNm) {
		List<CmmtBoard> list = cmmtBoardDao.selectList(systemId, enabled, boardId, boardNm);
		return new JsonList<>(list);
	}

	public CmmtBoard add(BoardParam param) {
		Date now = new Date();
		BnMember worker = SecurityUtils.getCurrentMember();

		if (param == null) {
			throw new InvalidationException("입력이 없습니다.");
		}

		final CmmtBoard board = param.getBoard();
		if (board == null) {
			throw new InvalidationException("입력이 없습니다.");
		}

		/*
		 * 입력검사
		 */

		InvalidationsException exs = new InvalidationsException();

		CmmtBoard dbBoard = cmmtBoardDao.select(board.getBoardId());
		if (dbBoard != null) {
			exs.add("boardId", "게시판ID가 이미 사용중입니다.");
		}
		else if (!boardUtils.isValidBoardId(board.getBoardId())) {
			exs.add("boardId", "게시판ID는 '영문/숫자/-'로만 입력해야 합니다.");
		}

		boardUtils.checkCmmtBoard(param.getBoard(), exs);

		/*
		 * 오류가 있다면, throw
		 */
		if (exs.size() > 0) {
			throw exs;
		}

		/*
		 * 권한정보
		 */
		List<CmmtBoardAuthority> baList = new ArrayList<>();
		param.getAuthority().forEach((authorityId, boardAuthority)->{
			CmmtAuthority authority = cmmtAuthorityDao.select(authorityId);
			if (authority == null) {
				throw new InvalidationException("권한정보가 올바르지 않습니다.");
			}
			CmmtBoardAuthority ba = CmmtBoardAuthority.builder()
					.boardId(board.getBoardId())
					.authorityId(authorityId)
					.boardAuthority(boardAuthority)
					.createdDt(now)
					.creatorId(worker.getMemberId())
					.build();
			baList.add(ba);
		});


		board.setCreatedDt(now);
		board.setCreatorId(worker.getMemberId());
		board.setUpdatedDt(now);
		board.setUpdaterId(worker.getMemberId());

		cmmtBoardDao.insert(board);

		cmmtBoardAuthorityDao.insertList(baList);

		CmmtBoard newBoard = cmmtBoardDao.select(board.getBoardId());
		List<CmmtBoardAuthority> authorityList = cmmtBoardAuthorityDao.selectList(newBoard.getBoardId());
		newBoard.setAuthorityList(authorityList);

		return newBoard;
	}

	public CmmtBoard get(String boardId) {
		CmmtBoard cmmtBoard = cmmtBoardDao.select(boardId);
		if (cmmtBoard == null) {
			throw new InvalidationException("게시판 정보가 없습니다.");
		}
		List<CmmtBoardAuthority> authorityList = cmmtBoardAuthorityDao.selectList(boardId);
		cmmtBoard.setAuthorityList(authorityList);
		return cmmtBoard;
	}

	public CmmtBoard modify(BoardParam param) {
		final CmmtBoard cmmtBoard = param.getBoard();
		if (cmmtBoard == null) {
			throw new InvalidationException("게시판 정보를 입력하세요.");
		}

		Map<String, String> authorityMap = param.getAuthority();
		if (authorityMap == null) {
			throw new InvalidationException("게시판 권한 정보를 입력하세요.");
		}

		CmmtBoard dbBoard = cmmtBoardDao.select(cmmtBoard.getBoardId());
		if (dbBoard == null) {
			throw new InvalidationException("등록되지 않은 게시판입니다.");
		}

		InvalidationsException exs = new InvalidationsException();
		boardUtils.checkCmmtBoard(cmmtBoard, exs);


		if (exs.size() > 0) {
			throw exs;
		}

		BnMember worker = SecurityUtils.getCurrentMember();
		Date now = new Date();

		/*
		 * 권한정보
		 */
		List<CmmtBoardAuthority> baList = new ArrayList<>();
		param.getAuthority().forEach((authorityId, boardAuthority)->{
			CmmtAuthority authority = cmmtAuthorityDao.select(authorityId);
			if (authority == null) {
				throw new InvalidationException("권한정보가 올바르지 않습니다.");
			}
			CmmtBoardAuthority ba = CmmtBoardAuthority.builder()
					.boardId(cmmtBoard.getBoardId())
					.authorityId(authorityId)
					.boardAuthority(boardAuthority)
					.createdDt(now)
					.creatorId(worker.getMemberId())
					.build();
			baList.add(ba);
		});

		/*
		 * 게시판정보 수정
		 */
		cmmtBoard.setArticleCnt(dbBoard.getArticleCnt());
		cmmtBoard.setCreatedDt(dbBoard.getCreatedDt());
		cmmtBoard.setCreatorId(dbBoard.getCreatorId());
		cmmtBoard.setUpdatedDt(now);
		cmmtBoard.setUpdaterId(worker.getMemberId());

		cmmtBoardDao.update(cmmtBoard);

		/*
		 * 권한정보 수정
		 */
		cmmtBoardAuthorityDao.delete_board(cmmtBoard.getBoardId());
		cmmtBoardAuthorityDao.insertList(baList);

		dbBoard = cmmtBoardDao.select(cmmtBoard.getBoardId());
		List<CmmtBoardAuthority> authorityList = cmmtBoardAuthorityDao.selectList(cmmtBoard.getBoardId());
		dbBoard.setAuthorityList(authorityList);

		return dbBoard;
	}

	public void remove(String boardId) {
		// 게시판 존재 검사
		CmmtBoard cmmtBoard = cmmtBoardDao.select(boardId);
		if (cmmtBoard == null) {
			throw new InvalidationException("게시판이 존재하지 않습니다.");
		}

		// 게시글 존재 검사
		boolean exists = cmmtBoardArticleDao.existsBoardArticles(boardId);
		if (exists) {
			throw new InvalidationException("게시글이 존재하여 삭제할 수 없습니다.");
		}

		// 게시판 권한 삭제
		cmmtBoardAuthorityDao.delete_board(boardId);

		// 게시판 삭제
		cmmtBoardDao.delete(boardId);
	}

}
