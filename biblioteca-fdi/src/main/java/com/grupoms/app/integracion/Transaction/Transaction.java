package com.grupoms.app.integracion.Transaction;
public interface Transaction{
    public void start()throws Exception;
    public void commit()throws Exception;
    public void rollback()throws Exception;
    public Object getResource();

    public void cerrarConnection();
}