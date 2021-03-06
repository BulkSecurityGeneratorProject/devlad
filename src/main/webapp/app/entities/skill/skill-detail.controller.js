(function() {
    'use strict';

    angular
        .module('devladApp')
        .controller('SkillDetailController', SkillDetailController);

    SkillDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Skill', 'SkillSet'];

    function SkillDetailController($scope, $rootScope, $stateParams, previousState, entity, Skill, SkillSet) {
        var vm = this;

        vm.skill = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('devladApp:skillUpdate', function(event, result) {
            vm.skill = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
