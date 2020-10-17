import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AuthHttpInterceptor, AuthModule } from '@auth0/auth0-angular';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NotebookListComponent } from './component/notebook-list/notebook-list.component';


@NgModule({
  declarations: [
    AppComponent,
    NotebookListComponent
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
  ],
  providers: [{ provide: HTTP_INTERCEPTORS, useClass: AuthHttpInterceptor, multi: true },],
  bootstrap: [AppComponent]
})
export class AppModule { }
