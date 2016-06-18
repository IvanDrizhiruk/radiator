(function() {
    'use strict';

    angular
        .module('radiatorApp')
        .controller('CommiterDetailController', CommiterDetailController);

    CommiterDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Commiter'];

    function CommiterDetailController($scope, $rootScope, $stateParams, entity, Commiter) {
        var vm = this;
        vm.commiter = entity;
        
        var unsubscribe = $rootScope.$on('radiatorApp:commiterUpdate', function(event, result) {
            vm.commiter = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
