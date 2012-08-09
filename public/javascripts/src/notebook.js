var Notebook = Notebook || {};

Notebook.notebooks={
		initialize : function(){
			var instance = this;
			
			this.selectors = {
				"createNotebookButton" : "#create-notebook-button",
				"createNotebookDiv" : "#create-notebook", 
				"createNotebookTitle" : "#create-notebook input", 
				"notebooks" : "#notebooks li[notebook-id]",
				"notebookShowLink" : "#notebooks li[notebook-id] a",
				"notebookDeleteLink" : "#notebooks li[notebook-id] i",
				"activeNotebook" : "#notebooks li[notebook-id].active",
			}
			
			this.notebookHtml = '<li notebook-id="$notebookId"><a href="#">$notebookTitle<i class="icon-trash"></i></a></li>';
			
			instance.makeCreatable();
			instance.makeShowable(this);
			instance.makeDeletable(this);
		},
		
		makeCreatable : function(){
			$(this.selectors["createNotebookButton"]).click(function(){
				Notebook.notebooks.toggleEditInPlaceCreate(true);
			});
			
			$(this.selectors["createNotebookTitle"]).blur(function(event){
				Notebook.notebooks.toggleEditInPlaceCreate(false);
			});

			$(this.selectors["createNotebookTitle"]).keyup(function(event){
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

		makeShowable : function(){
			$(document).on("click", Notebook.notebooks.selectors["notebookShowLink"], function(){
				var id = $(this).parent().attr("notebook-id");
				Notebook.notebooks.showNotebook(id);
				return false;
			})
		},

		makeDeletable : function(){
			$(document).on("click", Notebook.notebooks.selectors["notebookDeleteLink"], function(){
				var notebook = $(this).parent().parent();
				var id = $(notebook).attr("notebook-id");
				Notebook.notebooks.deleteNotebook(id, notebook);
				return false;
			})
		},
		
		createNotebook : function(title){
			var url = jsRoutes.controllers.NotebookController.create(title).ajax({
				success : function(data){
					var html = Notebook.notebooks.notebookHtml.replace("$notebookId", data).replace("$notebookTitle", title);
					var el = $(html).get(0);
					
					$("#notebooks li.nav-header:last").before(el);
//					$("#notebooks").append(html);
					
					//Notebook.notebooks.makeShowable(el);
					//Notebook.notebooks.makeDeletable(el);
				},
				error : function(jqXHR, textStatus, errorThrown){
					alert("error : " + errorThrown);
				}
			});
			
			Notebook.notebooks.toggleEditInPlaceCreate(false);
		},

		showNotebook : function(notebookId){
			var url = jsRoutes.controllers.NoteController.list(notebookId).ajax({
				success : function(data){
					$("#content").html(data);
					$("textarea").markItUp(mySettings);
					Notebook.notebooks.activateNotebook(notebookId);
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
				$(this.selectors["createNotebookButton"]).hide();
				$(this.selectors["createNotebookDiv"]).show();
				$(this.selectors["createNotebookTitle"]).focus();
			}else{
				$(this.selectors["createNotebookDiv"]).hide();
				$(this.selectors["createNotebookTitle"]).val("");
				$(this.selectors["createNotebookButton"]).show();
			}
		},
		
		activateNotebook : function(notebookId){
			$(this.selectors["notebooks"]).each(function(){
				var id = $(this).attr("notebook-id");
				if(notebookId == id){
					$(this).addClass("active");
				}else{
					$(this).removeClass("active");
				}
			});
		},
		
		getActiveNotebookId : function(){
			return $(this.selectors["activeNotebook"]).attr("notebook-id")
		}
		
}