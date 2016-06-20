(function() {
    'use strict';

    angular
        .module('radiatorApp')
        .directive('rIntegrationTestResults', rIntegrationTestResults);

    function rIntegrationTestResults() {
        var directive = {
            restrict : 'E',
            scope: { items: '=' },
            templateUrl : 'app/radiator/integration-test-result/integration-test-results.html'
        };

        return directive;
    }
})();
