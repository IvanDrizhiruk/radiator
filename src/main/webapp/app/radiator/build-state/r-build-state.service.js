(function() {
    'use strict';
    angular
        .module('radiatorApp')
        .factory('RBuildState', RBuildState);

    RBuildState.$inject = ['$resource', 'DateUtils'];

    function RBuildState ($resource, DateUtils) {
        var resourceUrl =  'api/report/build-states/last';

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
