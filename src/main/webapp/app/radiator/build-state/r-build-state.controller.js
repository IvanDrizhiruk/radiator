(function() {
    'use strict';

    angular
        .module('radiatorApp')
        .controller('RBuildStateController', RBuildStateController);

    RBuildStateController.$inject = ['$scope', '$state', 'RBuildState', 'RRefresher'];

    function RBuildStateController ($scope, $state, RBuildState, RRefresher) {
        var vm = this;
        vm.buildStates = [];
        vm.loadAll = function() {
            RBuildState.query(function(result) {
                vm.buildStates = result;
            });
        };

        RRefresher.registrate(vm.loadAll);

        vm.loadAll();
    }
})();
