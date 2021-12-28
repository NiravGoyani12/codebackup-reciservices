package com.rcibanque.rcidirect.services.core.utils;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

public class XPathUtilsTest {


	@Test
	public void testXPath() {

		Document document = null;
		try {
			document = XmlUtils.toDocument(IOUtils.toString(getClass().getResourceAsStream("/test.xml"), StandardCharsets.UTF_8));
		}
		catch (IOException e) {
			// Error caught bellow
		}

		assertThat(document).isNotNull();

		for(int i=0; i<1; i++) {

			assertThat("root").isEqualTo(XPathUtils.getNodeName(document, "/root"));
			assertThat("parent").isEqualTo(XPathUtils.getNodeName(document, "/root/parent"));
			assertThat("child").isEqualTo(XPathUtils.getNodeName(document, "/root/parent/child"));

			assertThat(XPathUtils.getNodeName(document, "/root-ko")).isNull();
			assertThat(XPathUtils.getNodeName(document, "/root/parent-ko")).isNull();
			assertThat(XPathUtils.getNodeName(document, "/root/parent/child-ko")).isNull();


			List<String> childTextNodes = XPathUtils.getTextNodeValues(document, "/root/parent/child");
			assertThat(childTextNodes).isNotEmpty();
			assertThat(childTextNodes).hasSize(3);
			assertThat(childTextNodes).contains("Child 1");
			assertThat(childTextNodes).contains("Child 2");
			assertThat(childTextNodes).contains("Child 3");

			childTextNodes = XPathUtils.getTextNodeValues(document, "/root/parent/child-ko");
			assertThat(childTextNodes).isEmpty();;


			String firstChildTextNode = XPathUtils.getTextNodeValue(document, "/root/parent/child");
			assertThat(firstChildTextNode).isNotNull();
			assertThat("Child 1").isEqualTo(firstChildTextNode);

			firstChildTextNode = XPathUtils.getTextNodeValue(document, "/root/parent/child-ko");
			assertThat(firstChildTextNode).isNull();


			String secondChildTextNode = XPathUtils.getTextNodeValue(document, "/root/parent/child[2]");
			assertThat(secondChildTextNode).isNotNull();
			assertThat("Child 2").isEqualTo(secondChildTextNode);
		}
	}

}
