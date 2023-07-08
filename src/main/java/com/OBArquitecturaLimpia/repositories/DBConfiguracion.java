package com.OBArquitecturaLimpia.repositories;


public abstract class DBConfiguracion<T> {
    protected String host;
    protected String puerto;
    protected String dbNombre;
    protected String usuario;
    protected String contrasenna;


    public abstract T obtenerPorConsulta(String consulta) throws Exception;
    public abstract void ejecutarConsulta(String consulta) throws Exception;
}
