package com.rcibanque.rcidirect.services.core.utils;

import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.rcibanque.rcidirect.services.core.exception.ExceptionUtils;

public class XPathUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(XPathUtils.class);


	private XPathUtils() {}


	private static XPathExpression newXPathExpression(String pXPathExpression) {

		XPathExpression res = null;

		if(StringUtils.isNotBlank(pXPathExpression)) {

			// XPath does not appear to guaranty thread safety on factory or expression

			try {
				XPathFactory xPathfactory = XPathFactory.newInstance();
				XPath xpath = xPathfactory.newXPath();
				res = xpath.compile(pXPathExpression);
			}
			catch (XPathExpressionException e) {
				LOGGER.error("XPath expression instantiation error: {}", ExceptionUtils.getStackTrace(e));
			}
		}

		return res;
	}


	private static NodeList getNodeList(Document pDocument, String pXPathExpression) {

		NodeList res = null;

		XPathExpression xPath = newXPathExpression(pXPathExpression);

		if(pDocument != null && xPath != null) {

			try {
				res = (NodeList) xPath.evaluate(pDocument, XPathConstants.NODESET);
			}
			catch (XPathExpressionException e) {
				LOGGER.error("XPath expression evaluation error: {}", ExceptionUtils.getStackTrace(e));
			}
		}

		return res;
	}


	/**
	 * @param pDocument XML document
	 * @param pXPathExpression XPath expression
	 * @return The node name of the specified element if such an element exist, or null
	 */
	public static String getNodeName(Document pDocument, String pXPathExpression) {

		String res = null;

		NodeList nodeList = getNodeList(pDocument, pXPathExpression);

		if (nodeList != null) {

			Node node = nodeList.item(0);

			if(node != null && node.getNodeType() == Node.ELEMENT_NODE) {

				res = node.getNodeName();
			}
		}

		return res;
	}


	/**
	 * @param pDocument XML document
	 * @param pXPathExpression XPath expression
	 * @return The text value of the specified element if such an element exist, or null.
	 * If no position is mentioned in the XPath expression and multiple elements are found, the first one is processed.
	 */
	public static String getTextNodeValue(Document pDocument, String pXPathExpression) {

		List<String> textNodes = getTextNodeValues(pDocument, pXPathExpression, true);

		return CollectionUtils.isNotEmpty(textNodes) ? textNodes.get(0) : null;
	}

	/**
	 * @param pDocument XML document
	 * @param pXPathExpression XPath expression
	 * @return The text values of the specified elements if such elements exist, or null
	 */
	public static List<String> getTextNodeValues(Document pDocument, String pXPathExpression) {

		return getTextNodeValues(pDocument, pXPathExpression, false);
	}

	private static List<String> getTextNodeValues(Document pDocument, String pXPathExpression, boolean pFirst) {

		List<String> res = new ArrayList<>();

		NodeList nodeList = getNodeList(pDocument, pXPathExpression);

		if (nodeList != null) {

			for (int i=0; i<nodeList.getLength() && (i==0 || ! pFirst); i++) {

				Node node = nodeList.item(i).getFirstChild();

				if (node != null
						&& (node.getNodeType() == Node.TEXT_NODE || node.getNodeType() == Node.CDATA_SECTION_NODE)) {

					res.add(node.getNodeValue());
				}
			}
		}

		return res;
	}


}
