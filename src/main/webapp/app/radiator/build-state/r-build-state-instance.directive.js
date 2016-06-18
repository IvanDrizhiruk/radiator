(function() {
    'use strict';

    angular
        .module('radiatorApp')
        .directive('rBuildStateInstance', rBuildStateInstance);

    function rBuildStateInstance() {
        var directive = {
            restrict : 'E',
            scope: { item: '=' },
            templateUrl : 'app/radiator/build-state/build-state-instance.html',
            bindToController: true,
            controller: rBuildStateInstanceController,
            controllerAs: 'vm'
        };

        return directive;
    }

    SortController.$inject = ['$scope', 'md5'];

    function rBuildStateInstanceController ($scope, md5) {
        var vm = this;

        vm.emailToImagePath = function (email) {
            return "http://www.gravatar.com/avatar/"
                + md5.createHash($.trim(email).toLowerCase())
                + "?d=none&s=192";
        }
    }

})();
