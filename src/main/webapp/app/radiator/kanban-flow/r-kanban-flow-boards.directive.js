(function() {
    'use strict';

    angular
        .module('radiatorApp')
        .directive('rKanbanFlowBoards', rKanbanFlowBoards);

    function rKanbanFlowBoards() {
        var directive = {
            restrict : 'E',
            scope: { boards: '=' },
            templateUrl : 'app/radiator/kanban-flow/r-kanban-flow-boards.html'
        };

        return directive;
    }
})();
