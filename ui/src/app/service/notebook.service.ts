import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class NotebookService {

  constructor(private http: HttpClient) { }

  public async searchNotebooks() {
    return await this.http.get<any[]>("/gw/api/notebook").toPromise()
  }
}
