package com.rcibanque.rcidirect.clients.crif.utils;

import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CrifUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(CrifUtils.class);

	public static final List<String> ERROR_TAGS = Stream.of("<Errors ", "&lt;Errors ", "Error Code=").collect(Collectors.toList());

	/**
	 * Converts a Java Bean to XML and passed as a String
	 *
	 * @param pType
	 *            Object to convert
	 * @return response String XML
	 */
	public static <T> String jaxbObjectToXML(T pType) {

		String response = null;
		try {
			// Create JAXB Context
			JAXBContext jaxbContext = JAXBContext.newInstance(pType.getClass());

			// Create Marshaller
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			// Required formatting??
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			// Print XML String to Console
			StringWriter sw = new StringWriter();

			// Write XML to StringWriter
			jaxbMarshaller.marshal(pType, sw);

			// Verify XML Content
			response = sw.toString();

		} catch (JAXBException e) {
			LOGGER.warn(e.getMessage());
		}
		return response;
	}

	public static XMLGregorianCalendar getDate(Date pDate) {

		XMLGregorianCalendar date = null;
		try {
			if (null != pDate) {
				date = DatatypeFactory.newInstance()
						.newXMLGregorianCalendar(new SimpleDateFormat("yyyy-MM-dd").format(pDate));
			}
		} catch (DatatypeConfigurationException e) {
			LOGGER.error(e.getMessage());
		}

		return date;
	}

	public static boolean responseHasErrors(String pResponse) {

		return ERROR_TAGS.parallelStream().anyMatch(pResponse::contains);
	}

}
