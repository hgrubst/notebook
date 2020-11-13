import { Component, Injectable, OnInit } from '@angular/core';
import { ActivatedRoute, ActivatedRouteSnapshot, Resolve, RouterStateSnapshot } from '@angular/router';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { NoteActions } from 'src/app/actions/NoteActions';
import { NotebookActions } from 'src/app/actions/NotebookActions';
import { Note } from 'src/app/model/Note';
import { NoteUpdateRequest } from 'src/app/model/NoteUpdateRequest';
import { NotelloState } from 'src/app/reducers/RootReducer';
import { NoteService } from 'src/app/service/note.service';

@Injectable({
  providedIn: 'root'
})
export class NotebookResolve implements Resolve<Promise<any>> {

  constructor(public noteService: NoteService, public notebookActions: NotebookActions) {


  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    return this.notebookActions.getNotebook(route.params['id']);
  }
}


@Component({
  selector: 'app-notebook-display',
  templateUrl: './notebook-display.component.html',
  styleUrls: ['./notebook-display.component.css']
})
export class NotebookDisplayComponent implements OnInit {
  notes$: Observable<Note[]>;

  constructor(private route: ActivatedRoute, private store: Store<NotelloState>, private noteActions: NoteActions) { }

  ngOnInit(): void {
    this.notes$ = this.store.select(store => store.notebook.selectedNotebook.notes)
  }

  async onEditClicked(note: Note) {
    const noteUpdateRequest = new NoteUpdateRequest();
    noteUpdateRequest.content = note.content + Date.now().toString();
    noteUpdateRequest.position = note.position;
    
    await this.noteActions.updateNote(note.id, noteUpdateRequest)
  }

  async onDeleteClicked(noteId: string) {
    await this.noteActions.deleteNote(noteId)
  }
}
