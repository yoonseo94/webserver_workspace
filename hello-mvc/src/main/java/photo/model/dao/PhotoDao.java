package photo.model.dao;

import static common.JdbcTemplate.close;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import photo.model.dto.Photo;
import photo.model.exception.PhotoException;

public class PhotoDao {

	private Properties prop = new Properties();

	public PhotoDao() {
		String filename = PhotoDao.class.getResource("/sql/photo-query.properties").getPath();
		
		try {
			prop.load(new FileReader(filename));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int getTotalContent(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("getTotalcontent");
		int totalContent = 0;
		
		try {
			pstmt = conn.prepareStatement(sql);
			rset = pstmt.executeQuery();
			while (rset.next()) {
				totalContent = rset.getInt(1);
			}
		} catch (Exception e) {
			throw new PhotoException("전체 게시물수 조회 오류!", e);
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return totalContent;
	}

	public List<Photo> findMorePage(Connection conn, Map<String, Integer> param) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("findMorePage");
		List<Photo> list = new ArrayList<>();
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, param.get("start"));
			pstmt.setInt(2, param.get("end"));
			rset = pstmt.executeQuery();
			while(rset.next()) {
				Photo photo = handlePhotoResultSet(rset);
				list.add(photo);
			}
		} catch (Exception e) {
			throw new PhotoException("더보기 페이지 조회 오류!", e);
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return list;
	}

	private Photo handlePhotoResultSet(ResultSet rset) throws SQLException {
		int no = rset.getInt("no");
		String memberId = rset.getString("member_id");
		String content = rset.getString("content");
		String originalFilename = rset.getString("original_filename");
		String renamedFilename = rset.getString("renamed_filename");
		int readCount = rset.getInt("read_count");
		Date regDate = rset.getDate("reg_date");
		return new Photo(no, memberId, content, originalFilename, renamedFilename, readCount, regDate);
	}
}
