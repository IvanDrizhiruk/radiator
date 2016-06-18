(function() {
    'use strict';

    angular
        .module('radiatorApp')
        .controller('CommiterDialogController', CommiterDialogController);

    CommiterDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Commiter'];

    function CommiterDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Commiter) {
        var vm = this;
        vm.commiter = entity;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('radiatorApp:commiterUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.commiter.id !== null) {
                Commiter.update(vm.commiter, onSaveSuccess, onSaveError);
            } else {
                Commiter.save(vm.commiter, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
