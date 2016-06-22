(function() {
    'use strict';

    angular
        .module('radiatorApp')
        .controller('KanbanFlowBoardDialogController', KanbanFlowBoardDialogController);

    KanbanFlowBoardDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'KanbanFlowBoard'];

    function KanbanFlowBoardDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, KanbanFlowBoard) {
        var vm = this;
        vm.kanbanFlowBoard = entity;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('radiatorApp:kanbanFlowBoardUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.kanbanFlowBoard.id !== null) {
                KanbanFlowBoard.update(vm.kanbanFlowBoard, onSaveSuccess, onSaveError);
            } else {
                KanbanFlowBoard.save(vm.kanbanFlowBoard, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
