<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/stylesheet.css" />
<title>Meneame</title>
</head>

<body>
	<header>

		<div id="logo">
			<a href="${pageContext.request.contextPath}/ListNewsServlet">
				menéame </a>
		</div>

		<nav>
			<ul>
				<li><a
					href="${pageContext.request.contextPath}/login/NewNewsServlet">Enviar
						noticia</a></li>
				<li><a
					href="${pageContext.request.contextPath}/login/UserNewsServlet">Mis
						noticias</a></li>
				<c:if test="${logueado == 'login'}">

					<li><a href="${pageContext.request.contextPath}/LogOutServlet">Log
							Out</a></li>
					<li><a
						href="${pageContext.request.contextPath }/login/EditUserServlet">Hola
							${user.name }!! </a></li>

				</c:if>
				<c:if test="${logueado == 'no_login'}">
					<li><a href="${pageContext.request.contextPath}/LoginServlet">Login</a></li>
					<li><a
						href="${pageContext.request.contextPath}/NewUserServlet">Registrarse</a></li>
				</c:if>


			</ul>

		</nav>

	</header>

	<section id="pagina_actual">
		<p>${category }</p>
		<div id="searchbox">
			Buscar : <input type="search" name="busqueda">
		</div>
	</section>

	<nav id="submenu">
		<ul>
			<li><a href="${pageContext.request.contextPath}/ListNewsServlet">
					Edición General</a></li>
			<li><a
				href="${pageContext.request.contextPath}/ListTecnologiaNewsServlet">
					Ciencia y Tecnología</a></li>
			<li><a
				href="${pageContext.request.contextPath}/ListDeportesNewsServlet">
					Deportes</a></li>
			<li><a
				href="${pageContext.request.contextPath}/ListPopularesNewsServlet">
					Populares</a></li>
			<li><a
				href="${pageContext.request.contextPath}/ListVisitadasNewsServlet">
					Más visitadas</a></li>
			<li><a
				href="${pageContext.request.contextPath}/ListDestacadasNewsServlet">
					Destacadas</a></li>
		</ul>
	</nav>

	<section id="noticias">
		<c:forEach var="map" items="${newsMap}">
			<article>
				<a href="${map.key.url }">${map.key.title } </a>
				<div class="imagen_noticia">
					<img src=" ${map.key.image }" alt="noticia 1">
				</div>
				<div class="descripcion">
					<p>
						<em>enviada por <strong>${map.value.name }</strong>
						</em>
					</p>
					<p>
						${map.key.text } <br> <em> Publicado el </em> <strong><fmt:formatDate
								type="date" value="${map.key.dateStamp }" /></strong> <em>a las </em> <strong><fmt:formatDate
								type="time" value="${map.key.timeStamp }" /></strong>
					</p>
					<div class="acciones">
					<c:if test="${map.key.owner == user.id}">
						<form method="get" action="${pageContext.request.contextPath}/login/EditNewsServlet">
							<input type="hidden" name="id" value="${map.key.id }">
<!-- 							<input type="submit" value="Editar" name="Editar"> -->
							<button type="submit" name="Editar">
								<img alt="Editar_Noticia" src="http://image005.flaticon.com/141/svg/126/126483.svg">
							</button>
						</form>
						<form method="post" action="${pageContext.request.contextPath}/login/DeleteNewsServlet">
							<input type="hidden" name="id" value="${map.key.id }">
<!-- 							<input type="submit" value="Borrar" name="Borrar">
 -->							<button type="submit" name="Borrar">
									<img alt="Borrar_Noticia" src="http://image005.flaticon.com/59/svg/63/63260.svg">
								</button>
						</form>
					</c:if>
					<form method="get" action="${pageContext.request.contextPath}/CommentNewsServlet">
						<input type="hidden" name="idNoticia" value=${map.key.id }>
						<!-- <input type="submit" value="comentarios" name="comentarios">  -->
						<button type="submit" name="comentarios">
			            	<img alt="Comentar_Noticia" src="https://cdn2.iconfinder.com/data/icons/3_Minicons-Free-_Pack/46/comments.png" alt="comentarios">
			        	</button> 
					</form>
					<form method="post" action="${pageContext.request.contextPath}/LikesServlet">
						<input type="hidden" name="idNoticia" value="${map.key.id }">
<!-- 						<input type="submit" value="Menéalo" name="meneo">
-->						<button type="submit" name="meneo">
				        	<img alt="Menear_Noticia" src="https://cdn1.iconfinder.com/data/icons/free-98-icons/32/like-128.png" alt="like"> 
				    	</button>
						 Meneos : ${map.key.likes }
					</form>
					Visitas : ${map.key.hits }
					</div>
				</div>

			</article>
		</c:forEach>

	</section>

	<aside>


		<section>
			<h2>Destacadas</h2>
			<ol>
				<c:forEach var="news" items="${destacadas}">
					<li><a href="${news.url }"> ${news.title } </a></li>
				</c:forEach>
			</ol>
		</section>

		<section>
			<h2>Más visitadas</h2>
			<ol>
				<c:forEach var="news" items="${visitadas}">
					<li><a href="${news.url }"> ${news.title } </a></li>
				</c:forEach>
			</ol>
		</section>

		<section>
			<h2>Populares</h2>
			<ol>
				<c:forEach var="news" items="${populares}">
					<li><a href="${news.url }"> ${news.title } </a></li>
				</c:forEach>
			</ol>
		</section>

	</aside>

	<footer>
		<section id="acerca_de">
			<h3>Acerca de...</h3>
			<p>Aplicación Web de la asignatura Programación en Internet. Desarrollada por
			Juan Luis Fragoso del Rey. Esta versión corresponde a la segunda entrega de 
			evaluación continua. </p>

		</section>
		<section id="redes-s">
			<a href="http://www.twitter.es">
				<div>
					<img src="data:image/jpeg;base64,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" alt="twitter">

					<p>twitter</p>
				</div>
			</a> <a href="http://www.facebook.es">
				<div>
					<img src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAOEAAADhCAMAAAAJbSJIAAAAeFBMVEU7WZj///8sT5O3v9RAXJokSpE3VpdacKQzU5Xj5u6Ek7iWosI1VZbq7fRDYJza3+r5+vyrttDQ1+WGmL7L0eH09vpfd6pLZqBrgrFQaaC/xthacaTm6/PDzN9heat5jbemscwMP4yNncAiSJCaqch1h7OAkruxvNRQ5noeAAAEJUlEQVR4nO3dyXLiMBSFYVmAJBLLMwZCBppOwvu/YTNkWDQNit3SvVKdf5ENlZS/soiMLRuRnbNd2xcilYq+7eyHTJx+VrWWRlFv139MGanr6ltY6qR455TR5aewKXLqzfFSXjRnYVmktwPPqaI8Ciud5h48luvqIKwN9XZ4zNSZsDrVMXpMaSs6Sb0VXpOdaFMepIdh2oo+5UF6GKa9SOdQ7XKp+xBCCCGEEEIIIYQQupXKjcmVENPvxPT0glJ5fnjRnF+PtFya9dtuO+lKW2VfVQv7WJZN9/Swr7evu7u3lZ5Sb+mQlJGzflvOF9mtFtbexXfGXsnl3aS6ifvsPjahksW+dObFJ1Sqf3DffREK5XJ/+70XsTAXO3tbFLHQrLuf+2ISypf5EGBEwt0gXzxCuR0IjESoxH4oMBKhGbwHIxHK1+HAKITm5YeHMbEJ882AeT4qoWzGACMQmqETYSxC1Y8aoxEIZ5NxQPZCtR4JZC+UD4kL89WPP/HGJhx+PBqHUE1H70LmQtOOBjIXjjyc4S9U/fhByls49oCNv1CMngyZC0/3JKUt3PxEsrB2fiHW157MuzNvPrl/04WRs79jDBSydvSVq6UyRsV3uXfmNhvaVsZ6LVs6ffZ9XEV745lausz3i57zG+166tnlJOJ9tHvw8MnJ5TRpQ72VY3I6ZnuNd4wehA7n8u060v+ip1wuqJURvwvdJvx93EKHczRx36IsHc4Fr6J+FIKLMO4nBbgIlxCyzkU4TV9IvZGjghBC/kEIIf8ghJB/EELIPwgh5B+EEPJIXcll7azIr/2FY8TAYqP/3dphNc213z+2IT6hau5uG0b2OEtd+ER76SaAsE5eSLzmK4DwnfbilH/hgvjym3/h4yb12aIkvjjlX0i93Ma/kHg6DCAkng4DCHfEazX8C9+SF1IvCvMvpF5Q5F1of6cubKiXvXkXki/s8y4kX1/rXUh+v4x3IfnSRd/CxTP1yUTfwjn1hO9d2JB/g4xv4RM10LtwQj0dehduUxdW9Cv5fQvJp0PfwgX9KnDfQvJd6Fs4p72yFkDI4Hsb85eLt9B/5nCT7LVft+SfnY7EC/fPf/XL4c6uSzfgR3En/qk01mJcC0II+QchhPyDEEL+QQgh/yCEkH8QQsg/CCHkH4QQ8g9CCPkHIYT8gxBC/kEIIf8ghJB/EELIPwgh5B+EEPIPQgj5ByGE/IMQQv5BCCH/IISQfxBCyD8IIeQfhBDyL6CQ6IlZwYSF6GkeZBNKqHpB9LilUELTCqLnvIQSyk5Ymi/6CCRU2oqsJhmmgYSmzkRWaYpnSoUR5ro6CLOyIBinQYSqKLOjMGuK8HsxhDAvmuwszEptQu9G/0JldJl9CrOq1jIs0rNQGanrKvsWZpnt2j7kAZxfYdG3nf34K38ArA5OdTHdpNAAAAAASUVORK5CYII=" alt="facebook">
					<p>facebook</p>
				</div>
			</a> <a href=https://plus.google.com/about?hl=es">
				<div>
					<img src="data:image/jpeg;base64,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" alt="google +">
					<p>google +</p>
				</div>
			</a> <a href="http://www.rss.nom.es/">
				<div>
					<img src="data:image/jpeg;base64,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" alt="rss">
					<p>RSS</p>
				</div>
			</a>
		</section>
	</footer>

</body>
</html>

