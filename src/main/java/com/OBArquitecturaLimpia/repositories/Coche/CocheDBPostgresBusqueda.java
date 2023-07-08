package com.OBArquitecturaLimpia.repositories.Coche;

import java.sql.ResultSet;
import java.util.ArrayList;


import com.OBArquitecturaLimpia.entities.Coche.Coche;
import com.OBArquitecturaLimpia.entities.Coche.CocheCombustion;
import com.OBArquitecturaLimpia.entities.Coche.CocheElectrico;
import com.OBArquitecturaLimpia.entities.Coche.CocheHibrido;


public class CocheDBPostgresBusqueda<T extends Coche> extends CocheDBPostgres<T> {
    protected String consultaBuscar;

    public CocheDBPostgresBusqueda(Class<T> tipoCoche) {
        super(tipoCoche);

        asignarConsultaBuscar();
    }


    public void asignarConsultaBuscar() {
        if (this.tipoCoche.equals(CocheElectrico.class)) {
            consultaBuscar =
                "SELECT c.*, ce.\"capacidadBateria\", 0 \"capacidadCombustible\"\r\n" + //
                "FROM public.\"Coche\" as c\r\n" + //
                "INNER JOIN public.\"CocheElectrico\" as ce on ce.\"idCoche\" = c.\"idCoche\"\r\n" +
                "WHERE marca LIKE '%{0}%'";
        } else if (this.tipoCoche.equals(CocheHibrido.class)) {
            consultaBuscar =
                "SELECT c.*, ch.\"capacidadBateria\", ch.\"capacidadCombustible\"\r\n" + //
                "FROM public.\"Coche\" as c\r\n" + //
                "INNER JOIN public.\"CocheHibrido\" as ch on ch.\"idCoche\" = c.\"idCoche\"\r\n" +
                "WHERE marca LIKE '%{0}%'";
        } else if (this.tipoCoche.equals(CocheCombustion.class)) {
            consultaBuscar =
                "SELECT c.*, 0 \"capacidadBateria\", cc.\"capacidadCombustible\"\r\n" + //
                "FROM public.\"Coche\" as c\r\n" + //
                "INNER JOIN public.\"CocheCombustion\" as cc on cc.\"idCoche\" = c.\"idCoche\"\r\n" +
                "WHERE marca LIKE '%{0}%'";
        } else {
            consultaBuscar =
                "SELECT *, 0 \"capacidadBateria\", 0 \"capacidadCombustible\"\r\n"
                +"FROM public.\"Coche\" as c\r\n" +
                "WHERE marca LIKE '%{0}%'";
        }
    }


    @Override
    public ArrayList<T> listar() {
        return super.listar();
    }

    public ArrayList<T> listar(String busqueda) {
        ArrayList<T> aCoches = new ArrayList<>();

        try {
            String consulta = consultaBuscar.replace("{0}", busqueda);
            ResultSet resultSet = configuracion.obtenerPorConsulta(consulta);

            while (resultSet.next()) {
                T coche = tipoCoche
                    .getDeclaredConstructor(String.class)
                    .newInstance(resultSet.getString("idCoche"));

                coche.setMarca(resultSet.getString("marca"));
                coche.setModelo(resultSet.getString("modelo"));
                coche.setTraccionDelantera(resultSet.getBoolean("traccionDelantera"));
                coche.setTraccionTrasera(resultSet.getBoolean("traccionTrasera"));
                coche.setCapacidadBateria(resultSet.getDouble("capacidadBateria"));
                coche.setCapacidadCombustible(resultSet.getDouble("capacidadCombustible"));

                aCoches.add(coche);
            }
        } catch (Exception e) { }

        return aCoches;
    }
}
