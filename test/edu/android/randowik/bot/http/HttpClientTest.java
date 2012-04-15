package edu.android.randowik.bot.http;

import static java.lang.System.out;

import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.Test;

import edu.android.randowik.AppContext;
import edu.android.randowik.bot.Bot;
import edu.android.randowik.bot.http.HttpClient;

import junit.framework.TestCase;

public class HttpClientTest extends TestCase {

	@Test
	public void testLoadRandomPageList() throws Exception {
		Bot bot = new Bot();
		HttpClient client = new HttpClient();
		String address = AppContext.getApiEntryPoint() + "?"
				+ bot.randomPagesQueryString();
		URL url = new URL(address);
		String page = client.loadPage(url);
		assertNotNull(String.format("Cannot load page [%1s]", address), page);
		out.println(String.format("Loaded address [%1s]", address));
		out.println(page);
	}
	
	@Test
	public void testLoadPage() throws Exception {
		HttpClient client = new HttpClient();
		String address = AppContext.getApiEntryPoint() + "?"+"action=query&prop=revisions&pageids=3780488&rvprop=content&format=xml";
		URL url = new URL(address);
		String page = client.loadPage(url);
		assertNotNull(String.format("Cannot load page [%1s]", address), page);
		out.println(String.format("Loaded address [%1s]", address));
		out.println(page);
	}
	
	@Test
	public void testRedirect() throws Exception {
		URL url = new URL(AppContext.getRandomServicePageEntryPoint());
		HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
		urlConn.setInstanceFollowRedirects(false);
        System.out.println("URL <" + url + "> redirects to: <" + urlConn.getHeaderField("Location") + ">, Response Code: " + +urlConn.getResponseCode());
	}

}
