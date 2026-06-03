package PuntaElPozo.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.TreeMap;

import PuntaElPozo.Model.Buceador;
import PuntaElPozo.Model.GrupoSanguineo;

public class BuceadorDAO {
    private ConexionDB conexionDB;

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
                PreparedStatement ps = con.prepareStatement(sql)) {

            rellenarPreparedStatement(ps, buceador);
            ps.executeUpdate();
            return true;
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
            ps.executeUpdate();
            return true;

        }
    }

    public boolean eliminar(int idBuceador) throws SQLException {
        String sql = "DELETE FROM buceadores WHERE id = ?;";

        try (Connection con = conexionDB.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idBuceador);
            ps.executeUpdate();
            return true;

        }
    }

    public Buceador buscarPorId(int idBuceador) throws SQLException {
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
                    FROM buceadores WHERE id = ?;
                """;

        try (Connection con = conexionDB.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idBuceador);
            ResultSet rs = ps.executeQuery();
            return crearBuceador(rs);

        }
    }

    public Buceador buscarPorDNI(String dni) throws SQLException {
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
                    FROM buceadores WHERE dni = ?;
                """;

        try (Connection con = conexionDB.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, dni.toUpperCase());

            try (ResultSet rs = ps.executeQuery()) {
                return crearBuceador(rs);
            }
        }
    }

    public Map<Integer, Buceador> cargarListaBuceadores() throws SQLException {
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

        Map<Integer, Buceador> listaBuceadores = new TreeMap<>();

        try (Connection con = conexionDB.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                listaBuceadores.put(rs.getInt("id"), crearBuceador(rs));
            }

            return listaBuceadores;
        }
    }

    private Buceador crearBuceador(ResultSet rs) throws SQLException {
        Buceador buceador = new Buceador(rs.getString("dni"));

        buceador.setId(rs.getInt("id"));
        buceador.setNombre(rs.getString("nombre"));
        buceador.setApellidos(rs.getString("apellidos"));
        buceador.setEmail(rs.getString("email"));
        buceador.setTelefono(rs.getString("telefono"));
        buceador.setFechaNacimiento(rs.getDate("fechaNacimiento").toLocalDate());
        buceador.setFechaAlta(rs.getDate("fechaAlta").toLocalDate());
        buceador.setFechaUltimoReconocimiento(rs.getDate("fechaUltimoReconocimiento").toLocalDate());
        buceador.setFechaCaducidadSeguro(rs.getDate("fechaCaducidadSeguro").toLocalDate());
        buceador.setCompaniaSeguro(rs.getString("companiaSeguro"));
        buceador.setContactoEmergNombre(rs.getString("contactoEmergNombre"));
        buceador.setContactoEmergTelefono(rs.getString("contactoEmergTelefono"));
        buceador.setGrupoSanguineo(GrupoSanguineo.valueOf(rs.getString("grupoSanguineo")));
        buceador.setAlergias(rs.getString("alergias"));
        buceador.setTitulacionActual(rs.getString("titulacionActual"));
        buceador.setOrganizacion(rs.getString("organizacion"));
        buceador.setNumeroInmersiones(rs.getInt("numeroInmersiones"));

        return buceador;
    }

    private void rellenarPreparedStatement(PreparedStatement ps, Buceador buceador) throws SQLException {

        ps.setString(1, buceador.getDNI());
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
        ps.setString(12, buceador.getGrupoSanguineo().name());
        ps.setString(13, buceador.getAlergias());
        ps.setString(14, buceador.getTitulacionActual());
        ps.setString(15, buceador.getOrganizacion());
        ps.setInt(16, buceador.getNumeroInmersiones());
    }
}
