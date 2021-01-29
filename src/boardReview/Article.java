//데이터 구조화 클래스 - DTO, VO
package boardReview;

public class Article {
	private int id;
	private String title;
	private String body;
	private String regDate;
	private int mid;
	private int hit;
	private int likeCnt;

	public Article() {

	}

	public Article(int id, String title, String body, int mid, String regDate, int hit, int likeCnt) {
		super();
		this.id = id;
		this.title = title;
		this.body = body;
		this.mid = mid;
		this.regDate = regDate;
		this.hit = hit;
		this.likeCnt = likeCnt;
	}

	public int getLikeCnt() {
		return likeCnt;
	}

	public void setLikeCnt(int likeCnt) {
		this.likeCnt = likeCnt;
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

	public int getMid() {
		return mid;
	}

	public void setMid(int mid) {
		this.mid = mid;
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
			// str = this.getMid() + "";

		}

		return str;
	}

}
