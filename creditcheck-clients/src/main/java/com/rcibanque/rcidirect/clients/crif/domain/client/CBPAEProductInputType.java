//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.06.06 at 05:24:21 PM BST 
//


package com.rcibanque.rcidirect.clients.crif.domain.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CB_PAE_ProductInputType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CB_PAE_ProductInputType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ExtractionParameters" type="{urn:crif-creditbureau:v1}ExtractionParametersType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CB_PAE_ProductInputType", propOrder = {
    "extractionParameters"
})
public class CBPAEProductInputType {

    @XmlElement(name = "ExtractionParameters", required = true)
    protected ExtractionParametersType extractionParameters;

    /**
     * Gets the value of the extractionParameters property.
     * 
     * @return
     *     possible object is
     *     {@link ExtractionParametersType }
     *     
     */
    public ExtractionParametersType getExtractionParameters() {
        return extractionParameters;
    }

    /**
     * Sets the value of the extractionParameters property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExtractionParametersType }
     *     
     */
    public void setExtractionParameters(ExtractionParametersType value) {
        this.extractionParameters = value;
    }

}
