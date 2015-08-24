'use strict';

angular.module('jhipsterApp').controller('MultiRelationalDisplayFieldEntityDialogController',
    ['$scope', '$stateParams', '$modalInstance', '$q', 'entity', 'MultiRelationalDisplayFieldEntity', 'OneToOneDisplayFieldEntity', 'ManyToManyDisplayFieldEntity', 'OneToManyDisplayFieldEntity',
        function($scope, $stateParams, $modalInstance, $q, entity, MultiRelationalDisplayFieldEntity, OneToOneDisplayFieldEntity, ManyToManyDisplayFieldEntity, OneToManyDisplayFieldEntity) {

        $scope.multiRelationalDisplayFieldEntity = entity;
        $scope.onetoonedisplayfieldentitys = OneToOneDisplayFieldEntity.query({filter: 'multirelationaldisplayfieldentity-is-null'});
        $q.all([$scope.multiRelationalDisplayFieldEntity.$promise, $scope.onetoonedisplayfieldentitys.$promise]).then(function() {
            if (!$scope.multiRelationalDisplayFieldEntity.oneToOneDisplayFieldEntity.id) {
                return $q.reject();
            }
            return OneToOneDisplayFieldEntity.get({id : $scope.multiRelationalDisplayFieldEntity.oneToOneDisplayFieldEntity.id}).$promise;
        }).then(function(oneToOneDisplayFieldEntity) {
            $scope.onetoonedisplayfieldentitys.push(oneToOneDisplayFieldEntity);
        });
        $scope.manytomanydisplayfieldentitys = ManyToManyDisplayFieldEntity.query();
        $scope.onetomanydisplayfieldentitys = OneToManyDisplayFieldEntity.query();
        $scope.load = function(id) {
            MultiRelationalDisplayFieldEntity.get({id : id}, function(result) {
                $scope.multiRelationalDisplayFieldEntity = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('jhipsterApp:multiRelationalDisplayFieldEntityUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.multiRelationalDisplayFieldEntity.id != null) {
                MultiRelationalDisplayFieldEntity.update($scope.multiRelationalDisplayFieldEntity, onSaveFinished);
            } else {
                MultiRelationalDisplayFieldEntity.save($scope.multiRelationalDisplayFieldEntity, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
