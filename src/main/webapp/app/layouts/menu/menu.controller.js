(function() {
    'use strict';

    angular
        .module('pharmaApp')
        .controller('MenuController', MenuController);

    MenuController.$inject = ['$state', 'Auth', 'Principal', 'ProfileService', 'LoginService','$scope'];

    function MenuController ($state, Auth, Principal, ProfileService, LoginService,$scope) {
        var vm = this;

        vm.isMenuCollapsed = true;
        vm.isAuthenticated = Principal.isAuthenticated;

        ProfileService.getProfileInfo().then(function(response) {
            vm.inProduction = response.inProduction;
            vm.swaggerEnabled = response.swaggerEnabled;
        });

        vm.login = login;
        vm.logout = logout;
        vm.toggleMenu = toggleMenu;
        vm.collapseMenu = collapseMenu;
        vm.$state = $state;

        function login() {
            collapseMenu();
            LoginService.open();
        }
$scope.groups = [
    {
      title: "Dynamic Group Header - 1",
      content: "Dynamic Group Body - 1",
      open: false
    },
    {
      title: "Dynamic Group Header - 2",
      content: "Dynamic Group Body - 2",
      open: false
    }
  ];
  
  $scope.addNew = function() {
    $scope.groups.push({
      title: "New One Created",
      content: "Dynamically added new one",
      open: false
    });
  }
        function logout() {
            collapseMenu();
            Auth.logout();
            $state.go('home');
        }

        function toggleMenu() {
            vm.isNavbarCollapsed = !vm.isNavbarCollapsed;
        }

        function collapseMenu() {
            vm.isMenuCollapsed = true;
        }
    }
})();
