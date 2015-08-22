'use strict';

angular.module('jhipsterApp')
    .controller('OneToOneEntityDetailController', function ($scope, $rootScope, $stateParams, entity, OneToOneEntity, MultiRelationalEntity) {
        $scope.oneToOneEntity = entity;
        $scope.load = function (id) {
            OneToOneEntity.get({id: id}, function(result) {
                $scope.oneToOneEntity = result;
            });
        };
        $rootScope.$on('jhipsterApp:oneToOneEntityUpdate', function(event, result) {
            $scope.oneToOneEntity = result;
        });
    });
