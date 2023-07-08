package com.OBArquitecturaLimpia.services.Coche;


import java.util.ArrayList;


import com.OBArquitecturaLimpia.entities.Coche.Coche;
import com.OBArquitecturaLimpia.repositories.Coche.CocheDBPostgresBusqueda;


public class CocheBusquedaServices<T extends Coche> extends CocheServices<T> {
    public CocheBusquedaServices(Class<T> type) {
        super(type);
        cocheDB = new CocheDBPostgresBusqueda<T>(type);
    }

    public ArrayList<T> listar(String textoBusqueda) {
        return ((CocheDBPostgresBusqueda<T>)cocheDB).listar(textoBusqueda);
    }
}
