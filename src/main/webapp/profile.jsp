<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Update profile</title>
    <style>
        nav{
            display: flex ;
            flex-flow: row wrap;
            gap: 12px;
            margin-bottom: 16px;
        }
        nav a:link{
            color: red;
        }
        nav a:visited{
            color: red;
        }
        nav a:hover{
            color: blue;
        }
        nav a:active{
            color: red;
        }
        main{
            width: 600px;
            margin: 16px auto;
            background-color: rgba(200,200,200,0.3);
            padding: 16px;
        }
        form>section{
            height: 22px;
            padding: 4px 2px;
        }
        form>section>label{
            width: 80px;
            display: inline-block;
            text-align: right;
        }
        form>section>input{
            height: 22px;
            margin-left: 16px;
            width: 200px;
        }
        form>section>button{
            height: 32px;
            display: inline-block;
            margin-left: 100px;
        }
    </style>
</head>
<body>
<%@ include file="menu.jsp"%>
<main>
    <form action="update_person" method="post">
        <section>
            <label>Name</label>
            <input name="name" type="text">
        </section>

        <section>
            <label>Email</label>
            <input name="email" type="email">
        </section>

        <section>
            <label>Password</label>
            <input name="name" type="password" placeholder="Leave empty to keep password un change">
        </section>
        <section>
            <button type="submit">Update</button>
        </section>
        <section>
            <output>${error}</output>
        </section>
        <input type="hidden" name="csrfToken" value="${sessionScope.csrfToken}">
    </form>
</main>
</body>
</html>
