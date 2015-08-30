'use strict';

angular.module('jhipsterApp')
    .controller('OneController', function ($scope, One) {
        $scope.ones = [];
        $scope.loadAll = function() {
            One.query(function(result) {
               $scope.ones = result;
            });
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            One.get({id: id}, function(result) {
                $scope.one = result;
                $('#deleteOneConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            One.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteOneConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.one = {image: null, id: null};
        };

        $scope.abbreviate = function (text) {
            if (!angular.isString(text)) {
                return '';
            }
            if (text.length < 30) {
                return text;
            }
            return text ? (text.substring(0, 15) + '...' + text.slice(-10)) : '';
        };

        $scope.byteSize = function (base64String) {
            if (!angular.isString(base64String)) {
                return '';
            }
            function endsWith(suffix, str) {
                return str.indexOf(suffix, str.length - suffix.length) !== -1;
            }
            function paddingSize(base64String) {
                if (endsWith('==', base64String)) {
                    return 2;
                }
                if (endsWith('=', base64String)) {
                    return 1;
                }
                return 0;
            }
            function size(base64String) {
                return base64String.length / 4 * 3 - paddingSize(base64String);
            }
            function formatAsBytes(size) {
                return size.toString().replace(/\B(?=(\d{3})+(?!\d))/g, " ") + " bytes";
            }

            return formatAsBytes(size(base64String));
        };
    });
