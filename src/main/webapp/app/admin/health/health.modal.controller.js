'use strict';

angular.module('jhApp')
    .controller('HealthModalController', function($scope, $uibModalInstance, currentHealth, baseName, subSystemName) {

        $scope.currentHealth = currentHealth;
        $scope.baseName = baseName;
        $scope.subSystemName = subSystemName;

        $scope.cancel = function() {
            $uibModalInstance.dismiss('cancel');
        };
    });
