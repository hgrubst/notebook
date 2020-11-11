import { Action, ActionReducerMap } from '@ngrx/store';
import { NotebookReducer, NotebookState } from './NotebookReducer';
import { NoteReducer, NoteState } from './NoteReducer';

export interface NotelloState {
    notebook: NotebookState,
    note: NoteState
}

export const rootReducer: ActionReducerMap<NotelloState, Action> = {
    notebook: NotebookReducer,
    note: NoteReducer
}