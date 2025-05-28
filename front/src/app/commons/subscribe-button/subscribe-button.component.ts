import { Component, Input } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { Router } from '@angular/router';

@Component({
  selector: 'app-subscribe-button',
  templateUrl: './subscribe-button.component.html',
  styleUrl: './subscribe-button.component.scss',
  imports: [MatButtonModule]
})
export class SubscribeButtonComponent {
  @Input()
  disabled: boolean = false;
  @Input()
  type?: 'submit';
  @Input()
  onClick: () => void = () => {};

  constructor(private router: Router){}
}
