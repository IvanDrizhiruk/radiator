(function() {
    'use strict';
    angular
        .module('radiatorApp')
        .factory('BuildState', BuildState);

    BuildState.$inject = ['$resource', 'DateUtils'];

    function BuildState ($resource, DateUtils) {
        var resourceUrl =  'api/build-states/:id';

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
