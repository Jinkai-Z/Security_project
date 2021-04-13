<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>${initParam.siteName}</title>
    <style>
        .hidden{
            display: none;
        }
        form{
            width: 600px;
            display: block;
            margin: 24px auto;
            background-color: rgba(200,200,200,0.3);
            padding: 16px;
        }
        form>section:nth-of-type(-n+4){
            padding-bottom: 4px;
        }
        form>section:nth-of-type(-n+4)>input, form>section:nth-of-type(-n+4)>button{
            height: 32px;
        }
        a:link{
            color: blue;
        }
        a:visited{
            color: blue;
        }
        a:hover{
            color: black;
        }
        input{
            width:200px;
        }
    </style>
</head>
<body>
<header>
    <h1>${initParam.siteName}</h1>
</header>
<main>
    <form method="post" action="login">
        <section class="hidden">
            <input type="hidden" name="csrfToken" value="${sessionScope.csrfToken}">
        </section>
        <section>
            <input type="email" name="email" placeholder="Enter Emil Here">
        </section>
        <section>
            <input type="password" name="password" placeholder="Enter Password Here">
        </section>
        <section>
            <button type="submit">Login</button>
        </section>
        <section>
            <output id="msg">${error}</output>
        </section>
        <section>
            <a href="register.jsp">Sign Up</a>
        </section>
    </form>
</main>
</body>
</html>
