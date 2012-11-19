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
	tagName: "div",
	
	attributes: {
		"class" : "well"
	},
	
    events: {
    	"dblclick" : "showNoteEditor",
    	"click .icon-pencil" : "showNoteEditor",
    	"click .icon-trash" : "deleteNote",
    },	
	
    initialize: function(){
    	this.bind("saveModal", this.updateNote, this);
    	this.model.bind("change", this.render, this);
    	this.model.bind('destroy', this.remove, this);
    },    
    
	template: _.template('<div class="btn-toolbar"><div class="btn-group"><a href="#" class="btn btn-mini"><i class="icon-pencil"></i></a><a href="#" class="btn btn-mini"><i class="icon-trash"></i></a></div></div><%=note.contentAsHtml%>'),

	render : function(){
		this.$el.html(this.template({
			note : this.model.toJSON()
		}));
		return this;
	},

	showNoteEditor : function(){
		modalView.setView(this);
		modalView.setContent(this.model.get("content"));
		modalView.show();
	},
	
	updateNote : function(){
		this.model.save({"content" : modalView.getContent()}, {wait:true});
		modalView.resetContent();
	},
	
	deleteNote : function(){
		this.model.destroy({wait: true});
	}
});

//the view for a list of notes (eg all the notes of a notebook)
var NotesView = Backbone.View.extend({
	
    events: {
    	"dblclick #create-note" : "showNoteEditor",
    },
	
    initialize: function(){
    	this.bind("saveModal", this.createNote, this);
    },
    
	setNotes : function(notes){
		this.collection = notes;
		modalView.setView(this);
	},

	renderNotes : function(){
		this.collection.each(function(note){
			var noteView = new NoteView({model:note});
			notesView.$el.append(noteView.render().el);
		});
	},
	
	addNote : function(note){
		var view = new NoteView({model:note});
		this.$el.append(view.render().el);

	},
	
	createNote : function(){
		this.collection.create({content : modalView.getContent()},{wait: true});
		modalView.resetContent();
	},
	
	showNotesPanel : function(){
		$("#welcome").hide();
		this.clear();
		$("#create-note").show();
	},
	
	showNoteEditor : function(){
		modalView.show();
	},
	
	clear : function(){
		$("#create-note ~ div").remove();
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