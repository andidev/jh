'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('oneToOneDisplayFieldEntity', {
                parent: 'entity',
                url: '/oneToOneDisplayFieldEntitys',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.oneToOneDisplayFieldEntity.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/oneToOneDisplayFieldEntity/oneToOneDisplayFieldEntitys.html',
                        controller: 'OneToOneDisplayFieldEntityController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('oneToOneDisplayFieldEntity');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('oneToOneDisplayFieldEntity.detail', {
                parent: 'entity',
                url: '/oneToOneDisplayFieldEntity/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.oneToOneDisplayFieldEntity.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/oneToOneDisplayFieldEntity/oneToOneDisplayFieldEntity-detail.html',
                        controller: 'OneToOneDisplayFieldEntityDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('oneToOneDisplayFieldEntity');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'OneToOneDisplayFieldEntity', function($stateParams, OneToOneDisplayFieldEntity) {
                        return OneToOneDisplayFieldEntity.get({id : $stateParams.id});
                    }]
                }
            })
            .state('oneToOneDisplayFieldEntity.new', {
                parent: 'oneToOneDisplayFieldEntity',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/oneToOneDisplayFieldEntity/oneToOneDisplayFieldEntity-dialog.html',
                        controller: 'OneToOneDisplayFieldEntityDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {displayField: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('oneToOneDisplayFieldEntity', null, { reload: true });
                    }, function() {
                        $state.go('oneToOneDisplayFieldEntity');
                    })
                }]
            })
            .state('oneToOneDisplayFieldEntity.edit', {
                parent: 'oneToOneDisplayFieldEntity',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/oneToOneDisplayFieldEntity/oneToOneDisplayFieldEntity-dialog.html',
                        controller: 'OneToOneDisplayFieldEntityDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['OneToOneDisplayFieldEntity', function(OneToOneDisplayFieldEntity) {
                                return OneToOneDisplayFieldEntity.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('oneToOneDisplayFieldEntity', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
