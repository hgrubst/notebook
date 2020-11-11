import { Note } from './Note';

export interface Notebook {
    id: string,
    title: string,
    userEmail: string,
    notes: Note[]
}