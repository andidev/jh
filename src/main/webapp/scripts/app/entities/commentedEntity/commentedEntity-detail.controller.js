'use strict';

angular.module('jhipsterApp')
    .controller('CommentedEntityDetailController', function ($scope, $rootScope, $stateParams, entity, CommentedEntity, CommentedRelationship) {
        $scope.commentedEntity = entity;
        $scope.load = function (id) {
            CommentedEntity.get({id: id}, function(result) {
                $scope.commentedEntity = result;
            });
        };
        $rootScope.$on('jhipsterApp:commentedEntityUpdate', function(event, result) {
            $scope.commentedEntity = result;
        });
    });
