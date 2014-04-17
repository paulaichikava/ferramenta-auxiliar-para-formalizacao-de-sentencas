package br.org.fplf.learn.baixo.axis.services.FEXT20;

public class GatewayFEXTProxy implements br.org.fplf.learn.baixo.axis.services.FEXT20.GatewayFEXT 
{
  private String _endpoint = null;
  private br.org.fplf.learn.baixo.axis.services.FEXT20.GatewayFEXT gatewayFEXT = null;
  
  public GatewayFEXTProxy() {
    _initGatewayFEXTProxy();
  }
  
  public GatewayFEXTProxy(String endpoint) {
    _endpoint = endpoint;
    _initGatewayFEXTProxy();
  }
  
  private void _initGatewayFEXTProxy() {
    try {
      gatewayFEXT = (new br.org.fplf.learn.baixo.axis.services.FEXT20.GatewayFEXTServiceLocator()).getFEXT20();
      if (gatewayFEXT != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)gatewayFEXT)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)gatewayFEXT)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (gatewayFEXT != null)
      ((javax.xml.rpc.Stub)gatewayFEXT)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public br.org.fplf.learn.baixo.axis.services.FEXT20.GatewayFEXT getGatewayFEXT() {
    if (gatewayFEXT == null)
      _initGatewayFEXTProxy();
    return gatewayFEXT;
  }
  
  public java.lang.String getResult(java.lang.String userName, java.lang.String password, int taskId) throws java.rmi.RemoteException{
    if (gatewayFEXT == null)
      _initGatewayFEXTProxy();
    return gatewayFEXT.getResult(userName, password, taskId);
  }
  
  public int runTask(java.lang.String userName, java.lang.String password, java.lang.String language, java.lang.String linguisticInfo, java.lang.String inText) throws java.rmi.RemoteException{
    if (gatewayFEXT == null)
      _initGatewayFEXTProxy();
    return gatewayFEXT.runTask(userName, password, language, linguisticInfo, inText);
  }
  
  public int isTaskDone(java.lang.String userName, java.lang.String password, int taskId) throws java.rmi.RemoteException{
    if (gatewayFEXT == null)
      _initGatewayFEXTProxy();
    return gatewayFEXT.isTaskDone(userName, password, taskId);
  }
  
  public java.lang.String getErrorMessage(int errorCode) throws java.rmi.RemoteException{
    if (gatewayFEXT == null)
      _initGatewayFEXTProxy();
    return gatewayFEXT.getErrorMessage(errorCode);
  }
  
  
}