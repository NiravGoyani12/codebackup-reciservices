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
 * <p>Java class for CBScoreType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CBScoreType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence minOccurs="0"&gt;
 *         &lt;element name="CBSGlocal" type="{urn:crif-creditbureau:v1}CBSGlocalType" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CBScoreType", propOrder = {
    "cbsGlocal"
})
public class CBScoreType {

    @XmlElement(name = "CBSGlocal")
    protected CBSGlocalType cbsGlocal;

    /**
     * Gets the value of the cbsGlocal property.
     * 
     * @return
     *     possible object is
     *     {@link CBSGlocalType }
     *     
     */
    public CBSGlocalType getCBSGlocal() {
        return cbsGlocal;
    }

    /**
     * Sets the value of the cbsGlocal property.
     * 
     * @param value
     *     allowed object is
     *     {@link CBSGlocalType }
     *     
     */
    public void setCBSGlocal(CBSGlocalType value) {
        this.cbsGlocal = value;
    }

}
