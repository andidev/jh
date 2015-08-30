'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('oneToOneEntity', {
                parent: 'entity',
                url: '/oneToOneEntitys',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.oneToOneEntity.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/oneToOneEntity/oneToOneEntitys.html',
                        controller: 'OneToOneEntityController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('oneToOneEntity');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('oneToOneEntity.detail', {
                parent: 'entity',
                url: '/oneToOneEntity/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.oneToOneEntity.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/oneToOneEntity/oneToOneEntity-detail.html',
                        controller: 'OneToOneEntityDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('oneToOneEntity');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'OneToOneEntity', function($stateParams, OneToOneEntity) {
                        return OneToOneEntity.get({id : $stateParams.id});
                    }]
                }
            })
            .state('oneToOneEntity.new', {
                parent: 'oneToOneEntity',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/oneToOneEntity/oneToOneEntity-dialog.html',
                        controller: 'OneToOneEntityDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('oneToOneEntity', null, { reload: true });
                    }, function() {
                        $state.go('oneToOneEntity');
                    })
                }]
            })
            .state('oneToOneEntity.edit', {
                parent: 'oneToOneEntity',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/oneToOneEntity/oneToOneEntity-dialog.html',
                        controller: 'OneToOneEntityDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['OneToOneEntity', function(OneToOneEntity) {
                                return OneToOneEntity.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('oneToOneEntity', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
