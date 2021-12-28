package com.rcibanque.rcidirect.clients.crif.domain.client;

public class RequestServiceProxy implements com.rcibanque.rcidirect.clients.crif.domain.client.RequestService {
  private String _endpoint = null;
  private com.rcibanque.rcidirect.clients.crif.domain.client.RequestService requestService = null;
  
  public RequestServiceProxy() {
    _initRequestServiceProxy();
  }
  
  public RequestServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initRequestServiceProxy();
  }
  
  private void _initRequestServiceProxy() {
    try {
      requestService = (new com.rcibanque.rcidirect.clients.crif.domain.client.RequestServiceServiceLocator()).getRequestServicePort();
      if (requestService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)requestService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)requestService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (requestService != null)
      ((javax.xml.rpc.Stub)requestService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.rcibanque.rcidirect.clients.crif.domain.client.RequestService getRequestService() {
    if (requestService == null)
      _initRequestServiceProxy();
    return requestService;
  }
  
  public java.lang.String getPdf(java.lang.String arg0) throws java.rmi.RemoteException{
    if (requestService == null)
      _initRequestServiceProxy();
    return requestService.getPdf(arg0);
  }
  
  public java.lang.String getDetail(java.lang.String arg0) throws java.rmi.RemoteException{
    if (requestService == null)
      _initRequestServiceProxy();
    return requestService.getDetail(arg0);
  }
  
  public java.lang.String getHtml(java.lang.String arg0) throws java.rmi.RemoteException{
    if (requestService == null)
      _initRequestServiceProxy();
    return requestService.getHtml(arg0);
  }
  
  public java.lang.String invoke(java.lang.String arg0) throws java.rmi.RemoteException{
    if (requestService == null)
      _initRequestServiceProxy();
    return requestService.invoke(arg0);
  }
  
  
}