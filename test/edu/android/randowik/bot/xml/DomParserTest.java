package edu.android.randowik.bot.xml;

import java.util.List;

import org.junit.Test;

import edu.android.randowik.bot.Page;

import junit.framework.TestCase;
import static java.lang.System.out;

public class DomParserTest extends TestCase {
	@Test
	public void testLoadRandomPages() throws Exception {
		DomParser parser = new DomParser();
		String xmlString = "<?xml version=\"1.0\"?><api><query><random><page id=\"3151860\" ns=\"0\" title=\"Лейк-Стей (тауншип, Миннесота)\" /><page id=\"2174443\" ns=\"0\" title=\"Искорость\" /><page id=\"1706232\" ns=\"0\" title=\"Либурн (округ)\" /><page id=\"2257637\" ns=\"0\" title=\"Слепая птица (фильм)\" /><page id=\"2016581\" ns=\"0\" title=\"Каменка (остров)\" /></random></query></api>";
		List<Page> pages = parser.loadRandomPages(xmlString);
		assertEquals(5, pages.size());
		out.println(pages);
	}

	@Test
	public void testLoadPageContent() throws Exception {
		DomParser parser = new DomParser();
		String xmlString = "<?xml version=\"1.0\"?><api>" +
				"<query><pages><page pageid=\"3780488\" ns=\"0\" title=\"Семейный фильм\">" +
						"<revisions><rev xml:space=\"preserve\">Test" +
								"</rev></revisions></page></pages></query>" +
								"</api>";
		Page page = new Page();
		page.setId("3780488");
		page.setTitle("Семейный фильм");
		parser.fillPageContent(page, xmlString);
		assertNotNull(page.getContent());
		assertEquals("Test", page.getContent());
		out.println(page);
	}
}
