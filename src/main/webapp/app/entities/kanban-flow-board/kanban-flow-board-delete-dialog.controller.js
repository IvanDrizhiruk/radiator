(function() {
    'use strict';

    angular
        .module('radiatorApp')
        .controller('KanbanFlowBoardDeleteController',KanbanFlowBoardDeleteController);

    KanbanFlowBoardDeleteController.$inject = ['$uibModalInstance', 'entity', 'KanbanFlowBoard'];

    function KanbanFlowBoardDeleteController($uibModalInstance, entity, KanbanFlowBoard) {
        var vm = this;
        vm.kanbanFlowBoard = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            KanbanFlowBoard.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
