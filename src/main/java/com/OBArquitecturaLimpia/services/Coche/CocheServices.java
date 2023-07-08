package com.OBArquitecturaLimpia.services.Coche;


import java.util.ArrayList;


import com.OBArquitecturaLimpia.entities.Coche.Coche;
import com.OBArquitecturaLimpia.exceptions.Coche.CocheException;
import com.OBArquitecturaLimpia.exceptions.Coche.CocheNoExisteException;
import com.OBArquitecturaLimpia.repositories.Coche.CocheDBPostgresBusqueda;


public class CocheServices<T extends Coche> {
    protected CocheDBPostgresBusqueda<T> cocheDB;


    public CocheServices(Class<T> type) {
        this.cocheDB = new CocheDBPostgresBusqueda<T>(type);
    }


    public ArrayList<T> listar() {
        return cocheDB.listar();
    }

    public T obtener(String idCoche) throws CocheException {
        T coche = (T) cocheDB.obtener(idCoche);

        if (coche != null) return coche;

        throw new CocheNoExisteException(idCoche);
    }

    public void crear(T coche) throws CocheException {
        if (!coche.tieneTraccionDelantera() && !coche.tieneTraccionTrasera()) {
            throw new CocheException("Error al crear el coche:\r\n\tDebe asignar al menos una tracción");
        }

        cocheDB.crear(coche);
    }

    public void borrar(String idCoche) throws CocheException {
        T coche = cocheDB.obtener(idCoche);

        if (coche == null) return;

        cocheDB.borrar(coche);
    }
}
