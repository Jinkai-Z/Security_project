<%--
  User: Jinkai Zhang
--%>
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
        form>section:nth-of-type(-n+6){
            padding-bottom: 4px;
        }
        form>section:nth-of-type(-n+6)>input, form>section:nth-of-type(-n+6)>button{
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
<main>
    <header>
        <h1>Contract Management App</h1>
    </header>
    <form method="post" action="signup">
        <section class="hidden">
            <input type="hidden" name="csrfToken" value="${sessionScope.csrfToken}">
        </section>
        <section>
            <input type="email" name="email" placeholder="Please enter email">
        </section>
        <section>
            <input type="text" name="name" placeholder="Please enter nick name">
        </section>
        <section>
            <input type="password" name="password" placeholder="Please enter password">
        </section>
        <section>
            <input type="password" name="password1" placeholder="Repeat password again">
        </section>
        <section>
            <button type="submit">Sign Up</button>
        </section>
        <section>
            <output id="output">${requestScope.error}</output>
        </section>
        <section>
            <a href="index.jsp">Back to Login</a>
        </section>
    </form>
</main>
</body>
</html>
