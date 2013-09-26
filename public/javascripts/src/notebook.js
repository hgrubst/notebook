var notello = angular.module('notello',['ngSanitize']);

notello.config(function($locationProvider, $routeProvider) {
	$locationProvider.html5Mode(true).hashPrefix('!');
    $routeProvider.
    	when('/notebooks/:notebookId', {controller:NoteCtrl, templateUrl:'/noteList.html'}).
    	when('/', {controller:NoteCtrl, templateUrl:'/welcome.html'});
});		

notello.directive("markitup", function(){
	return function(scope, element){
		if($(element).find(".markitup").length == 0){
			$(element).markItUp(mySettings);
		}
	}
})

function NotebookCtrl($scope, $http, $location) {
	$scope.selectedIndex = -1;
	
	$http.get(jsRoutes.controllers.NotebookController.list().url).success(function (data){
		$scope.notebooks = data;	
	});
	
	
	$scope.delete = function(event, index){
		event.preventDefault();
		event.stopPropagation();
		
		if(confirm("This will delete the notebook and all notes contained. Are you sure you want to continue?")){
			var notebook = $scope.notebooks[index];
			$http.delete(jsRoutes.controllers.NotebookController.delete(notebook.id).url).success(function (data){
				$scope.selectedIndex = -1;
				$scope.notebooks.splice(index,1);	
				$location.path("/");
			});
		}
	}
	
	$scope.create = function(){
		jsRoutes.controllers.NotebookController.create($scope.notebook).ajax({
			success : function(data){
				$scope.notebooks.push(data);
				$scope.$apply();
			},
		});
	}
	
	$scope.select = function(event, index){
		event.preventDefault();
		$scope.selectedIndex = index;
	}

}

function NoteCtrl($scope, $http, $routeParams){
	$scope.notebookId = $routeParams.notebookId;
	
	$http.get(jsRoutes.controllers.NoteController.list($routeParams.notebookId).url).success(function (data){
		$scope.notes = data;
		hljs.initHighlightingOnLoad();
	});

	
	$scope.cancel = function(note){
		note.mode='view';
		$("#note-" + note.id).find("textarea").val(note.content);//could not find out how to cancel user changes without explicitly reseting the value in jquery
	}
	
	$scope.save = function(note, index){
		//markitup messes with angular model so do jquery here
		var updatedContent = $("#note-" + note.id).find("textarea").val();
		jsRoutes.controllers.NoteController.update(note.id, note.notebook.id).ajax({
			data : {"content" : updatedContent},
			success : function(data){
				$scope.notes[index] = data;
				$scope.$apply();
			},
		});
		note.mode="view";
	}
	
	$scope.delete = function(event, index){
		event.preventDefault();
		
		if(confirm("Are you sure you want to delete this note?")){
			var note = $scope.notes[index];
			$http.delete(jsRoutes.controllers.NoteController.delete(note.id,$scope.notebookId).url).success(function (data){
				$scope.notes.splice(index,1);	
			});
		}
	}
	
	$scope.$on('deleteNotebook', function() {
	    $scope.notes = null;
	  });

}

function AddNoteCtrl($scope, $http, $routeParams){
	var addNoteDiv = $("div#addNote");
	
	$scope.hidden=true;
	
	$scope.save = function(){
		jsRoutes.controllers.NoteController.create($routeParams.notebookId).ajax({
			//markitup messes with angular model so do jquery here
			data : {"content" : addNoteDiv.find("textarea").val()},
			success : function(data){
				$scope.notes.push(data);//scope inherits from NoteCtrl scope
				$scope.hidden=true;
				$scope.content='';
				$scope.$apply();
			},
		});
	}

}

