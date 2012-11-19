var ModalView = Backbone.View.extend({

	el: "#note-modal",
    
	events: {
    	"click a.btn-primary" : "save",
    },
	
	initialize : function(){
		$("textarea", this.$el).markItUp(mySettings);
	},
	
    show : function(){
    	this.$el.modal("show");
    },
	
	save : function(){
		this.options.view.trigger("saveModal");
	},
    
	setView : function(view){
		this.options.view = view; 
	},
	
	setContent: function(content){
		$("textarea", this.$el).val(content);
	},
	
	getContent: function(){
		return $("textarea", this.$el).val();
	},
	
	resetContent: function(){
		$("textarea", this.$el).val("");
	}
});
