import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AuthHttpInterceptor, AuthModule } from '@auth0/auth0-angular';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NotebookListComponent } from './component/notebook-list/notebook-list.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { LayoutModule } from '@angular/cdk/layout';
import { MatButtonModule } from '@angular/material/button';
import { MatListModule } from '@angular/material/list';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatCardModule } from '@angular/material/card';
import { MatMenuModule } from '@angular/material/menu';
import { SidebarComponent } from './component/sidebar/sidebar.component';
import { NotebookDisplayComponent } from './component/notebook-display/notebook-display.component';
import { NoteDisplayComponent } from './component/note-display/note-display.component';
import { HomeComponent } from './component/home/home.component';
import { StoreModule } from '@ngrx/store';
import { NotebookReducer } from './reducers/NotebookReducer';
import { NoteReducer } from './reducers/NoteReducer';
import { rootReducer } from './reducers/RootReducer';

@NgModule({
  declarations: [
    AppComponent,
    NotebookListComponent,
    SidebarComponent,
    NotebookDisplayComponent,
    NoteDisplayComponent,
    HomeComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    // Import the module into the application, with configuration
    AuthModule.forRoot({
      domain: 'aclement.au.auth0.com',
      clientId: '1UGelaR8Dsky7ohnQG3VHIzFMnKos6l7',
      // Request this audience at user authentication time
      audience: 'https://aclement.au.auth0.com/api/v2/',

      // Request this scope at user authentication time
      scope: 'read:current_user',

      // Specify configuration for the interceptor              
      httpInterceptor: {
        allowedList: [
          {
            // Match any request that starts 'https://YOUR_DOMAIN/api/v2/' (note the asterisk)
            uri: '/gw/api/*',
            tokenOptions: {
              // The attached token should target this audience
              audience: 'https://aclement.au.auth0.com/api/v2/',

              // The attached token should have these scopes
              scope: 'read:current_user'
            }
          }
        ]
      }
    }),
    BrowserAnimationsModule,
    MatSidenavModule,
    MatToolbarModule,
    MatIconModule,
    LayoutModule,
    MatButtonModule,
    MatListModule,
    MatGridListModule,
    MatCardModule,
    MatMenuModule,
    StoreModule.forRoot(rootReducer, {}),

  ],
  providers: [{ provide: HTTP_INTERCEPTORS, useClass: AuthHttpInterceptor, multi: true },],
  bootstrap: [AppComponent]
})
export class AppModule { }
