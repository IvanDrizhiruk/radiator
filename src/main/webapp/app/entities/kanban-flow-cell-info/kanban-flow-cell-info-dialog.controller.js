(function() {
    'use strict';

    angular
        .module('radiatorApp')
        .controller('KanbanFlowCellInfoDialogController', KanbanFlowCellInfoDialogController);

    KanbanFlowCellInfoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'KanbanFlowCellInfo', 'KanbanFlowBoard', 'KanbanFlowColumn', 'KanbanFlowSwimlane'];

    function KanbanFlowCellInfoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, KanbanFlowCellInfo, KanbanFlowBoard, KanbanFlowColumn, KanbanFlowSwimlane) {
        var vm = this;
        vm.kanbanFlowCellInfo = entity;
        vm.kanbanflowboards = KanbanFlowBoard.query();
        vm.kanbanflowcolumns = KanbanFlowColumn.query();
        vm.kanbanflowswimlanes = KanbanFlowSwimlane.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('radiatorApp:kanbanFlowCellInfoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.kanbanFlowCellInfo.id !== null) {
                KanbanFlowCellInfo.update(vm.kanbanFlowCellInfo, onSaveSuccess, onSaveError);
            } else {
                KanbanFlowCellInfo.save(vm.kanbanFlowCellInfo, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        vm.datePickerOpenStatus = {};
        vm.datePickerOpenStatus.extractingDate = false;

        vm.openCalendar = function(date) {
            vm.datePickerOpenStatus[date] = true;
        };
    }
})();
