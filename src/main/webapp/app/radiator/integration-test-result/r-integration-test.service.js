(function() {
    'use strict';
    angular
        .module('radiatorApp')
        .factory('RIntegrationTest', rIntegrationTest);

    rIntegrationTest.$inject = ['$resource', 'DateUtils'];

    function rIntegrationTest ($resource, DateUtils) {
        var resourceUrl =  'api/report/integration-test/last';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
            //,
            //'get': {
            //    method: 'GET',
            //    transformResponse: function (data) {
            //        if (data) {
            //            data = angular.fromJson(data);
            //            data.extractingDate = DateUtils.convertDateTimeFromServer(data.extractingDate);
            //        }
            //        return data;
            //    }
            //}
        });
    }
})();
