package com.rcibanque.rcidirect.services.core.utils;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

public class XmlUtilsTest {


	@Test
	public void testLoadFile() {

		Document document = null;
		try {
			document = XmlUtils.toDocument(IOUtils.toString(getClass().getResourceAsStream("/test.xml"), StandardCharsets.UTF_8));
		}
		catch (IOException e) {
			// Error caught bellow
		}

		assertThat(document).isNotNull();

		assertThat("root").isEqualTo(document.getDocumentElement().getNodeName());
	}


	@Test
	public void testEscape() {

		assertThat("&quot;bread&quot; &amp; &quot;butter&quot;").isEqualTo(XmlUtils.escape("\"bread\" & \"butter\""));
		assertThat("").isEqualTo(XmlUtils.escape(null));
	}

}
