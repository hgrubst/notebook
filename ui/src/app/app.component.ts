import { DOCUMENT } from '@angular/common';
import { Component, Inject, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AuthService } from '@auth0/auth0-angular';
import { Store } from '@ngrx/store';
import { Observable, Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { Notebook } from './model/Notebook';
import { NotelloState } from './reducers/RootReducer';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  unsubscribe$ = new Subject();

  notebooks: Notebook[];
  spinner$: Observable<boolean>;

  constructor(
    public route: ActivatedRoute,
    @Inject(DOCUMENT)
    public document: Document,
    public store: Store<NotelloState>,
    public auth: AuthService) {

  }

  ngOnInit(): void {
    this.spinner$ = this.store.select(state => state.spinner)
  }

  ngOnDestroy() {
    this.unsubscribe$.next();
  }

  onToggleSidenav() {

  }
}
