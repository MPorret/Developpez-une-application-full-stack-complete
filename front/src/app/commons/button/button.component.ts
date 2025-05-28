import { Component, Input } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { Router } from '@angular/router';

@Component({
  selector: 'app-button',
  templateUrl: './button.component.html',
  styleUrl: './button.component.scss',
  imports: [MatButtonModule]
})
export class ButtonComponent {
  @Input()
  onClick: () => void = () => {};

  constructor(private router: Router){}
}
