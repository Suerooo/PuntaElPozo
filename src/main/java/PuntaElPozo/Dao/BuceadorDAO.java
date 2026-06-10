    package PuntaElPozo.Dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;

import PuntaElPozo.Model.Buceador;
import PuntaElPozo.Model.GrupoSanguineo;

public class BuceadorDAO {
    private ConexionDB conexionDB;

    public BuceadorDAO() {
        this.conexionDB = new ConexionDB();
    }

    public boolean insertar(Buceador buceador) throws SQLException {
        String sql = """
                    INSERT INTO buceadores (
                        dni,
                        nombre,
                        apellidos,
                        email,
                        telefono,
                        fechaNacimiento,
                        fechaUltimoReconocimiento,
                        fechaCaducidadSeguro,
                        companiaSeguro,
                        contactoEmergNombre,
                        contactoEmergTelefono,
                        grupoSanguineo,
                        alergias,
                        titulacionActual,
                        organizacion,
                        numeroInmersiones
                    )
                    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection con = conexionDB.getConnection();
                PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            rellenarPreparedStatement(ps, buceador);
            int filasInsertadas = ps.executeUpdate();

            if (filasInsertadas > 0) {
                asignarIdGenerado(ps, buceador);
                return true;
            }

            return false;
        }
    }

    public boolean actualizar(Buceador buceador) throws SQLException {
        String sql = """
                    UPDATE buceadores SET
                        dni = ?,
                        nombre = ?,
                        apellidos = ?,
                        email = ?,
                        telefono = ?,
                        fechaNacimiento = ?,
                        fechaUltimoReconocimiento = ?,
                        fechaCaducidadSeguro = ?,
                        companiaSeguro = ?,
                        contactoEmergNombre = ?,
                        contactoEmergTelefono = ?,
                        grupoSanguineo = ?,
                        alergias = ?,
                        titulacionActual = ?,
                        organizacion = ?,
                        numeroInmersiones = ?
                    WHERE id = ?;
                """;

        try (Connection con = conexionDB.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            rellenarPreparedStatement(ps, buceador);
            ps.setInt(17, buceador.getId());
            return ps.executeUpdate() > 0;

        }
    }

    public boolean eliminar(int idBuceador) throws SQLException {
        String sql = "DELETE FROM buceadores WHERE id = ?;";

        try (Connection con = conexionDB.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idBuceador);
            return ps.executeUpdate() > 0;

        }
    }

    public Map<Integer, Buceador> cargarMapaBuceadores() throws SQLException {
        String sql = """
                    SELECT
                        id,
                        dni,
                        nombre,
                        apellidos,
                        email,
                        telefono,
                        fechaNacimiento,
                        fechaAlta,
                        fechaUltimoReconocimiento,
                        fechaCaducidadSeguro,
                        companiaSeguro,
                        contactoEmergNombre,
                        contactoEmergTelefono,
                        grupoSanguineo,
                        alergias,
                        titulacionActual,
                        organizacion,
                        numeroInmersiones
                    FROM buceadores;
                """;

        Map<Integer, Buceador> mapaBuceadores = new TreeMap<>();

        try (Connection con = conexionDB.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                mapaBuceadores.put(rs.getInt("id"), crearBuceador(rs));

            }

            return mapaBuceadores;
        }
    }

    private Buceador crearBuceador(ResultSet rs) throws SQLException {
        Buceador buceador = new Buceador(rs.getString("dni"));

        buceador.setId(rs.getInt("id"));
        buceador.setNombre(rs.getString("nombre"));
        buceador.setApellidos(rs.getString("apellidos"));
        buceador.setEmail(rs.getString("email"));
        buceador.setTelefono(rs.getString("telefono"));
        LocalDate fechaNacimiento = obtenerFecha(rs, "fechaNacimiento");
        LocalDate fechaAlta = obtenerFecha(rs, "fechaAlta");
        LocalDate fechaUltimoReconocimiento = obtenerFecha(rs, "fechaUltimoReconocimiento");

        if (fechaNacimiento != null) {
            buceador.setFechaNacimiento(fechaNacimiento);
        }

        if (fechaAlta != null) {
            buceador.setFechaAlta(fechaAlta);
        }

        if (fechaUltimoReconocimiento != null) {
            buceador.setFechaUltimoReconocimiento(fechaUltimoReconocimiento);
        }

        buceador.setFechaCaducidadSeguro(obtenerFecha(rs, "fechaCaducidadSeguro"));
        buceador.setCompaniaSeguro(rs.getString("companiaSeguro"));
        buceador.setContactoEmergNombre(rs.getString("contactoEmergNombre"));
        buceador.setContactoEmergTelefono(rs.getString("contactoEmergTelefono"));
        buceador.setGrupoSanguineo(obtenerGrupoSanguineo(rs, "grupoSanguineo"));
        buceador.setAlergias(rs.getString("alergias"));
        buceador.setTitulacionActual(rs.getString("titulacionActual"));
        buceador.setOrganizacion(rs.getString("organizacion"));
        buceador.setNumeroInmersiones(rs.getInt("numeroInmersiones"));

        return buceador;
    }

    private void asignarIdGenerado(PreparedStatement ps, Buceador buceador) throws SQLException {
        try (ResultSet clavesGeneradas = ps.getGeneratedKeys()) {
            if (clavesGeneradas.next()) {
                buceador.setId(clavesGeneradas.getInt(1));
            }
        }
    }

    private LocalDate obtenerFecha(ResultSet rs, String columna) throws SQLException {
        Date fecha = rs.getDate(columna);
        return fecha == null ? null : fecha.toLocalDate();
    }

    private GrupoSanguineo obtenerGrupoSanguineo(ResultSet rs, String columna) throws SQLException {
        String valor = rs.getString(columna);
        return valor == null || valor.isBlank() ? null : GrupoSanguineo.valueOf(valor);
    }

    private void rellenarPreparedStatement(PreparedStatement ps, Buceador buceador) throws SQLException {

        ps.setString(1, buceador.getDni());
        ps.setString(2, buceador.getNombre());
        ps.setString(3, buceador.getApellidos());
        ps.setString(4, buceador.getEmail());
        ps.setString(5, buceador.getTelefono());
        ps.setObject(6, buceador.getFechaNacimiento());
        ps.setObject(7, buceador.getFechaUltimoReconocimiento());
        ps.setObject(8, buceador.getFechaCaducidadSeguro());
        ps.setString(9, buceador.getCompaniaSeguro());
        ps.setString(10, buceador.getContactoEmergNombre());
        ps.setString(11, buceador.getContactoEmergTelefono());
        ps.setString(12, buceador.getGrupoSanguineo() == null ? null : buceador.getGrupoSanguineo().name());
        ps.setString(13, buceador.getAlergias());
        ps.setString(14, buceador.getTitulacionActual());
        ps.setString(15, buceador.getOrganizacion());
        ps.setInt(16, buceador.getNumeroInmersiones());
    }
}
