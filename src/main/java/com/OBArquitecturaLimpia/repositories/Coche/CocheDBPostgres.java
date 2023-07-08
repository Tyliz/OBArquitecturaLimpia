package com.OBArquitecturaLimpia.repositories.Coche;


import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.UUID;


import com.OBArquitecturaLimpia.entities.Coche.Coche;
import com.OBArquitecturaLimpia.entities.Coche.CocheCombustion;
import com.OBArquitecturaLimpia.entities.Coche.CocheElectrico;
import com.OBArquitecturaLimpia.entities.Coche.CocheHibrido;
import com.OBArquitecturaLimpia.exceptions.Coche.CocheException;
import com.OBArquitecturaLimpia.repositories.DBPostgresConfiguracion;


public class CocheDBPostgres<T extends Coche> implements CocheDB<T> {
    protected Class<T> tipoCoche;
    protected DBPostgresConfiguracion configuracion;
    protected String consultaListar;
    protected String consultaCrear;
    protected String consultaBorrar;


    public CocheDBPostgres(Class<T> tipoCoche) {
        this.tipoCoche = tipoCoche;
        this.configuracion = new DBPostgresConfiguracion();

        asignarConsultaListar();

        asignarConsultaCrear();

        asignarConsultaBorrar();
    }


    private void asignarConsultaListar() {
        if (this.tipoCoche.equals(CocheElectrico.class)) {
            consultaListar =
                "SELECT c.*, ce.\"capacidadBateria\", 0 \"capacidadCombustible\" " +
                "FROM public.\"Coche\" as c " +
                "INNER JOIN public.\"CocheElectrico\" as ce on ce.\"idCoche\" = c.\"idCoche\"";
        } else if (this.tipoCoche.equals(CocheHibrido.class)) {
            consultaListar =
                "SELECT c.*, ch.\"capacidadBateria\", ch.\"capacidadCombustible\" " +
                "FROM public.\"Coche\" as c " +
                "INNER JOIN public.\"CocheHibrido\" as ch on ch.\"idCoche\" = c.\"idCoche\"";
        } else if (this.tipoCoche.equals(CocheCombustion.class)) {
            consultaListar =
                "SELECT c.*, 0 \"capacidadBateria\", cc.\"capacidadCombustible\" " +
                "FROM public.\"Coche\" as c " +
                "INNER JOIN public.\"CocheCombustion\" as cc on cc.\"idCoche\" = c.\"idCoche\"";
        } else {
            consultaListar =
                "SELECT *, 0 \"capacidadBateria\", 0 \"capacidadCombustible\" "
                +"FROM public.\"Coche\" as c";
        }
    }

    private void asignarConsultaCrear() {
        consultaCrear =
            "INSERT INTO public.\"Coche\" " +
            "(\"idCoche\", marca, modelo, \"traccionDelantera\", \"traccionTrasera\") " +
            "VALUES ('{0}', '{1}', '{2}', {3}, {4});";

        if (this.tipoCoche.equals(CocheElectrico.class)) {
            consultaCrear +=
                " INSERT INTO public.\"CocheElectrico\" " +
                "(\"idCoche\", \"capacidadBateria\") " +
                "VALUES ('{0}', {5});";
        } else if (this.tipoCoche.equals(CocheHibrido.class)) {
            consultaCrear +=
                " INSERT INTO public.\"CocheHibrido\" " +
                "(\"idCoche\", \"capacidadBateria\", \"capacidadCombustible\") " +
                "VALUES ('{0}', {5}, {6});";
        } else if (this.tipoCoche.equals(CocheCombustion.class)) {
            consultaCrear +=
                " INSERT INTO public.\"CocheCombustion\" " +
                "(\"idCoche\", \"capacidadCombustible\") " +
                "VALUES ('{0}', {6});";
        }
    }

    private void asignarConsultaBorrar() {
        consultaBorrar = "DELETE FROM public.\"Coche\" " +
            "WHERE \"idCoche\" = '{0}'";
    }


    @Override
    public ArrayList<T> listar() {
        ArrayList<T> aCoches = new ArrayList<>();

        try {
            ResultSet resultSet = configuracion.obtenerPorConsulta(consultaListar);

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


    @Override
    public T obtener(String idCoche) {
        try {
            ResultSet resultSet = configuracion.obtenerPorConsulta(
                consultaListar +
                " WHERE c.\"idCoche\" = '" + idCoche + "'"
            );

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

                return coche;
            }
        } catch (Exception e) { }
        return null;
    }


    @Override
    public void crear(T coche) throws CocheException {
        try {
            String idCoche = UUID.randomUUID().toString();
            String consulta = consultaCrear
                .replace("{0}", idCoche)
                .replace("{1}", coche.getMarca())
                .replace("{2}", coche.getModelo())
                .replace("{3}", Boolean.toString(coche.tieneTraccionDelantera()))
                .replace("{4}", Boolean.toString(coche.tieneTraccionTrasera()))
                .replace("{5}", Double.toString(coche.getCapacidadBateria()))
                .replace("{6}", Double.toString(coche.getCapacidadCombustible()));

            configuracion.ejecutarConsulta(consulta);
        } catch (Exception e) {
            throw new CocheException("Ocurrio un error al guardar el coche: \t" + e.getMessage());
        }
    }


    @Override
    public void borrar(T coche) throws CocheException {
        try {
            String consulta = consultaBorrar.replace("{0}", coche.getIdCoche());

            System.out.println("consulta borrar:" + consulta);
            configuracion.ejecutarConsulta(consulta);
        } catch (Exception e) {
            throw new CocheException("Ocurrio un error al guardar el coche: \t" + e.getMessage());
        }
    }

}
