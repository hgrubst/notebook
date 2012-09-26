var ModalView = Backbone.View.extend({

	el: "#note-modal",

	initialize : function(){
		$("textarea", this.$el).markItUp(mySettings);
//		this.$el("textarea").markItUp(mySettings);
	},
	
    show : function(){
    	this.$el.modal("show");
    }
});
