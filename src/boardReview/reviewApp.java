package boardReview;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class reviewApp {
	Scanner sc = new Scanner(System.in);

	static ArticleDao articleDao = new ArticleDao();
	static ReplyDao replyDao = new ReplyDao();
	static MemberDao memberDao = new MemberDao();
	static LikeDao likeDao = new LikeDao();
	static Member loginedMember = null;

	public void start() {

		while (true) {

			if (loginedMember == null) {
				System.out.println("명령어 입력 : ");
			} else {
				System.out.println("명령어 입력[" + loginedMember.getMemberId() + "(" + loginedMember.getNickname() + "] :");
			}

			String cmd = sc.nextLine();

			if (cmd.equals("article list")) {

				articleList();

			} else if (cmd.equals("article add")) {

				if (!isLogin()) {
					continue;
				}
				addArticle();

			} else if (cmd.equals("article update")) {

				updateArticle();

			} else if (cmd.equals("article delete")) {

				deleteArticle();

			} else if (cmd.equals("article read")) {

				readArticle();

			} else if (cmd.equals("article search")) {

				searchArticle();

			} else if (cmd.equals("member signup")) {

				signupMember();

			} else if (cmd.equals("member signin")) {

				loginMember();

			} else if (cmd.equals("help")) {

				System.out.println("article [add: 게시물 추가 / list : 게시물 목록 조회 / read : 게시물 조회 / search : 검색]");
				System.out.println(
						"member [signup : 회원가입 / signin : 로그인 / findpass : 비밀번호 찾기 / findid : 아이디 찾기 / logout : 로그아웃 / myinfo : 나의 정보 확인및 수정]");

			} else if (cmd.equals("member logout")) {
				if (!isLogin()) {
					continue;
				}

				logoutMember();

			} else if (cmd.equals("article sort")) {
				sortArticle();
			} else if (cmd.equals("article page")) {
				paging();
			} else if (cmd.equals("exit")) {
				System.out.println("종료");
				break;

			}
		}
	}

	private void sortArticle() {
		System.out.println("정렬 대상을 선택해주세요. (like: 좋아요, hit: 조회수) : ");
		String sortType = sc.nextLine();
		System.out.println("정렬 방법을 선택해주세요. (asc: 오름차순, desc: 내림차순) : ");
		String sortOrder = sc.nextLine();

		MyComparator comp = new MyComparator();
		comp.sortOrder = sortOrder;
		comp.sortType = sortType;

		ArrayList<Article> articles = articleDao.getArticles();
		Collections.sort(articles, comp);
		Pagination pagination = new Pagination();
		printArticles(articles, pagination);

	}

	private void logoutMember() {

		loginedMember = null;
		System.out.println("로그아웃 되셨습니다. ");

	}

	private void loginMember() {

		System.out.println("아이디를 입력해주세요 : ");
		String memberId = sc.nextLine();
		System.out.println("비밀번호를 입력해주세요 : ");
		String memberPw = sc.nextLine();

		Member member = memberDao.getMemberByLoginIdAndLoginPw(memberId, memberPw);

		if (member == null) {
			System.out.println("비밀번호를 틀렸거나 잘못된 회원정보입니다. ");
		} else {
			loginedMember = member;
			System.out.println(loginedMember.getNickname() + "님 환영합니다!");

		}

	}

	public void signupMember() {

		System.out.println("====회원 가입을 진행합니다.====");
		Member m = new Member();
		System.out.println("아이디를 입력해주세요 : ");
		m.setMemberId(sc.nextLine());
		System.out.println("비밀번호를 입력해주세요 : ");
		m.setMemberPW(sc.nextLine());
		System.out.println("닉네임을 입력해주세요 : ");
		m.setNickname(sc.nextLine());
		memberDao.insertMember(m);

		System.out.println("====회원가입이 완료되었습니다.====");

	}

	public void searchArticle() {

		System.out.println("검색 항목을 선택해주세요.");
		System.out.println("(1. 제목, 2. 내용, 3. 제목 + 내용, 4. 작성자) : ");
		int flag = Integer.parseInt(sc.nextLine());
		System.out.println("검색 키워드를 입력해주세요: ");
		String keyword = sc.nextLine();

		ArrayList<Article> searchedArticles = articleDao.getSearchedArticlesByFlag(flag, keyword);

		Pagination pagination = new Pagination();
		printArticles(searchedArticles, pagination);

	}

	public void readArticle() {

		System.out.println("상세보기할 게시물 선택 : ");
		int targetId = Integer.parseInt(sc.nextLine());

		Article targetArticle = articleDao.getArticleById(targetId);

		if (targetArticle == null) {
			System.out.println("게시물이 존재하지 않습니다. ");
		} else {
			targetArticle.setHit(targetArticle.getHit() + 1);
			printArticle(targetArticle);

			while (true) {

				System.out.println("상세보기 기능을 선택해주세요.");
				System.out.println(" (1. 댓글 등록, 2. 좋아요, 3. 수정, 4. 삭제, 5. 목록으로) : ");

				int readCmd = Integer.parseInt(sc.nextLine());

				if (readCmd == 1) {

					Reply r = new Reply();

					System.out.println("댓글 내용을 입력해주세요 : ");
					r.setBody(sc.nextLine());
					r.setParentId(targetArticle.getId());
					// r.setNickname(targetArticle.getNickname());

					replyDao.insertReply(r);

					System.out.println("댓글이 등록되었습니다. ");
					printArticle(targetArticle);
					// printReplies(r);

				} else if (readCmd == 2) {

					if (!isLogin()) {
						continue;
					}

					Like rst = likeDao.getLikeByArticleIdAndMemberId(targetArticle.getId(), loginedMember.getId());

					if (rst == null) {

						// 로그인한 아이디로 해당 게시물에 들어왔을 때, 좋아요 표시가 안눌려있을 때
						Like like = new Like(targetArticle.getId(), loginedMember.getId());
						likeDao.insertLike(like);
						targetArticle.setLikeCnt(targetArticle.getLikeCnt() + 1);

						System.out.println("좋아요를 체크했습니다. ");

					} else {

						likeDao.removeLike(rst);
						System.out.println("좋아요를 해제했습니다.");
						targetArticle.setLikeCnt(targetArticle.getLikeCnt() - 1);

					}

				} else if (readCmd == 3) {

					if (!isLogin() || !isMyArticle(targetArticle)) {
						continue;
					}

					System.out.println("게시물 제목을 입력해주세요. : ");
					targetArticle.setTitle(sc.nextLine());
					System.out.println("게시물 내용을 입력해주세요. : ");
					targetArticle.setBody(sc.nextLine());

					printArticle(targetArticle);

				} else if (readCmd == 4) {
					if (!isLogin() || isMyArticle(targetArticle)) {
						continue;
					}

					articleDao.removeArticle(targetArticle);
					break;

				} else if (readCmd == 5) {
					break;
				}

			}
		}

	}

	public void deleteArticle() {

		System.out.println("삭제할 게시물 선택 : ");
		int targetId = Integer.parseInt(sc.nextLine());

		Article targetArticle = articleDao.getArticleById(targetId);

		if (targetArticle == null) {

			System.out.println("게시물이 존재하지 않습니다. ");
		} else {

			articleDao.removeArticle(targetArticle);
			System.out.println(targetId + "번 게시물이 삭제되었습니다.");
		}

	}

	public void updateArticle() {

		System.out.println("수정할 게시물 선택 : ");
		int targetId = Integer.parseInt(sc.nextLine());

		Article targetArticle = articleDao.getArticleById(targetId);

		if (targetArticle == null) {

			System.out.println("없는 게시물입니다. ");

		} else {

			System.out.println("게시물 제목을 입력해주세요. : ");
			targetArticle.setTitle(sc.nextLine());
			System.out.println("게시물 내용을 입력해주세요. : ");
			targetArticle.setBody(sc.nextLine());

		}

	}

	public void addArticle() {

		Article a = new Article();

		System.out.print("게시물 제목을 입력해주세요. : ");
		a.setTitle(sc.nextLine());

		System.out.print("게시물 내용을 입력해주세요. : ");
		a.setBody(sc.nextLine());

		a.setMid(loginedMember.getId());

		articleDao.insertArticle(a);
		System.out.println("게시물이 등록되었습니다.");

	}

	public void articleList() {

		ArrayList<Article> articles = articleDao.getArticles();
		Pagination pagination = new Pagination();
		printArticles(articles, pagination);

	}

	private boolean isLogin() {

		if (loginedMember == null) {
			System.out.println("로그인이 필요한 기능입니다.");
			return false;
		} else {
			return true;
		}
	}

	private boolean isMyArticle(Article article) {
		if (loginedMember.getId() != article.getMid()) {
			System.out.println("자신의 게시물만 수정 가능합니다. ");
			return false;
		}
		return true;
	}

	private void paging() {

		ArrayList<Article> articles = articleDao.getArticles();
		int currentPageNo = 1;

		Pagination pagination = new Pagination();
		pagination.setCurrentPageNo(currentPageNo);
		printArticles(articles, pagination);

		while (true) {
			System.out.println("페이징 명령어를 입력해주세요. ");
			System.out.println(
					"((prev : 이전, next : 다음, prevPage : 이전페이지, nextPage : 다음페이지, count : 페이지당 게시물 수 go : 선택, back : 뒤로가기) :");

			String pageCmd = sc.nextLine();

			if (pageCmd.equals("next")) {

				pagination.setCurrentPageNo(pagination.getCurrentPageNo() + 1);

			} else if (pageCmd.equals("prev")) {

				pagination.setCurrentPageNo(pagination.getCurrentPageNo() - 1);

			} else if (pageCmd.equals("go")) {

				currentPageNo = Integer.parseInt(sc.nextLine());
				pagination.setCurrentPageNo(currentPageNo);

			} else if (pageCmd.equals("nextPage")) {

				int currentPageBlock = pagination.getCurrentPageBlock();
				pagination.setCurrentPageBlock(currentPageBlock + 1);
				pagination.setCurrentPageNo(pagination.getStartPageNoInBlock());

			} else if (pageCmd.equals("prevPage")) {

				int currentPageBlock = pagination.getCurrentPageBlock();
				pagination.setCurrentPageBlock(currentPageBlock - 1);
				pagination.setCurrentPageNo(pagination.getStartPageNoInBlock());

			} else if (pageCmd.equals("count")) {

				int count = Integer.parseInt(sc.nextLine());
				pagination.setArticlesCntPerPage(count);

			} else if (pageCmd.equals("back")) {
				break;
			}

			printArticles(articles, pagination);
		}

	}

	private static void printArticles(ArrayList<Article> articleList, Pagination pagination) {

//		int totalCntOfArticles = articleList.size(); // 전체 게시물 수
//		int startPageNo = 1; // 시작 페이지 번호
//		int articlesCntPerPage = 3; // 페이지 당 출력 게시물 개수
//		int pageCntPerBlock = 5; // 한 페이지 블록 당 페이지 개수
//		int endPageNo = (int) Math.ceil((double) totalCntOfArticles / articlesCntPerPage); // 마지막 페이지 번호.
//
//		if (currentPageNo < startPageNo) {
//			currentPageNo = startPageNo;
//		}
//
//		if (currentPageNo > endPageNo) {
//			currentPageNo = endPageNo;
//		}
//
//		int currentPageBlock = (int) Math.ceil((double) currentPageNo / pageCntPerBlock); // 현재 페이지 블록
//		int startPageNoInBlock = (currentPageBlock - 1) * pageCntPerBlock + 1; // 현재 페이지 블록의 시작 페이지 번호
//		int endPageNoInBlock = startPageNoInBlock + pageCntPerBlock - 1; // 현재 페이지 블록의 마지막 페이지 번호
//
//		if (endPageNoInBlock > endPageNo) {
//			endPageNoInBlock = endPageNo;
//		}
//
//		int startIndex = (currentPageNo - 1) * articlesCntPerPage; // 해당 페이지의 게시물 목록의 첫 인덱스
//		int endIndex = startIndex + articlesCntPerPage; // 해당 페이지의 게시물 목록의 마지막 인덱스
//
//		if (endIndex > totalCntOfArticles) {
//			endIndex = totalCntOfArticles;
//		}
//
//		System.out.println(endPageNoInBlock);

		for (int i = pagination.getStartIndex(); i < pagination.getEndIndex(); i++) {
			Article article = articleList.get(i);

			System.out.println("번호 : " + article.getId());
			System.out.println("제목 : " + article.getTitle());
			Member regMember = memberDao.getMemberById(article.getMid());
			System.out.println("작성자 : " + regMember.getNickname());
			System.out.println("조회수 : " + article.getHit());
			System.out.println("좋아요 : " + article.getLikeCnt());
			System.out.println("=======================");
		}

		for (int i = pagination.getStartPageNoInBlock(); i <= pagination.getEndPageNoInBlock(); i++) {
			if (i == pagination.getCurrentPageNo()) {
				System.out.print("[" + i + "] ");
			} else {
				System.out.print(i + " ");
			}
		}

		System.out.println("");
	}

	private static void printArticle(Article targetArticle) {
		// 찾고자 하는 target게시물만 출력.
		System.out.println("====" + targetArticle.getId() + "====");
		System.out.println("번호 : " + targetArticle.getId());
		System.out.println("제목 : " + targetArticle.getTitle());
		System.out.println("내용 : " + targetArticle.getBody());
		Member regMember = memberDao.getMemberById(targetArticle.getMid());
		System.out.println("작성자 : " + regMember.getNickname());
		System.out.println("등록날짜 : " + targetArticle.getRegDate());
		System.out.println("조회수 : " + targetArticle.getHit());
		System.out.println("좋아요 : " + targetArticle.getLikeCnt());
		System.out.println("====================================");
		System.out.println("=======댓글=======");

		ArrayList<Reply> replies = replyDao.getRepliesByParentId(targetArticle.getId());
		printReplies(replies);
	}

	private static void printReplies(ArrayList<Reply> replyList) {
		for (int i = 0; i < replyList.size(); i++) {
			Reply reply = replyList.get(i);

			System.out.println("내용 : " + reply.getBody());
			System.out.println("작성자 : " + reply.getBody());
			System.out.println("등록날짜 : " + reply.getBody());
			System.out.println("========================");

		}

	}

}

class MyComparator implements Comparator<Article> {

	String sortOrder = "asc";
	String sortType = "hit";

	@Override
	public int compare(Article o1, Article o2) {
		int c1 = 0;
		int c2 = 0;

		if (sortType.equals("hit")) {

			c1 = o1.getHit();
			c2 = o2.getHit();

		} else if (sortType.equals("like")) {

			c1 = o1.getLikeCnt();
			c2 = o2.getLikeCnt();
		}

		if (sortOrder.equals("asc")) {
			// asc일 때(오름차순)
			if (c1 > c2) {
				return 1;
			}
			return 1;
		} else {
			// desc일 떄(내림차순)
			if (c1 < c2) {
				return 1;
			}
			return -1;
		}
	}

}