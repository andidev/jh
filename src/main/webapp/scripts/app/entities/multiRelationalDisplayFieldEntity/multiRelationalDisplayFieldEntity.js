'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('multiRelationalDisplayFieldEntity', {
                parent: 'entity',
                url: '/multiRelationalDisplayFieldEntitys',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.multiRelationalDisplayFieldEntity.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/multiRelationalDisplayFieldEntity/multiRelationalDisplayFieldEntitys.html',
                        controller: 'MultiRelationalDisplayFieldEntityController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('multiRelationalDisplayFieldEntity');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('multiRelationalDisplayFieldEntity.detail', {
                parent: 'entity',
                url: '/multiRelationalDisplayFieldEntity/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.multiRelationalDisplayFieldEntity.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/multiRelationalDisplayFieldEntity/multiRelationalDisplayFieldEntity-detail.html',
                        controller: 'MultiRelationalDisplayFieldEntityDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('multiRelationalDisplayFieldEntity');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'MultiRelationalDisplayFieldEntity', function($stateParams, MultiRelationalDisplayFieldEntity) {
                        return MultiRelationalDisplayFieldEntity.get({id : $stateParams.id});
                    }]
                }
            })
            .state('multiRelationalDisplayFieldEntity.new', {
                parent: 'multiRelationalDisplayFieldEntity',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/multiRelationalDisplayFieldEntity/multiRelationalDisplayFieldEntity-dialog.html',
                        controller: 'MultiRelationalDisplayFieldEntityDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('multiRelationalDisplayFieldEntity', null, { reload: true });
                    }, function() {
                        $state.go('multiRelationalDisplayFieldEntity');
                    })
                }]
            })
            .state('multiRelationalDisplayFieldEntity.edit', {
                parent: 'multiRelationalDisplayFieldEntity',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/multiRelationalDisplayFieldEntity/multiRelationalDisplayFieldEntity-dialog.html',
                        controller: 'MultiRelationalDisplayFieldEntityDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['MultiRelationalDisplayFieldEntity', function(MultiRelationalDisplayFieldEntity) {
                                return MultiRelationalDisplayFieldEntity.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('multiRelationalDisplayFieldEntity', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
