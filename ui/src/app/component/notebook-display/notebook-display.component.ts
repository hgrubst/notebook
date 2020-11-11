import { Component, Injectable, OnInit } from '@angular/core';
import { ActivatedRoute, ActivatedRouteSnapshot, Resolve, RouterStateSnapshot } from '@angular/router';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { NoteActions } from 'src/app/actions/NoteActions';
import { Note } from 'src/app/model/Note';
import { NoteSearchRequest } from 'src/app/model/NoteSearchRequest';
import { NotelloState } from 'src/app/reducers/RootReducer';
import { NoteService } from 'src/app/service/note.service';

@Injectable({
  providedIn: 'root'
})
export class NotebookNotesResolve implements Resolve<Promise<any>> {

  constructor(public noteService: NoteService, public noteActions: NoteActions) {

  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    const noteSearchRequest = new NoteSearchRequest();
    noteSearchRequest.notebookId = route.params['id'];

    return this.noteActions.searchNotes(noteSearchRequest);
  }
}


@Component({
  selector: 'app-notebook-display',
  templateUrl: './notebook-display.component.html',
  styleUrls: ['./notebook-display.component.css']
})
export class NotebookDisplayComponent implements OnInit {
  notes$: Observable<Note[]>;

  constructor(private route: ActivatedRoute, private store: Store<NotelloState>) { }

  ngOnInit(): void {

    this.notes$ = this.store.select(store => store.note.notes.content)
  }

}
