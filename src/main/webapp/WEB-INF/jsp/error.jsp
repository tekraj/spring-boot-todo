<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Error Page</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-5">
    <h1>Error <c:out value="${status}"/></h1>
    <h2><c:out value="${error}"/></h2>
    <p><c:out value="${message}"/></p>
    <a class="btn btn-primary" href="/">Go Home</a>
</div>
</body>
</html>
