import { Component, Input, OnInit } from '@angular/core';
import { Note } from 'src/app/model/Note';

@Component({
  selector: 'app-note-display',
  templateUrl: './note-display.component.html',
  styleUrls: ['./note-display.component.css']
})
export class NoteDisplayComponent implements OnInit {

  @Input()
  note : Note

  constructor() { }

  ngOnInit(): void {
  }

}
