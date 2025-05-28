import { Component } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-back-button',
  templateUrl: './back-button.component.html',
  styleUrl: './back-button.component.scss',
  imports: [MatIconModule]
})
export class BackButtonComponent {
  public goBack() {
    window.history.back();
  }
}
