(function() {
    'use strict';

    angular
        .module('radiatorApp')
        .controller('IntegrationTestResultDialogController', IntegrationTestResultDialogController);

    IntegrationTestResultDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'IntegrationTestResult'];

    function IntegrationTestResultDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, IntegrationTestResult) {
        var vm = this;
        vm.integrationTestResult = entity;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('radiatorApp:integrationTestResultUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.integrationTestResult.id !== null) {
                IntegrationTestResult.update(vm.integrationTestResult, onSaveSuccess, onSaveError);
            } else {
                IntegrationTestResult.save(vm.integrationTestResult, onSaveSuccess, onSaveError);
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
