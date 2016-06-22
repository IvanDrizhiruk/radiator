(function() {
    'use strict';

    angular
        .module('radiatorApp')
        .controller('KanbanFlowCellInfoDeleteController',KanbanFlowCellInfoDeleteController);

    KanbanFlowCellInfoDeleteController.$inject = ['$uibModalInstance', 'entity', 'KanbanFlowCellInfo'];

    function KanbanFlowCellInfoDeleteController($uibModalInstance, entity, KanbanFlowCellInfo) {
        var vm = this;
        vm.kanbanFlowCellInfo = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            KanbanFlowCellInfo.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
