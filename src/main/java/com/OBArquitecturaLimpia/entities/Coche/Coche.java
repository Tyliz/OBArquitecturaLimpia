package com.OBArquitecturaLimpia.entities.Coche;

public class Coche {
    protected String idCoche;
    protected String marca;
    protected String modelo;
    protected boolean traccionDelantera;
    protected boolean traccionTrasera;


    public Coche() {
        idCoche = "";
    }

    public Coche(String idCoche) {
        this.idCoche =  idCoche;
    }

    public String getIdCoche() {
        return idCoche;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public boolean tieneTraccionDelantera() {
        return traccionDelantera;
    }

    public void setTraccionDelantera(boolean traccionDelantera) {
        this.traccionDelantera = traccionDelantera;
    }

    public boolean tieneTraccionTrasera() {
        return traccionTrasera;
    }

    public void setTraccionTrasera(boolean traccionTrasera) {
        this.traccionTrasera = traccionTrasera;
    }

    public String obtenerTraccion() {
        return traccionDelantera && traccionTrasera
            ? "Doble tracción"
            : traccionTrasera
                ? "Tracción trasera"
                : "Tracción delantera";
    }

    public void setCapacidadBateria(double capacidadBateria) {}
    public void setCapacidadCombustible(double capacidadCombustible) {}
    public double getCapacidadBateria() { return 0; }
    public double getCapacidadCombustible() { return 0; }
}
