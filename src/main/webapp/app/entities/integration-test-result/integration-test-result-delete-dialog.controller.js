(function() {
    'use strict';

    angular
        .module('radiatorApp')
        .controller('IntegrationTestResultDeleteController',IntegrationTestResultDeleteController);

    IntegrationTestResultDeleteController.$inject = ['$uibModalInstance', 'entity', 'IntegrationTestResult'];

    function IntegrationTestResultDeleteController($uibModalInstance, entity, IntegrationTestResult) {
        var vm = this;
        vm.integrationTestResult = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            IntegrationTestResult.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
