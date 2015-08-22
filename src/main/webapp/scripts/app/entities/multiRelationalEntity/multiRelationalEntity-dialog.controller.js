'use strict';

angular.module('jhipsterApp').controller('MultiRelationalEntityDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'MultiRelationalEntity', 'OneToOneEntity', 'ManyToManyEntity', 'OneToManyEntity',
        function($scope, $stateParams, $modalInstance, entity, MultiRelationalEntity, OneToOneEntity, ManyToManyEntity, OneToManyEntity) {

        $scope.multiRelationalEntity = entity;
        $scope.onetooneentitys = OneToOneEntity.query({filter: 'multirelationalentity-is-null'});
        $scope.manytomanyentitys = ManyToManyEntity.query();
        $scope.onetomanyentitys = OneToManyEntity.query();
        $scope.load = function(id) {
            MultiRelationalEntity.get({id : id}, function(result) {
                $scope.multiRelationalEntity = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('jhipsterApp:multiRelationalEntityUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.multiRelationalEntity.id != null) {
                MultiRelationalEntity.update($scope.multiRelationalEntity, onSaveFinished);
            } else {
                MultiRelationalEntity.save($scope.multiRelationalEntity, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
