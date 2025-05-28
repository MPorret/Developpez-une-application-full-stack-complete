import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HTTP_INTERCEPTORS, provideHttpClient, withInterceptorsFromDi } from '@angular/common/http';
import { JwtInterceptor } from './interceptors/jwt.interceptor';
import { LayoutwithheaderComponent } from './layouts/layoutwithheader/layoutwithheader.component';
import { LayoutComponent } from './layouts/layout/layout.component';
import { SubscribeButtonComponent } from './commons/subscribe-button/subscribe-button.component';
import { MenuComponent } from "./commons/menu/menu.component";
import { RegisterComponent } from './pages/register/register.component';
import { ReactiveFormsModule } from '@angular/forms';
import { BackButtonComponent } from './commons/back-button/back-button.component';
import { HomeComponent } from './pages/home/home.component';
import { CardComponent } from './commons/card/card.component';
import { ButtonComponent } from './commons/button/button.component';

@NgModule({
  declarations: [
    AppComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MenuComponent,
    ReactiveFormsModule,
    LayoutwithheaderComponent,
    LayoutComponent,
    HomeComponent,
    RegisterComponent,
    SubscribeButtonComponent,
    ButtonComponent,
    CardComponent,
    BackButtonComponent
],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
    provideHttpClient(
      withInterceptorsFromDi()
    )
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
