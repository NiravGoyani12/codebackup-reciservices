package com.rcibanque.rcidirect.services.core.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.rcibanque.rcidirect.services.core.exception.ExceptionUtils;

public class XmlUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(XmlUtils.class);


	private XmlUtils() {}


	/**
	 * Creates an org.w3c.dom.Document object from the passed in XML String
	 * @param pXML
	 * @return A instance of Document created from the passed in XML or null if the conversion cannot be performed
	 */
	public static Document toDocument(String pXML) {

		Document res = null;

		if(StringUtils.isNotBlank(pXML)) {

			try {
				res = toDocumentWithBAIS(pXML);
			}
			catch (ParserConfigurationException | SAXException | IOException e1) {

				// Sometimes this second method works whereas the first one does not: seems to be a BOM issue
				try {
					res = toDocumentWithSRIS(pXML);
				}
				catch (ParserConfigurationException | SAXException | IOException e2) {

					LOGGER.error("XML parsing error: {}", ExceptionUtils.getStackTrace(e1)); // Use first exception?
				}
			}
		}

		return res;
	}

	private static Document toDocumentWithBAIS(String pXML) throws IOException, ParserConfigurationException, SAXException {

		try (InputStream xmlStream = new ByteArrayInputStream(pXML.getBytes())) { // "try with resources" => closed
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); // Not sure the class is thread-safe
			DocumentBuilder builder = factory.newDocumentBuilder();
			return builder.parse(xmlStream);
		}
	}

	private static Document toDocumentWithSRIS(String pXML) throws IOException, ParserConfigurationException, SAXException {

		try (Reader reader = new StringReader(pXML)) { // "try with resources" => closed
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); // Not sure the class is thread-safe
			DocumentBuilder builder = factory.newDocumentBuilder();
			return builder.parse(new InputSource(reader));
		}
	}


	/**
	 * Escapes an XML 1.0 string
	 *
	 * @param pString XML
	 * @return Escaped XML, or an empty string if input is null
	 *
	 * @see StringEscapeUtils#escapeXml10
	 */
	public static String escape(String pString) {

		return pString == null ? StringUtils.EMPTY : StringEscapeUtils.escapeXml10(pString);
	}

}
