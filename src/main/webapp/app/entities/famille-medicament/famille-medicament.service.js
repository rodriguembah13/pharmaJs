(function() {
    'use strict';
    angular
        .module('pharmaApp')
        .factory('FamilleMedicament', FamilleMedicament);

    FamilleMedicament.$inject = ['$resource'];

    function FamilleMedicament ($resource) {
        var resourceUrl =  'api/famille-medicaments/:id';

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
