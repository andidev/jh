'use strict';

angular.module('jhipsterApp').controller('MultiRelationalEntityDialogController',
    ['$scope', '$stateParams', '$modalInstance', '$q', 'entity', 'MultiRelationalEntity', 'OneToOneEntity', 'ManyToManyEntity', 'OneToManyEntity',
        function($scope, $stateParams, $modalInstance, $q, entity, MultiRelationalEntity, OneToOneEntity, ManyToManyEntity, OneToManyEntity) {

        $scope.multiRelationalEntity = entity;
        $scope.onetooneentitys = OneToOneEntity.query({filter: 'multirelationalentity-is-null'});
        $q.all([$scope.multiRelationalEntity.$promise, $scope.onetooneentitys.$promise]).then(function() {
            if (!$scope.multiRelationalEntity.oneToOneEntityId) {
                return $q.reject();
            }
            return OneToOneEntity.get({id : $scope.multiRelationalEntity.oneToOneEntityId}).$promise;
        }).then(function(oneToOneEntity) {
            $scope.onetooneentitys.push(oneToOneEntity);
        });
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
