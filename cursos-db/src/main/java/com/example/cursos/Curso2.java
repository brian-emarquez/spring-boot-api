package com.example.cursos;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "cursos")
public class Curso2 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String instructor;

    @Column(name = "duracion_horas")
    private Integer duracionHoras;

    private BigDecimal precio;
    private Boolean activo;

    public Curso2() {}

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getInstructor() { return instructor; }
    public Integer getDuracionHoras() { return duracionHoras; }
    public BigDecimal getPrecio() { return precio; }
    public Boolean getActivo() { return activo; }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setInstructor(String instructor) { this.instructor = instructor; }
    public void setDuracionHoras(Integer duracionHoras) { this.duracionHoras = duracionHoras; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }
    public void setActivo(Boolean activo) { this.activo = activo; }
}

