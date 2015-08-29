'use strict';

angular.module('jhipsterApp')
    .controller('CommentedEntityController', function ($scope, CommentedEntity) {
        $scope.commentedEntitys = [];
        $scope.loadAll = function() {
            CommentedEntity.query(function(result) {
               $scope.commentedEntitys = result;
            });
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            CommentedEntity.get({id: id}, function(result) {
                $scope.commentedEntity = result;
                $('#deleteCommentedEntityConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            CommentedEntity.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteCommentedEntityConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.commentedEntity = {commentedField: null, id: null};
        };
    });
