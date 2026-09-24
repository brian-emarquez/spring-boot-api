# Spring Boot API – Cursos (Back + Front)

Proyecto de práctica para aprender a construir una **API REST con Spring Boot** conectada a **SQL Server**, y consumirla desde un **front en Angular**.

El repositorio está dividido en 3 carpetas, en el orden en que se fueron construyendo:

| Carpeta | Tipo | Qué es | Puerto | Guía |
| --- | --- | --- | --- | --- |
| [cursos-rest](cursos-rest/) | Back (Spring Boot) | Primeros endpoints `GET` con datos fijos, sin base de datos | `8080` | [readme](cursos-rest/readme.md) |
| [cursos-db](cursos-db/) | Back (Spring Boot + JPA) | CRUD completo (`GET`, `POST`, `PUT`, `DELETE`) contra SQL Server | `8080` | [readme](cursos-db/readme.md) |
| [cursos-front](cursos-front/) | Front (Angular) | Pantalla para listar, crear, editar y borrar cursos usando la API de `cursos-db` | `4200` | ver abajo |

> `cursos-rest` y `cursos-db` usan el mismo puerto `8080`: levanta **solo uno a la vez**.

## Cómo se conectan

```text
┌──────────────────────┐     HTTP (JSON)      ┌──────────────────────┐     JPA      ┌──────────────┐
│  cursos-front        │ ───────────────────▶ │  cursos-db           │ ───────────▶ │  SQL Server  │
│  Angular :4200       │ ◀─────────────────── │  Spring Boot :8080   │ ◀─────────── │  demo.cursos │
└──────────────────────┘  /api/cursos2        └──────────────────────┘              └──────────────┘
```

El back permite peticiones desde `http://localhost:4200` gracias a la configuración CORS en [WebConfig.java](cursos-db/src/main/java/com/example/cursos/WebConfig.java).

## Tecnologías

| Back | Front | Base de datos |
| --- | --- | --- |
| Java 21 | Angular 22 | SQL Server |
| Spring Boot 4 (Web MVC, Data JPA, Validation) | TypeScript | Driver `mssql-jdbc` |
| Maven (incluye Maven Wrapper) | `HttpClient` + `FormsModule` + signals | |

## Requisitos previos

- **JDK 21** o superior
- **Node.js** + **npm** (para el front)
- **SQL Server** corriendo en `localhost:1433` con una base llamada `demo`

## Levantar todo (paso a paso)

### 1. Base de datos

Ejecuta el script [cursos-db/db/demo - cursos.sql](cursos-db/db/demo%20-%20cursos.sql) en SQL Server para crear la tabla `dbo.cursos`.

Revisa usuario y contraseña en [cursos-db/src/main/resources/application.properties](cursos-db/src/main/resources/application.properties).

### 2. Back (`cursos-db`)

```bash
cd cursos-db
mvnw.cmd spring-boot:run      # Windows
./mvnw spring-boot:run        # Linux / macOS
```

Prueba rápida: <http://localhost:8080/api/cursos2>

### 3. Front (`cursos-front`)

En otra terminal:

```bash
cd cursos-front
npm install        # solo la primera vez
npm start          # equivale a: ng serve
```

Abre <http://localhost:4200>

## Endpoints (chuleta rápida)

### `cursos-rest` – datos fijos

| Método | URL | Qué devuelve |
| --- | --- | --- |
| `GET` | <http://localhost:8080/hola> | Texto: saludo |
| `GET` | <http://localhost:8080/curso> | JSON: un curso |
| `GET` | <http://localhost:8080/cursos> | JSON: lista de cursos |
| `GET` | <http://localhost:8080/info> | JSON desde un `Map` |

Detalle: [cursos-rest/readme.md](cursos-rest/readme.md)

### `cursos-db` – CRUD con SQL Server

| Método | URL | Acción |
| --- | --- | --- |
| `GET` | <http://localhost:8080/api/cursos2> | Listar todos |
| `GET` | <http://localhost:8080/api/cursos2/activos> | Listar solo activos |
| `GET` | <http://localhost:8080/api/cursos2/1> | Obtener uno por id |
| `POST` | <http://localhost:8080/api/cursos2> | Crear (body JSON) |
| `PUT` | <http://localhost:8080/api/cursos2/1> | Actualizar (body JSON) |
| `DELETE` | <http://localhost:8080/api/cursos2/1> | Borrar |

Body JSON para `POST` / `PUT`:

```json
{
  "nombre": "Spring Boot desde cero",
  "instructor": "Brian Marquez",
  "duracionHoras": 20,
  "precio": 49.99,
  "activo": true
}
```

Detalle con curl y respuestas: [cursos-db/readme.md](cursos-db/readme.md)

## Front (`cursos-front`)

Una sola pantalla, **Gestión de Cursos**, que usa todos los endpoints del CRUD:

| En la pantalla | Llama a |
| --- | --- |
| Al abrir se carga la tabla | `GET /api/cursos2` |
| Formulario → botón **Crear** | `POST /api/cursos2` |
| Botón **Editar** → formulario → **Actualizar** | `PUT /api/cursos2/{id}` |
| Botón **Eliminar** (pide confirmación) | `DELETE /api/cursos2/{id}` |

Si el back no está corriendo, aparece el mensaje *"No se pudo conectar con la API"*.

Archivos principales:

| Archivo | Qué hace |
| --- | --- |
| [curso.model.ts](cursos-front/src/app/curso.model.ts) | Interfaz `Curso` (mismos campos que el JSON de la API) |
| [curso.service.ts](cursos-front/src/app/curso.service.ts) | Llamadas HTTP a la API (`listar`, `obtener`, `crear`, `actualizar`, `eliminar`) |
| [cursos/cursos.ts](cursos-front/src/app/cursos/cursos.ts) | Lógica del componente (formulario, editar, borrar, errores) |
| [cursos/cursos.html](cursos-front/src/app/cursos/cursos.html) | Formulario + tabla de cursos |
| [app.config.ts](cursos-front/src/app/app.config.ts) | Registra `provideHttpClient()` para poder hacer peticiones |

> La URL de la API está en `curso.service.ts` (`http://localhost:8080/api/cursos2`). Si cambias el puerto del back, cámbiala ahí.

## Estructura del repositorio

```text
spring-boot-api/
├── README.md               ← este archivo
├── cursos-rest/            ← Back 1: endpoints GET básicos
│   └── src/main/java/com/example/cursos/
│       ├── CursosApplication.java
│       ├── HolaController.java
│       └── Curso.java
├── cursos-db/              ← Back 2: CRUD con SQL Server
│   ├── db/demo - cursos.sql
│   └── src/main/java/com/example/cursos/
│       ├── main.java
│       ├── Curso2.java
│       ├── Curso2Repository.java
│       ├── Curso2Controller.java
│       └── WebConfig.java  (CORS para el front)
└── cursos-front/           ← Front: Angular
    └── src/app/
        ├── curso.model.ts
        ├── curso.service.ts
        └── cursos/         (componente con formulario + tabla)
```

## Problemas comunes

| Problema | Solución |
| --- | --- |
| El back no arranca | Verifica que SQL Server esté encendido y que usuario/contraseña en `application.properties` sean correctos |
| `Port 8080 already in use` | Tienes otro back corriendo (`cursos-rest` o `cursos-db`). Detén uno |
| El front muestra "No se pudo conectar con la API" | Levanta `cursos-db` primero |
| Error de CORS en la consola del navegador | Revisa que `WebConfig.java` permita `http://localhost:4200` |
| `POST`/`PUT` devuelve 500 | Faltan campos: todas las columnas de la tabla son `NOT NULL` |

## Autor

Brian Marquez – [@brian-emarquez](https://github.com/brian-emarquez)
