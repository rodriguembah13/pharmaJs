'use strict';

describe('Controller Tests', function() {

    describe('LigneVente Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockLigneVente, MockMedicament, MockVente, MockClient;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockLigneVente = jasmine.createSpy('MockLigneVente');
            MockMedicament = jasmine.createSpy('MockMedicament');
            MockVente = jasmine.createSpy('MockVente');
            MockClient = jasmine.createSpy('MockClient');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'LigneVente': MockLigneVente,
                'Medicament': MockMedicament,
                'Vente': MockVente,
                'Client': MockClient
            };
            createController = function() {
                $injector.get('$controller')("LigneVenteDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'pharmaApp:ligneVenteUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
