(function() {
    'use strict';

    angular
        .module('radiatorApp')
        .controller('RIntegrationTestResultController', RIntegrationTestResultController);

    RIntegrationTestResultController.$inject = ['$scope', 'RIntegrationTest', 'RRefresher'];

    function RIntegrationTestResultController ($scope, RIntegrationTest, RRefresher) {
        var vm = this;
        vm.buildStates = [];
        vm.loadAll = function() {
            RIntegrationTest.query(function(testResult) {
                vm.testResult = testResult;
            });
        };

        RRefresher.registrate(vm.loadAll);

        vm.loadAll();
    }
})();
