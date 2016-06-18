(function() {
    'use strict';
    angular
        .module('radiatorApp')
        .factory('Commiter', Commiter);

    Commiter.$inject = ['$resource'];

    function Commiter ($resource) {
        var resourceUrl =  'api/commiters/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
