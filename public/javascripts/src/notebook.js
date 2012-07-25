var Notebook = Notebook || {};

Notebook.notebooks={
		initialize : function(){
			var instance = this;
			
			var notebooks = $("#notebooks li[notebook-id]");
			
			notebooks.each(function(){
				instance.makeShowable(this);
				instance.makeDeletable(this);
			});
		},
		
		makeShowable : function(notebook){
			var id = $(notebook).attr("notebook-id");
			
			$("a", notebook).click(function(){
				Notebook.notebooks.showNotebook(id);
				return false;
			});
		},

		makeDeletable : function(notebook){
			var id = $(notebook).attr("notebook-id");
			
			$("i", notebook).click(function(){
				Notebook.notebooks.deleteNotebook(id, notebook);
				return false;
			});
		},
		
		showNotebook : function(notebookId){
			alert("show notebook " + notebookId);
			var url1 = jsRoutes.controllers.NoteController.list(notebookId);
			var url = jsRoutes.controllers.NoteController.list(notebookId).ajax({
				success : function(data){
					$("#content").html(data);
				},
				error : function(){
					alert("error");
				}
			});
		},
		
		deleteNotebook : function(notebookId, notebook){
			var url = jsRoutes.controllers.NotebookController.delete(notebookId).ajax({
				success : function(){
					$(notebook).remove();
				},
				error : function(){
					alert("error");
				}
			});
			
			return false;
		}
		
}