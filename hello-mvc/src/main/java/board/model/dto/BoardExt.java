package board.model.dto;

public class BoardExt extends Board {

	private int attachCount;

	public int getAttachCount() {
		return attachCount;
	}

	public void setAttachCount(int attachCount) {
		this.attachCount = attachCount;
	}

	@Override
	public String toString() {
		return "BoardExt [attachCount=" + attachCount + ", toString()=" + super.toString() + "]";
	}
	
}