CREATE DATABASE IF NOT EXISTS punta_el_pozo;

USE punta_el_pozo;

CREATE TABLE IF NOT EXISTS buceadores (
  id INT AUTO_INCREMENT PRIMARY KEY,
  dni CHAR(9) NOT NULL UNIQUE,
  nombre VARCHAR(50) NOT NULL,
  apellidos VARCHAR(100) NOT NULL,
  email VARCHAR(254) NOT NULL DEFAULT 'Desconocido',
  telefono VARCHAR(255) NOT NULL DEFAULT 'Desconocido',
  fechaNacimiento DATE,
  fechaAlta DATE NOT NULL DEFAULT (CURRENT_DATE),
  fechaUltimoReconocimiento DATE,
  fechaCaducidadSeguro DATE,
  companiaSeguro VARCHAR(100) NOT NULL DEFAULT 'Desconocido',
  contactoEmergNombre VARCHAR(50) NOT NULL DEFAULT 'Desconocido',
  contactoEmergTelefono VARCHAR(255) NOT NULL DEFAULT 'Desconocido',
  grupoSanguineo ENUM(
    'A_POS',
    'A_NEG',
    'B_POS',
    'B_NEG',
    'AB_POS',
    'AB_NEG',
    'O_POS',
    'O_NEG',
    'DESCONOCIDO'
  ) NOT NULL DEFAULT 'DESCONOCIDO',
  alergias VARCHAR(254) NOT NULL DEFAULT 'Sin alergias conocidas',
  titulacionActual VARCHAR(254) NOT NULL DEFAULT 'Deconocida',
  organizacion VARCHAR(255) NOT NULL DEFAULT 'Desconocida',
  numeroInmersiones INT NOT NULL DEFAULT 0
);
