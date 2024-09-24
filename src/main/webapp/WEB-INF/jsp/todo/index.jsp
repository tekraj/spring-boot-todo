<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!Doctype html>
<html>
	<jsp:include page="../common/head.jsp"  />
	<body>
		<jsp:include page="../common/header.jsp"  />
		<div class="main container">
			<table class="table table-striped">
			    <thead>
			        <tr>
			            <th>ID</th>
			            <th>Title</th>
			            <th>Description</th>
			            <th>Actions</th>
			        </tr>
			    </thead>
			    <tbody>
			        <c:forEach var="todo" items="${todos}">
			            <tr>
			                <td>${todo.id}</td>
			                <td>${todo.title}</td>
			                <td>${todo.description}</td>
			                <td>
			                    <a href="/todo/edit/${todo.id}" class="btn btn-warning btn-sm">Edit</a>
			                    <a href="/todo/delete/${todo.id}" class="btn btn-danger btn-sm">Delete</a>
			                </td>
			            </tr>
			        </c:forEach>
			    </tbody>
		</div>
		<jsp:include page="../common/footer.jsp"  />
	</body>
</html>
