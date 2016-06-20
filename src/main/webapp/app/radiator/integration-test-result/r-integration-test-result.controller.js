(function() {
    'use strict';

    angular
        .module('radiatorApp')
        .controller('RIntegrationTestResultController', RIntegrationTestResultController);

    RIntegrationTestResultController.$inject = ['$scope', 'IntegrationTestResult'];

    function RIntegrationTestResultController ($scope, IntegrationTestResult) {
        var vm = this;
        vm.buildStates = [];
        vm.loadAll = function() {
            IntegrationTestResult.query(function(testResult) {
                vm.testResult = testResult;
            });
        };

        vm.loadAll();
    }
})();
