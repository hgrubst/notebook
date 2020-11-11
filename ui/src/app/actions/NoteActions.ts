import { Injectable } from '@angular/core';
import { createAction, props, Store } from '@ngrx/store';
import { Note } from '../model/Note';
import { NotebookSearchRequest } from '../model/NotebookSearchRequest';
import { NoteSearchRequest } from '../model/NoteSearchRequest';
import { PagedSearchResponse } from '../model/PagedSearchResponse';
import { NotelloState } from '../reducers/RootReducer';
import { NoteService } from '../service/note.service';

@Injectable({ providedIn: 'root' })
export class NoteActions {



    static searchNoteRequest = createAction('[Note] searchRequest', props<{ noteSearchRequest }>())
    static searchNoteSuccess = createAction('[Note] searchSuccess', props<{ noteSearchRequest: NoteSearchRequest, notes: PagedSearchResponse<Note> }>())
    static searchNoteFailure = createAction('[Note] searchFailure', props<{ noteSearchRequest }>())


    constructor(public store: Store<NotelloState>, public noteService: NoteService) {

    }

    async searchNotes(noteSearchRequest: NoteSearchRequest) {
        try {
            this.store.dispatch(NoteActions.searchNoteRequest({ noteSearchRequest }));

            let notes = await this.noteService.searchNotes(noteSearchRequest)
            this.store.dispatch(NoteActions.searchNoteSuccess({ noteSearchRequest: noteSearchRequest, notes }));
            return notes;
        } catch (error) {
            this.store.dispatch(NoteActions.searchNoteFailure({ noteSearchRequest }));
            throw error;
        }
    }

}