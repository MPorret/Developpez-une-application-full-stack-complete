import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { TopicsComponent } from './pages/topics/topics.component';
import { RegisterComponent } from './pages/register/register.component';
import { UnauthGuard } from './guards/unauth.guard';
import { AuthGuard } from './guards/auth.guard';
import { LayoutwithheaderComponent } from './layouts/layoutwithheader/layoutwithheader.component';
import { LayoutComponent } from './layouts/layout/layout.component';
import { LoginComponent } from './pages/login/login.component';
import { ProfileComponent } from './pages/profile/profile.component';

const routes: Routes = [
  { path: '', 
    canActivate: [UnauthGuard],
    component: LayoutComponent,
    children: [
      { path: '', component: HomeComponent }
    ]
  },
  { path: 'register', 
    canActivate: [UnauthGuard],
    component: LayoutwithheaderComponent,
    children: [
      { path: '', component: RegisterComponent }
    ]
  },
  { path: 'login', 
    canActivate: [UnauthGuard],
    component: LayoutwithheaderComponent,
    children: [
      { path: '', component: LoginComponent }
    ]
  },
  { path: 'topics', 
    canActivate: [AuthGuard],
    component: LayoutwithheaderComponent,
    children: [
      { path: '', component: TopicsComponent }
    ]
  },
  { path: 'profile', 
    canActivate: [AuthGuard],
    component: LayoutwithheaderComponent,
    children: [
      { path: '', component: ProfileComponent }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
