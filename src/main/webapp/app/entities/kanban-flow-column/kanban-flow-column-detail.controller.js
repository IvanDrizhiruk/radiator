(function() {
    'use strict';

    angular
        .module('radiatorApp')
        .controller('KanbanFlowColumnDetailController', KanbanFlowColumnDetailController);

    KanbanFlowColumnDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'KanbanFlowColumn'];

    function KanbanFlowColumnDetailController($scope, $rootScope, $stateParams, entity, KanbanFlowColumn) {
        var vm = this;
        vm.kanbanFlowColumn = entity;
        
        var unsubscribe = $rootScope.$on('radiatorApp:kanbanFlowColumnUpdate', function(event, result) {
            vm.kanbanFlowColumn = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
