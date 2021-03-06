import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import * as qs from 'qs';
import { Note } from '../model/Note';
import { Notebook } from '../model/Notebook';
import { NotebookSearchRequest } from '../model/NotebookSearchRequest';
import { NoteSearchRequest } from '../model/NoteSearchRequest';
import { NoteUpdateRequest } from '../model/NoteUpdateRequest';
import { PagedSearchResponse } from '../model/PagedSearchResponse';

@Injectable({
  providedIn: 'root'
})
export class NoteService {

  constructor(private http: HttpClient) { }

  public async searchNotebooks(notebookSearchRequest: NotebookSearchRequest) {
    return this.http.get<PagedSearchResponse<Notebook>>(`/gw/note/notebook?${qs.stringify(notebookSearchRequest)}`).toPromise()
  }

  public async getNotebook(id: string) {
    return this.http.get<Notebook>(`/gw/note/notebook/${id}`).toPromise()
  }

  public async searchNotes(noteSearchRequest: NoteSearchRequest) {
    return this.http.get<PagedSearchResponse<Note>>(`/gw/note/note?${qs.stringify(noteSearchRequest)}`).toPromise()
  }

  public async updateNote(noteId: string, noteUpdateRequest: NoteUpdateRequest) {
    return this.http.put<Note>(`/gw/note/note/${noteId}`, noteUpdateRequest).toPromise()
  }

  public async deleteNote(noteId: string) {
    return this.http.delete<Note>(`/gw/note/note/${noteId}`).toPromise()
  }
}
