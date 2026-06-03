package PuntaElPozo.Persistence;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import PuntaElPozo.Dao.BuceadorDAO;
import PuntaElPozo.Model.Buceador;

public class SincronizarBuceadores {
    BuceadorDAO dao;
    BuceadorFileDat fileDat;

    public Map<Integer, Buceador> cargar() throws IOException, ClassNotFoundException {
        try {
            Map<Integer, Buceador> listaBuceadores = dao.cargarListaBuceadores();

            fileDat.guardar(listaBuceadores);
            return listaBuceadores;

        } catch (SQLException e) {

            return fileDat.cargar();
        }
    }

    public void guardar(Map<Integer, Buceador> listaBuceadores) throws IOException {
        fileDat.guardar(listaBuceadores);
    }
}
