'use strict';

angular.module('jhipsterApp').controller('OneToOneEntityDialogController',
    ['$scope', '$stateParams', '$modalInstance', '$q', 'entity', 'OneToOneEntity', 'MultiRelationalEntity', 'User',
        function($scope, $stateParams, $modalInstance, $q, entity, OneToOneEntity, MultiRelationalEntity, User) {

        $scope.oneToOneEntity = entity;
        $scope.multirelationalentitys = MultiRelationalEntity.query();
        $scope.users = User.query();
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
