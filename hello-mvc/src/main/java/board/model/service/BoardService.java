package board.model.service;

import static common.JdbcTemplate.close;
import static common.JdbcTemplate.getConnection;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import board.model.dao.BoardDao;
import board.model.dto.Attachment;
import board.model.dto.BoardExt;

public class BoardService {
	public static final int NUM_PER_PAGE = 10; // 한페이지에 표시할 컨텐츠수
	private static BoardDao boardDao = new BoardDao();
	
	
	public List<BoardExt> findAll(Map<String, Object> param) {
		Connection conn = getConnection();
		List<BoardExt> list = boardDao.findAll(conn, param);
		close(conn);
		return list;
	}

	// DQL 
	// 조회결과가 1행1열
	public static int getTotalContents() {
		Connection conn = getConnection();
		int totalContents = boardDao.getTotalContents(conn);
		close(conn);
		return totalContents;
	}

//	public List<Attachment> findAllBoardAttach() {
//		Connection conn = getConnection();
//		List<Attachment> attachList = boardDao.findAllBoardAttach(conn);
//		close(conn);
//		return attachList;
//	}

}
