<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!Doctype html>
<html>
<jsp:include page="../common/head.jsp" />
<body>
    <jsp:include page="../common/header.jsp" />
    <div class="main container">
		<h2>
			<c:choose>
                <c:when test='${todo.id != null}'>Update Todo</c:when>
                <c:otherwise>Create New Todo</c:otherwise>
             </c:choose>
		</h2>
		<form action="<c:choose>
                        <c:when test='${todo.id != null}'>/todo/update/${todo.id}</c:when>
                        <c:otherwise>/todo/store</c:otherwise>
                     </c:choose>" 
              method="POST">
			 
            <div class="mb-3">
                <label for="title" class="form-label">Title</label>
                <input type="text" class="form-control" id="title" name="title"
                       value="<c:if test='${todo.title != null}'>${todo.title}</c:if>" 
                       required>
            </div>
            
            <div class="mb-3">
                <label for="description" class="form-label">Description</label>
                <textarea class="form-control" id="description" name="description" rows="3"
                          required><c:if test='${todo.description != null}'>${todo.description}</c:if></textarea>
            </div>

            <button type="submit" class="btn btn-primary">
                <c:choose>
                    <c:when test="${todo.id != null}">Update Todo</c:when>
                    <c:otherwise>Create Todo</c:otherwise>
                </c:choose>
            </button>

        </form>
    </div>
    <jsp:include page="../common/footer.jsp" />
</body>

</html>