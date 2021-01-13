package boardReview;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class reviewApp {
	Scanner sc = new Scanner(System.in);
	ArticleDao articleDao = new ArticleDao();
	ReplyDao replyDao = new ReplyDao();

	public void start() {

		while (true) {

			System.out.print("명령어를 입력해주세요. : ");
			String cmd = sc.nextLine();

			if (cmd.equals("list")) {

				articleList();

			} else if (cmd.equals("add")) {

				addArticle();

			} else if (cmd.equals("update")) {

				updateArticle();

			} else if (cmd.equals("delete")) {

				deleteArticle();

			} else if (cmd.equals("read")) {

				readArticle();

			} else if (cmd.equals("search")) {

				searchArticle();

			} else if (cmd.equals("exit")) {
				System.out.println("종료");
				break;

			}
		}
	}

	public void searchArticle() {

		System.out.println("검색 항목을 선택해주세요.");
		System.out.println("(1. 제목, 2. 내용, 3. 제목 + 내용, 4. 작성자) : ");
		int flag = sc.nextInt();
		System.out.println("검색 키워드를 입력해주세요: ");
		String keyword = sc.nextLine();

		ArrayList<Article> searchedArticles = articleDao.getSearchedArticlesByFlag(flag, keyword);

		printArticles(searchedArticles);

	}

	public void readArticle() {
		ArrayList<Article> articles = articleDao.getArticles();

		System.out.println("상세보기할 게시물 선택 : ");
		int targetId = sc.nextInt();
		Article targetArticle = articleDao.getArticleById(targetId);

		if (targetArticle == null) {
			System.out.println("없는 게시물입니다. ");
		} else {
			targetArticle.setHit(targetArticle.getHit() + 1);
			System.out.println("====" + targetArticle.getId() + "====");
			System.out.println("번호 : " + targetArticle.getId());
			System.out.println("제목 : " + targetArticle.getTitle());
			System.out.println("내용 : " + targetArticle.getBody());
			System.out.println("작성자 : " + targetArticle.getNickname());
			System.out.println("등록날짜 : " + targetArticle.getRegDate());
			System.out.println("조회수 : " + targetArticle.getHit());
			System.out.println("===================");
			System.out.println("====댓글====");
			ArrayList<Reply> replies = replyDao.getRepliesByParentId(targetArticle.getId());
			printReplies(replies);

			while (true) {

				System.out.println("상세보기 기능을 선택해주세요.");
				System.out.println(" (1. 댓글 등록, 2. 좋아요, 3. 수정, 4. 삭제, 5. 목록으로) : ");

				int readCmd = sc.nextInt();

				if (readCmd == 1) {

					Reply r = new Reply();
					System.out.println("댓글 내용을 입력해주세요 : ");
					r.setBody(sc.nextLine());
					r.setParentId(targetArticle.getId());
					r.setNickname("익명");

					replyDao.insertReply(r);

					System.out.println("댓글이 등록되었습니다. ");
					System.out.println("=====" + targetArticle.getId() + "====");
					System.out.println("번호 : " + targetArticle.getId());
					System.out.println("제목 : " + targetArticle.getTitle());
					System.out.println("내용 : " + targetArticle.getBody());
					System.out.println("===============");
					System.out.println("====댓글====");
					printReplies(replies);

				} else if (readCmd == 2) {
					System.out.println("좋아요 기능");
				} else if (readCmd == 3) {
					System.out.println("수정 기능");
				} else if (readCmd == 4) {
					System.out.println("삭제 기능");
				} else if (readCmd == 5) {
					break;
				}

			}
		}

	}

	public void deleteArticle() {
		ArrayList<Article> articles = articleDao.getArticles();

		System.out.println("삭제할 게시물 선택 : ");
		int targetId = sc.nextInt();

		Article targetArticle = articleDao.getArticleById(targetId);

		if (targetArticle == null) {

			System.out.println("없는 게시물입니다. ");
		} else {

			articleDao.removeArticle(targetArticle);
			System.out.println(targetId + "번 게시물이 삭제되었습니다.");
		}

	}

	public void updateArticle() {
		ArrayList<Article> articles = articleDao.getArticles();

		System.out.println("수정할 게시물 선택 : ");
		int targetId = sc.nextInt();

		Article targetArticle = articleDao.getArticleById(targetId);

		if (targetArticle == null) {

			System.out.println("없는 게시물입니다. ");
		} else {

			System.out.println("게시물 제목을 입력해주세요. : ");
			String newTitle = sc.nextLine();
			System.out.println("게시물 내용을 입력해주세요. : ");
			String newBody = sc.nextLine();

			targetArticle.setTitle(newTitle);
			targetArticle.setBody(newBody);

		}

	}

	public void addArticle() {
		Article a = new Article();

		System.out.print("게시물 제목을 입력해주세요. : ");
		a.setTitle(sc.nextLine());

		System.out.print("게시물 내용을 입력해주세요. : ");
		a.setBody(sc.nextLine());

		articleDao.insertArticle(a);
		System.out.println("게시물이 등록되었습니다.");

	}

	public void articleList() {
		ArrayList<Article> articles = articleDao.getArticles();
		printArticles(articles);

	}

	private static void printArticles(ArrayList<Article> articleList) {

		for (int i = 0; i < articleList.size(); i++) {
			Article article = articleList.get(i);

			System.out.println("번호 : " + article.getId());
			System.out.println("제목 : " + article.getTitle());
			System.out.println("=======================");
		}
	}

	private void printReplies(ArrayList<Reply> replyList) {
		for (int i = 0; i < replyList.size(); i++) {
			Reply reply = replyList.get(i);

			System.out.println("내용 : " + reply.getBody());
			System.out.println("작성자 : " + reply.getBody());
			System.out.println("등록날짜 : " + reply.getBody());
			System.out.println("========================");

		}

	}

}
