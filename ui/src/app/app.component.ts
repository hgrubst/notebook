import { DOCUMENT } from '@angular/common';
import { Component, Inject, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AuthService } from '@auth0/auth0-angular';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { Notebook } from './model/Notebook';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  
  unsubscribe$ = new Subject();

  notebooks: Notebook[];

  constructor(
    public route : ActivatedRoute, 
    @Inject(DOCUMENT)
    public document: Document,
    public auth: AuthService) {

  }

  ngOnInit(): void {
    //Called after the constructor, initializing input properties, and the first call to ngOnChanges.
    //Add 'implements OnInit' to the class.
    
    this.route.params.pipe(takeUntil(this.unsubscribe$)).subscribe((params)=>{
      console.log('----------------------------')
      this.notebooks = params['notebook']
    })

  }

  ngOnDestroy(){
    this.unsubscribe$.next();
  }
  
  onToggleSidenav(){
    
  }
}
