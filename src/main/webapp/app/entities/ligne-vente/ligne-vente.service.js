(function() {
    'use strict';
    angular
        .module('pharmaApp')
        .factory('LigneVente', LigneVente);

    LigneVente.$inject = ['$resource'];

    function LigneVente ($resource) {
        var resourceUrl =  'api/ligne-ventes/:id';

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
