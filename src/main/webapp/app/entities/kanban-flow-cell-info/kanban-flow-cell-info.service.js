(function() {
    'use strict';
    angular
        .module('radiatorApp')
        .factory('KanbanFlowCellInfo', KanbanFlowCellInfo);

    KanbanFlowCellInfo.$inject = ['$resource'];

    function KanbanFlowCellInfo ($resource) {
        var resourceUrl =  'api/kanban-flow-cell-infos/:id';

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
