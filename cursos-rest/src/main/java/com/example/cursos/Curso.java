package com.example.cursos;

public record Curso(
        Long id,
        String nombre,
        String instructor,
        int duracionHoras
        ) {
}