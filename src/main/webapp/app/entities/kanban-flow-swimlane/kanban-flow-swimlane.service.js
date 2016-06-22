(function() {
    'use strict';
    angular
        .module('radiatorApp')
        .factory('KanbanFlowSwimlane', KanbanFlowSwimlane);

    KanbanFlowSwimlane.$inject = ['$resource'];

    function KanbanFlowSwimlane ($resource) {
        var resourceUrl =  'api/kanban-flow-swimlanes/:id';

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
