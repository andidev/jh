'use strict';

angular.module('jhipsterApp')
    .controller('ManyToManyEntityDetailController', function ($scope, $rootScope, $stateParams, entity, ManyToManyEntity, MultiRelationalEntity) {
        $scope.manyToManyEntity = entity;
        $scope.load = function (id) {
            ManyToManyEntity.get({id: id}, function(result) {
                $scope.manyToManyEntity = result;
            });
        };
        $rootScope.$on('jhipsterApp:manyToManyEntityUpdate', function(event, result) {
            $scope.manyToManyEntity = result;
        });
    });
