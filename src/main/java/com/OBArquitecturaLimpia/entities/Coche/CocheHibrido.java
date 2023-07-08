package com.OBArquitecturaLimpia.entities.Coche;

public class CocheHibrido extends Coche {
    protected double capacidadCombustible;
    protected double capacidadBateria;


    public CocheHibrido() {
        super();
    }

    public CocheHibrido(String idCoche) {
        super(idCoche);
    }


    public double getCapacidadBateria() {
        return capacidadBateria;
    }

    public void setCapacidadBateria(double capacidadBateria) {
        this.capacidadBateria = capacidadBateria;
    }

    public double getCapacidadCombustible() {
        return capacidadCombustible;
    }

    public void setCapacidadCombustible(double capacidadCombustible) {
        this.capacidadCombustible = capacidadCombustible;
    }
}
