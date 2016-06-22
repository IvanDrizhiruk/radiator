(function() {
    'use strict';

    angular
        .module('radiatorApp')
        .controller('KanbanFlowSwimlaneController', KanbanFlowSwimlaneController);

    KanbanFlowSwimlaneController.$inject = ['$scope', '$state', 'KanbanFlowSwimlane'];

    function KanbanFlowSwimlaneController ($scope, $state, KanbanFlowSwimlane) {
        var vm = this;
        vm.kanbanFlowSwimlanes = [];
        vm.loadAll = function() {
            KanbanFlowSwimlane.query(function(result) {
                vm.kanbanFlowSwimlanes = result;
            });
        };

        vm.loadAll();
        
    }
})();
