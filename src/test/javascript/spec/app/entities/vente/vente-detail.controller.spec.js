'use strict';

describe('Controller Tests', function() {

    describe('Vente Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockVente, MockClient, MockLigneVente, MockUser;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockVente = jasmine.createSpy('MockVente');
            MockClient = jasmine.createSpy('MockClient');
            MockLigneVente = jasmine.createSpy('MockLigneVente');
            MockUser = jasmine.createSpy('MockUser');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Vente': MockVente,
                'Client': MockClient,
                'LigneVente': MockLigneVente,
                'User': MockUser
            };
            createController = function() {
                $injector.get('$controller')("VenteDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'pharmaApp:venteUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
