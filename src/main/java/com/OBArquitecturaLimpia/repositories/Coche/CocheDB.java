package com.OBArquitecturaLimpia.repositories.Coche;


import java.util.ArrayList;


import com.OBArquitecturaLimpia.entities.Coche.Coche;
import com.OBArquitecturaLimpia.exceptions.Coche.CocheException;


public interface CocheDB<T extends Coche> {
    ArrayList<T> listar();
    T obtener(String idCoche) throws CocheException;
    void crear(T coche) throws CocheException;
    void borrar(T coche) throws CocheException;
}
