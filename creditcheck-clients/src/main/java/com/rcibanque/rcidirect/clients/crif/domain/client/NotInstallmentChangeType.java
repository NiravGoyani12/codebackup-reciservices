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
 * <p>Java class for NotInstallmentChangeType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="NotInstallmentChangeType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Old" type="{urn:crif-creditbureau:v1}NotInstallmentDBType"/&gt;
 *         &lt;element name="New" type="{urn:crif-creditbureau:v1}NotInstallmentDBType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NotInstallmentChangeType", propOrder = {
    "old",
    "_new"
})
public class NotInstallmentChangeType {

    @XmlElement(name = "Old", required = true)
    protected NotInstallmentDBType old;
    @XmlElement(name = "New", required = true)
    protected NotInstallmentDBType _new;

    /**
     * Gets the value of the old property.
     * 
     * @return
     *     possible object is
     *     {@link NotInstallmentDBType }
     *     
     */
    public NotInstallmentDBType getOld() {
        return old;
    }

    /**
     * Sets the value of the old property.
     * 
     * @param value
     *     allowed object is
     *     {@link NotInstallmentDBType }
     *     
     */
    public void setOld(NotInstallmentDBType value) {
        this.old = value;
    }

    /**
     * Gets the value of the new property.
     * 
     * @return
     *     possible object is
     *     {@link NotInstallmentDBType }
     *     
     */
    public NotInstallmentDBType getNew() {
        return _new;
    }

    /**
     * Sets the value of the new property.
     * 
     * @param value
     *     allowed object is
     *     {@link NotInstallmentDBType }
     *     
     */
    public void setNew(NotInstallmentDBType value) {
        this._new = value;
    }

}