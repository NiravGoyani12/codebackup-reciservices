/**
 * RequestServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rcibanque.rcidirect.clients.crif.domain.client;

public class RequestServiceServiceLocator extends org.apache.axis.client.Service implements com.rcibanque.rcidirect.clients.crif.domain.client.RequestServiceService {

    public RequestServiceServiceLocator() {
    }


    public RequestServiceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public RequestServiceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for RequestServicePort
    private java.lang.String RequestServicePort_address = "file:///D:/workspaces/rcidirect_v2/rcidirect-clients-ie/rcidirect-clients-crif/src/main/java/resources/CRIF.wsdl";

    public java.lang.String getRequestServicePortAddress() {
        return RequestServicePort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String RequestServicePortWSDDServiceName = "RequestServicePort";

    public java.lang.String getRequestServicePortWSDDServiceName() {
        return RequestServicePortWSDDServiceName;
    }

    public void setRequestServicePortWSDDServiceName(java.lang.String name) {
        RequestServicePortWSDDServiceName = name;
    }

    public com.rcibanque.rcidirect.clients.crif.domain.client.RequestService getRequestServicePort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(RequestServicePort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getRequestServicePort(endpoint);
    }

    public com.rcibanque.rcidirect.clients.crif.domain.client.RequestService getRequestServicePort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.rcibanque.rcidirect.clients.crif.domain.client.RequestServiceServiceSoapBindingStub _stub = new com.rcibanque.rcidirect.clients.crif.domain.client.RequestServiceServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getRequestServicePortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setRequestServicePortEndpointAddress(java.lang.String address) {
        RequestServicePort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.rcibanque.rcidirect.clients.crif.domain.client.RequestService.class.isAssignableFrom(serviceEndpointInterface)) {
                com.rcibanque.rcidirect.clients.crif.domain.client.RequestServiceServiceSoapBindingStub _stub = new com.rcibanque.rcidirect.clients.crif.domain.client.RequestServiceServiceSoapBindingStub(new java.net.URL(RequestServicePort_address), this);
                _stub.setPortName(getRequestServicePortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("RequestServicePort".equals(inputPortName)) {
            return getRequestServicePort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://webservice.cg.crif.com/", "RequestServiceService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://webservice.cg.crif.com/", "RequestServicePort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("RequestServicePort".equals(portName)) {
            setRequestServicePortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
