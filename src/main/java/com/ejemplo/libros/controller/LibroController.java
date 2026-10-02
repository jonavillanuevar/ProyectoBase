package com.ejemplo.libros.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ejemplo.libros.entity.Libro;
import com.ejemplo.libros.service.LibroService;

/**
 * CONTROLLER = recibe las peticiones HTTP.
 *
 * URL BASE:
 * http://localhost:9080/api/libros
 *
 * Aquí debes aprender a relacionar:
 *
 * GET     -> consultar
 * POST    -> registrar
 * PUT     -> actualizar un recurso completo
 * DELETE  -> eliminar
 *
 * Todavía NO usamos DTOs ni PATCH para no mezclar demasiadas cosas.
 */
@RestController
@RequestMapping("/api/libros")
public class LibroController {

    private final LibroService libroService;

    public LibroController(LibroService libroService) {
        this.libroService = libroService;
    }

    // ================================================================
    // 1. GET - LISTAR TODOS
    // ================================================================

    /**
     * GET http://localhost:9080/api/libros
     *
     * FLUJO:
     * Controller -> libroService.listarTodos()
     *            -> libroRepository.findAll()
     *            -> base de datos
     *            -> List<Libro>
     */
    @GetMapping
    public List<Libro> listarTodos() {
        return libroService.listarTodos();
    }

    // ================================================================
    // 2. GET - BUSCAR POR ID
    // ================================================================

    /**
     * GET http://localhost:9080/api/libros/1
     *
     * @PathVariable toma el número que viene en la URL.
     * Si la URL termina en /5, entonces id vale 5.
     *
     * Aquí practicamos Optional.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Libro> buscarPorId(@PathVariable Integer id) {

        Optional<Libro> libroOptional = libroService.buscarPorId(id);

        // Si Optional está vacío significa que no existe ese id.
        if (libroOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Si llegamos aquí, sabemos que sí existe.
        Libro libroEncontrado = libroOptional.get();

        return ResponseEntity.ok(libroEncontrado);
    }

    // ================================================================
    // 3. POST - REGISTRAR
    // ================================================================

    /**
     * POST http://localhost:9080/api/libros
     *
     * JSON DE EJEMPLO:
     * {
     *   "nombre": "Clean Code",
     *   "year": 2008
     * }
     *
     * @RequestBody convierte el JSON recibido en un objeto Libro.
     *
     * Como el id es autogenerado, NO enviamos id.
     */
    @PostMapping
    public Libro registrar(@RequestBody Libro libro) {
        return libroService.guardar(libro);
    }

    // ================================================================
    // 4. PUT - ACTUALIZAR
    // ================================================================

    /**
     * PUT http://localhost:9080/api/libros/1
     *
     * JSON DE EJEMPLO:
     * {
     *   "nombre": "Clean Code - Edicion actualizada",
     *   "year": 2009
     * }
     *
     * ESTA ES LA LÓGICA QUE MÁS TE INTERESA DOMINAR.
     *
     * PIÉNSALA EN 5 PASOS:
     *
     * 1. Recibo el id por la URL.
     * 2. Busco ese id en la base de datos.
     * 3. Si NO existe -> retorno 404 y termino el método.
     * 4. Si existe -> saco el Libro del Optional y cambio sus atributos.
     * 5. Guardo el objeto modificado con save().
     *
     * IMPORTANTE SOBRE EL ALCANCE DE VARIABLES:
     * libroEncontrado se declara DESPUÉS del if.
     * Podemos hacer eso porque si el libro no existe, el método ya terminó
     * con "return ResponseEntity.notFound().build();".
     *
     * Por lo tanto, al continuar después del if, estamos seguros de que
     * Optional sí contiene un Libro.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Libro> actualizar(@PathVariable Integer id,
                                             @RequestBody Libro libroRecibido) {

        // PASO 1: buscar el registro actual en la base de datos.
        Optional<Libro> libroOptional = libroService.buscarPorId(id);

        // PASO 2: resolver el caso "no existe" primero.
        if (libroOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // PASO 3: como ya comprobamos que existe, ahora podemos hacer get().
        Libro libroEncontrado = libroOptional.get();

        // PASO 4: copiamos los datos nuevos SOBRE el objeto encontrado.
        // El id NO se cambia.
        libroEncontrado.setNombre(libroRecibido.getNombre());
        libroEncontrado.setYear(libroRecibido.getYear());

        // PASO 5: save() detecta el id existente y hace UPDATE.
        Libro libroActualizado = libroService.guardar(libroEncontrado);

        return ResponseEntity.ok(libroActualizado);
    }

    // ================================================================
    // 5. DELETE - ELIMINAR
    // ================================================================

    /**
     * DELETE http://localhost:9080/api/libros/1
     *
     * Primero usamos existsById().
     * Aquí no necesitamos recuperar todo el libro; solo queremos saber
     * si existe antes de intentar eliminarlo.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {

        if (!libroService.existePorId(id)) {
            return ResponseEntity.notFound().build();
        }

        libroService.eliminarPorId(id);

        // noContent() = HTTP 204.
        // Significa: operación correcta, pero no hay un cuerpo JSON que devolver.
        return ResponseEntity.noContent().build();
    }

    // ================================================================
    // 6. GET - CONTAR REGISTROS
    // ================================================================

    /**
     * GET http://localhost:9080/api/libros/contar
     *
     * Este endpoint está aquí principalmente para que practiques count().
     */
    @GetMapping("/contar")
    public long contar() {
        return libroService.contar();
    }

    // ================================================================
    // 7. GET - COMPROBAR SI EXISTE UN ID
    // ================================================================

    /**
     * GET http://localhost:9080/api/libros/1/existe
     *
     * Devuelve true o false.
     * Lo incluimos para practicar existsById().
     */
    @GetMapping("/{id}/existe")
    public boolean existe(@PathVariable Integer id) {
        return libroService.existePorId(id);
    }

    /*
     * ================================================================
     * MUY IMPORTANTE SOBRE PUT Y JSON INCOMPLETO
     * ================================================================
     *
     * Con este PUT estamos diciendo que el cliente envía el recurso completo.
     *
     * Si haces esto:
     * {
     *   "nombre": "Pepaaaaaa"
     * }
     *
     * libroRecibido.getYear() será null.
     * Luego hacemos:
     * libroEncontrado.setYear(null);
     *
     * Por eso, MIENTRAS PRACTICAS PUT, envía el JSON completo:
     * {
     *   "nombre": "Pepaaaaaa",
     *   "year": 2020
     * }
     *
     * Más adelante aprenderemos PATCH, validaciones y DTOs.
     */

}
