package edu.android.randowik.bot;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.List;

import edu.android.randowik.AppContext;
import edu.android.randowik.bot.http.HttpClient;
import edu.android.randowik.bot.xml.DomParser;

public class Bot {
	public static final String TAG = "Bot";

	public String randomPagesQueryString() {
		return "action=query&list=random&rnlimit=5&format=xml&rnnamespace=0";
	}

	public List<Page> fetchRandomPages() throws Exception {
		List<Page> pages = null;
		HttpClient client = new HttpClient();
		DomParser parser = new DomParser();
		String address = AppContext.getApiEntryPoint() + "?"
				+ randomPagesQueryString();
		log("fetchRandomPages address: " + address);
		String xmlString = client.loadPage(new URL(address));
		pages = parser.loadRandomPages(xmlString);
		return pages;
	}

	public void fillPageContent(Page page) throws Exception {
		if (page == null)
			throw new IllegalArgumentException("Page cannot be null");
		if (page.getId() == null)
			throw new IllegalArgumentException("Page Id cannot be null");
		HttpClient client = new HttpClient();
		DomParser parser = new DomParser();
		String xmlString = client.loadPage(new URL(AppContext
				.getApiEntryPoint()
				+ "?"
				+ "action=query&prop=revisions&pageids="
				+ page.getId()
				+ "&rvprop=content&format=xml"));
		parser.fillPageContent(page, xmlString);
	}

	public void fillPageHtmlContent(Page page) throws Exception {
		if (page == null)
			throw new IllegalArgumentException("Page cannot be null");
		if (page.getId() == null)
			throw new IllegalArgumentException("Page Id cannot be null");
		HttpClient client = new HttpClient();
		DomParser parser = new DomParser();
		String address = AppContext.getApiEntryPoint() + "?"
				+ "format=xml&action=query&prop=revisions&pageids="
				+ page.getId() + "&rvprop=content&rvsection=0&rvparse";
		log("fillPageHtmlContent address: " + address);
		String xmlString = client.loadPage(new URL(address));
		parser.fillPageContent(page, xmlString);
	}

	public Page fetchIndexRandomPage() throws Exception {
		Page page = new Page();
		HttpClient client = new HttpClient();
		String address = AppContext.getRandomServicePageEntryPoint();
		log("fetchIndexRandomPage address: " + address);
		URL randomServiceUrl = new URL(address);
		HttpURLConnection urlConn = (HttpURLConnection) randomServiceUrl
				.openConnection();
		urlConn.setInstanceFollowRedirects(false);
		String redirectedAddress = urlConn.getHeaderField("Location");
		String html = client.loadPage(new URL(redirectedAddress));
		log("redirected: " + redirectedAddress);
		log("fetchIndexRandomPage html: " + html);
		String decodedRedirectAddress = URLDecoder.decode(redirectedAddress, "UTF-8");
		int indexOfLastSlash = decodedRedirectAddress.lastIndexOf('/') + 1;
		String title = decodedRedirectAddress.substring(indexOfLastSlash);
		page.setTitle(title);
		page.setContent(html);
		return page;
	}

	private static void log(String s) {
		System.out.println(s);
	}
}
