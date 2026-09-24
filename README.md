# Spring Boot API – Cursos

API REST de ejemplo construida con **Spring Boot 4** y **Java 21** que expone información de cursos. El proyecto está preparado para conectarse a **SQL Server** mediante Spring Data JPA.

## Tecnologías

- Java 21
- Spring Boot 4.1.1
  - Spring Web MVC
  - Spring Data JPA
  - Bean Validation
- SQL Server (driver `mssql-jdbc`)
- Maven (incluye Maven Wrapper)

## Estructura del proyecto

```
spring-boot-api/
└── cursos/
    ├── pom.xml
    ├── mvnw / mvnw.cmd
    └── src/
        ├── main/
        │   ├── java/com/example/cursos/
        │   │   ├── CursosApplication.java   # Clase principal
        │   │   ├── HolaController.java      # Endpoints REST
        │   │   └── Curso.java               # Modelo (record)
        │   └── resources/
        │       └── application.properties   # Configuración
        └── test/java/com/example/cursos/
            └── CursosApplicationTests.java
```

## Requisitos previos

- JDK 21 o superior
- SQL Server en ejecución (por defecto en `localhost:1433`) con una base de datos llamada `demo`

## Configuración

La conexión a la base de datos se define en `cursos/src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=demo;encrypt=true;trustServerCertificate=true
spring.datasource.username=sa
spring.datasource.password=<tu_contraseña>
spring.jpa.hibernate.ddl-auto=none
spring.jpa.show-sql=true
```

> **Recomendación:** evita subir contraseñas al repositorio. Puedes sobrescribir estos valores con variables de entorno, por ejemplo `SPRING_DATASOURCE_USERNAME` y `SPRING_DATASOURCE_PASSWORD`.

## Ejecución

Desde la carpeta `cursos`:

```bash
# Linux / macOS
./mvnw spring-boot:run

# Windows
mvnw.cmd spring-boot:run
```

La aplicación se inicia en `http://localhost:8080`.

## Endpoints

| Método | Ruta      | Descripción                                   |
|--------|-----------|-----------------------------------------------|
| GET    | `/hola`   | Devuelve un saludo en texto plano             |
| GET    | `/curso`  | Devuelve un curso de ejemplo                  |
| GET    | `/cursos` | Devuelve una lista de cursos                  |
| GET    | `/info`   | Devuelve información básica de la API en JSON |

### Ejemplos

```bash
curl http://localhost:8080/hola
# ¡Hola Mundo desde Spring Boot!

curl http://localhost:8080/curso
```

```json
{
  "id": 1,
  "nombre": "Spring Boot desde cero",
  "instructor": "Brian Marquez",
  "duracionHoras": 20
}
```

```bash
curl http://localhost:8080/cursos
```

```json
[
  { "id": 1, "nombre": "Spring Boot desde cero", "instructor": "Ana Torres", "duracionHoras": 20 },
  { "id": 2, "nombre": "Java Intermedio", "instructor": "Luis Pérez", "duracionHoras": 15 },
  { "id": 3, "nombre": "Bases de Datos con JPA", "instructor": "María Gómez", "duracionHoras": 18 }
]
```

## Proyecto `cursos-db` (CRUD con SQL Server)

CRUD completo en `http://localhost:8080/api/cursos2` probado con Postman:

| Método   | URL                                         |
|----------|---------------------------------------------|
| `GET`    | <http://localhost:8080/api/cursos2>         |
| `GET`    | <http://localhost:8080/api/cursos2/activos> |
| `GET`    | <http://localhost:8080/api/cursos2/1>       |
| `POST`   | <http://localhost:8080/api/cursos2>         |
| `PUT`    | <http://localhost:8080/api/cursos2/1>       |
| `DELETE` | <http://localhost:8080/api/cursos2/1>       |

Detalle con bodies JSON, curl y respuestas: [cursos-db/readme.md](cursos-db/readme.md)

## Tests

```bash
cd cursos
./mvnw test
```

## Autor

Brian Marquez – [@brian-emarquez](https://github.com/brian-emarquez)
