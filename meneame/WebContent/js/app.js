angular.module('meneame', ["ngRoute"])
.config(function($routeProvider){
    $routeProvider
    	.when("/",  {
    		controller: "listNews",
    		controllerAs: "vm",
    		templateUrl: "pages/listNews.html",
    		resolve: {
    		      // provoca 100 milisegundos (0,1 segundos) de delay que
					// deberían ser suficientes para que el servidor haga
					// cualquier actualización que se le haya pedido antes de
					// leer las órdenes.
    			  // extraído del script.js utilizado como ejemplo en
					// https://docs.angularjs.org/api/ngRoute/service/$route
    		      delay: function($q, $timeout) {
    		        var delay = $q.defer();
    		        $timeout(delay.resolve, 500);
    		        return delay.promise;
    		      }
    		}
    	})
        .when("/Categoria/:categoria", {
            controller: "listNews",
            controllerAs: "vm",
            templateUrl: "pages/listNews.html",
    		resolve: {
  		      // provoca 100 milisegundos (0,1 segundos) de delay que deberían
				// ser suficientes para que el servidor haga cualquier
				// actualización que se le haya pedido antes de leer las
				// órdenes.
  			  // extraído del script.js utilizado como ejemplo en
				// https://docs.angularjs.org/api/ngRoute/service/$route
  		      delay: function($q, $timeout) {
  		        var delay = $q.defer();
  		        $timeout(delay.resolve, 100);
  		        return delay.promise;
  		      }
  		}
        })
        .when("/misNoticias/:usuario", {
            controller: "listNews",
            controllerAs: "vm",
            templateUrl: "pages/listNews.html",
    		resolve: {
  		      // provoca 100 milisegundos (0,1 segundos) de delay que deberían
				// ser suficientes para que el servidor haga cualquier
				// actualización que se le haya pedido antes de leer las
				// órdenes.
  			  // extraído del script.js utilizado como ejemplo en
				// https://docs.angularjs.org/api/ngRoute/service/$route
  		      delay: function($q, $timeout) {
  		        var delay = $q.defer();
  		        $timeout(delay.resolve, 100);
  		        return delay.promise;
  		      }
  		}
        })
        .when("/Populares", {
            controller: "listNews",
            controllerAs: "vm",
            templateUrl: "pages/listNews.html"
        })
         .when("/Visitadas", {
            controller: "listNews",
            controllerAs: "vm",
            templateUrl: "pages/listNews.html"
        })
         .when("/Destacadas", {
            controller: "listNews",
            controllerAs: "vm",
            templateUrl: "pages/listNews.html"
        })
        .when("/nuevaNoticia", {
            controller: "newsCtrl",
            controllerAs: "vm",
            templateUrl: "pages/enviarNoticia.html"
        })
        .when("/editarNoticia/:idNoticia", {
            controller: "newsCtrl",
            controllerAs: "vm",
            templateUrl: "pages/enviarNoticia.html"
        })
        .when("/borrarNoticia/:idNoticia", {
            controller: "newsCtrl",
            controllerAs: "vm",
            templateUrl: "pages/listNews.html"
        })
        .when("/newUser", {
            controller: "userCtrl",
            controllerAs: "vm",
            templateUrl: "pages/nuevoUsuario.html"
        })
        .when("/editarUsuario/:idUser", {
            controller: "userCtrl",
            controllerAs: "vm",
            templateUrl: "pages/nuevoUsuario.html"
        })
        .when("/comentarios/:idNoticia", {
            controller: "commentsCtrl",
            controllerAs: "vm",
            templateUrl: "pages/listComments.html"
        })
        .when("/nuevoComentario/:idNoticia", {
            controller: "commentsCtrl",
            controllerAs: "vm",
            templateUrl: "pages/nuevoComentario.html"
        })
        .when("/editarComentario/:idComentario", {
            controller: "commentsCtrl",
            controllerAs: "vm",
            templateUrl: "pages/nuevoComentario.html"
        })
        .when("/borrarComentario/:idComentario", {
            controller: "commentsCtrl",
            controllerAs: "vm",
            templateUrl: "pages/listComments.html"
        })
        ;
})
.factory("newsFactory", function($http){
   var url = 'http://localhost:8080/meneame/rest/news/';

    var interfaz = {
    		
    		
    		 obtenerNoticias: function(){
    			 return $http.get(url)
    			 .then(function(response){
    				 return response.data;
    				 });
    			 },
    		 obtenerNoticiaID: function(idNoticia){
    			 var urlid = url + idNoticia;
    			 return $http.get(urlid)
    			 .then(function(response){
    				 return response.data;
    				 });
    			 },
    		 obtenerNoticiaDetalleID: function(idNoticia){
    			 var urlid = url +"detalle/" +idNoticia;
    			 return $http.get(urlid)
    			 .then(function(response){
    				 return response.data;
    				 });
    			 },
    		 obtenerMisNoticias: function(usuario){
    			 var urlid = url + "byuser/"+ usuario;
    			 return $http.get(urlid)
    			 .then(function(response){
    				 return response.data;
    				 });
    			 },
    		 obtenerNoticiasCategoria: function(categoria){
    			 var urlid = url + categoria;
    			 return $http.get(urlid)
    			 .then(function(response){
    				 return response.data;
    				 });
    			 },
		    obtenerPopulares: function(){
		    	var urlid = url + "populares/";
				 return $http.get(urlid)
				 .then(function(response){
					 return response.data;
					 });
				 },
			 obtenerVisitadas: function(){
			    	var urlvisitadas = url + "visitadas/";
					 return $http.get(urlvisitadas)
					 .then(function(response){
						 return response.data;
						 });
			 	},
			 obtenerDestacadas: function(){
			    	var urlid = url + "destacadas/";
					 return $http.get(urlid)
					 .then(function(response){
						 return response.data;
						 });
				},
			nuevaNoticia: function(news){
					return $http.post(url, news)
					.then(function(response){
						return response.status;
					})
			},
			editarNoticia: function(news, idNoticia){
				var urlid= url + idNoticia;
				return $http.put(urlid, news)
				.then(function(response){
					return response.status;
				})
			},
			votarNoticia: function(news, idNoticia){
				var urlid= url +"likes/" +  idNoticia;
				return $http.put(urlid, news)
				.then(function(response){
					return response.status;
				})
			},
			deleteNoticia: function(news, idNoticia){
				var urlid= url + idNoticia;
				return $http.delete(urlid, news)
				.then(function(response){
					return response.status;
				})
			}
			 	
    	
    }
    return interfaz;
})

.factory("userFactory", function($http){
	 var url = 'http://localhost:8080/meneame/rest/users/';
	 
	 var interfaz = {
    		 obtenerUsuarioSesion: function(){
    			 var urlid = url + "getUser";
    			 return $http.get(urlid)
    			 .then(function(response){
    				 return response.data;
    				 });
    			 },
    		 obtenerUsuarioID: function(id){
    			 var urlid = url + id;
    			 return $http.get(urlid)
    			 .then(function(response){
    				 return response.data;
    				 });
    			 },
			nuevoUsuario: function(user){
				return $http.post(url, user)
				.then(function(response){
					return response.status;
					})
    			},
			editarUsuario: function(user,idUser){
				var urlid= url + idUser;
				console.log(user)
				console.log("id")
				console.log(idUser)
				return $http.put(urlid, user)
				.then(function(response){
					return response.status;
					})
    			},
    		borrarUsuario: function(user,idUser){
    			var urlid= url + idUser;
    			return $http.delete(urlid, user)
    			.then(function(response){
    				return response.status;
    				})
    		}
    		
    			 
	 }
	 return interfaz;
})

.factory("commentFactory", function($http){
	 var url = 'http://localhost:8080/meneame/rest/comments/';
	 
	 var interfaz = {
			 obtenerComentariosNoticia: function(idNoticia){
    			 var urlid = url + "news/" + idNoticia;
    			 return $http.get(urlid)
    			 .then(function(response){
    				 return response.data;
    				 });
    			 },
			 obtenerComentarioID: function(idComentario){
    			 var urlid = url  + idComentario;
    			 return $http.get(urlid)
    			 .then(function(response){
    				 return response.data;
    				 });
    			 },
			nuevoComentario: function(comment, idNoticia){
				return $http.post(url, comment)
				.then(function(response){
					return response.status;
					})
    			},
			editarComentario: function(comment,idComentario){
				var urlid = url + idComentario;
				return $http.put(urlid, comment)
				.then(function(response){
					return response.status;
					})
    			},
			deleteComentario: function(comment, idComentario){
				var urlid= url + idComentario;
				return $http.delete(urlid, comment)
				.then(function(response){
					return response.status;
				})
			}
	 }
	 return interfaz;
})

.controller("mainAppCtrl", function(userFactory,$location){
	var vm = this;
	vm.user={};
	vm.actual={};
	
	vm.funciones = {
			
			estoy : function(ruta){
	            return $location.path() == ruta;
	        },
			
			getUserSesion : function() {
				userFactory.obtenerUsuarioSesion()
	    			.then(function(respuesta){
	    			console.log("Trayendo mainApp user con id: ", vm.user.id," Respuesta: ", respuesta);
	    			vm.user = respuesta;
	    			}, function(respuesta){
	    			console.log("Error obteniendo usuario sesion");
	    			})
			}
	}
	
	if(vm.funciones.estoy("/")){
		vm.actual = "Edición General";
	}else if(vm.funciones.estoy("/Categoria/Ciencia")){
		vm.actual="Ciencia y Tecnología";
	}
	
	vm.funciones.getUserSesion();
})

.controller("listNews", function(newsFactory, userFactory,$scope, $location, $routeParams){
    var vm = this;
    vm.news=[];
    vm.user={};
    vm.destacadas=[];
    vm.visitadas=[];
    vm.populares=[];
       
    vm.funciones = {
			estoy : function(ruta){
	            return $location.path() == ruta;
	        },
			getUserSesion : function() {
				userFactory.obtenerUsuarioSesion()
	    			.then(function(respuesta){
	    			console.log("Trayendo usuario sesion: ", respuesta);
	    			vm.user = respuesta;
	    			}, function(respuesta){
	    			console.log("Error obteniendo usuario sesion");
	    			})
			},
			getNews : function() {
		        newsFactory.obtenerNoticias()
					.then(function(respuesta){
	    			console.log("Trayendo todas las noticias: ", respuesta);
	    			vm.news = respuesta;
	    			angular.forEach(vm.news, function(noticia){
	    				userFactory.obtenerUsuarioID(noticia.owner)
	    				.then(function(usuario){
	    					noticia.name = usuario.name;
	    				},function(usuario){
	    					console.log("Error obteniendo nombre usuario noticia");
	    				})
	    			})
	    			}, function(respuesta){
	    			console.log("Error obteniendo noticias");
	    			})
			},
			getNewsByUser : function() {
		        newsFactory.obtenerMisNoticias($routeParams.usuario)
					.then(function(respuesta){
	    			console.log("Trayendo las noticias del usuario: ", respuesta);
	    			vm.news = respuesta;
	    			}, function(respuesta){
	    			console.log("Error obteniendo noticias del usuario");
	    			})
			},
			getPopularesAside : function() {
		        newsFactory.obtenerPopulares()
					.then(function(respuesta){
					console.log("Trayendo noticias populares: ", respuesta);
					vm.populares = respuesta;
					}, function(respuesta){
					console.log("Error obteniendo populares");
					})
			},
			getVisitadasAside : function() {
				newsFactory.obtenerVisitadas()
					.then(function(respuesta){
					console.log("Trayendo noticias visitadas: ", respuesta);
					vm.visitadas = respuesta;
					}, function(respuesta){
					console.log("Error obteniendo visitadas");
					})
			},
			getDestacadasAside : function() {
				newsFactory.obtenerDestacadas()
					.then(function(respuesta){
					console.log("Trayendo noticias destacadas: ", respuesta);
					vm.destacadas = respuesta;
					}, function(respuesta){
					console.log("Error obteniendo destacadas");
					})
			},
			getNewsCategoria: function() {
		        newsFactory.obtenerNoticiasCategoria($routeParams.categoria)
				.then(function(respuesta){
    			console.log("Trayendo las noticias por categoría: ", respuesta);
    			vm.news = respuesta;
    			}, function(respuesta){
    			console.log("Error obteniendo noticias por categoría");
    			})
    			
		},
			getVisitadas : function() {
				newsFactory.obtenerVisitadas()
					.then(function(respuesta){
					console.log("Trayendo noticias visitadas: ", respuesta);
					vm.news = respuesta;
					}, function(respuesta){
					console.log("Error obteniendo visitadas");
					})
			},
			getDestacadas : function() {
				newsFactory.obtenerDestacadas()
					.then(function(respuesta){
					console.log("Trayendo noticias destacadas: ", respuesta);
					vm.news = respuesta;
					}, function(respuesta){
					console.log("Error obteniendo destacadas");
					})
			},
			getPopulares : function() {
		        newsFactory.obtenerPopulares()
					.then(function(respuesta){
					console.log("Trayendo noticias populares: ", respuesta);
					vm.news = respuesta;
					}, function(respuesta){
					console.log("Error obteniendo populares");
					})
			}
			

};

	$scope.goEdit = function (idNews){
		$location.url('/editarNoticia/'+idNews);
	};
	
	$scope.goDelete = function (idNews){
		$location.url('/borrarNoticia/'+idNews);
	};
	
	$scope.goComments = function (idNews){
		$location.url('/comentarios/'+idNews);
	};
	
    if(vm.funciones.estoy("/")){
    	vm.actual = "Edicion General";
        vm.funciones.getNews();
        vm.funciones.getPopularesAside();
        vm.funciones.getVisitadasAside();
        vm.funciones.getDestacadasAside();
    }else if(vm.funciones.estoy("/Categoria/"+$routeParams.categoria)){
    	if($routeParams.categoria == "Ciencia"){
    		vm.actual = "Ciencia y Tecnología";
    	}else if($routeParams.categoria == "Deportes"){
    		vm.actual = "Deportes";
    	}
    	vm.funciones.getNewsCategoria($routeParams.categoria);
        vm.funciones.getUserSesion();
    	vm.funciones.getPopularesAside();
        vm.funciones.getVisitadasAside();
        vm.funciones.getDestacadasAside();
    }else if(vm.funciones.estoy("/Populares")){
    	vm.actual = "Más Populares";
    	vm.funciones.getPopulares();
        vm.funciones.getUserSesion();
    	vm.funciones.getPopularesAside();
        vm.funciones.getVisitadasAside();
        vm.funciones.getDestacadasAside();
    }else if(vm.funciones.estoy("/Visitadas")){
    	vm.actual = "Más Visitadas";
    	vm.funciones.getVisitadas();
        vm.funciones.getUserSesion();
    	vm.funciones.getPopularesAside();
        vm.funciones.getVisitadasAside();
        vm.funciones.getDestacadasAside();
    }else if(vm.funciones.estoy("/Destacadas")){
    	vm.actual = "Más Destacadas";
    	vm.funciones.getDestacadas();
        vm.funciones.getUserSesion();
    	vm.funciones.getPopularesAside();
        vm.funciones.getVisitadasAside();
        vm.funciones.getDestacadasAside();
    }else if(vm.funciones.estoy("/misNoticias/"+$routeParams.usuario)){
    	vm.actual = "Mis Noticias";
		vm.funciones.getNewsByUser($routeParams.usuario);
	    vm.funciones.getUserSesion();
		vm.funciones.getPopularesAside();
	    vm.funciones.getVisitadasAside();
	    vm.funciones.getDestacadasAside();
    }
}
)
.controller("newsCtrl", function(newsFactory,userFactory,$routeParams,$location, $scope){
    var vm = this;
    vm.news={};
    vm.user={};
       
    vm.funciones = {
			estoy : function(ruta){
	            return $location.path() == ruta;
	        },
			
			insertarNoticia : function() {
		        newsFactory.nuevaNoticia(vm.news)
					.then(function(respuesta){
	    			console.log("Insertando nueva noticia. Respuesta recibida: ", respuesta);
	    			}, function(respuesta){
	    			console.log("Error insertando nueva noticia");
	    			})
			}, 
			getUserSesion : function() {
				userFactory.obtenerUsuarioSesion()
	    			.then(function(respuesta){
	    			console.log("Trayendo usuario sesion: ", respuesta);
	    			vm.user = respuesta;
	    			}, function(respuesta){
	    			console.log("Error obteniendo usuario sesion");
	    			})
			},
			getNoticiaId : function() {
				newsFactory.obtenerNoticiaID($routeParams.idNoticia)
	    			.then(function(respuesta){
	    			console.log("Trayendo noticia con id : ", $routeParams.idNoticia, ". Respuesta : ", respuesta);
	    			vm.news = respuesta;
	    			}, function(respuesta){
	    			console.log("Error obteniendo noticia con id :", $routeParams.idNoticia);
	    			$location.url('/');
	    			})
			},
			modificarNoticia : function() {
		        newsFactory.editarNoticia(vm.news, $routeParams.idNoticia)
					.then(function(respuesta){
	    			console.log("Editando noticia. Respuesta recibida: ", respuesta);
	    			}, function(respuesta){
	    			console.log("Error editando noticia");
	    			})
			},
			borrarNoticia : function() {
		        newsFactory.deleteNoticia(vm.news, $routeParams.idNoticia)
					.then(function(respuesta){
	    			console.log("Borrando noticia. Respuesta recibida: ", respuesta);
	    			}, function(respuesta){
	    			console.log("Error borrando noticia");
	    			})
			},
			escogerOpcion : function(){
				if(vm.funciones.estoy('/nuevaNoticia')){
					vm.funciones.insertarNoticia($routeParams.idNoticia);
				}else if(vm.funciones.estoy('/editarNoticia/'+$routeParams.idNoticia)){
					vm.funciones.modificarNoticia($routeParams.idNoticia);
				}
			}
	}

    if(vm.funciones.estoy("/editarNoticia/"+$routeParams.idNoticia)){
    	vm.funciones.getNoticiaId($routeParams.idNoticia);
    	vm.funciones.getUserSesion();
    }else if(vm.funciones.estoy("/borrarNoticia/"+$routeParams.idNoticia)){
    	vm.funciones.borrarNoticia($routeParams.idNoticia);
    	$location.url('/');
    }
   
}
)

.controller("userCtrl", function(userFactory,$routeParams, $location){
	var vm = this;
	vm.user={};
	vm.actual={};
	vm.error = "";
	
	vm.funciones ={
			
			estoy : function(ruta){
	            return $location.path() == ruta;
	        },
	        
			getUserSesion : function() {
				userFactory.obtenerUsuarioSesion()
	    			.then(function(respuesta){
	    			console.log("Trayendo usuario sesion: ", respuesta);
	    			vm.user = respuesta;
	    			}, function(respuesta){
	    			console.log("Error obteniendo usuario sesion");
	    			})
			},
	        
			insertarUsuario : function() {
		        userFactory.nuevoUsuario(vm.user)
					.then(function(respuesta){
	    			console.log("Insertando nuevo usuario. Respuesta recibida: ", respuesta);
	    			}, function(respuesta){
	    			console.log("Error insertando nuevo usuario");
	    			})
			},
			modificarUsuario : function() {
		        userFactory.editarUsuario(vm.user, $routeParams.idUser)
					.then(function(respuesta){
	    			console.log("Editando usuario. Respuesta recibida: ", respuesta);
	    			}, function(respuesta){
	    			console.log("Error editando usuario");
	    			})
			},
			
			eliminarUsuario : function() {
				userFactory.borrarUsuario(vm.user,$routeParams.idUser)
				.then(function(respuesta){
    			console.log("Borrando usuario. Respuesta recibida: ", respuesta);
    			}, function(respuesta){
    			console.log("Error borrando usuario");
    			})
			},
			escogerOpcion : function(){
				if(vm.funciones.estoy('/newUser')){
					vm.funciones.insertarUsuario($routeParams.idUser);
				}else if(vm.funciones.estoy('/editarUsuario/'+$routeParams.idUser)){
					vm.funciones.modificarUsuario($routeParams.idUser);
				}
			} 
			
	}
	vm.funciones.getUserSesion();
	if(vm.funciones.estoy("/newUser")){
		vm.actual="Nuevo Usuario";
	}else if(vm.funciones.estoy("/editarUsuario/"+$routeParams.idUser)){
		vm.actual="Editar Usuario";
		vm.funciones.getUserSesion();
	}
}
)
.controller("commentsCtrl", function(newsFactory,userFactory,commentFactory,$routeParams, $location, $scope){
	var vm = this;
	vm.user={};
	vm.comments=[];
	vm.comentario={};
	vm.news={};
	vm.destacadas=[];
	vm.visitadas=[];
	vm.populares=[];
	vm.actual={};
	
	vm.funciones ={
			
			estoy : function(ruta){
	            return $location.path() == ruta;
	        },
	        
			getUserSesion : function() {
				userFactory.obtenerUsuarioSesion()
	    			.then(function(respuesta){
	    			console.log("Trayendo usuario sesion: ", respuesta);
	    			vm.user = respuesta;
	    			}, function(respuesta){
	    			console.log("Error obteniendo usuario sesion");
	    			})
			},
			
			getNoticiaId : function() {
				newsFactory.obtenerNoticiaDetalleID($routeParams.idNoticia)
	    			.then(function(respuesta){
	    			console.log("Trayendo noticia con id : ", $routeParams.idNews,". Respuesta : ", respuesta);
	    			vm.news = respuesta;
	    			}, function(respuesta){
	    			console.log("Error obteniendo noticia");
	    			})
			},
			getComentarioId : function() {
				commentFactory.obtenerComentarioID($routeParams.idComentario)
	    			.then(function(respuesta){
	    			console.log("Trayendo comentario con id : ", $routeParams.idNews,". Respuesta : ", respuesta);
	    			vm.comentario = respuesta;
	    			}, function(respuesta){
	    			console.log("Error obteniendo comentario");
	    			$location.url('/');
	    			})
			}, 
			getPopularesAside : function() {
		        newsFactory.obtenerPopulares()
					.then(function(respuesta){
					console.log("Trayendo noticias populares: ", respuesta);
					vm.populares = respuesta;
					}, function(respuesta){
					console.log("Error obteniendo populares");
					})
			},
			getVisitadasAside : function() {
				newsFactory.obtenerVisitadas()
					.then(function(respuesta){
					console.log("Trayendo noticias visitadas: ", respuesta);
					vm.visitadas = respuesta;
					}, function(respuesta){
					console.log("Error obteniendo visitadas");
					})
			},
			getDestacadasAside : function() {
				newsFactory.obtenerDestacadas()
					.then(function(respuesta){
					console.log("Trayendo noticias destacadas: ", respuesta);
					vm.destacadas = respuesta;
					}, function(respuesta){
					console.log("Error obteniendo destacadas");
					})
			},
			
			getCommentsNews : function() {
				commentFactory.obtenerComentariosNoticia($routeParams.idNoticia)
	    			.then(function(respuesta){
	    			console.log("Trayendo comentarios noticia con id : " , $routeParams.idNews , "Respuesta : ", respuesta);
	    			vm.comments = respuesta;
	    			angular.forEach(vm.comments, function(comm){
	    				userFactory.obtenerUsuarioID(comm.owner)
	    				.then(function(usuario){
	    					comm.name = usuario.name;
	    				},function(usuario){
	    					console.log("Error obteniendo nombre usuario comentario");
	    				})
	    			})
	    			}, function(respuesta){
	    			console.log("Error obteniendo comentarios");
    			})
			},
			insertarComentario : function() {
		        commentFactory.nuevoComentario(vm.comentario)
					.then(function(respuesta){
	    			console.log("Insertando nuevo comentario. Respuesta recibida: ", respuesta);
	    			$location.url('/comentarios/'+$routeParams.idNoticia);
	    			}, function(respuesta){
	    			console.log("Error insertando nuevo comentario");
	    			})
			}, 
			modificarComentario : function() {
		        commentFactory.editarComentario(vm.comentario, $routeParams.idComentario)
					.then(function(respuesta){
	    			console.log("Editando comentario. Respuesta recibida: ", respuesta);
	    			$location.url('/comentarios/'+vm.comentario.news);
	    			}, function(respuesta){
	    			console.log("Error editando comentario");
	    			})
			},
			
			borrarComentario : function() {
		        commentFactory.deleteComentario(vm.comentario, $routeParams.idComentario)
					.then(function(respuesta){
	    			console.log("Borrando comentario. Respuesta recibida: ", respuesta);
	    			$location.url('/comentarios/'+vm.comentario.news);
	    			}, function(respuesta){
	    			console.log("Error borrando comentario");
	    			})
			},
			

			escogerOpcion : function(){
					if(vm.funciones.estoy('/nuevoComentario/'+$routeParams.idNoticia)){
						vm.funciones.insertarComentario($routeParams.idNoticia);
					}else if(vm.funciones.estoy('/editarComentario/'+$routeParams.idComentario)){
						vm.funciones.modificarComentario($routeParams.idComentario);
					}
				} ,
	        
			
	};
	
	$scope.goCommentsNews = function (idNews){
		$location.url('/comentarios/'+idNews);
	};
	
	$scope.goNewComment = function (idNews){
		$location.url('/nuevoComentario/'+idNews);
	};
	
	$scope.goEditComment = function (idComment){
		$location.url('/editarComentario/'+ idComment);
	};
	
	$scope.goDeleteComment = function (idComment){
		$location.url('/borrarComentario/'+ idComment);
	};
	

	

	if(vm.funciones.estoy("/comentarios/"+$routeParams.idNoticia)){
		vm.funciones.getUserSesion();
		vm.funciones.getNoticiaId($routeParams.idNoticia);
		vm.funciones.getPopularesAside();
		vm.funciones.getVisitadasAside();
		vm.funciones.getDestacadasAside();
		vm.funciones.getCommentsNews($routeParams.idNoticia);
	}else if(vm.funciones.estoy('/nuevoComentario/'+$routeParams.idNoticia)){
		vm.actual="Nuevo Comentario";
		vm.comentario.news= $routeParams.idNoticia;
		vm.funciones.getNoticiaId($routeParams.idNoticia);
		vm.funciones.getUserSesion();
	}else if(vm.funciones.estoy('/editarComentario/'+$routeParams.idComentario)){
		vm.actual="Editar Comentario";
		vm.comentario.news= $routeParams.idNoticia;
		vm.funciones.getComentarioId($routeParams.idComentario);
		vm.funciones.getUserSesion();
	}else if(vm.funciones.estoy('/borrarComentario/'+$routeParams.idComentario)){
		vm.funciones.getComentarioId($routeParams.idComentario);
		vm.funciones.borrarComentario($routeParams.idComentario);
	}
		
}
)

;
