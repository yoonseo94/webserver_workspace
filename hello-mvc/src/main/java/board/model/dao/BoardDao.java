package board.model.dao;

import static common.JdbcTemplate.close;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import board.model.dto.Attachment;
import board.model.dto.BoardExt;
import board.model.exception.BoardException;



public class BoardDao {
	private Properties prop = new Properties();

	public BoardDao() {
		String fileName = BoardDao.class.getResource("/sql/board-query.properties").getPath();
		try {
			prop.load(new FileReader(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * 1건 조회시 Board객체 하나 또는 null 리턴
	 * n건 조회시 여러건의 Board객체를 가진 list 또는 빈 list
	 * DQL 일때 resultset필요
	 */
	public List<BoardExt> findAll(Connection conn, Map<String, Object> param) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		List<BoardExt> list = new ArrayList<>();
		String sql = prop.getProperty("findAll");
		
		try {
			pstmt = conn.prepareStatement(sql);
			// 시작 페이지 끝나는 페이지 (1페이지 : 1 ~ 10)
			pstmt.setInt(1, (int) param.get("start"));
			pstmt.setInt(2, (int) param.get("end"));
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				BoardExt board = handleBoardResultSet(rset);
				list.add(board);
			}
		} catch (Exception e) {
			throw new BoardException("게시글 목록 조회 오류", e);
		} finally {
			close(rset);
			close(pstmt);
		}
		return list;
	}

	private BoardExt handleBoardResultSet(ResultSet rset) throws SQLException {
		BoardExt board = new BoardExt();
		board.setNo(rset.getInt("no"));
		board.setTitle(rset.getString("title"));
		board.setMemberId(rset.getString("member_id"));
		board.setContent(rset.getString("content"));
		board.setReadCount(rset.getInt("read_count"));				
		board.setRegDate(rset.getDate("reg_date"));				
		board.setAttachCount(rset.getInt("attach_cnt"));
		
		return board;
	}

	public int getTotalContents(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		int totalContents = 0;
		String sql = prop.getProperty("getTotalContents");
		
		try {
			pstmt = conn.prepareStatement(sql);
			rset = pstmt.executeQuery();
			while(rset.next()) {
				totalContents = rset.getInt(1); // 컬럼인덱스
			}
		} catch (SQLException e) {
			throw new BoardException("전체 게시판 수 조회 오류", e);
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return totalContents;
	}

    public int insertBoard(Connection conn, BoardExt board) {
        PreparedStatement pstmt = null;
        String sql = prop.getProperty("insertBoard");
        int result = 0;
        
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, board.getTitle());
            pstmt.setString(2, board.getMemberId());
            pstmt.setString(3, board.getContent());    
            
            result = pstmt.executeUpdate();
        } catch (Exception e) {
            throw new BoardException("게시글 등록 오류", e);
        } finally {
            close(pstmt);
        }
        return result;
    }

    // DQL 1행1열짜리는 반복문으로 인덱스 1 가져오기
	public int findCurrentBoardNo(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		int no = 0;
		String sql = prop.getProperty("findCurrentBoardNo");
		
		try {
			pstmt = conn.prepareStatement(sql);
			rset = pstmt.executeQuery();
			while(rset.next())
				no = rset.getInt(1);
			
		} catch (SQLException e) {
			throw new BoardException("게시글번호 조회 오류", e);
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return no;
	}

	// DML 은 int를 반환하도록 처리한다.
	public int insertAttachment(Connection conn, Attachment attach) {
		PreparedStatement pstmt = null;
        String sql = prop.getProperty("insertAttachment");
        int result = 0;
        
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, attach.getBoardNo());
            pstmt.setString(2, attach.getOriginalFilename());
            pstmt.setString(3, attach.getRenameFilename());    
            
            result = pstmt.executeUpdate();
        } catch (Exception e) {
            throw new BoardException("첨부파일 등록 오류", e);
        } finally {
            close(pstmt);
        }
        return result;
	}

//	public List<Attachment> findAllBoardAttach(Connection conn) {
//		PreparedStatement pstmt = null;
//		ResultSet  rset = null;
//		List<Attachment> attachList = new ArrayList<>();
//		String sql = prop.getProperty("findAllBoardAttach");
//		
//		try {
//			
//		} catch (Exception e) {
//			throw new BoardException("첨부파일 게시판 관련 오류", e);
//		} finally {
//			close(rset);
//			close(pstmt);
//		}
//		return attachList;
//	}


}
