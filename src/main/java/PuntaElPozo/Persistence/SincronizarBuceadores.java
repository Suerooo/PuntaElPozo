package PuntaElPozo.Persistence;

import java.io.IOException;
import java.sql.SQLException;
import java.util.TreeMap;
import java.util.Map;

import PuntaElPozo.Dao.BuceadorDAO;
import PuntaElPozo.Model.Buceador;

public class SincronizarBuceadores {

    private final BuceadorDAO dao;
    private final BuceadorFileDat fileDat;

    public SincronizarBuceadores(BuceadorDAO dao, BuceadorFileDat fileDat) {
        this.dao = dao;
        this.fileDat = fileDat;
    }

    public Map<Integer, Buceador> cargar() throws IOException, ClassNotFoundException {
        try {
            Map<Integer, Buceador> mapaMySQL = dao.cargarMapaBuceadores();

            if (mapaMySQL == null) {
                mapaMySQL = new TreeMap<>();
            }

            Map<Integer, Buceador> mapaLocal = fileDat.cargar();

            if (!mapaLocal.isEmpty()) {

                sincronizarLocalConMySQL(mapaLocal, mapaMySQL);

                mapaMySQL = dao.cargarMapaBuceadores();

                if (mapaMySQL == null) {
                    mapaMySQL = new TreeMap<>();
                }
            }

            fileDat.guardar(mapaMySQL);

            return mapaMySQL;

        } catch (SQLException e) {

            return fileDat.cargar();
        }
    }

    public void guardar(Map<Integer, Buceador> mapaBuceadores) throws IOException {

        if (mapaBuceadores == null) {
            mapaBuceadores = new TreeMap<>();
        }

        fileDat.guardar(mapaBuceadores);
    }

    private void sincronizarLocalConMySQL(Map<Integer, Buceador> mapaLocal, Map<Integer, Buceador> mapaMySQL)
            throws SQLException {

        for (Buceador buceadorLocal : mapaLocal.values()) {

            if (buceadorLocal == null) {
                continue;
            }

            Buceador buceadorMySQL = buscarEnMapaPorDni(mapaMySQL, buceadorLocal.getDni());

            if (buceadorMySQL == null) {

                dao.insertar(buceadorLocal);

            } else {

                dao.actualizar(asignarIdMySQL(buceadorLocal, buceadorMySQL.getId()));
            }
        }
    }

    private Buceador asignarIdMySQL(Buceador buceador, Integer idMySQL) {
        buceador.setId(idMySQL);

        return buceador;
    }

    private Buceador buscarEnMapaPorDni(Map<Integer, Buceador> mapa, String dni) {

        if (mapa == null || dni == null) {
            return null;
        }

        for (Buceador buceador : mapa.values()) {
            if (buceador.getDni().equalsIgnoreCase(dni)) {
                return buceador;
            }
        }

        return null;
    }
}
