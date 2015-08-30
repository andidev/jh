'use strict';

angular.module('jhipsterApp').controller('OneToManyDisplayFieldEntityDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'OneToManyDisplayFieldEntity', 'MultiRelationalDisplayFieldEntity',
        function($scope, $stateParams, $modalInstance, entity, OneToManyDisplayFieldEntity, MultiRelationalDisplayFieldEntity) {

        $scope.oneToManyDisplayFieldEntity = entity;
        $scope.multirelationaldisplayfieldentitys = MultiRelationalDisplayFieldEntity.query();
        $scope.load = function(id) {
            OneToManyDisplayFieldEntity.get({id : id}, function(result) {
                $scope.oneToManyDisplayFieldEntity = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('jhipsterApp:oneToManyDisplayFieldEntityUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.oneToManyDisplayFieldEntity.id != null) {
                OneToManyDisplayFieldEntity.update($scope.oneToManyDisplayFieldEntity, onSaveFinished);
            } else {
                OneToManyDisplayFieldEntity.save($scope.oneToManyDisplayFieldEntity, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
