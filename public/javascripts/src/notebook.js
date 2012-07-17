var Notebook = Notebook || {};

Notebook.notebooks={
		initialize : function(){
			var instance = this;
			
			var notebooks = $("#notebooks li[notebook-id]");
			
			notebooks.each(function(){
				instance.makeDeletable(this);
			});
		},
		
		makeDeletable : function(notebook){
			var instance = this;
			
			var id = $(notebook).attr("notebook-id");
			
			$("i", notebook).click(function(){
				instance.deleteNotebook(id, notebook);
				return false;
			});
		},
		
		deleteNotebook : function(notebookId, notebook){
			alert("delete " + notebookId);
			
			var url = jsRoutes.controllers.NotebookController.delete(notebookId).ajax({
				success : function(){
					$(notebook).remove();
					alert("sucess");
				},
				error : function(){
					alert("error");
				}
			});
			
			return false;
		}
		
}