(function() {
    'use strict';

    angular
        .module('radiatorApp')
        .controller('KanbanFlowColumnDialogController', KanbanFlowColumnDialogController);

    KanbanFlowColumnDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'KanbanFlowColumn', 'KanbanFlowBoard'];

    function KanbanFlowColumnDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, KanbanFlowColumn, KanbanFlowBoard) {
        var vm = this;
        vm.kanbanFlowColumn = entity;
        vm.kanbanflowboards = KanbanFlowBoard.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('radiatorApp:kanbanFlowColumnUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.kanbanFlowColumn.id !== null) {
                KanbanFlowColumn.update(vm.kanbanFlowColumn, onSaveSuccess, onSaveError);
            } else {
                KanbanFlowColumn.save(vm.kanbanFlowColumn, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
