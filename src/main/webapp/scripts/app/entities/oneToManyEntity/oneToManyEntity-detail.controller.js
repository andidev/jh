'use strict';

angular.module('jhipsterApp')
    .controller('OneToManyEntityDetailController', function ($scope, $rootScope, $stateParams, entity, OneToManyEntity, MultiRelationalEntity) {
        $scope.oneToManyEntity = entity;
        $scope.load = function (id) {
            OneToManyEntity.get({id: id}, function(result) {
                $scope.oneToManyEntity = result;
            });
        };
        $rootScope.$on('jhipsterApp:oneToManyEntityUpdate', function(event, result) {
            $scope.oneToManyEntity = result;
        });
    });
