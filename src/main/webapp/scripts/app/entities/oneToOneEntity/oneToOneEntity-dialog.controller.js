'use strict';

angular.module('jhipsterApp').controller('OneToOneEntityDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'OneToOneEntity', 'MultiRelationalEntity',
        function($scope, $stateParams, $modalInstance, entity, OneToOneEntity, MultiRelationalEntity) {

        $scope.oneToOneEntity = entity;
        $scope.multirelationalentitys = MultiRelationalEntity.query();
        $scope.load = function(id) {
            OneToOneEntity.get({id : id}, function(result) {
                $scope.oneToOneEntity = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('jhipsterApp:oneToOneEntityUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.oneToOneEntity.id != null) {
                OneToOneEntity.update($scope.oneToOneEntity, onSaveFinished);
            } else {
                OneToOneEntity.save($scope.oneToOneEntity, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
