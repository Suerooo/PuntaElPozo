CREATE DATABASE IF NOT EXISTS punta_el_pozo
  DEFAULT CHARACTER SET utf8mb4
  DEFAULT COLLATE utf8mb4_unicode_ci;

USE punta_el_pozo;

CREATE TABLE IF NOT EXISTS buceadores (
  id INT AUTO_INCREMENT PRIMARY KEY,
  dni CHAR(9) NOT NULL UNIQUE,
  nombre VARCHAR(50) NOT NULL,
  apellidos VARCHAR(100) NOT NULL,
  email VARCHAR(254) NOT NULL DEFAULT '',
  telefono VARCHAR(255) NOT NULL DEFAULT '',
  fechaNacimiento DATE,
  fechaAlta DATE NOT NULL DEFAULT (CURRENT_DATE),
  fechaUltimoReconocimiento DATE,
  fechaCaducidadSeguro DATE,
  companiaSeguro VARCHAR(100) NOT NULL DEFAULT '',
  contactoEmergNombre VARCHAR(50) NOT NULL DEFAULT '',
  contactoEmergTelefono VARCHAR(255) NOT NULL DEFAULT '',
  grupoSanguineo ENUM(
    'A_POS',
    'A_NEG',
    'B_POS',
    'B_NEG',
    'AB_POS',
    'AB_NEG',
    'O_POS',
    'O_NEG'
  ) DEFAULT NULL,
  alergias VARCHAR(254) NOT NULL DEFAULT '',
  titulacionActual VARCHAR(254) NOT NULL DEFAULT '',
  organizacion VARCHAR(255) NOT NULL DEFAULT '',
  numeroInmersiones INT NOT NULL DEFAULT 0
) DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
