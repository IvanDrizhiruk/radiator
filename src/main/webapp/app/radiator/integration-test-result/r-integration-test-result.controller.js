(function() {
    'use strict';

    angular
        .module('radiatorApp')
        .controller('RIntegrationTestResultController', RIntegrationTestResultController);

    RIntegrationTestResultController.$inject = ['$scope', 'RIntegrationTest'];

    function RIntegrationTestResultController ($scope, RIntegrationTest) {
        var vm = this;
        vm.buildStates = [];
        vm.loadAll = function() {
            RIntegrationTest.query(function(testResult) {
                vm.testResult = testResult;
            });
        };

        vm.loadAll();
    }
})();
