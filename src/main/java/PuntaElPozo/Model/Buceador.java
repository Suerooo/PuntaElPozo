package PuntaElPozo.Model;

import java.io.Serializable;
import java.time.LocalDate;

public class Buceador implements Serializable, Comparable<Buceador> {
    private Integer id;
    private final String DNI;
    private String nombre;
    private String apellidos;
    private String email;
    private String telefono;
    private LocalDate fechaNacimiento;
    private LocalDate fechaAlta;
    private LocalDate fechaUltimoReconocimiento;
    private LocalDate fechaCaducidadSeguro;
    private String companiaSeguro;
    private String contactoEmergNombre;
    private String contactoEmergTelefono;
    private GrupoSanguineo grupoSanguineo;
    private String alergias;
    private String titulacionActual;
    private String organizacion;
    private Integer numeroInmersiones;

    // ====================================================================================
    // CONSTRUCTORES
    // ====================================================================================

    public Buceador(String dni, String nombre, String apellidos) throws IllegalArgumentException {
        if (dniValido(dni)) {
            throw new IllegalArgumentException("Este dni no es valido");
        }

        this.DNI = dni.trim().toUpperCase();
        setNombre(nombre);
        setApellidos(apellidos);
    }

    public Buceador(String dni) throws IllegalArgumentException {
        if (dniValido(dni)) {
            throw new IllegalArgumentException("Este dni no es valido");
        }

        this.DNI = dni.trim().toUpperCase();
    }

    // ====================================================================================
    // GETTERS/SETTERS
    // ====================================================================================

    // ===============================
    // ID
    // ===============================

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) throws IllegalArgumentException {
        if (id == null) {
            throw new IllegalArgumentException("id no puede ser null");
        }

        if (this.id != null) {
            throw new IllegalArgumentException("El id no puede modificar una vez asignado");
        }

        this.id = id;
    }

    // ===============================
    // DNI
    // ===============================

    public String getDNI() {
        return DNI;
    }

    private static boolean dniValido(String dni) {
        return !dni.trim().toUpperCase().matches("^\\d{8}[A-Z]$");
    }

    // ===============================
    // NOMBRE
    // ===============================

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) throws IllegalArgumentException {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("nombre no puede ser null ni vacío");
        }

        if (nombre.length() < 2 || nombre.length() > 50) {
            throw new IllegalArgumentException("nombre no puede ser menos de 2 caracteres ni mayor que 50");
        }

        this.nombre = nombre.trim();
    }

    // ===============================
    // APELLIDOS
    // ===============================

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) throws IllegalArgumentException {
        if (apellidos == null || apellidos.isBlank()) {
            throw new IllegalArgumentException("apellidos no puede ser null ni vacío");
        }

        if (apellidos.length() < 2 || apellidos.length() > 100) {
            throw new IllegalArgumentException("apellidos no puede ser menos de 2 caracteres ni mayor que 100");
        }

        this.apellidos = apellidos.trim();
    }

    // ===============================
    // EMAIL
    // ===============================

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws IllegalArgumentException {
        if (email == null || email.isBlank()) {
            this.email = "Desconocido";
        }

        if (email.trim().length() >= 255) {
            throw new IllegalArgumentException("El email no puede superar los 255 caracteres");
        }

        if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            throw new IllegalArgumentException("El email no tiene el formato correcto");
        }

        this.email = email.trim();
    }

    // ===============================
    // TELEFONO
    // ===============================

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) throws IllegalArgumentException {
        if (telefono == null || telefono.trim().isEmpty()) {
            this.telefono = "Desconocido";
        }

        if (!telefono.matches("^\\+(?:\\[0-9] ?){6,14}\\[0-9]$")) {
            throw new IllegalArgumentException("El numero de telefono no tiene el formato correcto");
        }

        this.telefono = telefono.trim();
    }

    // ===============================
    // FECHA NACIMIENTO
    // ===============================

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) throws IllegalArgumentException {
        if (!fechaNacimiento.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de nacimiento debe ser anterior a la fecha actual");
        }

        this.fechaNacimiento = fechaNacimiento;
    }

    // ===============================
    // FECHA ALTA
    // ===============================

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(LocalDate fechaAlta) throws IllegalArgumentException {
        if (fechaAlta == null) {
            throw new IllegalArgumentException("La fecha de alta no puede ser null");
        }

        if (fechaAlta.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de alta no puede ser después de la fecha actual");
        }

        if (this.fechaAlta != null) {
            throw new IllegalArgumentException("El fecha de alta no se puede modificar una vez asignada");
        }

        this.fechaAlta = fechaAlta;
    }

    // ===============================
    // FECHA ULTIMO RECONOCIMIENTO
    // ===============================

    public LocalDate getFechaUltimoReconocimiento() {
        return fechaUltimoReconocimiento;
    }

    public void setFechaUltimoReconocimiento(LocalDate fechaUltimoReconocimiento) throws IllegalArgumentException {

        if (fechaUltimoReconocimiento.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha del último no puede ser después de la fecha actual");
        }

        this.fechaUltimoReconocimiento = fechaUltimoReconocimiento;
    }

    // ===============================
    // FECHA CADUCIDAD SEGURO
    // ===============================

    public LocalDate getFechaCaducidadSeguro() {
        return fechaCaducidadSeguro;
    }

    public void setFechaCaducidadSeguro(LocalDate fechaCaducidadSeguro) {
        this.fechaCaducidadSeguro = fechaCaducidadSeguro;
    }

    // ===============================
    // COMPAÑIA SEGURO
    // ===============================

    public String getCompaniaSeguro() {
        return companiaSeguro;
    }

    public void setCompaniaSeguro(String companiaSeguro) throws IllegalArgumentException {
        if (companiaSeguro == null || companiaSeguro.trim().isEmpty()) {
            this.companiaSeguro = "Desconocido";
        }

        if (companiaSeguro.length() > 100) {
            throw new IllegalArgumentException("La compañía de seguro no puede superar los 255 caracteres");
        }

        this.companiaSeguro = companiaSeguro.trim();
    }

    // ===============================
    // NOMBRE CONTACTO DE EMERGENCIA
    // ===============================

    public String getContactoEmergNombre() {
        return contactoEmergNombre;
    }

    public void setContactoEmergNombre(String contactoEmergNombre) throws IllegalArgumentException {
        if (contactoEmergNombre == null || contactoEmergNombre.isBlank()) {
            this.contactoEmergNombre = "Desconocido";
        }

        if (nombre.trim().length() < 2 || nombre.trim().length() > 50) {
            throw new IllegalArgumentException(
                    "El nombre del contacto de emergencia no puede ser menos de 2 caracteres ni mayor que 50");
        }

        this.nombre = nombre.trim();
    }

    // ===============================
    // TELEFONO CONTACTO DE EMERGENCIA
    // ===============================

    public String getContactoEmergTelefono() {
        return contactoEmergTelefono;
    }

    public void setContactoEmergTelefono(String contactoEmergTelefono) throws IllegalArgumentException {
        if (contactoEmergTelefono == null || contactoEmergTelefono.trim().isEmpty()) {
            this.contactoEmergTelefono = "Desconocido";
        }

        if (!contactoEmergTelefono.trim().matches("^\\+(?:\\[0-9] ?){6,14}\\[0-9]$")) {
            throw new IllegalArgumentException(
                    "El numero de telefono del contacto de emergencia no tiene el formato correcto");
        }

        this.contactoEmergTelefono = contactoEmergTelefono.trim();
    }

    // ===============================
    // GRUPO SANGUINEO
    // ===============================

    public GrupoSanguineo getGrupoSanguineo() {
        return grupoSanguineo;
    }

    public void setGrupoSanguineo(GrupoSanguineo grupoSanguineo) throws IllegalArgumentException {
        if (grupoSanguineo == null) {
            this.grupoSanguineo = GrupoSanguineo.DESCONOCIDO;
        }

        this.grupoSanguineo = grupoSanguineo;
    }

    // ===============================
    // ALERGIAS
    // ===============================

    public String getAlergias() {
        return alergias;
    }

    public void setAlergias(String alergias) throws IllegalArgumentException {
        if (alergias == null) {
            this.alergias = "Sin alergias conocidas";
        }

        if (alergias.length() >= 255) {
            throw new IllegalArgumentException("Las alergias no pueden superar los 255 caracteres");
        }

        this.alergias = alergias.trim();
    }

    // ===============================
    // TITULACION ACTUAL
    // ===============================

    public String getTitulacionActual() {
        return titulacionActual;
    }

    public void setTitulacionActual(String titulacionActual) throws IllegalArgumentException {
        if (titulacionActual == null) {
            this.titulacionActual = "Deconocida";
        }

        if (titulacionActual.trim().length() >= 255) {
            throw new IllegalArgumentException("La titulación actual no puede superar los 255 caracteres");
        }

        this.titulacionActual = titulacionActual.trim();
    }

    // ===============================
    // ORGANIZACION
    // ===============================

    public String getOrganizacion() {
        return organizacion;
    }

    public void setOrganizacion(String organizacion) throws IllegalArgumentException {
        if (organizacion == null) {
            this.organizacion = "Desconocida";
        }

        if (organizacion.trim().length() > 255) {
            throw new IllegalArgumentException("La organización no puede superar los 255 caracteres");
        }

        this.organizacion = organizacion.trim();
    }

    // ===============================
    // NUMERO DE INMERSIONES
    // ===============================

    public Integer getNumeroInmersiones() {
        return numeroInmersiones;
    }

    public void setNumeroInmersiones(Integer numeroInmersiones) throws IllegalArgumentException {
        if (numeroInmersiones == null) {
            this.numeroInmersiones = 0;
        }

        if (numeroInmersiones < 0) {
            throw new IllegalArgumentException("El número de inmersiones no puede ser negativo");
        }

        this.numeroInmersiones = numeroInmersiones;
    }

    // ====================================================================================
    // OVERRIDE
    // ====================================================================================

    // ===============================
    // EQUALS
    // ===============================

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Buceador other = (Buceador) obj;
        return this.DNI.equalsIgnoreCase(other.DNI);
    }

    // ===============================
    // TO STRING
    // ===============================

    @Override
    public String toString() {
        return String.format(
                "ID: %s - DNI: %s - NOMBRE: %s - APELLIDOS: %s - FECHA ALTA: %s",
                this.id, this.DNI, this.nombre, this.apellidos, this.fechaAlta);
    }

    // ===============================
    // COMPARE TO
    // ===============================

    @Override
    public int compareTo(Buceador o) {
        return this.id.compareTo(o.id);
    }

}
