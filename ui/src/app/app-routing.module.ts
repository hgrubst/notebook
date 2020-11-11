import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AuthGuard } from '@auth0/auth0-angular';
import { HomeComponent } from './component/home/home.component';
import { NotebookDisplayComponent, NotebookNotesResolve } from './component/notebook-display/notebook-display.component';
import { NotebookListComponent, NotebookListResolve } from './component/notebook-list/notebook-list.component';

const routes: Routes = [


  //we resolve notebooks on homecomponent but use notebooks in the sidebar. Would be good to have the routes in a way to reflect this
  {
    path: '',
    pathMatch: 'full',
    component: HomeComponent,
  },
  {
    path: 'notebook',
    canActivate: [AuthGuard],
    children: [
      {
        path: ':id',
        component: NotebookDisplayComponent,
        resolve: {
          notebook: NotebookNotesResolve
        }
      }
    ]
  }


];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
