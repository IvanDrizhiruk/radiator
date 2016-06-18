(function() {
    'use strict';

    angular
        .module('radiatorApp')
        .controller('BuildStateDialogController', BuildStateDialogController);

    BuildStateDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'BuildState', 'Commiter'];

    function BuildStateDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, BuildState, Commiter) {
        var vm = this;
        vm.buildState = entity;
        vm.commiters = Commiter.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('radiatorApp:buildStateUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.buildState.id !== null) {
                BuildState.update(vm.buildState, onSaveSuccess, onSaveError);
            } else {
                BuildState.save(vm.buildState, onSaveSuccess, onSaveError);
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
