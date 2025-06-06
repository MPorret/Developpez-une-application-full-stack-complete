import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { TopicsComponent } from './pages/topics/topics.component';
import { RegisterComponent } from './pages/auth/register/register.component';
import { UnauthGuard } from './guards/unauth.guard';
import { AuthGuard } from './guards/auth.guard';
import { LayoutwithheaderComponent } from './layouts/layoutwithheader/layoutwithheader.component';
import { LayoutComponent } from './layouts/layout/layout.component';
import { LoginComponent } from './pages/auth/login/login.component';
import { ProfileComponent } from './pages/profile/profile.component';
import { SubjectsComponent } from './pages/subjects/subjects.component';
import { CreateComponent } from './pages/subjects/create/create.component';
import { DetailsComponent } from './pages/subjects/details/details.component';
import { LayoutForAuthComponent } from './layouts/layoutforauth/layoutforauth.component';

const routes: Routes = [
  { path: '', 
    canActivate: [UnauthGuard],
    component: LayoutComponent,
    children: [
      { path: '', component: HomeComponent }
    ]
  },
  { path: 'auth', 
    canActivate: [UnauthGuard],
    component: LayoutForAuthComponent,
    children: [
      { path: 'register', component: RegisterComponent },
      { path: 'login', component: LoginComponent }
    ]
  },
  { path: 'articles', 
    canActivate: [AuthGuard],
    component: LayoutwithheaderComponent,
    children: [
      { path: '', component: SubjectsComponent },
      { path: 'create', component: CreateComponent },
      { path: ':id', component: DetailsComponent }
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
