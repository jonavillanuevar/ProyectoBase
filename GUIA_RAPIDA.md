# API Libros Básica - Guía de estudio

Este proyecto está hecho para estudiar el CRUD fundamental de Spring Boot sin DTOs, sin genéricos y sin lógica innecesaria.

## Antes de ejecutar

1. En PostgreSQL crea una base de datos llamada `api_libros`.
2. Abre `src/main/resources/application.properties`.
3. Coloca tu usuario y contraseña de PostgreSQL.
4. Importa el proyecto en STS/Eclipse como **Existing Maven Project**.
5. Ejecuta `ApiLibrosBasicaApplication` como **Spring Boot App**.

## Orden recomendado para memorizar

No memorices todo el código de golpe. Memoriza esta relación:

| Operación | HTTP | JpaRepository |
|---|---|---|
| Registrar | POST | `save()` |
| Listar | GET | `findAll()` |
| Buscar por id | GET | `findById()` |
| Actualizar | PUT | `findById()` + setters + `save()` |
| Eliminar | DELETE | `existsById()` + `deleteById()` |
| Saber si existe | GET | `existsById()` |
| Contar | GET | `count()` |

Los cinco que debes dominar primero son:

`save()` · `findAll()` · `findById()` · `existsById()` · `deleteById()`

`count()` es sencillo y también conviene reconocerlo.

## Pruebas en Bruno

### POST
`POST http://localhost:9080/api/libros`

```json
{
  "nombre": "Clean Code",
  "year": 2008
}
```

### GET todos
`GET http://localhost:9080/api/libros`

### GET uno
`GET http://localhost:9080/api/libros/1`

### PUT
`PUT http://localhost:9080/api/libros/1`

```json
{
  "nombre": "Clean Code actualizado",
  "year": 2009
}
```

### DELETE
`DELETE http://localhost:9080/api/libros/1`

## La lógica del PUT en una frase

**buscar -> comprobar -> obtener -> modificar -> guardar**

Esa secuencia es mucho más importante que memorizar cada línea de código.
