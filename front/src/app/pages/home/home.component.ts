import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { ButtonComponent } from 'src/app/commons/button/button.component';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
  imports: [ButtonComponent]
})
export class HomeComponent {
  constructor(private router: Router) {
  }

  public goToRegister(){
    this.router.navigate(["/auth/register"])
  }

  public goToLogin(){
    this.router.navigate(["/auth/login"])
  }
}
