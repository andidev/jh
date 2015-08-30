'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('oneToManyDisplayFieldEntity', {
                parent: 'entity',
                url: '/oneToManyDisplayFieldEntitys',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.oneToManyDisplayFieldEntity.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/oneToManyDisplayFieldEntity/oneToManyDisplayFieldEntitys.html',
                        controller: 'OneToManyDisplayFieldEntityController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('oneToManyDisplayFieldEntity');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('oneToManyDisplayFieldEntity.detail', {
                parent: 'entity',
                url: '/oneToManyDisplayFieldEntity/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.oneToManyDisplayFieldEntity.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/oneToManyDisplayFieldEntity/oneToManyDisplayFieldEntity-detail.html',
                        controller: 'OneToManyDisplayFieldEntityDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('oneToManyDisplayFieldEntity');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'OneToManyDisplayFieldEntity', function($stateParams, OneToManyDisplayFieldEntity) {
                        return OneToManyDisplayFieldEntity.get({id : $stateParams.id});
                    }]
                }
            })
            .state('oneToManyDisplayFieldEntity.new', {
                parent: 'oneToManyDisplayFieldEntity',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/oneToManyDisplayFieldEntity/oneToManyDisplayFieldEntity-dialog.html',
                        controller: 'OneToManyDisplayFieldEntityDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {displayField: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('oneToManyDisplayFieldEntity', null, { reload: true });
                    }, function() {
                        $state.go('oneToManyDisplayFieldEntity');
                    })
                }]
            })
            .state('oneToManyDisplayFieldEntity.edit', {
                parent: 'oneToManyDisplayFieldEntity',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/oneToManyDisplayFieldEntity/oneToManyDisplayFieldEntity-dialog.html',
                        controller: 'OneToManyDisplayFieldEntityDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['OneToManyDisplayFieldEntity', function(OneToManyDisplayFieldEntity) {
                                return OneToManyDisplayFieldEntity.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('oneToManyDisplayFieldEntity', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
