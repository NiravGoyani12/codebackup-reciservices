//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.06.06 at 05:24:21 PM BST 
//


package com.rcibanque.rcidirect.clients.crif.domain.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for LinkDescType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LinkDescType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;attribute name="RoleOfCISDesc" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="ConsumerFlagDesc" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LinkDescType")
public class LinkDescType {

    @XmlAttribute(name = "RoleOfCISDesc")
    protected String roleOfCISDesc;
    @XmlAttribute(name = "ConsumerFlagDesc")
    protected String consumerFlagDesc;

    /**
     * Gets the value of the roleOfCISDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRoleOfCISDesc() {
        return roleOfCISDesc;
    }

    /**
     * Sets the value of the roleOfCISDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRoleOfCISDesc(String value) {
        this.roleOfCISDesc = value;
    }

    /**
     * Gets the value of the consumerFlagDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConsumerFlagDesc() {
        return consumerFlagDesc;
    }

    /**
     * Sets the value of the consumerFlagDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConsumerFlagDesc(String value) {
        this.consumerFlagDesc = value;
    }

}