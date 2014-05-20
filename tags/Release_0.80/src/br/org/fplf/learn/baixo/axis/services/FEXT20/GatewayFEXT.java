/**
 * GatewayFEXT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package br.org.fplf.learn.baixo.axis.services.FEXT20;

public interface GatewayFEXT extends java.rmi.Remote 
{
    public java.lang.String getResult(java.lang.String userName, java.lang.String password, int taskId) throws java.rmi.RemoteException;
    public int runTask(java.lang.String userName, java.lang.String password, java.lang.String language, java.lang.String linguisticInfo, java.lang.String inText) throws java.rmi.RemoteException;
    public int isTaskDone(java.lang.String userName, java.lang.String password, int taskId) throws java.rmi.RemoteException;
    public java.lang.String getErrorMessage(int errorCode) throws java.rmi.RemoteException;
}
