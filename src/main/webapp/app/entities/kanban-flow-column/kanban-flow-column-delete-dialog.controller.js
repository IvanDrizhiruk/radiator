(function() {
    'use strict';

    angular
        .module('radiatorApp')
        .controller('KanbanFlowColumnDeleteController',KanbanFlowColumnDeleteController);

    KanbanFlowColumnDeleteController.$inject = ['$uibModalInstance', 'entity', 'KanbanFlowColumn'];

    function KanbanFlowColumnDeleteController($uibModalInstance, entity, KanbanFlowColumn) {
        var vm = this;
        vm.kanbanFlowColumn = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            KanbanFlowColumn.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
