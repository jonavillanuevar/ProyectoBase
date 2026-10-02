package com.ejemplo.libros.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ejemplo.libros.entity.Libro;
import com.ejemplo.libros.repository.LibroRepository;

/**
 * SERVICE = capa donde colocamos la lógica de la aplicación.
 *
 * FLUJO NORMAL:
 * Bruno / navegador
 *      -> Controller
 *      -> Service
 *      -> Repository
 *      -> PostgreSQL
 *
 * Después la respuesta vuelve en sentido contrario.
 *
 * Aunque algunos métodos parezcan demasiado simples, quiero que mantengas esta capa
 * porque necesitas acostumbrarte a la arquitectura Controller-Service-Repository.
 */
@Service
public class LibroService {

    private final LibroRepository libroRepository;

    /**
     * INYECCIÓN POR CONSTRUCTOR.
     *
     * Spring crea automáticamente una implementación de LibroRepository
     * y nos la entrega aquí.
     *
     * No hacemos: new LibroRepository()
     */
    public LibroService(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

    /**
     * READ ALL
     *
     * JpaRepository.findAll() -> SELECT de todos los libros.
     */
    public List<Libro> listarTodos() {
        return libroRepository.findAll();
    }

    /**
     * READ ONE
     *
     * findById NO devuelve directamente Libro.
     * Devuelve Optional<Libro> porque el registro podría existir o no.
     *
     * Optional puede entenderse como una "caja":
     * - puede contener un Libro
     * - puede estar vacía
     */
    public Optional<Libro> buscarPorId(Integer id) {
        return libroRepository.findById(id);
    }

    /**
     * CREATE / UPDATE
     *
     * save() sirve para las dos cosas:
     * - si el objeto no tiene un id existente -> INSERT
     * - si el objeto corresponde a un registro existente -> UPDATE
     */
    public Libro guardar(Libro libro) {
        return libroRepository.save(libro);
    }

    /**
     * existsById() es útil cuando SOLO queremos saber si existe,
     * sin necesidad de recuperar el objeto completo.
     */
    public boolean existePorId(Integer id) {
        return libroRepository.existsById(id);
    }

    /**
     * DELETE
     */
    public void eliminarPorId(Integer id) {
        libroRepository.deleteById(id);
    }

    /**
     * count() devuelve la cantidad total de filas.
     */
    public long contar() {
        return libroRepository.count();
    }

}
