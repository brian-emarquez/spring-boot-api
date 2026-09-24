package com.example.cursos.repository;

import java.util.List;

import com.example.cursos.Curso2;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Curso2Repository extends JpaRepository<Curso2, Long> {
    List<Curso2> findByActivoTrue();
}