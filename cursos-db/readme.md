# cursos-db – CRUD de cursos con SQL Server

API REST con **Spring Boot + Spring Data JPA** que hace un CRUD completo sobre la tabla `dbo.cursos` de la base de datos `demo` en SQL Server.

## Resumen rápido (chuleta)

Base URL: `http://localhost:8080/api/cursos2`

| Acción (Postman)        | Método   | URL                                          | Body | Respuesta OK        |
|-------------------------|----------|----------------------------------------------|------|---------------------|
| cursos2 - Listar        | `GET`    | <http://localhost:8080/api/cursos2>            | –    | `200` lista         |
| cursos2 - Activos       | `GET`    | <http://localhost:8080/api/cursos2/activos>    | –    | `200` lista         |
| cursos2 - Obtener uno   | `GET`    | <http://localhost:8080/api/cursos2/1>          | –    | `200` / `404`       |
| Cursos2 - Crear         | `POST`   | <http://localhost:8080/api/cursos2>            | JSON | `201 Created`       |
| Cursos2 - Actualizar    | `PUT`    | <http://localhost:8080/api/cursos2/1>          | JSON | `200` / `404`       |
| Cursos2 - Borrar        | `DELETE` | <http://localhost:8080/api/cursos2/1>          | –    | `204` / `404`       |

> Cambia el `1` por el `id` del curso que quieras consultar, actualizar o borrar.
> Para **POST** y **PUT**, en Postman: pestaña **Body → raw → JSON**.

## Cómo levantarlo

1. Tener SQL Server corriendo en `localhost:1433` con la base `demo`.
2. Crear la tabla con el script [db/demo - cursos.sql](db/demo%20-%20cursos.sql).
3. Revisar usuario/contraseña en [application.properties](src/main/resources/application.properties).
4. Ejecutar desde la carpeta `cursos-db`:

```bash
# Windows
mvnw.cmd spring-boot:run

# Linux / macOS
./mvnw spring-boot:run
```

La API queda en `http://localhost:8080`.

## Endpoints en detalle

### 1. Listar todos – `GET`

```http
GET http://localhost:8080/api/cursos2
```

```bash
curl http://localhost:8080/api/cursos2
```

Respuesta `200 OK`:

```json
[
  {
    "id": 1,
    "nombre": "Spring Boot desde cero",
    "instructor": "Brian Marquez",
    "duracionHoras": 20,
    "precio": 49.99,
    "activo": true
  }
]
```

### 2. Listar solo activos – `GET`

```http
GET http://localhost:8080/api/cursos2/activos
```

```bash
curl http://localhost:8080/api/cursos2/activos
```

Devuelve solo los cursos con `activo = true`.

### 3. Obtener uno por id – `GET`

```http
GET http://localhost:8080/api/cursos2/{id}
```

```bash
curl http://localhost:8080/api/cursos2/1
```

- `200 OK` con el curso si existe.
- `404 Not Found` si no existe.

### 4. Crear – `POST`

```http
POST http://localhost:8080/api/cursos2
Content-Type: application/json
```

Body:

```json
{
  "nombre": "Spring Boot desde cero",
  "instructor": "Brian Marquez",
  "duracionHoras": 20,
  "precio": 49.99,
  "activo": true
}
```

```bash
curl -X POST http://localhost:8080/api/cursos2 \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Spring Boot desde cero","instructor":"Brian Marquez","duracionHoras":20,"precio":49.99,"activo":true}'
```

- `201 Created` con el curso creado (incluye el `id` generado).
- Header `Location: /api/cursos2/{id}`.
- **No envíes `id`**: lo genera la base de datos (`IDENTITY`).
- Si no envías `activo`, se guarda como `true`.

### 5. Actualizar – `PUT`

```http
PUT http://localhost:8080/api/cursos2/{id}
Content-Type: application/json
```

Body (se envían **todos** los campos, PUT reemplaza el curso completo):

```json
{
  "nombre": "Spring Boot avanzado",
  "instructor": "Brian Marquez",
  "duracionHoras": 30,
  "precio": 79.99,
  "activo": true
}
```

```bash
curl -X PUT http://localhost:8080/api/cursos2/1 \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Spring Boot avanzado","instructor":"Brian Marquez","duracionHoras":30,"precio":79.99,"activo":true}'
```

- `200 OK` con el curso actualizado.
- `404 Not Found` si el `id` no existe.

### 6. Borrar – `DELETE`

```http
DELETE http://localhost:8080/api/cursos2/{id}
```

```bash
curl -X DELETE http://localhost:8080/api/cursos2/1
```

- `204 No Content` si se borró.
- `404 Not Found` si el `id` no existe.

## Recordatorio: qué hace cada método HTTP

| Método   | Para qué sirve           | ¿Lleva body? |
|----------|--------------------------|--------------|
| `GET`    | Leer / consultar datos   | No           |
| `POST`   | Crear un registro nuevo  | Sí (JSON)    |
| `PUT`    | Reemplazar / actualizar  | Sí (JSON)    |
| `DELETE` | Eliminar un registro     | No           |

## Estructura del código

| Archivo | Qué hace |
| ------- | -------- |
| [Curso2.java](src/main/java/com/example/cursos/Curso2.java) | Entidad JPA mapeada a la tabla `cursos` |
| [Curso2Repository.java](src/main/java/com/example/cursos/Curso2Repository.java) | Acceso a datos (`JpaRepository`) + `findByActivoTrue()` |
| [Curso2Controller.java](src/main/java/com/example/cursos/Curso2Controller.java) | Endpoints REST en `/api/cursos2` |
| [main.java](src/main/java/com/example/cursos/main.java) | Clase principal de Spring Boot |
| [db/demo - cursos.sql](db/demo%20-%20cursos.sql) | Script para crear la tabla en SQL Server |

## Tabla `dbo.cursos`

| Columna          | Tipo            | Campo JSON      |
|------------------|-----------------|-----------------|
| `id`             | `bigint` (auto) | `id`            |
| `nombre`         | `nvarchar(150)` | `nombre`        |
| `instructor`     | `nvarchar(100)` | `instructor`    |
| `duracion_horas` | `int`           | `duracionHoras` |
| `precio`         | `decimal(10,2)` | `precio`        |
| `activo`         | `bit` (def. 1)  | `activo`        |

> Todas las columnas son `NOT NULL`: al crear o actualizar envía siempre `nombre`, `instructor`, `duracionHoras` y `precio`, o SQL Server devolverá error (500).
