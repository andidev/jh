'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('oneToManyEntity', {
                parent: 'entity',
                url: '/oneToManyEntitys',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.oneToManyEntity.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/oneToManyEntity/oneToManyEntitys.html',
                        controller: 'OneToManyEntityController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('oneToManyEntity');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('oneToManyEntity.detail', {
                parent: 'entity',
                url: '/oneToManyEntity/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.oneToManyEntity.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/oneToManyEntity/oneToManyEntity-detail.html',
                        controller: 'OneToManyEntityDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('oneToManyEntity');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'OneToManyEntity', function($stateParams, OneToManyEntity) {
                        return OneToManyEntity.get({id : $stateParams.id});
                    }]
                }
            })
            .state('oneToManyEntity.new', {
                parent: 'oneToManyEntity',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/oneToManyEntity/oneToManyEntity-dialog.html',
                        controller: 'OneToManyEntityDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('oneToManyEntity', null, { reload: true });
                    }, function() {
                        $state.go('oneToManyEntity');
                    })
                }]
            })
            .state('oneToManyEntity.edit', {
                parent: 'oneToManyEntity',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/oneToManyEntity/oneToManyEntity-dialog.html',
                        controller: 'OneToManyEntityDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['OneToManyEntity', function(OneToManyEntity) {
                                return OneToManyEntity.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('oneToManyEntity', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
