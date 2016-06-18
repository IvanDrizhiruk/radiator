(function() {
    'use strict';

    angular
        .module('radiatorApp')
        .controller('CommiterController', CommiterController);

    CommiterController.$inject = ['$scope', '$state', 'Commiter'];

    function CommiterController ($scope, $state, Commiter) {
        var vm = this;
        vm.commiters = [];
        vm.loadAll = function() {
            Commiter.query(function(result) {
                vm.commiters = result;
            });
        };

        vm.loadAll();
        
    }
})();
