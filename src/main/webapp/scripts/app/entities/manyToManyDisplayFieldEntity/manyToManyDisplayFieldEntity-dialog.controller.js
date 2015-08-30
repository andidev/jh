'use strict';

angular.module('jhipsterApp').controller('ManyToManyDisplayFieldEntityDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'ManyToManyDisplayFieldEntity', 'MultiRelationalDisplayFieldEntity',
        function($scope, $stateParams, $modalInstance, entity, ManyToManyDisplayFieldEntity, MultiRelationalDisplayFieldEntity) {

        $scope.manyToManyDisplayFieldEntity = entity;
        $scope.multirelationaldisplayfieldentitys = MultiRelationalDisplayFieldEntity.query();
        $scope.load = function(id) {
            ManyToManyDisplayFieldEntity.get({id : id}, function(result) {
                $scope.manyToManyDisplayFieldEntity = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('jhipsterApp:manyToManyDisplayFieldEntityUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.manyToManyDisplayFieldEntity.id != null) {
                ManyToManyDisplayFieldEntity.update($scope.manyToManyDisplayFieldEntity, onSaveFinished);
            } else {
                ManyToManyDisplayFieldEntity.save($scope.manyToManyDisplayFieldEntity, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
