# Punta El Pozo

Aplicación JavaFX para gestionar buceadores del club.

## Requisitos

- JDK 17.
- Maven o NetBeans con soporte para proyectos Maven.
- MySQL o MariaDB en local.

## Preparar la base de datos

Ejecuta el script:

```bash
sql/punta_el_pozo.sql
```

El script crea la base `punta_el_pozo` y la tabla `buceadores` si no existen.

## Configuración de MySQL

Por defecto la aplicación usa:

- URL: `jdbc:mysql://localhost:3306/punta_el_pozo`
- Usuario: `root`
- Contraseña: vacía

## Ejecutar

```bash
mvn clean javafx:run
```

También se puede abrir el proyecto en NetBeans y ejecutar la clase principal `PuntaElPozo.App`.

Si MySQL no está disponible, la aplicación usa `buceadores.dat` como copia local. En una instalación limpia ese fichero se crea automáticamente al guardar datos.
