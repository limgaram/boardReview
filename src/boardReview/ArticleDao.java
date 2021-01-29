package boardReview;

import java.util.ArrayList;
import java.util.Date;

public class ArticleDao {
	private static ArrayList<Article> articles;
	private int no = 4;

	public ArticleDao() {

		articles = new ArrayList<>();
		Article a1 = new Article(1, "aa", "aa1", 1, Util.getCurrentDate(), 100, 30);
		Article a2 = new Article(2, "bb", "bb1", 2, Util.getCurrentDate(), 30, 20);
		Article a3 = new Article(3, "cc", "cc1", 3, Util.getCurrentDate(), 50, 40);

		articles.add(a1);
		articles.add(a2);
		articles.add(a3);

		for (int i = 1; i <= 50; i++) {
			Article a4 = new Article();
			a4.setId(i);
			a4.setTitle("제목" + i);
			a4.setBody("내용" + i);
			a4.setMid(1);
			articles.add(a4);
		}
	}

	public void insertArticle(Article a) {
		a.setId(no);
		no++;
		a.setRegDate(Util.getCurrentDate());

		articles.add(a);
	}

	public void removeArticle(Article a) {
		articles.remove(a);
	}

	public static Article getArticleById(int targetId) {
		for (int i = 0; i < articles.size(); i++) {
			int id = articles.get(i).getId();

			if (id == targetId) {
				return articles.get(i);
			}
		}
		return null;
	}

	public ArrayList<Article> getSearchedArticlesByFlag(int flag, String keyword) {
		ArrayList<Article> searchedArticles = new ArrayList<>();

		for (int i = 0; i < articles.size(); i++) {
			Article article = articles.get(i);
			String str = article.getPropertiesFlag(flag);

			if (str.contains(keyword)) {
				searchedArticles.add(article);
			}
		}
		return searchedArticles;
	}

	public ArrayList<Article> getArticles() {
		return articles;
	}

}
