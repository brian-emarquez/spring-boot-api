import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Cursos } from './cursos/cursos';

//@Component({
// imports: [RouterOutlet],
// selector: 'app-root',
// styleUrl: './app.css',
// templateUrl: './app.html',
//)
//xport class App {
// protected readonly title = signal('cursos-front');
//

@Component({
  selector: 'app-root',
  imports: [Cursos],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {}