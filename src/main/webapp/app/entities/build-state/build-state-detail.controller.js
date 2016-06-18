(function() {
    'use strict';

    angular
        .module('radiatorApp')
        .controller('BuildStateDetailController', BuildStateDetailController);

    BuildStateDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'BuildState', 'Commiter'];

    function BuildStateDetailController($scope, $rootScope, $stateParams, entity, BuildState, Commiter) {
        var vm = this;
        vm.buildState = entity;
        
        var unsubscribe = $rootScope.$on('radiatorApp:buildStateUpdate', function(event, result) {
            vm.buildState = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
