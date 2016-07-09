(function() {
    'use strict';

    angular
        .module('radiatorApp')
        .directive('rKanbanFlowBoard', rKanbanFlowBoard);

    function rKanbanFlowBoard() {
        var directive = {
            restrict : 'E',
            scope: { board: '=' },
            templateUrl : 'app/radiator/kanban-flow/r-kanban-flow-board.html'
        };

        return directive;
    }
})();
