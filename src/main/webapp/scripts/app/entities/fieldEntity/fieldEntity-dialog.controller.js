'use strict';

angular.module('jhipsterApp').controller('FieldEntityDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'FieldEntity',
        function($scope, $stateParams, $modalInstance, entity, FieldEntity) {

        $scope.fieldEntity = entity;
        $scope.load = function(id) {
            FieldEntity.get({id : id}, function(result) {
                $scope.fieldEntity = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('jhipsterApp:fieldEntityUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.fieldEntity.id != null) {
                FieldEntity.update($scope.fieldEntity, onSaveFinished);
            } else {
                FieldEntity.save($scope.fieldEntity, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
