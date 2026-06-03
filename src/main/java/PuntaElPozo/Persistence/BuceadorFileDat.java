package PuntaElPozo.Persistence;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;

import PuntaElPozo.Model.Buceador;

public class BuceadorFileDat {
    private String ruta;

    public BuceadorFileDat(String ruta) {
        this.ruta = ruta;
    }

    public void guardar(Map<Integer, Buceador> listaBuceadores) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(ruta))) {
            out.writeObject(listaBuceadores);
        }
    }

    public Map<Integer, Buceador> cargar() throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(ruta))) {
            return (Map<Integer, Buceador>) in.readObject();
        }
    }
}
