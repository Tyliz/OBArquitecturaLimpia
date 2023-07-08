package com.OBArquitecturaLimpia.repositories.Coche;


import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;


import com.OBArquitecturaLimpia.entities.Coche.Coche;
import com.OBArquitecturaLimpia.entities.Coche.CocheCombustion;
import com.OBArquitecturaLimpia.entities.Coche.CocheElectrico;
import com.OBArquitecturaLimpia.entities.Coche.CocheHibrido;
import com.OBArquitecturaLimpia.exceptions.Coche.CocheException;


public class CocheDBFichero<T extends Coche> implements CocheDB<T>{
    protected Class<T> tipoCoche;
    protected String ficheroCoches;

    public CocheDBFichero(Class<T> tipoCoche) {
        this.tipoCoche = tipoCoche;

        if (this.tipoCoche.equals(Coche.class)) {
            ficheroCoches = "coches.txt";
        } else if (this.tipoCoche.equals(CocheElectrico.class)) {
            ficheroCoches = "cochesElectricos.txt";
        } else if (this.tipoCoche.equals(CocheHibrido.class)) {
            ficheroCoches = "cochesHibridos.txt";
        } else {
            ficheroCoches = "cochesCombustion.txt";
        }
    }

    @Override
    public ArrayList<T> listar() {
        ArrayList<T> aCoches = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(new File(ficheroCoches));

            while (scanner.hasNext()) {
                String cocheString = scanner.next();
                String[] cocheValores = cocheString.split(",");

                T coche = tipoCoche.getDeclaredConstructor(String.class).newInstance(cocheValores[0]);

                coche.setMarca(cocheValores[1]);
                coche.setModelo(cocheValores[2]);
                coche.setTraccionDelantera(Boolean.parseBoolean(cocheValores[3]));
                coche.setTraccionTrasera(Boolean.parseBoolean(cocheValores[4]));

                if (this.tipoCoche.equals(CocheElectrico.class)) {
                    coche.setCapacidadBateria(Double.parseDouble(cocheValores[5]));
                } else if (this.tipoCoche.equals(CocheHibrido.class)) {
                    coche.setCapacidadBateria(Double.parseDouble(cocheValores[5]));
                    coche.setCapacidadCombustible(Double.parseDouble(cocheValores[6]));
                } else if (this.tipoCoche.equals(CocheCombustion.class)) {
                    coche.setCapacidadCombustible(Double.parseDouble(cocheValores[5]));
                }

                aCoches.add(coche);
            }
        } catch (Exception e) { }

        return aCoches;
    }

    @Override
    public T obtener(String idCoche) throws CocheException {
        try {
            Scanner scanner = new Scanner(new File(ficheroCoches));

            while (scanner.hasNext()) {
                String cocheString = scanner.next();
                String[] cocheValores = cocheString.split(",");
                if (cocheValores[0].equals(idCoche)) {
                    T coche = tipoCoche.getDeclaredConstructor(String.class).newInstance(cocheValores[0]);

                    coche.setMarca(cocheValores[1]);
                    coche.setModelo(cocheValores[2]);
                    coche.setTraccionDelantera(Boolean.parseBoolean(cocheValores[3]));
                    coche.setTraccionTrasera(Boolean.parseBoolean(cocheValores[4]));

                    if (this.tipoCoche.equals(CocheElectrico.class)) {
                        coche.setCapacidadBateria(Double.parseDouble(cocheValores[5]));
                    } else if (this.tipoCoche.equals(CocheHibrido.class)) {
                        coche.setCapacidadBateria(Double.parseDouble(cocheValores[5]));
                        coche.setCapacidadCombustible(Double.parseDouble(cocheValores[6]));
                    } else if (this.tipoCoche.equals(CocheCombustion.class)) {
                        coche.setCapacidadCombustible(Double.parseDouble(cocheValores[5]));
                    }

                    return coche;
                }
            }
        } catch (Exception e) {
            throw new CocheException("Ocurrio un evento mientras se obtenia el coche:\r\n\t" + e.getMessage());
        }

        return null;
    }

    @Override
    public void crear(T coche) throws CocheException {
        try {
            String idCoche = UUID.randomUUID().toString();
            FileOutputStream fileOutputStream = new FileOutputStream(ficheroCoches, true);
            PrintStream printStream = new PrintStream(fileOutputStream);

            printStream.println(idCoche + separarCochePorComas(coche));

            printStream.flush();
            printStream.close();
        } catch (Exception e) {
            throw new CocheException("Ocurrio un error al guardar el coche:\r\n\t" + e.getMessage());
        }

    }

    @Override
    public void borrar(T coche) throws CocheException {
        List<T> aCochesRestantes = listar()
            .stream()
            .filter(x -> !x.getIdCoche().equals(coche.getIdCoche()))
            .toList();

        try {
            PrintStream printStream = new PrintStream(ficheroCoches);

            for (T cocheRestante : aCochesRestantes) {
                printStream.println(separarCochePorComas(cocheRestante));
            }

            printStream.flush();
            printStream.close();
        } catch (Exception e) {
            throw new CocheException("Ocurrio un error al borrar el coche\r\n\t" + e.getMessage());
        }

    }


    private String separarCochePorComas(T coche) {
        String resultado = coche.getIdCoche() + "," + coche.getMarca() + "," + coche.getModelo() + "," + coche.tieneTraccionDelantera() + "," + coche.tieneTraccionTrasera();
        if (this.tipoCoche.equals(Coche.class)) {
            return resultado;
        } if (this.tipoCoche.equals(CocheElectrico.class)) {
            return resultado + "," +
                coche.getCapacidadBateria();
        } else if (this.tipoCoche.equals(CocheHibrido.class)) {
            return resultado + "," +
                coche.getCapacidadBateria() + "," +
                coche.getCapacidadCombustible();
        } else {
            return resultado + "," + coche.getCapacidadCombustible();
        }
    }
}
