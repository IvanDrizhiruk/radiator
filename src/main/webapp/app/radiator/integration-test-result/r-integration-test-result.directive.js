(function() {
    'use strict';

    angular
        .module('radiatorApp')
        .directive('rIntegrationTestResult', rIntegrationTestResult);

    function rIntegrationTestResult() {
        var directive = {
            restrict : 'E',
            scope: { item: '=' },
            templateUrl : 'app/radiator/integration-test-result/integration-test-result.html',
            bindToController: true,
            controller: rIntegrationTestResultController,
            controllerAs: 'vm'
        };

        return directive;
    }

    rIntegrationTestResultController.$inject = ['$scope'];

    function rIntegrationTestResultController ($scope) {
        var vm = $scope.vm;

        console.log("ISD $scope", vm);

        vm.chartData = {
            data: [vm.item.passed, vm.item.pending, vm.item.failed],
            labels: ['Passed', 'Pending', 'Failed'],
            colors: ['#109618', '#FF9900', '#DC3912']
        };
    }
})();
