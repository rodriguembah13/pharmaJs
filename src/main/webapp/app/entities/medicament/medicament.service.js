(function() {
    'use strict';
    angular
        .module('pharmaApp')
        .factory('Medicament', Medicament);

    Medicament.$inject = ['$resource'];

    function Medicament ($resource) {
        var resourceUrl =  'api/medicaments/:id';

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
