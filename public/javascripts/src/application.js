var NotelloView = Backbone.View.extend({
	
    events: {
        "click #create-notebook-button"   : "editInPlaceOn",
        "blur #create-notebook input"   : "editInPlaceOff",
        "keyup #create-notebook input"  : "updateOnKeypress",
    },
	
	initialize : function(){
		this.selectors = {
			"createNotebookButton" : "#create-notebook-button",
			"createNotebookDiv" : "#create-notebook", 
			"createNotebookTitle" : "#create-notebook input", 
			"notebookLinks" : "li a",
		};
		
		
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
		var notebooksView = this;
		this.collection.each(function(notebook){
			var notebookView = new NotebookView({model:notebook});
			notebooksView.$el.append(notebookView.render().el);
		});
	},
	
	updateOnKeypress : function(event){
		switch (event.which) {
		case 13://enter
			this.collection.create({title:$(this.selectors["createNotebookTitle"]).val()},{wait:true});
			break;
		case 27://escape
			break;
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
	}
	
});



$(document).ready(function(){
	_notebooksView = new NotelloView({el:'#notebooks',collection:new Notebooks()});
	notesView = new NotesView({el:'#content'});
	modalView = new ModalView();
	setupAjaxIndicators();
});

var AppRouter = Backbone.Router.extend({
    routes: {
        "*actions": "defaultRoute" // Backbone will try match the route above first
    },
});
// Instantiate the router
var app_router = new AppRouter;
// Start Backbone history a neccesary step for bookmarkable URL's
Backbone.history.start({pushState: true});

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