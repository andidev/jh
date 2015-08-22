'use strict';

angular.module('jhipsterApp')
    .controller('OneToManyDisplayFieldEntityDetailController', function ($scope, $rootScope, $stateParams, entity, OneToManyDisplayFieldEntity, MultiRelationalDisplayFieldEntity) {
        $scope.oneToManyDisplayFieldEntity = entity;
        $scope.load = function (id) {
            OneToManyDisplayFieldEntity.get({id: id}, function(result) {
                $scope.oneToManyDisplayFieldEntity = result;
            });
        };
        $rootScope.$on('jhipsterApp:oneToManyDisplayFieldEntityUpdate', function(event, result) {
            $scope.oneToManyDisplayFieldEntity = result;
        });
    });
