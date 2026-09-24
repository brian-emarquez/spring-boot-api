import { Component, OnInit, inject, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { DecimalPipe } from '@angular/common';
import { Curso } from '../curso.model';
import { CursoService } from '../curso.service';

@Component({
  selector: 'app-cursos',
  imports: [FormsModule, DecimalPipe],
  templateUrl: './cursos.html',
  styleUrl: './cursos.css'
})
export class Cursos implements OnInit {
  private service = inject(CursoService);

  cursos = signal<Curso[]>([]);
  editandoId = signal<number | null>(null);
  error = signal('');
  form: Curso = this.formVacio();

  ngOnInit() {
    this.cargar();
  }

  cargar() {
    this.service.listar().subscribe({
      next: data => { this.cursos.set(data); this.error.set(''); },
      error: () => this.error.set('No se pudo conectar con la API. ¿Está corriendo Spring Boot?')
    });
  }

  guardar() {
    const id = this.editandoId();
    const peticion = id
      ? this.service.actualizar(id, this.form)
      : this.service.crear(this.form);

    peticion.subscribe({
      next: () => { this.cancelar(); this.cargar(); },
      error: () => this.error.set('Error al guardar el curso')
    });
  }

  editar(curso: Curso) {
    this.editandoId.set(curso.id!);
    this.form = { ...curso };
  }

  eliminar(curso: Curso) {
    if (!confirm(`¿Eliminar el curso "${curso.nombre}"?`)) return;
    this.service.eliminar(curso.id!).subscribe({
      next: () => this.cargar(),
      error: () => this.error.set('Error al eliminar el curso')
    });
  }

  cancelar() {
    this.editandoId.set(null);
    this.form = this.formVacio();
  }

  private formVacio(): Curso {
    return { nombre: '', instructor: '', duracionHoras: 1, precio: 0, activo: true };
  }
}