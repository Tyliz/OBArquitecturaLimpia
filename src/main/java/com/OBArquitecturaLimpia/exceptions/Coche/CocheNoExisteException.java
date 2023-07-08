package com.OBArquitecturaLimpia.exceptions.Coche;

public class CocheNoExisteException extends CocheException {
    public CocheNoExisteException(String idCoche) {
        super("El coche no existe, " + idCoche);
    }
}
