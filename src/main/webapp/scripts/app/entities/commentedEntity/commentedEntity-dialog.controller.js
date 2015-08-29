'use strict';

angular.module('jhipsterApp').controller('CommentedEntityDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'CommentedEntity', 'CommentedRelationship',
        function($scope, $stateParams, $modalInstance, entity, CommentedEntity, CommentedRelationship) {

        $scope.commentedEntity = entity;
        $scope.commentedrelationships = CommentedRelationship.query();
        $scope.load = function(id) {
            CommentedEntity.get({id : id}, function(result) {
                $scope.commentedEntity = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('jhipsterApp:commentedEntityUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.commentedEntity.id != null) {
                CommentedEntity.update($scope.commentedEntity, onSaveFinished);
            } else {
                CommentedEntity.save($scope.commentedEntity, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
