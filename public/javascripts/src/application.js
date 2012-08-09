$(document).ready(function(){
	registerListeners();
});

function registerListeners(){
//	$("#notebooks li[notebook-id]").each(function(){
//		new Notebook.notebook($(this));
//	});
	Notebook.notebooks.initialize();
	Notebook.modal.initialize();
	Notebook.note.initialize();
}
