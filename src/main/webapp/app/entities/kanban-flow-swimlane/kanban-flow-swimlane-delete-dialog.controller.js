(function() {
    'use strict';

    angular
        .module('radiatorApp')
        .controller('KanbanFlowSwimlaneDeleteController',KanbanFlowSwimlaneDeleteController);

    KanbanFlowSwimlaneDeleteController.$inject = ['$uibModalInstance', 'entity', 'KanbanFlowSwimlane'];

    function KanbanFlowSwimlaneDeleteController($uibModalInstance, entity, KanbanFlowSwimlane) {
        var vm = this;
        vm.kanbanFlowSwimlane = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            KanbanFlowSwimlane.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
