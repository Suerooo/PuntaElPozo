package PuntaElPozo.Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
    private static final String URL = obtenerVariableEntorno(
            "PUNTA_DB_URL",
            "jdbc:mysql://localhost:3306/punta_el_pozo");
    private static final String USUARIO = obtenerVariableEntorno("PUNTA_DB_USER", "root");
    private static final String PASSWORD = obtenerVariableEntorno("PUNTA_DB_PASSWORD", "");

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, PASSWORD);
    }

    private static String obtenerVariableEntorno(String nombre, String valorPorDefecto) {
        String valor = System.getenv(nombre);
        return valor == null || valor.isBlank() ? valorPorDefecto : valor;
    }
}
