//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2019.06.07 at 02:08:39 PM BST
//


package com.rcibanque.rcidirect.clients.crif.domain.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="OBO" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;attribute name="orgcode" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                 &lt;attribute name="branchcode" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="Auth"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;attribute name="orgcode" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                 &lt;attribute name="usr" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                 &lt;attribute name="pwd" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="Request"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Content" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="Params"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="Param"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="ServiceID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                       &lt;element name="ProcessID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                       &lt;element name="ForceRequest" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *                                       &lt;element name="NDG" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                     &lt;/sequence&gt;
 *                                   &lt;/restriction&gt;
 *                                 &lt;/complexContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="DosID" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="Sync" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
		"obo",
		"auth",
		"request"
})
@XmlRootElement(name = "CG_Req_Invoke", namespace="urn:crif-CrifGate:2:1")
public class CGReqInvoke {

	@XmlElement(name = "OBO")
	protected CGReqInvoke.OBO obo;
	@XmlElement(name = "Auth", required = true, namespace="urn:crif-CrifGate:2:1")
	protected CGReqInvoke.Auth auth;
	@XmlElement(name = "Request", required = true,  namespace="urn:crif-CrifGate:2:1")
	protected CGReqInvoke.Request request;
	@XmlAttribute(name = "DosID")
	protected String dosID;
	@XmlAttribute(name = "Sync")
	protected String sync;

	/**
	 * Gets the value of the obo property.
	 *
	 * @return
	 *     possible object is
	 *     {@link CGReqInvoke.OBO }
	 *
	 */
	public CGReqInvoke.OBO getOBO() {
		return obo;
	}

	/**
	 * Sets the value of the obo property.
	 *
	 * @param value
	 *     allowed object is
	 *     {@link CGReqInvoke.OBO }
	 *
	 */
	public void setOBO(CGReqInvoke.OBO value) {
		this.obo = value;
	}

	/**
	 * Gets the value of the auth property.
	 *
	 * @return
	 *     possible object is
	 *     {@link CGReqInvoke.Auth }
	 *
	 */
	public CGReqInvoke.Auth getAuth() {
		return auth;
	}

	/**
	 * Sets the value of the auth property.
	 *
	 * @param value
	 *     allowed object is
	 *     {@link CGReqInvoke.Auth }
	 *
	 */
	public void setAuth(CGReqInvoke.Auth value) {
		this.auth = value;
	}

	/**
	 * Gets the value of the request property.
	 *
	 * @return
	 *     possible object is
	 *     {@link CGReqInvoke.Request }
	 *
	 */
	public CGReqInvoke.Request getRequest() {
		return request;
	}

	/**
	 * Sets the value of the request property.
	 *
	 * @param value
	 *     allowed object is
	 *     {@link CGReqInvoke.Request }
	 *
	 */
	public void setRequest(CGReqInvoke.Request value) {
		this.request = value;
	}

	/**
	 * Gets the value of the dosID property.
	 *
	 * @return
	 *     possible object is
	 *     {@link String }
	 *
	 */
	public String getDosID() {
		return dosID;
	}

	/**
	 * Sets the value of the dosID property.
	 *
	 * @param value
	 *     allowed object is
	 *     {@link String }
	 *
	 */
	public void setDosID(String value) {
		this.dosID = value;
	}

	/**
	 * Gets the value of the sync property.
	 *
	 * @return
	 *     possible object is
	 *     {@link String }
	 *
	 */
	public String getSync() {
		return sync;
	}

	/**
	 * Sets the value of the sync property.
	 *
	 * @param value
	 *     allowed object is
	 *     {@link String }
	 *
	 */
	public void setSync(String value) {
		this.sync = value;
	}


	/**
	 * <p>Java class for anonymous complex type.
	 *
	 * <p>The following schema fragment specifies the expected content contained within this class.
	 *
	 * <pre>
	 * &lt;complexType&gt;
	 *   &lt;complexContent&gt;
	 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
	 *       &lt;attribute name="orgcode" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
	 *       &lt;attribute name="usr" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
	 *       &lt;attribute name="pwd" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
	 *     &lt;/restriction&gt;
	 *   &lt;/complexContent&gt;
	 * &lt;/complexType&gt;
	 * </pre>
	 *
	 *
	 */
	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "")
	public static class Auth {

		@XmlAttribute(name = "orgcode")
		protected String orgcode;
		@XmlAttribute(name = "usr", required = true)
		protected String usr;
		@XmlAttribute(name = "pwd", required = true)
		protected String pwd;

		/**
		 * Gets the value of the orgcode property.
		 *
		 * @return
		 *     possible object is
		 *     {@link String }
		 *
		 */
		public String getOrgcode() {
			return orgcode;
		}

		/**
		 * Sets the value of the orgcode property.
		 *
		 * @param value
		 *     allowed object is
		 *     {@link String }
		 *
		 */
		public void setOrgcode(String value) {
			this.orgcode = value;
		}

		/**
		 * Gets the value of the usr property.
		 *
		 * @return
		 *     possible object is
		 *     {@link String }
		 *
		 */
		public String getUsr() {
			return usr;
		}

		/**
		 * Sets the value of the usr property.
		 *
		 * @param value
		 *     allowed object is
		 *     {@link String }
		 *
		 */
		public void setUsr(String value) {
			this.usr = value;
		}

		/**
		 * Gets the value of the pwd property.
		 *
		 * @return
		 *     possible object is
		 *     {@link String }
		 *
		 */
		public String getPwd() {
			return pwd;
		}

		/**
		 * Sets the value of the pwd property.
		 *
		 * @param value
		 *     allowed object is
		 *     {@link String }
		 *
		 */
		public void setPwd(String value) {
			this.pwd = value;
		}

	}


	/**
	 * <p>Java class for anonymous complex type.
	 *
	 * <p>The following schema fragment specifies the expected content contained within this class.
	 *
	 * <pre>
	 * &lt;complexType&gt;
	 *   &lt;complexContent&gt;
	 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
	 *       &lt;attribute name="orgcode" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
	 *       &lt;attribute name="branchcode" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
	 *     &lt;/restriction&gt;
	 *   &lt;/complexContent&gt;
	 * &lt;/complexType&gt;
	 * </pre>
	 *
	 *
	 */
	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "")
	public static class OBO {

		@XmlAttribute(name = "orgcode", required = true)
		protected String orgcode;
		@XmlAttribute(name = "branchcode", required = true)
		protected String branchcode;

		/**
		 * Gets the value of the orgcode property.
		 *
		 * @return
		 *     possible object is
		 *     {@link String }
		 *
		 */
		public String getOrgcode() {
			return orgcode;
		}

		/**
		 * Sets the value of the orgcode property.
		 *
		 * @param value
		 *     allowed object is
		 *     {@link String }
		 *
		 */
		public void setOrgcode(String value) {
			this.orgcode = value;
		}

		/**
		 * Gets the value of the branchcode property.
		 *
		 * @return
		 *     possible object is
		 *     {@link String }
		 *
		 */
		public String getBranchcode() {
			return branchcode;
		}

		/**
		 * Sets the value of the branchcode property.
		 *
		 * @param value
		 *     allowed object is
		 *     {@link String }
		 *
		 */
		public void setBranchcode(String value) {
			this.branchcode = value;
		}

	}


	/**
	 * <p>Java class for anonymous complex type.
	 *
	 * <p>The following schema fragment specifies the expected content contained within this class.
	 *
	 * <pre>
	 * &lt;complexType&gt;
	 *   &lt;complexContent&gt;
	 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
	 *       &lt;sequence&gt;
	 *         &lt;element name="Content" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
	 *         &lt;element name="Params"&gt;
	 *           &lt;complexType&gt;
	 *             &lt;complexContent&gt;
	 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
	 *                 &lt;sequence&gt;
	 *                   &lt;element name="Param"&gt;
	 *                     &lt;complexType&gt;
	 *                       &lt;complexContent&gt;
	 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
	 *                           &lt;sequence&gt;
	 *                             &lt;element name="ServiceID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
	 *                             &lt;element name="ProcessID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
	 *                             &lt;element name="ForceRequest" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
	 *                             &lt;element name="NDG" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
	 *                           &lt;/sequence&gt;
	 *                         &lt;/restriction&gt;
	 *                       &lt;/complexContent&gt;
	 *                     &lt;/complexType&gt;
	 *                   &lt;/element&gt;
	 *                 &lt;/sequence&gt;
	 *               &lt;/restriction&gt;
	 *             &lt;/complexContent&gt;
	 *           &lt;/complexType&gt;
	 *         &lt;/element&gt;
	 *       &lt;/sequence&gt;
	 *     &lt;/restriction&gt;
	 *   &lt;/complexContent&gt;
	 * &lt;/complexType&gt;
	 * </pre>
	 *
	 *
	 */
	@XmlRootElement(name = "CG_Req_Invoke", namespace="urn:crif-CrifGate:2:1")
	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = {
			"content",
			"params"
	})
	public static class Request {

		@XmlElement(name = "Content", namespace="urn:crif-CrifGate:2:1" , required = true)
		protected String content;
		@XmlElement(name = "Params", required = true, namespace="urn:crif-CrifGate:2:1")
		protected CGReqInvoke.Request.Params params;

		/**
		 * Gets the value of the content property.
		 *
		 * @return
		 *     possible object is
		 *     {@link String }
		 *
		 */
		public String getContent() {
			return content;
		}

		/**
		 * Sets the value of the content property.
		 *
		 * @param value
		 *     allowed object is
		 *     {@link String }
		 *
		 */
		public void setContent(String value) {
			this.content = value;
		}

		/**
		 * Gets the value of the params property.
		 *
		 * @return
		 *     possible object is
		 *     {@link CGReqInvoke.Request.Params }
		 *
		 */
		public CGReqInvoke.Request.Params getParams() {
			return params;
		}

		/**
		 * Sets the value of the params property.
		 *
		 * @param value
		 *     allowed object is
		 *     {@link CGReqInvoke.Request.Params }
		 *
		 */
		public void setParams(CGReqInvoke.Request.Params value) {
			this.params = value;
		}


		/**
		 * <p>Java class for anonymous complex type.
		 *
		 * <p>The following schema fragment specifies the expected content contained within this class.
		 *
		 * <pre>
		 * &lt;complexType&gt;
		 *   &lt;complexContent&gt;
		 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
		 *       &lt;sequence&gt;
		 *         &lt;element name="Param"&gt;
		 *           &lt;complexType&gt;
		 *             &lt;complexContent&gt;
		 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
		 *                 &lt;sequence&gt;
		 *                   &lt;element name="ServiceID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
		 *                   &lt;element name="ProcessID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
		 *                   &lt;element name="ForceRequest" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
		 *                   &lt;element name="NDG" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
		 *                 &lt;/sequence&gt;
		 *               &lt;/restriction&gt;
		 *             &lt;/complexContent&gt;
		 *           &lt;/complexType&gt;
		 *         &lt;/element&gt;
		 *       &lt;/sequence&gt;
		 *     &lt;/restriction&gt;
		 *   &lt;/complexContent&gt;
		 * &lt;/complexType&gt;
		 * </pre>
		 *
		 *
		 */
		@XmlAccessorType(XmlAccessType.FIELD)
		@XmlType(name = "", propOrder = {
				"param"
		})
		public static class Params {

			@XmlElement(name = "Param", required = true, namespace="urn:crif-CrifGate:2:1")
			protected CGReqInvoke.Request.Params.Param param;

			/**
			 * Gets the value of the param property.
			 *
			 * @return
			 *     possible object is
			 *     {@link CGReqInvoke.Request.Params.Param }
			 *
			 */
			public CGReqInvoke.Request.Params.Param getParam() {
				return param;
			}

			/**
			 * Sets the value of the param property.
			 *
			 * @param value
			 *     allowed object is
			 *     {@link CGReqInvoke.Request.Params.Param }
			 *
			 */
			public void setParam(CGReqInvoke.Request.Params.Param value) {
				this.param = value;
			}


			/**
			 * <p>Java class for anonymous complex type.
			 *
			 * <p>The following schema fragment specifies the expected content contained within this class.
			 *
			 * <pre>
			 * &lt;complexType&gt;
			 *   &lt;complexContent&gt;
			 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
			 *       &lt;sequence&gt;
			 *         &lt;element name="ServiceID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
			 *         &lt;element name="ProcessID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
			 *         &lt;element name="ForceRequest" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
			 *         &lt;element name="NDG" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
			 *       &lt;/sequence&gt;
			 *     &lt;/restriction&gt;
			 *   &lt;/complexContent&gt;
			 * &lt;/complexType&gt;
			 * </pre>
			 *
			 *
			 */
			@XmlAccessorType(XmlAccessType.FIELD)
			@XmlType(name = "", propOrder = {
					"serviceID",
					"processID",
					"forceRequest",
					"ndg"
			})
			public static class Param {

				@XmlElement(name = "ServiceID", required = true,  namespace="urn:crif-CrifGate:2:1")
				protected String serviceID;
				@XmlElement(name = "ProcessID", required = true,  namespace="urn:crif-CrifGate:2:1")
				protected String processID;
				@XmlElement(name = "ForceRequest",  namespace="urn:crif-CrifGate:2:1")
				protected Boolean forceRequest;
				@XmlElement(name = "NDG")
				protected String ndg;

				/**
				 * Gets the value of the serviceID property.
				 *
				 * @return
				 *     possible object is
				 *     {@link String }
				 *
				 */
				public String getServiceID() {
					return serviceID;
				}

				/**
				 * Sets the value of the serviceID property.
				 *
				 * @param value
				 *     allowed object is
				 *     {@link String }
				 *
				 */
				public void setServiceID(String value) {
					this.serviceID = value;
				}

				/**
				 * Gets the value of the processID property.
				 *
				 * @return
				 *     possible object is
				 *     {@link String }
				 *
				 */
				public String getProcessID() {
					return processID;
				}

				/**
				 * Sets the value of the processID property.
				 *
				 * @param value
				 *     allowed object is
				 *     {@link String }
				 *
				 */
				public void setProcessID(String value) {
					this.processID = value;
				}

				/**
				 * Gets the value of the forceRequest property.
				 *
				 * @return
				 *     possible object is
				 *     {@link Boolean }
				 *
				 */
				public Boolean isForceRequest() {
					return forceRequest;
				}

				/**
				 * Sets the value of the forceRequest property.
				 *
				 * @param value
				 *     allowed object is
				 *     {@link Boolean }
				 *
				 */
				public void setForceRequest(Boolean value) {
					this.forceRequest = value;
				}

				/**
				 * Gets the value of the ndg property.
				 *
				 * @return
				 *     possible object is
				 *     {@link String }
				 *
				 */
				public String getNDG() {
					return ndg;
				}

				/**
				 * Sets the value of the ndg property.
				 *
				 * @param value
				 *     allowed object is
				 *     {@link String }
				 *
				 */
				public void setNDG(String value) {
					this.ndg = value;
				}

			}

		}

	}

}
