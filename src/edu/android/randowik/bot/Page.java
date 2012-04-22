package edu.android.randowik.bot;

public class Page {
	private String id;
	private String title;
	private String pageUrl;
	private String content;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPageUrl() {
		return pageUrl;
	}

	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}

	@Override
	public String toString() {
		return "Page [id=" + id + ", title=" + title + ", pageUrl=" + pageUrl + ", content=" + content + "]";
	}

}
