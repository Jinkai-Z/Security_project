<%@ page contentType="text/html;charset=UTF-8" language="java"  %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Error Page</title>
</head>
<body>
Some error occurred. Click to <a href="index.jsp">Home Page</a>.
<script>
    setTimeout(function () {
        window.location = "index.jsp";
    }, 5000);
</script>
</body>
</html>
