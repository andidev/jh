'use strict';

angular.module('jhipsterApp')
    .controller('ManyToManyDisplayFieldEntityDetailController', function ($scope, $rootScope, $stateParams, entity, ManyToManyDisplayFieldEntity, MultiRelationalDisplayFieldEntity) {
        $scope.manyToManyDisplayFieldEntity = entity;
        $scope.load = function (id) {
            ManyToManyDisplayFieldEntity.get({id: id}, function(result) {
                $scope.manyToManyDisplayFieldEntity = result;
            });
        };
        $rootScope.$on('jhipsterApp:manyToManyDisplayFieldEntityUpdate', function(event, result) {
            $scope.manyToManyDisplayFieldEntity = result;
        });
    });
