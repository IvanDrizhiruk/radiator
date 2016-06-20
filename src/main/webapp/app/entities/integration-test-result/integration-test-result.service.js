(function() {
    'use strict';
    angular
        .module('radiatorApp')
        .factory('IntegrationTestResult', IntegrationTestResult);

    IntegrationTestResult.$inject = ['$resource', 'DateUtils'];

    function IntegrationTestResult ($resource, DateUtils) {
        var resourceUrl =  'api/integration-test-results/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.extractingDate = DateUtils.convertDateTimeFromServer(data.extractingDate);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
