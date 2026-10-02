package com.ejemplo.libros.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * ENTIDAD = representación Java de una tabla de la base de datos.
 *
 * Esta clase representa la tabla "libros".
 *
 * Por ahora NO usamos DTOs.
 * Eso significa que el JSON que entra y sale del controlador se convierte
 * directamente en objetos Libro.
 */
@Entity
@Table(name = "libros")
public class Libro {

    /**
     * @Id indica que este atributo es la PRIMARY KEY.
     *
     * @GeneratedValue con IDENTITY indica que PostgreSQL generará el id.
     * Por eso al registrar un libro NO debes enviar el id en el JSON.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * nullable = false indica que la columna no debería aceptar null.
     * Por ahora no usamos @NotBlank porque todavía no estamos viendo validaciones.
     */
    @Column(nullable = false, length = 120)
    private String nombre;

    /**
     * Mantenemos el atributo year porque se parece al proyecto que venías practicando.
     */
    @Column(nullable = false)
    private Integer year;

    /**
     * JPA necesita un constructor vacío.
     */
    public Libro() {
    }

    public Libro(Integer id, String nombre, Integer year) {
        this.id = id;
        this.nombre = nombre;
        this.year = year;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

}
