package boardReview;

public class Article {
	int id;
	String title;
	String body;
	String nickname;
	String regDate;
	int hit;

	public Article() {

	}

	public Article(int id, String title, String body, String nickname, String regDate, int hit) {
		super();
		this.id = id;
		this.title = title;
		this.body = body;
		this.nickname = nickname;
		this.regDate = regDate;
		this.hit = hit;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public int getHit() {
		return hit;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}

	public String getPropertiesFlag(int flag) {
		String str = "";
		if (flag == 1) {
			str = this.getTitle();
		} else if (flag == 2) {
			str = this.getBody();
		} else if (flag == 3) {
			str = this.getTitle() + this.getBody();
		} else {
			str = this.getNickname();

		}

		return str;
	}

}
