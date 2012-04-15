package edu.android.randowik.bot;

import java.net.URL;
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
		String address = AppContext
				.getApiEntryPoint() + "?" + randomPagesQueryString();
		log("fetchRandomPages address: "+address);
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
		String address = AppContext
				.getApiEntryPoint()
				+ "?"
				+ "format=xml&action=query&prop=revisions&pageids="
				+ page.getId() + "&rvprop=content&rvsection=0&rvparse";
		log("fillPageHtmlContent address: "+address);
		String xmlString = client.loadPage(new URL(address));
		parser.fillPageContent(page, xmlString);
	}
	
	private static void log(String s) {
		System.out.println(s);
	}
}
