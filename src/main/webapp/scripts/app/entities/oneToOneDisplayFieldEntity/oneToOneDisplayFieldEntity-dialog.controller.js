'use strict';

angular.module('jhipsterApp').controller('OneToOneDisplayFieldEntityDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'OneToOneDisplayFieldEntity', 'MultiRelationalDisplayFieldEntity',
        function($scope, $stateParams, $modalInstance, entity, OneToOneDisplayFieldEntity, MultiRelationalDisplayFieldEntity) {

        $scope.oneToOneDisplayFieldEntity = entity;
        $scope.multirelationaldisplayfieldentitys = MultiRelationalDisplayFieldEntity.query();
        $scope.load = function(id) {
            OneToOneDisplayFieldEntity.get({id : id}, function(result) {
                $scope.oneToOneDisplayFieldEntity = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('jhipsterApp:oneToOneDisplayFieldEntityUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.oneToOneDisplayFieldEntity.id != null) {
                OneToOneDisplayFieldEntity.update($scope.oneToOneDisplayFieldEntity, onSaveFinished);
            } else {
                OneToOneDisplayFieldEntity.save($scope.oneToOneDisplayFieldEntity, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
