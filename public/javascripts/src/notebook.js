var Notebook = Backbone.Model.extend({
	initialize: function(){
		this.notes=new NoteCollection();
		
		this.notes.bind("add", _notesView.addNote, _notesView);
		this.notes.bind("reset", _notesView.renderNotes, _notesView);
		
		var that = this;
		this.notes.url= function(){
			return "/notebooks/" + that.id + "/notes";
		}
	}
});

var Notebooks = Backbone.Collection.extend({
	model : Notebook,
	url : "/notebooks",
    parse: function(response){
        return response.notebooks;
    }
});

var NotebookView = Backbone.View.extend({
	
	tagName: "li",
	
	template: _.template('<a href="/notebooks/<%=notebook.id%>"><%= notebook.title %> <i class="icon-trash"></i></a>'),
	
    events: {
        "click i"   : "deleteNotebook",
        "click a" : "displayNotes"
    },
    
    initialize: function() {
        this.model.bind('destroy', this.remove, this);
    },
	
	render : function(){
		this.$el.html(this.template({
			notebook : this.model.toJSON()
		}));
		return this;
	},
	
	deleteNotebook : function(e){
		//prevent default to avoid the anchor from trying to go to link 
		e.preventDefault();
		if(confirm("This will delete the notebook and all notes contained. Are you sure you want to continue?")){
			this.model.destroy();
			if(this.isFocus()){
				_notesView.showWelcomePanel();
			}
		}
		//dont propagate to avoid displaying notes
		e.stopPropagation();
	},
	
	displayNotes : function(e){
		e.preventDefault();
		_router.navigate("notebooks/" + this.model.id, {trigger:true});
	},
	
	focus : function(){
		_notebooksView.clearFocus();
		this.$el.addClass("active");
	},

	isFocus : function(){
		return this.$el.hasClass("active");
	}
	
});

