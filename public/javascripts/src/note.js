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
	},
	
    events: {
    	"dblclick #create-note" : "showNoteEditor",
    	"click .icon-pencil" : "showNoteEditor",
    	"click .icon-trash" : "deleteNote",
    	"click .tab-pane a.btn:first" : "displayViewPane",
    	"click .tab-pane a.btn-primary" : "updateNote",
    },	
	
    initialize: function(){
    	this.bind("saveModal", this.updateNote, this);
    	this.model.bind("change", this.render, this);
    	this.model.bind('destroy', this.remove, this);
    },    
    
	template: _.template( 
			'<div class="tabbable">' + 
				'<ul class="nav nav-tabs">' + 
					'<li class="active"><a href="#note-<%=note.id%>-view" data-toggle="tab">View</a></li>' + 
					'<li><a href="#note-<%=note.id%>-edit" data-toggle="tab">Edit</a></li>' + 
				'</ul>' + 
				'<div class="tab-content">' + 
					'<div class="tab-pane active" id="note-<%=note.id%>-view">' + 
						'<%=note.contentAsHtml%>' + 
					'</div>' + 
					'<div class="tab-pane" id="note-<%=note.id%>-edit">' + 
						'<textarea><%=note.content%></textarea>' +
						'<div class="btn-toolbar"> ' + 
							'<div class="btn-group">' + 
								'<a href="#" class="btn">Cancel</a>' + 
							'</div>' + 
							'<div class="btn-group">' + 
								'<a href="#" class="btn btn-primary">Save</a>' +
							'</div>' + 
						'</div>' +
					'</div>' + 
				'</div>' +
			'</div>'), 
			
	render : function(){
		this.$el.html(this.template({
			note : this.model.toJSON()
		}));
		if(!$("textarea", this.$el).hasClass("markItUpEditor")){
			$("textarea", this.$el).markItUp(mySettings);
		}
		return this;
	},

	showNoteEditor : function(){
		_modalView.setView(this);
		_modalView.setContent(this.model.get("content"));
		_modalView.show();
	},
	
	updateNote : function(){
		this.model.save({"content" : $("textarea", this.$el).val()});
	},
	
	deleteNote : function(e){
		if(confirm("This will delete this note. Are you sure you want to continue?")){
			this.model.destroy();
		}else{
			e.stopImmediatePropagation();
		}
	}, 
	
	displayViewPane : function(){
		$("li:first a", this.$el).click();
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
		_modalView.setView(this);
	},

	renderNotes : function(){
		this.collection.each(function(note){
			var noteView = new NoteView({model:note});
			_notesView.$el.append(noteView.render().el);
		});
	},
	
	addNote : function(note){
		var view = new NoteView({model:note});
		this.$el.append(view.render().el);

	},
	
	createNote : function(){
		this.collection.create({content : _modalView.getContent()});
		_modalView.resetContent();
	},
	
	showNotesPanel : function(){
		$("#welcome").hide();
		this.clear();
		$("#create-note").show();
	},
	
	showWelcomePanel : function(){
		$("#welcome").show();
		this.clear();
		$("#create-note").hide();
	},

	showNoteEditor : function(){
		_modalView.show();
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