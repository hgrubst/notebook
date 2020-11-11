import { Injectable } from '@angular/core';
import { createReducer, on } from '@ngrx/store';
import { Action } from 'rxjs/internal/scheduler/Action';
import { NotebookActions } from '../actions/NotebookActions';
import { Notebook } from '../model/Notebook';
import { PagedSearchResponse } from '../model/PagedSearchResponse';

@Injectable({ providedIn: 'root' })
export class NotebookState {
    notebooks: PagedSearchResponse<Notebook>;
    selectedNotebook: Notebook;
}

export const NotebookReducer = createReducer(new NotebookState(),

    on(NotebookActions.searchNotebookSuccess, (state, action) => ({ ...state, notebooks: action.notebooks })),
    
    on(NotebookActions.getNotebookSuccess, (state, action) => ({ ...state, selectedNotebook: action.notebook }))


)