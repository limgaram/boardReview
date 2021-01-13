package boardReview;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ArticleDao {
	private static ArrayList<Article> articles;
	private int no = 4;

	public ArticleDao() {

		articles = new ArrayList<>();
		Article a1 = new Article(1, "aa", "aa1", "익명", getCurrentDate(), 0);
		Article a2 = new Article(2, "bb", "bb1", "익명", getCurrentDate(), 0);
		Article a3 = new Article(3, "cc", "cc1", "익명", getCurrentDate(), 0);

		articles.add(a1);
		articles.add(a2);
		articles.add(a3);
	}

	public void insertArticle(Article a) {
		a.setId(no);
		no++;
		a.setRegDate(getCurrentDate());

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

	private static String getCurrentDate() {
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy.MM.dd");
		Date time = new Date();
		String time1 = format1.format(time);

		return time1;
	}

}
