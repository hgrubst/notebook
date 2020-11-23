import { Component, Injectable, OnInit } from '@angular/core';
import { ActivatedRoute, ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot } from '@angular/router';
import { AuthService } from '@auth0/auth0-angular';
import { Observable } from 'rxjs';
import { take } from 'rxjs/operators';
import { NotebookSearchRequest } from 'src/app/model/NotebookSearchRequest';
import { NoteService } from 'src/app/service/note.service';


@Injectable({
  providedIn: 'root'
})
export class NotebookListResolve implements Resolve<any[]> {

  constructor(public noteService: NoteService, public auth: AuthService, private router: Router) {

  }

  async resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Promise<any> {
    return this.auth.user$.pipe(take(1)).subscribe(async user => {
      let searchRequest = new NotebookSearchRequest();
      searchRequest.userEmail = user.email
      let notebooks = await this.noteService.searchNotebooks(searchRequest);

      if (state.url.endsWith('/notebook') && notebooks.numberOfElements > 0) {
        console.log('redirecting to notebook', notebooks.content[0].id)
        //redirect to the first notebook directly here
        this.router.navigate(['/notebook', notebooks.content[0].id])
      }

      return notebooks;
    });
  }
}


@Component({
  selector: 'app-notebook-list',
  templateUrl: './notebook-list.component.html',
  styleUrls: ['./notebook-list.component.css']
})
export class NotebookListComponent implements OnInit {
  notebooks: any[];

  constructor(public route: ActivatedRoute) { }

  ngOnInit(): void {

    this.notebooks = this.route.snapshot.data.notebooks;

  }

}
