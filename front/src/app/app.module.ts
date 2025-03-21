import { NgModule } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './pages/home/home.component';
import { TopicsComponent } from './pages/topics/topics.component';
import { PostsComponent } from './pages/posts/posts.component';
import { PostDetailsComponent } from './pages/postDetails/postDetails.component';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { NavbarComponent } from './navbar/navbar.component';
import { AuthModule } from './auth/auth.module';
import { JwtInterceptor } from './interceptors/jwt.interceptor';
import { FormsModule } from '@angular/forms';
import { UserDetailsComponent } from './pages/userDetails/userDetails.component';
import { ReactiveFormsModule } from '@angular/forms';
import { CreatePostComponent } from './pages/createPost/createPost.component';
@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    TopicsComponent,
    PostsComponent,
    NavbarComponent,
    PostDetailsComponent,
    UserDetailsComponent,
    CreatePostComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatButtonModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    // AuthModule,
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
