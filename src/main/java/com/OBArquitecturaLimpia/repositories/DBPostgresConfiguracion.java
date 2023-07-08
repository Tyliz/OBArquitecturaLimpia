package com.OBArquitecturaLimpia.repositories;


import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DBPostgresConfiguracion extends DBConfiguracion<ResultSet> {
    protected String url;

    public DBPostgresConfiguracion() {
        host = "localhost";
        puerto = "5432";
        dbNombre = "DBJavaEjercicios";
        usuario = "postgres";
        contrasenna = "Supermac28.";

        url = "jdbc:postgresql://" + host + ":" + puerto + "/" + dbNombre;
    }

    @Override
    public ResultSet obtenerPorConsulta(String consulta) throws ClassNotFoundException, SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        }  catch (ClassNotFoundException e) {
            throw e;
        }

        try (Connection conexion = DriverManager.getConnection(url, usuario, contrasenna)) {
            Statement statement = conexion.createStatement();
            ResultSet resultSet = statement.executeQuery(consulta);

            return resultSet;
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public void ejecutarConsulta(String consulta) throws ClassNotFoundException, SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        }  catch (ClassNotFoundException e) {
            throw e;
        }

        try (Connection conexion = DriverManager.getConnection(url, usuario, contrasenna)) {
            Statement statement = conexion.createStatement();
            statement.executeUpdate(consulta);

        } catch (SQLException e) {
            throw e;
        }
    }

}
