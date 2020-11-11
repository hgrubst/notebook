import { Injectable } from '@angular/core';
import { createAction, props, Store } from '@ngrx/store';
import { Notebook } from '../model/Notebook';
import { NotebookSearchRequest } from '../model/NotebookSearchRequest';
import { PagedSearchResponse } from '../model/PagedSearchResponse';
import { NotelloState } from '../reducers/RootReducer';
import { NoteService } from '../service/note.service';

@Injectable({ providedIn: 'root' })
export class NotebookActions {



    static searchNotebookRequest = createAction('[Notebook] searchRequest', props<{ notebookSearchRequest: NotebookSearchRequest }>())
    static searchNotebookSuccess = createAction('[Notebook] searchSuccess', props<{ notebookSearchRequest: NotebookSearchRequest, notebooks: PagedSearchResponse<Notebook> }>())
    static searchNotebookFailure = createAction('[Notebook] searchFailure', props<{ notebookSearchRequest: NotebookSearchRequest }>())

    static getNotebookRequest = createAction('[Notebook] getRequest', props<{ id: string }>())
    static getNotebookSuccess = createAction('[Notebook] getSuccess', props<{ id: string, notebook: Notebook }>())
    static getNotebookFailure = createAction('[Notebook] getFailure', props<{ id: string }>())

    constructor(public store: Store<NotelloState>, public noteService: NoteService) {

    }

    async searchNotebooks(notebookSearchRequest: NotebookSearchRequest) {
        try {
            this.store.dispatch(NotebookActions.searchNotebookRequest({ notebookSearchRequest }));

            let notebooks = await this.noteService.searchNotebooks(notebookSearchRequest)
            this.store.dispatch(NotebookActions.searchNotebookSuccess({ notebookSearchRequest, notebooks }));
            return notebooks;
        } catch (error) {
            this.store.dispatch(NotebookActions.searchNotebookFailure({ notebookSearchRequest }));
            throw error;
        }
    }

    async getNotebook(id: string) {
        try {
            this.store.dispatch(NotebookActions.getNotebookRequest({ id }));

            let notebook = await this.noteService.getNotebook(id)
            this.store.dispatch(NotebookActions.getNotebookSuccess({ id, notebook }));
            return notebook;
        } catch (error) {
            this.store.dispatch(NotebookActions.getNotebookFailure({ id }));
            throw error;
        }
    }
}