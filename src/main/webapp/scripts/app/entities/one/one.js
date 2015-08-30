'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('one', {
                parent: 'entity',
                url: '/ones',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.one.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/one/ones.html',
                        controller: 'OneController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('one');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('one.detail', {
                parent: 'entity',
                url: '/one/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.one.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/one/one-detail.html',
                        controller: 'OneDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('one');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'One', function($stateParams, One) {
                        return One.get({id : $stateParams.id});
                    }]
                }
            })
            .state('one.new', {
                parent: 'one',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/one/one-dialog.html',
                        controller: 'OneDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {image: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('one', null, { reload: true });
                    }, function() {
                        $state.go('one');
                    })
                }]
            })
            .state('one.edit', {
                parent: 'one',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/one/one-dialog.html',
                        controller: 'OneDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['One', function(One) {
                                return One.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('one', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
