(function() {
    'use strict';

    angular
        .module('radiatorApp')
        .controller('BuildStateController', BuildStateController);

    BuildStateController.$inject = ['$scope', '$state', 'BuildState'];

    function BuildStateController ($scope, $state, BuildState) {
        var vm = this;
        vm.buildStates = [];
        vm.loadAll = function() {
            BuildState.query(function(result) {
                vm.buildStates = result;
            });
        };

        vm.loadAll();
        
    }
})();
