(function() {
    'use strict';
    angular
        .module('radiatorApp')
        .factory('KanbanFlowBoard', KanbanFlowBoard);

    KanbanFlowBoard.$inject = ['$resource'];

    function KanbanFlowBoard ($resource) {
        var resourceUrl =  'api/kanban-flow-boards/:id';

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
