package photo.model.dto;

import java.sql.Date;

public class Photo {
	private int no;
	private String memberId;
	private String content;
	private String originalFilename;
	private String renamedFilename;
	private int readCount;
	private Date regDate;
	public Photo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Photo(int no, String memberId, String content, String originalFilename, String renamedFilename,
			int readCount, Date regDate) {
		super();
		this.no = no;
		this.memberId = memberId;
		this.content = content;
		this.originalFilename = originalFilename;
		this.renamedFilename = renamedFilename;
		this.readCount = readCount;
		this.regDate = regDate;
	}
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
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
	public String getOriginalFilename() {
		return originalFilename;
	}
	public void setOriginalFilename(String originalFilename) {
		this.originalFilename = originalFilename;
	}
	public String getRenamedFilename() {
		return renamedFilename;
	}
	public void setRenamedFilename(String renamedFilename) {
		this.renamedFilename = renamedFilename;
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
		return "Photo [no=" + no + ", memberId=" + memberId + ", content=" + content + ", originalFilename="
				+ originalFilename + ", renamedFilename=" + renamedFilename + ", readCount=" + readCount + ", regDate="
				+ regDate + "]";
	}
	
	
}
