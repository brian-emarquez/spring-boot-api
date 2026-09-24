package com.example.cursos;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Curso2Repository extends JpaRepository<Curso2, Long> {
    List<Curso2> findByActivoTrue();
}