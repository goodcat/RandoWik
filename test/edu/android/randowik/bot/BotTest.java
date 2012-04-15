package edu.android.randowik.bot;

import org.junit.Test;

import junit.framework.TestCase;

public class BotTest extends TestCase {
	@Test
	public void testFetchIndexRandomPage() throws Exception {
		Bot bot = new Bot();
		Page page = bot.fetchIndexRandomPage();
		System.out.println(page);
	}
}
