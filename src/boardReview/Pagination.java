package boardReview;

public class Pagination {

	private int currentPageNo = 1;
	private int totalCntOfArticles; // 전체 게시물 개수
	private int startPageNo = 1; // 시작 페이지 번호
	private int articlesCntPerPage = 3; // 페이지 당 출력 게시물 개수
	private int pageCntPerBlock = 5; // 한 페이지 블록 당 페이지 개수
	private int currentPageBlock = 1;

	public int getStartPageNoInBlock() {
		return (getCurrentPageBlock() - 1) * pageCntPerBlock + 1;
	}

	public int getEndPageNoInBlock() {
		return getStartPageNoInBlock() + pageCntPerBlock - 1;
	}

	public int getStartIndex() {
		return (currentPageNo - 1) * articlesCntPerPage;
	}

	public int getEndIndex() {
		return getStartIndex() + articlesCntPerPage;
	}

	public int getEndPageNo() {
		return (int) Math.ceil((double) totalCntOfArticles / articlesCntPerPage);
	}

	public int getCurrentPageBlock() {
		return this.currentPageBlock;
	}

	public void setCurrentPageBlock(int currentPageBlock) {
		this.currentPageBlock = currentPageBlock;
	}

	public int getCurrentPageNo() {
		return currentPageNo;
	}

	public void setCurrentPageNo(int currentPageNo) {
		this.currentPageNo = currentPageNo;
		int currentPageBlock = (int) Math.ceil((double) currentPageNo / pageCntPerBlock);
		setCurrentPageBlock(currentPageBlock);
	}

	public int getTotalCntOfArticles() {
		return totalCntOfArticles;
	}

	public void setTotalCntOfArticles(int totalCntOfArticles) {
		this.totalCntOfArticles = totalCntOfArticles;
	}

	public int getStartPageNo() {
		return startPageNo;
	}

	public void setStartPageNo(int startPageNo) {
		this.startPageNo = startPageNo;
	}

	public int getArticlesCntPerPage() {
		return articlesCntPerPage;
	}

	public void setArticlesCntPerPage(int articlesCntPerPage) {
		this.articlesCntPerPage = articlesCntPerPage;
	}

	public int getPageCntPerBlock() {
		return pageCntPerBlock;
	}

	public void setPageCntPerBlock(int pageCntPerBlock) {
		this.pageCntPerBlock = pageCntPerBlock;
	}
}
