package PuntaElPozo.Model;

import java.io.Serializable;
import java.time.LocalDate;

public class Buceador implements Serializable, Comparable<Buceador> {
    private Integer id;
    private String dni;
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

        setDni(dni);
        setNombre(nombre);
        setApellidos(apellidos);

    }

    public Buceador(String dni) throws IllegalArgumentException {

        setDni(dni);

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

        this.id = id;
    }

    // ===============================
    // DNI
    // ===============================

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        // ^ inicio de la cadena
        // \\d{8} exactamente 8 dígitos del 0 9
        // [A-Z] una letra mayúscula de la A a la Z
        // $ final de la cadena.
        if (!dni.trim().toUpperCase().matches("^\\d{8}[A-Z]$")) {
            throw new IllegalArgumentException("Este dni no es valido");
        }

        this.dni = dni;
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
        email = limpiarTextoVacio(email);

        if (email.isBlank()) {
            this.email = "";
            return;
        }

        if (email.length() >= 255) {
            throw new IllegalArgumentException("El email no puede superar los 255 caracteres");
        }

        // ^ inicio de la cadena
        // [a-zA-Z0-9._%+-]+ uno o más caracteres antes de la @
        // @ símbolo arroba obligatorio
        // [a-zA-Z0-9.-]+ uno o más caracteres del dominio
        // \\. punto literal obligatorio antes de la extensión
        // [a-zA-Z]{2,} extensión del dominio con 2 o más letras
        // $ final de la cadena
        if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            throw new IllegalArgumentException("El email no tiene el formato correcto");
        }

        this.email = email;
    }

    // ===============================
    // TELEFONO
    // ===============================

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) throws IllegalArgumentException {
        telefono = limpiarTextoVacio(telefono);

        if (telefono.isBlank()) {
            this.telefono = "";
            return;
        }

        // ^ inicio de la cadena
        // \\+? símbolo + opcional al inicio
        // [0-9\\s\\-] caracteres permitidos: números, espacios y guiones
        // {9,20} entre 9 y 20 caracteres permitidos
        // $ final de la cadena
        if (!telefono.matches("^\\+?[0-9\\s\\-]{9,20}$")) {
            throw new IllegalArgumentException("El numero de telefono no tiene el formato correcto");
        }

        this.telefono = telefono;
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
        companiaSeguro = limpiarTextoVacio(companiaSeguro);

        if (companiaSeguro.isBlank()) {
            this.companiaSeguro = "";
            return;
        }

        if (companiaSeguro.length() > 100) {
            throw new IllegalArgumentException("La compañía de seguro no puede superar los 255 caracteres");
        }

        this.companiaSeguro = companiaSeguro;
    }

    // ===============================
    // NOMBRE CONTACTO DE EMERGENCIA
    // ===============================

    public String getContactoEmergNombre() {
        return contactoEmergNombre;
    }

    public void setContactoEmergNombre(String contactoEmergNombre) throws IllegalArgumentException {
        contactoEmergNombre = limpiarTextoVacio(contactoEmergNombre);

        if (contactoEmergNombre.isBlank()) {
            this.contactoEmergNombre = "";
            return;
        }

        if (contactoEmergNombre.length() < 2 || contactoEmergNombre.length() > 50) {
            throw new IllegalArgumentException(
                    "El nombre del contacto de emergencia no puede ser menos de 2 caracteres ni mayor que 50");
        }

        this.contactoEmergNombre = contactoEmergNombre;
    }

    // ===============================
    // TELEFONO CONTACTO DE EMERGENCIA
    // ===============================

    public String getContactoEmergTelefono() {
        return contactoEmergTelefono;
    }

    public void setContactoEmergTelefono(String contactoEmergTelefono) throws IllegalArgumentException {
        contactoEmergTelefono = limpiarTextoVacio(contactoEmergTelefono);

        if (contactoEmergTelefono.isBlank()) {
            this.contactoEmergTelefono = "";
            return;
        }

        // ^ inicio de la cadena
        // \\+? símbolo + opcional al inicio
        // [0-9\\s\\-] caracteres permitidos: números, espacios y guiones
        // {9,20} entre 9 y 20 caracteres permitidos
        // $ final de la cadena
        if (!contactoEmergTelefono.matches("^\\+?[0-9\\s\\-]{9,20}$")) {
            throw new IllegalArgumentException(
                    "El numero de telefono del contacto de emergencia no tiene el formato correcto");
        }

        this.contactoEmergTelefono = contactoEmergTelefono;
    }

    // ===============================
    // GRUPO SANGUINEO
    // ===============================

    public GrupoSanguineo getGrupoSanguineo() {
        return grupoSanguineo;
    }

    public void setGrupoSanguineo(GrupoSanguineo grupoSanguineo) {
        this.grupoSanguineo = grupoSanguineo;
    }

    // ===============================
    // ALERGIAS
    // ===============================

    public String getAlergias() {
        return alergias;
    }

    public void setAlergias(String alergias) throws IllegalArgumentException {
        alergias = limpiarTextoVacio(alergias);

        if (alergias.isBlank()) {
            this.alergias = "";
            return;
        }

        if (alergias.length() >= 255) {
            throw new IllegalArgumentException("Las alergias no pueden superar los 255 caracteres");
        }

        this.alergias = alergias;
    }

    // ===============================
    // TITULACION ACTUAL
    // ===============================

    public String getTitulacionActual() {
        return titulacionActual;
    }

    public void setTitulacionActual(String titulacionActual) throws IllegalArgumentException {
        titulacionActual = limpiarTextoVacio(titulacionActual);

        if (titulacionActual.isBlank()) {
            this.titulacionActual = "";
            return;
        }

        if (titulacionActual.length() >= 255) {
            throw new IllegalArgumentException("La titulación actual no puede superar los 255 caracteres");
        }

        this.titulacionActual = titulacionActual;
    }

    // ===============================
    // ORGANIZACION
    // ===============================

    public String getOrganizacion() {
        return organizacion;
    }

    public void setOrganizacion(String organizacion) throws IllegalArgumentException {
        organizacion = limpiarTextoVacio(organizacion);

        if (organizacion.isBlank()) {
            this.organizacion = "";
            return;
        }

        if (organizacion.length() > 255) {
            throw new IllegalArgumentException("La organización no puede superar los 255 caracteres");
        }

        this.organizacion = organizacion;
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
            return;
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
        return this.dni.equalsIgnoreCase(other.dni);
    }

    // ===============================
    // TO STRING
    // ===============================

    @Override
    public String toString() {
        return String.format(
                "ID: %s - DNI: %s - NOMBRE: %s - APELLIDOS: %s - FECHA ALTA: %s",
                this.id, this.dni, this.nombre, this.apellidos, this.fechaAlta);
    }

    // ===============================
    // COMPARE TO
    // ===============================

    @Override
    public int compareTo(Buceador o) {
        return this.id.compareTo(o.id);
    }

    private String limpiarTextoVacio(String valor) {
        if (valor == null) {
            return "";
        }

        String texto = valor.trim();

        if (texto.equalsIgnoreCase("Desconocido")
                || texto.equalsIgnoreCase("Desconocida")
                || texto.equalsIgnoreCase("Deconocida")
                || texto.equalsIgnoreCase("Sin alergias conocidas")) {
            return "";
        }

        return texto;
    }

}
