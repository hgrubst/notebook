package repositories.listener;

import java.util.Collections;
import java.util.Comparator;

import models.Note;
import models.Notebook;

import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.stereotype.Component;

import com.mongodb.DBObject;

public class NotebookListener extends AbstractMongoEventListener<Notebook>{

	@Override
	public void onAfterConvert(DBObject dbo, Notebook source) {

		Collections.sort(source.getNotes(), new Comparator<Note>() {
			@Override
			public int compare(Note note1, Note note2) {
				return note1.getCreatedDate().compareTo(note2.getCreatedDate());
			}
		});
	
	}
	
}
