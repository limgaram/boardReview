package boardReview;

public class Member {

	private int id;
	private String memberId;
	private String memberPW;
	private String nickname;
	private String regDate;

	public Member() {

	}

	public Member(int id, String memberId, String memberPW, String nickname, String regDate) {
		super();
		this.id = id;
		this.memberId = memberId;
		this.memberPW = memberPW;
		this.nickname = nickname;
		this.regDate = regDate;

	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMemberPW() {
		return memberPW;
	}

	public void setMemberPW(String memberPW) {
		this.memberPW = memberPW;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

}
