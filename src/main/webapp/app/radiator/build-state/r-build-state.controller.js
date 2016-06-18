(function() {
    'use strict';

    angular
        .module('radiatorApp')
        .controller('RBuildStateController', RBuildStateController);

    RBuildStateController.$inject = ['$scope', '$state', 'RBuildState'];

    function RBuildStateController ($scope, $state, RBuildState) {
        var vm = this;
        vm.buildStates = [];
        vm.loadAll = function() {
            RBuildState.query(function(result) {
                vm.buildStates = result;
            });
        };

        vm.loadAll();
    }
})();
