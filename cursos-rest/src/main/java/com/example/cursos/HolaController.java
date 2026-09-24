package com.example.cursos;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
public class HolaController {

    @GetMapping("/hola")
    public String hola() {
        return "¡Hola Mundo desde Spring Boot!";
    }

    @GetMapping("/curso")
    public Curso curso() {
        return new Curso(1L, "Spring Boot desde cero", "Brian Marquez", 20);
    }
    @GetMapping("/cursos")
    public List<Curso> cursos() {
        return List.of(
                new Curso(1L, "Spring Boot desde cero", "Ana Torres", 20),
                new Curso(2L, "Java Intermedio", "Luis Pérez", 15),
                new Curso(3L, "Bases de Datos con JPA", "María Gómez", 18)
        );
    }

    @GetMapping("/info")
    public Map<String, Object> info() {
        return Map.of("mensaje", "Hola", "version", 1, "activo", true);
    }
}