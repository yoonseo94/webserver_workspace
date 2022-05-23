package board.model.dto;

import java.sql.Date;

public class Attachment {
	private int no;
	private int boardNo;
	private String originalFilename;
	private String renameFilename;
	private Date regDate;
	
	public Attachment() {
		super();
	}
	
	public Attachment(int no, int boardNo, String originalFilename, String renameFilename, Date regDate) {
		super();
		this.no = no;
		this.boardNo = boardNo;
		this.originalFilename = originalFilename;
		this.renameFilename = renameFilename;
		this.regDate = regDate;
	}
	
	public int getNo() {
		return no;
	}
	public void setNo(int number) {
		this.no = number;
	}
	public int getBoardNo() {
		return boardNo;
	}
	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}
	public String getOriginalFilename() {
		return originalFilename;
	}
	public void setOriginalFilename(String originalFilename) {
		this.originalFilename = originalFilename;
	}
	public String getRenameFilename() {
		return renameFilename;
	}
	public void setRenameFilename(String renameFilename) {
		this.renameFilename = renameFilename;
	}
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	
	@Override
	public String toString() {
		return "Attachment [number=" + no + ", boardNo=" + boardNo + ", originalFilename=" + originalFilename
				+ ", renameFilename=" + renameFilename + ", regDate=" + regDate + "]";
	}

}
