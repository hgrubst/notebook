import { Injectable } from '@angular/core';
import { createAction, props, Store } from '@ngrx/store';
import { Note } from '../model/Note';
import { NotebookSearchRequest } from '../model/NotebookSearchRequest';
import { NoteSearchRequest } from '../model/NoteSearchRequest';
import { NoteUpdateRequest } from '../model/NoteUpdateRequest';
import { PagedSearchResponse } from '../model/PagedSearchResponse';
import { NotelloState } from '../reducers/RootReducer';
import { NoteService } from '../service/note.service';

@Injectable({ providedIn: 'root' })
export class NoteActions {



    static searchNoteRequest = createAction('[Note] searchRequest', props<{ noteSearchRequest }>())
    static searchNoteSuccess = createAction('[Note] searchSuccess', props<{ noteSearchRequest: NoteSearchRequest, notes: PagedSearchResponse<Note> }>())
    static searchNoteFailure = createAction('[Note] searchFailure', props<{ noteSearchRequest }>())

    static updateNoteRequest = createAction('[Note] updateRequest', props<{ noteId: string, noteUpdateRequest }>())
    static updateNoteSuccess = createAction('[Note] updateSuccess', props<{ noteId: string, noteUpdateRequest: NoteUpdateRequest, note: Note }>())
    static updateNoteFailure = createAction('[Note] updateFailure', props<{ noteId: string, noteUpdateRequest }>())

    static deleteNoteRequest = createAction('[Note] deleteRequest', props<{ noteId: string }>())
    static deleteNoteSuccess = createAction('[Note] deleteNoteSuccess', props<{ noteId: string, note: Note }>())
    static deleteNoteFailure = createAction('[Note] deleteFailure', props<{ noteId: string }>())


    constructor(public store: Store<NotelloState>, public noteService: NoteService) {

    }

    async searchNotes(noteSearchRequest: NoteSearchRequest) {
        try {
            this.store.dispatch(NoteActions.searchNoteRequest({ noteSearchRequest }));

            let notes = await this.noteService.searchNotes(noteSearchRequest)
            this.store.dispatch(NoteActions.searchNoteSuccess({ noteSearchRequest, notes }));
            return notes;
        } catch (error) {
            this.store.dispatch(NoteActions.searchNoteFailure({ noteSearchRequest }));
            throw error;
        }
    }

    async updateNote(noteId: string, noteUpdateRequest: NoteUpdateRequest) {
        try {
            this.store.dispatch(NoteActions.updateNoteRequest({ noteId, noteUpdateRequest }));

            let note = await this.noteService.updateNote(noteId, noteUpdateRequest)
            this.store.dispatch(NoteActions.updateNoteSuccess({ noteId, noteUpdateRequest, note }));
            return note;
        } catch (error) {
            this.store.dispatch(NoteActions.updateNoteFailure({ noteId, noteUpdateRequest }));
            throw error;
        }
    }

    async deleteNote(noteId: string) {
        try {
            this.store.dispatch(NoteActions.deleteNoteRequest({ noteId }));

            let note = await this.noteService.deleteNote(noteId)
            this.store.dispatch(NoteActions.deleteNoteSuccess({ noteId, note }));
            return note;
        } catch (error) {
            this.store.dispatch(NoteActions.deleteNoteFailure({ noteId }));
            throw error;
        }
    }

}