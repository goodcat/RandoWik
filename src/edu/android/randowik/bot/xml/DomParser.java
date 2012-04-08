package edu.android.randowik.bot.xml;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import edu.android.randowik.bot.Page;

public class DomParser {

	public List<Page> loadRandomPages(String xmlString) throws Exception {
		List<Page> pages = new ArrayList<Page>();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = null;
		Document doc = null;
		InputSource inputSource = null;
		try {
			db = dbf.newDocumentBuilder();
			inputSource = new InputSource(new StringReader(xmlString));
			doc = db.parse(inputSource);
			doc.getDocumentElement().normalize();
			NodeList nodeList = doc.getElementsByTagName("random");
			Node node = nodeList.item(0);
			Page page;
			for (int i = 0; i < node.getChildNodes().getLength(); i++) {
				Node temp = node.getChildNodes().item(i);
				page = new Page();
				if (temp.getNodeName().equalsIgnoreCase("page")) {
					NamedNodeMap attrs = temp.getAttributes();
					Node idNode = attrs.getNamedItem("id");
					Node titleNode = attrs.getNamedItem("title");
					page.setId(idNode.getNodeValue());
					page.setTitle(titleNode.getNodeValue());
					pages.add(page);
				}
			}
		} finally {

		}
		return pages;
	}

	public void fillPageContent(Page page, String xmlString) throws Exception {
		if (page == null)
			throw new IllegalArgumentException("Page cannot be null");
		if (page.getId() == null)
			throw new IllegalArgumentException("Page ID cannot be null");
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = null;
		Document doc = null;
		InputSource inputSource = null;
		try {
			db = dbf.newDocumentBuilder();
			inputSource = new InputSource(new StringReader(xmlString));
			doc = db.parse(inputSource);
			doc.getDocumentElement().normalize();
			NodeList nodeList = doc.getElementsByTagName("api");
			Node apiNode = nodeList.item(0);
			Node queryNode = apiNode.getFirstChild();
			Node pagesNode = queryNode.getFirstChild();
			Node pageNode = pagesNode.getFirstChild();
			Node revisionsNode = pageNode.getFirstChild();
			if (revisionsNode.getChildNodes() != null) {
				if (revisionsNode.getChildNodes().getLength() > 0) {
					Node temp = revisionsNode.getChildNodes().item(0);
					if (temp.getNodeName().equalsIgnoreCase("rev")) {
						StringBuilder buffer = new StringBuilder();
						NodeList childList = temp.getChildNodes();
						for (int i = 0; i < childList.getLength(); i++) {
							Node child = childList.item(i);
							if (child.getNodeType() != Node.TEXT_NODE)
								continue; // skip non-text nodes
							buffer.append(child.getNodeValue());
						}
						page.setContent(buffer.toString());
					}
				}
			}
		} finally {

		}
	}
}
