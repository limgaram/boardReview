package boardReview;

import java.util.ArrayList;
import java.util.Date;

public class MemberDao {

	private static ArrayList<Member> members;
	private int no = 1;

	public MemberDao() {
		members = new ArrayList<Member>();
		Member m1 = new Member(1, "a1", "aa", "aa", Util.getCurrentDate());
		Member m2 = new Member(2, "b1", "bb", "bb", Util.getCurrentDate());
		Member m3 = new Member(3, "c1", "cc", "cc", Util.getCurrentDate());

		members.add(m1);
		members.add(m2);
		members.add(m3);

	}

	public void insertMember(Member m) {
		m.setId(no);
		no++;
		m.setRegDate(Util.getCurrentDate());

		members.add(m);
	}

	public Member getMemberById(int id) {
		for (int i = 0; i < members.size(); i++) {
			Member m = members.get(i);
			if (m.getId() == id) {
				return m;
			}
		}
		return null;
	}

	public Member getMemberByLoginIdAndLoginPw(String memberId, String memberPw) {

		for (int i = 0; i < members.size(); i++) {
			Member m = members.get(i);
			if (m.getMemberId().equals(memberId) && m.getMemberPW().equals(memberPw)) {
				return m;
			}
		}
		return null;
	}

}
