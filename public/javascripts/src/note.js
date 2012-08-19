var Notebook = Notebook || {};

Notebook.note={
		initialize : function(){
			var instance = this;
			
			this.selectors = {
				"notes" : "div[note-id]",
				"noteHtmlContentDiv" : "div[note-id] div:first-child", 
				"noteMarkdownContentDiv" : "div[note-id] div:last-child", 
				"noteTextArea" : Notebook.modal.selectors["noteModal"] + " textarea",
				"noteSaveButton" : Notebook.modal.selectors["noteModal"] + " a:last",
				"activeNote" : "div[note-id].active",
				"activeNoteMarkdownContentDiv" : "div[note-id].active div:last-child"
			}

			this.makeEditable();
			this.makeCreatable();
		},
		
		makeEditable : function(){
			var instance = this;
			
			$(document).on("dblclick", instance.selectors["noteHtmlContentDiv"], function(){
				var noteId = $(this).parent().attr("note-id");
				instance.editNote(noteId);
			})
		},

		makeCreatable : function(){
			var instance = this;
			
			$(document).on("click", instance.selectors["noteSaveButton"], function(){
				var noteId = $(instance.selectors["activeNote"]).attr("note-id");
				if(noteId == ""){
					instance.createNote();
				}else{
					instance.updateNote(noteId);
				}
			})
		},
		
		editNote : function(noteId){
			this.activateNote(noteId);
			
			var markdown = $(this.selectors['activeNoteMarkdownContentDiv']).html().trim();
			
			$(this.selectors["noteTextArea"]).val(markdown);

			$(Notebook.modal.selectors["noteModal"]).modal("show");
		},
		
		createNote : function(){
			var notebookId = Notebook.notebooks.getActiveNotebookId();
			
			jsRoutes.controllers.NoteController.create(notebookId).ajax({
				data : {"markdown" : $(this.selectors["noteTextArea"]).val()},
				success : function(data){
					$(Notebook.modal.selectors["noteModal"]).modal("hide");
					Notebook.notebooks.showNotebook(notebookId);
				},
				error : function(jqXHR, textStatus, errorThrown){
					alert("error : " + errorThrown);
				}
			});
		},

		updateNote : function(noteId){
			var notebookId = Notebook.notebooks.getActiveNotebookId();
			
			jsRoutes.controllers.NoteController.update(noteId, notebookId).ajax({
				data : {"markdown" : $(this.selectors["noteTextArea"]).val()},
				success : function(data){
					$(Notebook.modal.selectors["noteModal"]).modal("hide");
					Notebook.notebooks.showNotebook(notebookId);
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
		}
}