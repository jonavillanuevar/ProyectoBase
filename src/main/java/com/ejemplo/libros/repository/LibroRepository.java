package com.ejemplo.libros.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ejemplo.libros.entity.Libro;

/**
 * REPOSITORY = capa que habla con la base de datos.
 *
 * Al extender JpaRepository<Libro, Integer>, Spring Data JPA ya nos entrega
 * muchos métodos CRUD. No tenemos que escribir SQL para las operaciones básicas.
 *
 * MÉTODOS IMPORTANTES QUE DEBES RECONOCER DE MEMORIA:
 *
 * save(libro)
 *      Guarda un objeto nuevo o actualiza uno existente.
 *
 * findAll()
 *      Devuelve todos los registros.
 *
 * findById(id)
 *      Busca por id y devuelve Optional<Libro>.
 *
 * existsById(id)
 *      Devuelve true o false según exista ese id.
 *
 * deleteById(id)
 *      Elimina usando el id.
 *
 * count()
 *      Devuelve cuántos registros existen.
 *
 * IMPORTANTE:
 * Esta interfaz queda vacía porque JpaRepository ya implementa esos métodos.
 */
public interface LibroRepository extends JpaRepository<Libro, Integer> {

}
