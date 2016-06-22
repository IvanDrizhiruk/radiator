(function() {
    'use strict';
    angular
        .module('radiatorApp')
        .factory('KanbanFlowColumn', KanbanFlowColumn);

    KanbanFlowColumn.$inject = ['$resource'];

    function KanbanFlowColumn ($resource) {
        var resourceUrl =  'api/kanban-flow-columns/:id';

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
