package boardReview;

import java.util.ArrayList;
import java.util.Date;

public class LikeDao {

	private ArrayList<Like> likes;
	private int no = 1;

	public LikeDao() {
		likes = new ArrayList<>();

	}

	public void insertLike(Like like) {
		like.setId(no);
		no++;
		like.setRegDate(Util.getCurrentDate());
		likes.add(like);
	}

	public void removeLike(Like like) {
		likes.remove(like);
	}

	public Like getLikeByArticleIdAndMemberId(int aid, int mid) {

		for (int i = 0; i < likes.size(); i++) {
			Like like = likes.get(i);
			if (like.getParentId() == aid && like.getMemberId() == mid) {
				return like;
			}
		}
		return null;
	}

	public int getLikeCount(int id) {

		int cnt = 0;
		for (int i = 0; i < likes.size(); i++) {
			Like like = likes.get(i);
			if (like.getParentId() == id) {
				cnt++;
			}
		}

		return cnt;
	}

}
