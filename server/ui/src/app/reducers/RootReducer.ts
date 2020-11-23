import { Action, ActionReducerMap } from '@ngrx/store';
import { NotebookReducer, NotebookState } from './NotebookReducer';
import { NoteReducer, NoteState } from './NoteReducer';
import { SpinnerReducer } from './SpinnerReducer';

export interface NotelloState {
    notebook: NotebookState;
    note: NoteState;
    spinner : boolean
}

export const rootReducer: ActionReducerMap<NotelloState, Action> = {
    notebook: NotebookReducer,
    note: NoteReducer,
    spinner : SpinnerReducer
}