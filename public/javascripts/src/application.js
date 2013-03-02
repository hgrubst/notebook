var NotelloView = Backbone.View.extend({
	
    events: {
        "click #create-notebook-button"   : "editInPlaceOn",
        "blur #create-notebook input"   : "editInPlaceOff",
        "keyup #create-notebook input"  : "updateOnKeypress",
        "mousedown #create-notebook button" : "createNotebook",
    },
	
	initialize : function(){
		this.selectors = {
			"createNotebookButton" : "#create-notebook-button",
			"createNotebookDiv" : "#create-notebook", 
			"createNotebookTitle" : "#create-notebook input", 
			"notebookLinks" : "li a",
		};
		
		this.subViews = {};
		
		this.collection.bind("add", this.addNotebook, this);
		this.collection.bind("reset", this.renderNotebooks, this);

		this.collection.fetch();
	},

	addNotebook : function(notebook){
		view = new NotebookView({model:notebook});
		this.$el.append(view.render().el);
		this.editInPlaceOff();
	},

	renderNotebooks : function(){
		this.collection.each(function(notebook){
			var notebookView = new NotebookView({model:notebook});
			_notebooksView.subViews[notebook.id]=notebookView;
			_notebooksView.$el.append(notebookView.render().el);
		});
		// Start Backbone history a neccesary step for bookmarkable URL's
		Backbone.history.start({pushState: true});
	},

	createNotebook : function(event){
		if(event && event.type == 'mousedown'){
			if($("#create-notebook button").get()[0] == event.target){
				if($(this.selectors["createNotebookTitle"]).val() != ""){
					this.collection.create({title:$(this.selectors["createNotebookTitle"]).val()});
				}
				event.stopPropagation();
			}
		}
		
	},

	updateOnKeypress : function(event){
		switch (event.which) {
		case 13://enter
			this.createNotebook();
		case 27://escape
			this.editInPlaceOff();
		default:
			break;
		}
	},
	
	editInPlaceOn : function(){
		this.toggleEditInPlaceCreate(true);
	},
	
	editInPlaceOff : function(){
		this.toggleEditInPlaceCreate(false);
	},
	
	toggleEditInPlaceCreate : function(editInPlace){
		if(editInPlace){
			$(this.selectors["createNotebookButton"]).hide();
			$(this.selectors["createNotebookDiv"]).show();
			$(this.selectors["createNotebookTitle"]).focus();
		}else{
			$(this.selectors["createNotebookDiv"]).hide();
			$(this.selectors["createNotebookTitle"]).val("");
			$(this.selectors["createNotebookButton"]).show();
		}
	},
	
	clearFocus : function(){
		$("li").each(function(){
			$(this).removeClass("active");
		})
	},
	
	getSubViews : function(){
		return this.subViews;
	}
	
});



$(document).ready(function(){
	_notebooksView = new NotelloView({el:'#notebooks',collection:new Notebooks()});
	_notesView = new NotesView({el:'#content'});
	_modalView = new ModalView();
	setupAjaxIndicators();

	// Instantiate the router
	_router = new AppRouter();
});

var AppRouter = Backbone.Router.extend({
    routes: {
    	"notebooks/:id" : "showNotebook",
        "*actions": "defaultRoute" // Backbone will try match the route above first
    },
    
    showNotebook: function(id){
    	var view = _notebooksView.getSubViews()[id];
		_notesView.showNotesPanel();
		_notesView.setNotes(view.model.notes);
		_notesView.collection.fetch();
		view.focus();
    }
    
});

var _ajaxTimeout;
function setupAjaxIndicators(){
	var loadIndicator = $(".alert-info").first();
	var errorIndicator = $(".alert-error").first();
	
	loadIndicator.ajaxStart(function(){
		if(_ajaxTimeout){clearTimeout(_ajaxTimeout);}
		_ajaxTimeout = setTimeout(function(){
			loadIndicator.show();
		}, 2000);
	});
	
	loadIndicator.ajaxStop(function(){
		if(_ajaxTimeout){clearTimeout(_ajaxTimeout);}
		$(this).hide();
	});
	
	errorIndicator.ajaxError(function(){
		errorIndicator.show();
		errorIndicator.delay(5000).fadeOut();
	})

}