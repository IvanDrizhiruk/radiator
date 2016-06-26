(function() {
    'use strict';

    angular
        .module('radiatorApp')
        .controller('KanbanFlowSwimlaneDialogController', KanbanFlowSwimlaneDialogController);

    KanbanFlowSwimlaneDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'KanbanFlowSwimlane', 'KanbanFlowBoard'];

    function KanbanFlowSwimlaneDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, KanbanFlowSwimlane, KanbanFlowBoard) {
        var vm = this;
        vm.kanbanFlowSwimlane = entity;
        vm.kanbanflowboards = KanbanFlowBoard.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('radiatorApp:kanbanFlowSwimlaneUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.kanbanFlowSwimlane.id !== null) {
                KanbanFlowSwimlane.update(vm.kanbanFlowSwimlane, onSaveSuccess, onSaveError);
            } else {
                KanbanFlowSwimlane.save(vm.kanbanFlowSwimlane, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
