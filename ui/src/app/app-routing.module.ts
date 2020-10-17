import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { NotebookListComponent, NotebookListResolve } from './component/notebook-list/notebook-list.component';

const routes: Routes = [

  {
    path: 'notebook',
    children: [
      {
        path: 'list',
        component: NotebookListComponent,
        resolve: {
          notebooks: NotebookListResolve
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
