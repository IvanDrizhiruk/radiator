(function() {
    'use strict';

    angular
        .module('radiatorApp')
        .controller('IntegrationTestResultController', IntegrationTestResultController);

    IntegrationTestResultController.$inject = ['$scope', '$state', 'IntegrationTestResult'];

    function IntegrationTestResultController ($scope, $state, IntegrationTestResult) {
        var vm = this;
        vm.integrationTestResults = [];
        vm.loadAll = function() {
            IntegrationTestResult.query(function(result) {
                vm.integrationTestResults = result;
            });
        };

        vm.loadAll();
        
    }
})();
