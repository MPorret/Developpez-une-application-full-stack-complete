import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../auth/services/auth.service';
import { MatSidenav } from '@angular/material/sidenav';
import { SessionService } from '../services/session.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {

  @ViewChild('sidenav') sidenav!: MatSidenav;

  constructor(public router: Router, private sessionService: SessionService) {}

  ngOnInit(): void {
  }

  logout() {
    this.sessionService.logOut()
    this.router.navigate(['/']);
    this.sidenav.close();
  }

  openSidenav() {
    this.sidenav.open();
  }

  closeSidenav() {
    this.sidenav.close();
  }

}
