var Notebook = Notebook || {};

Notebook.notebooks={
		initialize : function(){
			var instance = this;
			
			this.createNotebookButton = $("#create-notebook-button");
			this.createNotebookDiv = $("#create-notebook");
			this.createNotebookTitle = $("#create-notebook input");
			this.createNotebookGo = $("#create-notebook button");
			
			this.notebookHtml = '<li notebook-id="$notebookId"><a href="#">$notebookTitle<i class="icon-trash"></i></a></li>';
			
			instance.makeCreatable();
			
			var notebooks = $("#notebooks li[notebook-id]");
			
			notebooks.each(function(){
				instance.makeShowable(this);
				instance.makeDeletable(this);
			});
		},
		
		makeCreatable : function(notebook){
			this.createNotebookButton.click(function(){
				Notebook.notebooks.toggleEditInPlaceCreate(true);
			});
			
			this.createNotebookTitle.blur(function(event){
				Notebook.notebooks.toggleEditInPlaceCreate(false);
			});

//			this.createNotebookGo.click(function(event){
//
//				if(event.target == Notebook.notebooks.createNotebookGo.get(0)){
//					alert("button clicked");
//				}else{
//					alert("elsewhere clicked");
//				}
//				
//				Notebook.notebooks.createNotebook(Notebook.notebooks.createNotebookTitle.val());
//			});

			
			this.createNotebookTitle.keyup(function(event){
				switch (event.which) {
				case 13://enter
					Notebook.notebooks.createNotebook($(this).val());
					break;
				case 27://escape
					Notebook.notebooks.toggleEditInPlaceCreate(false);
					break;
				default:
					break;
				}
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
		
		createNotebook : function(title){
			var url = jsRoutes.controllers.NotebookController.create(title).ajax({
				success : function(data){
					var html = Notebook.notebooks.notebookHtml.replace("$notebookId", data).replace("$notebookTitle", title);
					var el = $(html).get(0);
					
					$("#notebooks li.nav-header:last").before(el);
//					$("#notebooks").append(html);
					
					Notebook.notebooks.makeShowable(el);
					Notebook.notebooks.makeDeletable(el);
				},
				error : function(jqXHR, textStatus, errorThrown){
					alert("error : " + errorThrown);
				}
			});
			
			Notebook.notebooks.toggleEditInPlaceCreate(false);
		},

		showNotebook : function(notebookId){
			//alert("show notebook " + notebookId);
			var url = jsRoutes.controllers.NoteController.list(notebookId).ajax({
				success : function(data){
					$("#content").html(data);
					$("textarea").markItUp(mySettings);
				},
				error : function(jqXHR, textStatus, errorThrown){
					alert("error : " + errorThrown);
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
		},
		
		toggleEditInPlaceCreate : function(editInPlace){
			if(editInPlace){
				this.createNotebookButton.hide();
				this.createNotebookDiv.show();
				this.createNotebookTitle.focus();
			}else{
				this.createNotebookDiv.hide();
				this.createNotebookTitle.val("");
				this.createNotebookButton.show();
			}
		}
		
}