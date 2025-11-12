import { Component } from '@angular/core';
@Component({
  selector: 'app-root',
  standalone: false,
  // Removed standalone: true. This component is now managed by AppModule.
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'adaptive-learning-platform';
}
