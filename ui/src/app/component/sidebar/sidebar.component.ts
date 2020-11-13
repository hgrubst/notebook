import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { AuthService } from '@auth0/auth0-angular';
import { Store } from '@ngrx/store';
import { Observable, of, Subject } from 'rxjs';
import { distinctUntilChanged, filter, map, mapTo, pairwise, switchMap, take, takeUntil } from 'rxjs/operators';
import { NotebookActions } from 'src/app/actions/NotebookActions';
import { Notebook } from 'src/app/model/Notebook';
import { NotebookSearchRequest } from 'src/app/model/NotebookSearchRequest';
import { PagedSearchResponse } from 'src/app/model/PagedSearchResponse';
import { NotelloState } from 'src/app/reducers/RootReducer';
import { NoteService } from 'src/app/service/note.service';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent implements OnInit, OnDestroy {

  unsubscribe$: Subject<any>;

  notebooks$: Observable<Notebook[]>

  notebooks: Notebook[]

  constructor(private auth: AuthService, private noteService: NoteService, private notebookActions: NotebookActions, private store: Store<NotelloState>) { }

  ngOnInit(): void {
    //fetch notebooks as soon as you are authenticated
    this.auth.isAuthenticated$.pipe(
      take(1),
      filter(authenticated => authenticated),
      switchMap(() => this.auth.user$)
    ).subscribe(async (user) => {

      let searchRequest = new NotebookSearchRequest();
      searchRequest.userEmail = user.email
      this.notebookActions.searchNotebooks(searchRequest);

      // let notebooks = await this.noteService.searchNotebooks(searchRequest);
      // this.notebooks = notebooks.content;
    })

    this.notebooks$ = this.store.select(state => state.notebook).pipe(
      filter(notebookState => !!notebookState.notebooks),
      map((notebookState) => notebookState.notebooks.content)
    )

  }

  ngOnDestroy(): void {
    this.unsubscribe$.next();
  }

  async onRefreshClicked(){
    this.notebookActions.searchNotebooks()
  }

}
