<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../common/head.jsp" %>

<div class="main container">
    <div class="row justify-content-center" style="min-height: 100vh;">
        <div class="col-md-6">
            <div class="card mt-5">
                <div class="card-header text-center">
                    <h3>Login</h3>
                </div>
                <div class="card-body">
                    <form action="/login" method="post">
                        <div class="form-group">
                            <label for="username">Email</label>
                            <input type="text" class="form-control" id="username" name="username" required>
                        </div>
                        <div class="form-group">
                            <label for="password">Password</label>
                            <input type="password" class="form-control" id="password" name="password" required>
                        </div>
                        <div class="form-group text-center">
                            <button type="submit" class="btn btn-primary btn-block">Login</button>
                        </div>
                        <c:if test="${not empty param.error}">
                            <div class="alert alert-danger text-center" role="alert">
                                Invalid username or password.
                            </div>
                        </c:if>
                    </form>
                </div>
                <div class="card-footer text-center">
                    <p>Don't have an account? <a href="#">Register here</a></p>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
