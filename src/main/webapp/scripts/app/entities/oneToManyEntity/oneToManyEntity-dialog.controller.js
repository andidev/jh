'use strict';

angular.module('jhipsterApp').controller('OneToManyEntityDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'OneToManyEntity', 'MultiRelationalEntity',
        function($scope, $stateParams, $modalInstance, entity, OneToManyEntity, MultiRelationalEntity) {

        $scope.oneToManyEntity = entity;
        $scope.multirelationalentitys = MultiRelationalEntity.query();
        $scope.load = function(id) {
            OneToManyEntity.get({id : id}, function(result) {
                $scope.oneToManyEntity = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('jhipsterApp:oneToManyEntityUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.oneToManyEntity.id != null) {
                OneToManyEntity.update($scope.oneToManyEntity, onSaveFinished);
            } else {
                OneToManyEntity.save($scope.oneToManyEntity, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
