'use strict';

angular.module('jhipsterApp')
    .controller('MultiRelationalEntityDetailController', function ($scope, $rootScope, $stateParams, entity, MultiRelationalEntity, OneToOneEntity, ManyToManyEntity, OneToManyEntity) {
        $scope.multiRelationalEntity = entity;
        $scope.load = function (id) {
            MultiRelationalEntity.get({id: id}, function(result) {
                $scope.multiRelationalEntity = result;
            });
        };
        $rootScope.$on('jhipsterApp:multiRelationalEntityUpdate', function(event, result) {
            $scope.multiRelationalEntity = result;
        });
    });
