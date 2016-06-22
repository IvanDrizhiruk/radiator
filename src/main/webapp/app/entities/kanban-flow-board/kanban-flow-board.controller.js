(function() {
    'use strict';

    angular
        .module('radiatorApp')
        .controller('KanbanFlowBoardController', KanbanFlowBoardController);

    KanbanFlowBoardController.$inject = ['$scope', '$state', 'KanbanFlowBoard'];

    function KanbanFlowBoardController ($scope, $state, KanbanFlowBoard) {
        var vm = this;
        vm.kanbanFlowBoards = [];
        vm.loadAll = function() {
            KanbanFlowBoard.query(function(result) {
                vm.kanbanFlowBoards = result;
            });
        };

        vm.loadAll();
        
    }
})();
