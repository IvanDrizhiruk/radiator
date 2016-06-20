(function() {
    'use strict';

    angular
        .module('radiatorApp')
        .controller('IntegrationTestResultDetailController', IntegrationTestResultDetailController);

    IntegrationTestResultDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'IntegrationTestResult'];

    function IntegrationTestResultDetailController($scope, $rootScope, $stateParams, entity, IntegrationTestResult) {
        var vm = this;
        vm.integrationTestResult = entity;
        
        var unsubscribe = $rootScope.$on('radiatorApp:integrationTestResultUpdate', function(event, result) {
            vm.integrationTestResult = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
