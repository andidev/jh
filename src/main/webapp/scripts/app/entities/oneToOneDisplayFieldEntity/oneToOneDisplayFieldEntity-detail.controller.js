'use strict';

angular.module('jhipsterApp')
    .controller('OneToOneDisplayFieldEntityDetailController', function ($scope, $rootScope, $stateParams, entity, OneToOneDisplayFieldEntity, MultiRelationalDisplayFieldEntity) {
        $scope.oneToOneDisplayFieldEntity = entity;
        $scope.load = function (id) {
            OneToOneDisplayFieldEntity.get({id: id}, function(result) {
                $scope.oneToOneDisplayFieldEntity = result;
            });
        };
        $rootScope.$on('jhipsterApp:oneToOneDisplayFieldEntityUpdate', function(event, result) {
            $scope.oneToOneDisplayFieldEntity = result;
        });
    });
