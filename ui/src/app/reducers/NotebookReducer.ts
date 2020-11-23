import { Injectable } from '@angular/core';
import { createReducer, on } from '@ngrx/store';
import { Action } from 'rxjs/internal/scheduler/Action';
import { NoteActions } from '../actions/NoteActions';
import { NotebookActions } from '../actions/NotebookActions';
import { Notebook } from '../model/Notebook';
import { NotebookSearchRequest } from '../model/NotebookSearchRequest';
import { PagedSearchResponse } from '../model/PagedSearchResponse';

@Injectable({ providedIn: 'root' })
export class NotebookState {
    notebookSearchRequest: NotebookSearchRequest
    notebooks: PagedSearchResponse<Notebook>;
    selectedNotebook: Notebook;
}

export const NotebookReducer = createReducer(new NotebookState(),

    on(NotebookActions.searchNotebookRequest, (state, action) => ({ ...state, notebookSearchRequest: action.notebookSearchRequest })),
    on(NotebookActions.searchNotebookSuccess, (state, action) => ({ ...state, notebooks: action.notebooks })),

    on(NotebookActions.getNotebookSuccess, (state, action) => ({ ...state, selectedNotebook: action.notebook })),

    on(NoteActions.updateNoteSuccess, (state, action) => ({
        ...state,
        selectedNotebook: {
            ...state.selectedNotebook,
            notes: state.selectedNotebook.notes.map(note => note.id !== action.note.id ? note : action.note)
        }
    })),

    on(NoteActions.deleteNoteSuccess, (state, action) => ({
        ...state,
        selectedNotebook: {
            ...state.selectedNotebook,
            notes: state.selectedNotebook.notes.filter(note => note.id !== action.noteId)
        }
    }))


)