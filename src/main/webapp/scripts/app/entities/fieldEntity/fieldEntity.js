'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('fieldEntity', {
                parent: 'entity',
                url: '/fieldEntitys',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.fieldEntity.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/fieldEntity/fieldEntitys.html',
                        controller: 'FieldEntityController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('fieldEntity');
                        $translatePartialLoader.addPart('language');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('fieldEntity.detail', {
                parent: 'entity',
                url: '/fieldEntity/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.fieldEntity.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/fieldEntity/fieldEntity-detail.html',
                        controller: 'FieldEntityDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('fieldEntity');
                        $translatePartialLoader.addPart('language');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'FieldEntity', function($stateParams, FieldEntity) {
                        return FieldEntity.get({id : $stateParams.id});
                    }]
                }
            })
            .state('fieldEntity.new', {
                parent: 'fieldEntity',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/fieldEntity/fieldEntity-dialog.html',
                        controller: 'FieldEntityDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {name: null, email: null, aDouble: null, aRequiredDouble: null, anEnum: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('fieldEntity', null, { reload: true });
                    }, function() {
                        $state.go('fieldEntity');
                    })
                }]
            })
            .state('fieldEntity.edit', {
                parent: 'fieldEntity',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/fieldEntity/fieldEntity-dialog.html',
                        controller: 'FieldEntityDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['FieldEntity', function(FieldEntity) {
                                return FieldEntity.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('fieldEntity', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
