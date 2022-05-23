package board.model.dto;

import java.sql.Date;

public class Board {

	private int no;
	private String title;
	private String memberId;
	private String content;
	private int readCount;
	private Date regDate;
	
	public Board() {
		super();
	}

	public Board(int number, String title, String memberId, String content, int readCount, Date regDate) {
		super();
		this.no = number;
		this.title = title;
		this.memberId = memberId;
		this.content = content;
		this.readCount = readCount;
		this.regDate = regDate;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int number) {
		this.no = number;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getReadCount() {
		return readCount;
	}

	public void setReadCount(int readCount) {
		this.readCount = readCount;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	@Override
	public String toString() {
		return "Board [number=" + no + ", title=" + title + ", memberId=" + memberId + ", content=" + content
				+ ", readCount=" + readCount + ", regDate=" + regDate + "]";
	}
		
}
