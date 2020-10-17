import { Component, Injectable, OnInit } from '@angular/core';
import { ActivatedRoute, ActivatedRouteSnapshot, Resolve, RouterStateSnapshot } from '@angular/router';
import { NotebookService } from 'src/app/service/notebook.service';


@Injectable({
  providedIn: 'root'
})
export class NotebookListResolve implements Resolve<any[]> {

  constructor(public notebookService: NotebookService) {

  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    return this.notebookService.searchNotebooks()
  }
}


@Component({
  selector: 'app-notebook-list',
  templateUrl: './notebook-list.component.html',
  styleUrls: ['./notebook-list.component.css']
})
export class NotebookListComponent implements OnInit {
  notebooks: any[];

  constructor(public route: ActivatedRoute) { }

  ngOnInit(): void {

    this.notebooks = this.route.snapshot.data.notebooks;

  }

}
