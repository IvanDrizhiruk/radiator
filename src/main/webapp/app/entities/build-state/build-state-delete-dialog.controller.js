(function() {
    'use strict';

    angular
        .module('radiatorApp')
        .controller('BuildStateDeleteController',BuildStateDeleteController);

    BuildStateDeleteController.$inject = ['$uibModalInstance', 'entity', 'BuildState'];

    function BuildStateDeleteController($uibModalInstance, entity, BuildState) {
        var vm = this;
        vm.buildState = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            BuildState.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
