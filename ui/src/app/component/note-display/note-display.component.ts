import { Component, Input, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { NoteActions } from 'src/app/actions/NoteActions';
import { Note } from 'src/app/model/Note';
import { NoteUpdateRequest } from 'src/app/model/NoteUpdateRequest';

@Component({
  selector: 'app-note-display',
  templateUrl: './note-display.component.html',
  styleUrls: ['./note-display.component.css']
})
export class NoteDisplayComponent implements OnInit {

  @Input()
  note: Note

  editMode = false;

  noteEditForm: FormGroup

  constructor(private noteActions: NoteActions) { }

  ngOnInit(): void {
    this.noteEditForm = new FormGroup({
      content: new FormControl(this.note.content),
      position: new FormControl(this.note.position)
    })
  }

  async onDeleteClicked(noteId: string) {
    await this.noteActions.deleteNote(noteId)
  }

  async onUpdateNoteClicked(note: Note) {
    if (this.noteEditForm.valid) {
      const noteUpdateRequest = new NoteUpdateRequest();
      noteUpdateRequest.content = this.noteEditForm.value['content']
      noteUpdateRequest.position = this.noteEditForm.value['position'];

      await this.noteActions.updateNote(note.id, noteUpdateRequest)
      this.editMode = false
    }
  }

}
