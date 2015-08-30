'use strict';

angular.module('jhipsterApp')
    .controller('MultiRelationalDisplayFieldEntityDetailController', function ($scope, $rootScope, $stateParams, entity, MultiRelationalDisplayFieldEntity, OneToOneDisplayFieldEntity, ManyToManyDisplayFieldEntity, OneToManyDisplayFieldEntity) {
        $scope.multiRelationalDisplayFieldEntity = entity;
        $scope.load = function (id) {
            MultiRelationalDisplayFieldEntity.get({id: id}, function(result) {
                $scope.multiRelationalDisplayFieldEntity = result;
            });
        };
        $rootScope.$on('jhipsterApp:multiRelationalDisplayFieldEntityUpdate', function(event, result) {
            $scope.multiRelationalDisplayFieldEntity = result;
        });
    });
