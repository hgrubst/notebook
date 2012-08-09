var Notebook = Notebook || {};

Notebook.note={
		initialize : function(){
			var instance = this;
			
			this.selectors = {
				"notes" : "div[note-id]",
				"noteHtmlContentDiv" : "div[note-id] div:first", 
				"noteMarkdownContentDiv" : "div[note-id] div:last", 
				"noteTextArea" : Notebook.modal.selectors["noteModal"] + " textarea",
				"noteSaveButton" : Notebook.modal.selectors["noteModal"] + " a:last",
			}

			instance.makeEditable();
			instance.makeCreatable();
		},
		
		makeEditable : function(){
			$(document).on("dblclick", Notebook.note.selectors["noteHtmlContentDiv"], function(){
				var noteId = $(this).parent().attr("note-id");
				Notebook.note.editNote(noteId);
			})
		},

		makeCreatable : function(){
			$(document).on("click", Notebook.note.selectors["noteSaveButton"], function(){
				Notebook.note.createNote();
			})
		},
		
		editNote : function(noteId){
			$(Notebook.modal.selectors["noteModal"]).modal("show");
			Notebook.note.activateNote(noteId);
		},
		
		createNote : function(){
			var notebookId = Notebook.notebooks.getActiveNotebookId();
			
			jsRoutes.controllers.NoteController.create(notebookId).ajax({
				data : {"markdown" : $(this.selectors["noteTextArea"]).val()},
				success : function(data){
					alert("success");
				},
				error : function(jqXHR, textStatus, errorThrown){
					alert("error : " + errorThrown);
				}
			});
		},
		
		activateNote : function(noteId){
			$(this.selectors["notes"]).each(function(){
				var id = $(this).attr("note-id");
				if(noteId == id){
					$(this).addClass("active");
				}else{
					$(this).removeClass("active");
				}
			});
		},
		
}