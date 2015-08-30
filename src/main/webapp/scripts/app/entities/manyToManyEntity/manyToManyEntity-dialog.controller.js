'use strict';

angular.module('jhipsterApp').controller('ManyToManyEntityDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'ManyToManyEntity', 'MultiRelationalEntity', 'User',
        function($scope, $stateParams, $modalInstance, entity, ManyToManyEntity, MultiRelationalEntity, User) {

        $scope.manyToManyEntity = entity;
        $scope.multirelationalentitys = MultiRelationalEntity.query();
        $scope.users = User.query();
        $scope.load = function(id) {
            ManyToManyEntity.get({id : id}, function(result) {
                $scope.manyToManyEntity = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('jhipsterApp:manyToManyEntityUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.manyToManyEntity.id != null) {
                ManyToManyEntity.update($scope.manyToManyEntity, onSaveFinished);
            } else {
                ManyToManyEntity.save($scope.manyToManyEntity, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
