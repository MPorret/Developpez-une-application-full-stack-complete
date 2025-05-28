import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { TopicsComponent } from './pages/topics/topics.component';
import { RegisterComponent } from './pages/register/register.component';
import { UnauthGuard } from './guards/unauth.guard';
import { AuthGuard } from './guards/auth.guard';
import { LayoutwithheaderComponent } from './layouts/layoutwithheader/layoutwithheader.component';
import { LayoutComponent } from './layouts/layout/layout.component';

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
  { path: 'topics', 
    canActivate: [AuthGuard],
    component: LayoutwithheaderComponent,
    children: [
      { path: '', component: TopicsComponent }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
