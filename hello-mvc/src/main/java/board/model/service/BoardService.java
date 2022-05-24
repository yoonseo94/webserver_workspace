package board.model.service;

import static common.JdbcTemplate.close;
import static common.JdbcTemplate.commit;
import static common.JdbcTemplate.getConnection;
import static common.JdbcTemplate.rollback;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import board.model.dao.BoardDao;
import board.model.dto.Attachment;
import board.model.dto.BoardExt;



public class BoardService {
	public static final int NUM_PER_PAGE = 10; // 한페이지에 표시할 컨텐츠수
	private BoardDao boardDao = new BoardDao();
	
	
	public List<BoardExt> findAll(Map<String, Object> param) {
		Connection conn = getConnection();
		List<BoardExt> list = boardDao.findAll(conn, param);
		close(conn);
		return list;
	}

	// DQL 
	// 조회결과가 1행1열
	public int getTotalContents() {
		Connection conn = getConnection();
		int totalContents = boardDao.getTotalContents(conn);
		close(conn);
		return totalContents;
	}

    public  int insertBoard(BoardExt board) {
        int result = 0;
        Connection conn = getConnection();
        // try 로 묶은게 트랜잭션으로 묶여서 작동
        try {
        	// 1. board에 등록
            result = boardDao.insertBoard(conn, board); // pk no값 결정 - seq_board_no.nextVal
            
            // 2. board pk 가져오기 (왜냐면 attachment에 pk컬럼이 없기 때문에)
            int no = boardDao.findCurrentBoardNo(conn); // sqp_board_no.currVal
            System.out.println("등록된 board.no = " + no);
            
            
            // 2. attachment에 등록
            List<Attachment> attachments = board.getAttachments();
            if(attachments != null && !attachments.isEmpty()) {
            	for(Attachment attach : attachments) {
            		attach.setBoardNo(no);
            		result = boardDao.insertAttachment(conn, attach);
            	}
            }

            commit(conn);
        } catch (Exception e) {
            rollback(conn);
            throw e;  
        } finally {
            close(conn);
        }
        return result;
    }

//	public List<Attachment> findAllBoardAttach() {
//		Connection conn = getConnection();
//		List<Attachment> attachList = boardDao.findAllBoardAttach(conn);
//		close(conn);
//		return attachList;
//	}

}
