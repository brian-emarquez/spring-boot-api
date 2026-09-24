# cursos-rest – Primeros endpoints REST con Spring Boot

Proyecto introductorio: endpoints `GET` que devuelven **texto** y **JSON** a partir de datos fijos en el código (todavía **sin base de datos**).

## Resumen rápido (chuleta)

Base URL: `http://localhost:8080`

| Acción (Postman) | Método | URL                                  | Qué devuelve                         |
|------------------|--------|--------------------------------------|--------------------------------------|
| hola             | `GET`  | <http://localhost:8080/hola>         | Texto plano (un saludo)              |
| curso            | `GET`  | <http://localhost:8080/curso>        | JSON con **un** curso (objeto)       |
| cursos           | `GET`  | <http://localhost:8080/cursos>       | JSON con **una lista** de cursos     |
| info             | `GET`  | <http://localhost:8080/info>         | JSON armado con un `Map`             |

> Todos son `GET`: solo leen datos, no llevan body. Responden siempre `200 OK`.

## Cómo levantarlo

Desde la carpeta `cursos-rest`:

```bash
# Windows
mvnw.cmd spring-boot:run

# Linux / macOS
./mvnw spring-boot:run
```

La API queda en `http://localhost:8080`.

> El `pom.xml` incluye Spring Data JPA y el driver de SQL Server, así que al arrancar intenta conectarse a la base `demo` configurada en [application.properties](src/main/resources/application.properties). Si SQL Server no está corriendo, la app puede fallar al iniciar aunque estos endpoints no usen la base.

## Endpoints en detalle

### 1. hola – devuelve texto

```http
GET http://localhost:8080/hola
```

```bash
curl http://localhost:8080/hola
```

Respuesta (`text/plain`):

```text
¡Hola Mundo desde Spring Boot!
```

**Qué aprendes:** si el método devuelve un `String`, Spring lo manda tal cual como texto.

### 2. curso – devuelve un objeto JSON

```http
GET http://localhost:8080/curso
```

```bash
curl http://localhost:8080/curso
```

Respuesta (`application/json`):

```json
{
  "id": 1,
  "nombre": "Spring Boot desde cero",
  "instructor": "Brian Marquez",
  "duracionHoras": 20
}
```

**Qué aprendes:** si el método devuelve un objeto (aquí el `record Curso`), Spring lo convierte automáticamente a JSON. Los nombres de los campos del record son las claves del JSON.

### 3. cursos – devuelve una lista JSON

```http
GET http://localhost:8080/cursos
```

```bash
curl http://localhost:8080/cursos
```

Respuesta:

```json
[
  { "id": 1, "nombre": "Spring Boot desde cero", "instructor": "Ana Torres", "duracionHoras": 20 },
  { "id": 2, "nombre": "Java Intermedio", "instructor": "Luis Pérez", "duracionHoras": 15 },
  { "id": 3, "nombre": "Bases de Datos con JPA", "instructor": "María Gómez", "duracionHoras": 18 }
]
```

**Qué aprendes:** un `List<Curso>` se convierte en un arreglo JSON `[ ... ]`.

### 4. info – devuelve un JSON desde un Map

```http
GET http://localhost:8080/info
```

```bash
curl http://localhost:8080/info
```

Respuesta:

```json
{
  "mensaje": "Hola",
  "version": 1,
  "activo": true
}
```

**Qué aprendes:** un `Map<String, Object>` también se convierte en JSON, útil para respuestas rápidas sin crear una clase. El orden de las claves puede variar porque `Map.of` no garantiza orden.

## Estructura del código

| Archivo | Qué hace |
| ------- | -------- |
| [CursosApplication.java](src/main/java/com/example/cursos/CursosApplication.java) | Clase principal: arranca Spring Boot |
| [HolaController.java](src/main/java/com/example/cursos/HolaController.java) | `@RestController` con los 4 endpoints `@GetMapping` |
| [Curso.java](src/main/java/com/example/cursos/Curso.java) | `record` con `id`, `nombre`, `instructor`, `duracionHoras` |
| [application.properties](src/main/resources/application.properties) | Configuración (nombre de la app y conexión a SQL Server) |

## Anotaciones clave

| Anotación | Para qué sirve |
| --------- | -------------- |
| `@SpringBootApplication` | Marca la clase principal y activa la autoconfiguración |
| `@RestController` | La clase responde peticiones HTTP y devuelve datos (texto/JSON), no vistas |
| `@GetMapping("/ruta")` | Asocia un método a una petición `GET` en esa ruta |

## Siguiente paso

El CRUD completo (`GET`, `POST`, `PUT`, `DELETE`) contra SQL Server está en [../cursos-db/readme.md](../cursos-db/readme.md).
