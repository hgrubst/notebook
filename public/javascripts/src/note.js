var Note = Backbone.Model.extend({
});

var NoteCollection = Backbone.Collection.extend({
	model : Note,
    parse: function(response){
        return response.notes;
    }
});

//the view for a single note
var NoteView = Backbone.View.extend({
	
});

//the view for a list of notes (eg all the notes of a notebook)
var NotesView = Backbone.View.extend({
	
    events: {
    	"dblclick #create-note" : "showNoteEditor",
        "click save"   : "createNote",//click on save button of markitup editor
    },
	
	setNotes : function(notes){
		this.collection = notes;
		this.collection.bind("add", this.addNote, this);
		this.collection.bind("reset", this.renderNotes, this);
	},

	renderNotes : function(){
		var notesView = this;
		this.collection.each(function(note){
			var noteView = new NoteView({model:note});
			notesView.$el.append(noteView.render().el);
		});
	},
	
	addNote : function(){
		view = new NotebookView({model:notebook});
		this.$el.append(view.render().el);

	},
	
	createNote : function(){
		this.collection.create({content : "markdownContent"});
	},
	
	showNotesPanel : function(){
		$("#welcome").hide();
		$("#create-note").show();
	},
	
	showNoteEditor : function(){
		modalView.show();
	}
});
















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