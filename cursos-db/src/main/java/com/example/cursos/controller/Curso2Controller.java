package com.example.cursos.controller;

import java.net.URI;
import java.util.List;

import com.example.cursos.Curso2;
import com.example.cursos.repository.Curso2Repository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cursos2")
public class Curso2Controller {

    private final Curso2Repository repository;

    public Curso2Controller(Curso2Repository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Curso2> listar() {
        return repository.findAll();
    }

    @GetMapping("/activos")
    public List<Curso2> activos() {
        return repository.findByActivoTrue();
    }

    @PostMapping
    public ResponseEntity<Curso2> crear(@RequestBody Curso2 datos) {
        Curso2 nuevo = new Curso2();
        nuevo.setNombre(datos.getNombre());
        nuevo.setInstructor(datos.getInstructor());
        nuevo.setDuracionHoras(datos.getDuracionHoras());
        nuevo.setPrecio(datos.getPrecio());
        nuevo.setActivo(datos.getActivo() != null ? datos.getActivo() : true);

        Curso2 guardado = repository.save(nuevo);
        return ResponseEntity
                .created(URI.create("/api/cursos2/" + guardado.getId()))
                .body(guardado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Curso2> obtener(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Curso2> actualizar(@PathVariable Long id, @RequestBody Curso2 datos) {
        return repository.findById(id)
                .map(curso -> {
                    curso.setNombre(datos.getNombre());
                    curso.setInstructor(datos.getInstructor());
                    curso.setDuracionHoras(datos.getDuracionHoras());
                    curso.setPrecio(datos.getPrecio());
                    curso.setActivo(datos.getActivo());
                    return ResponseEntity.ok(repository.save(curso));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}