import { Injectable } from '@angular/core';
import { createReducer } from '@ngrx/store';
import { Note } from '../model/Note';
import { PagedSearchResponse } from '../model/PagedSearchResponse';

@Injectable({ providedIn: 'root' })
export class NoteState {

    notes: PagedSearchResponse<Note>;

}

export const NoteReducer = createReducer(new NoteState(),




)