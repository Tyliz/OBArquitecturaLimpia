package com.OBArquitecturaLimpia.entities.Coche;

public class CocheCombustion extends Coche {
    protected double capacidadCombustible;


    public CocheCombustion() {
        super();
    }

    public CocheCombustion(String idCoche) {
        super(idCoche);
    }


    public double getCapacidadCombustible() {
        return capacidadCombustible;
    }

    public void setCapacidadCombustible(double capacidadCombustible) {
        this.capacidadCombustible = capacidadCombustible;
    }
}
