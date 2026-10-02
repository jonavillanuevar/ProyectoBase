package com.ejemplo.libros;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * CLASE PRINCIPAL.
 *
 * FLUJO AL EJECUTAR:
 * 1. Ejecutamos esta clase como Spring Boot App.
 * 2. Spring arranca el servidor web.
 * 3. Spring busca nuestras clases @RestController, @Service, @Repository y @Entity.
 * 4. Lee application.properties para conectarse a PostgreSQL.
 * 5. Hibernate revisa la entidad Libro y puede crear/actualizar la tabla libros.
 */
@SpringBootApplication
public class ApiLibrosBasicaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiLibrosBasicaApplication.class, args);
    }

}
