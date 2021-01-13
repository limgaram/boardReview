package boardReview;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ReplyDao {
	private static ArrayList<Reply> replies;
	private int no = 1;

	public ReplyDao() {

		replies = new ArrayList<>();

	}

	public void insertReply(Reply a) {
		a.setId(no);
		no++;
		a.setRegDate(getCurrentDate());

		replies.add(a);
	}

	public ArrayList<Reply> getReplies() {
		return replies;
	}

	public ArrayList<Reply> getRepliesByParentId(int parentId) {
		ArrayList<Reply> searchedReplies = new ArrayList<>();
		for (int i = 0; i < replies.size(); i++) {
			Reply reply = replies.get(i);
			if (reply.getParentId() == parentId) {
				searchedReplies.add(reply);
			}
		}
		return replies;
	}

	private static String getCurrentDate() {
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy.MM.dd");
		Date time = new Date();
		String time1 = format1.format(time);

		return time1;
	}
}
