<%--
  User: Jinkai Zhang
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.sample.Contact" %>
<%@ page import="com.example.service.ContactService" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.service.TelephoneService" %>
<%@ page import="com.sample.Telephone" %>
<%@ page import="org.apache.commons.text.StringEscapeUtils" %>
<html>
<head>
    <title>Contacts</title>
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

        .action-panel *{
            margin-right: 8px;
            height: 22px;
        }
        .contact-list{
            width: 600px;
            margin:16px auto;
            background: rgba(200,200,200,0.3);
            padding: 16px;
        }
        .contact-list> section{
            margin: 8px;
        }
        .contact-list>section>*{
            line-height: 22px;
            margin: 0 8px;
        }
    </style>
</head>
<body>
<%@ include file="menu.jsp"%>
<main>
    <section class="action-panel">
        <section>
            <form method="post" action="contact_add">
                <label>New Contact</label>
                <input name="contactName" type="text" placeholder="Contact Name"/>
                <input name="telephone" type="tel" placeholder="Telephone"/>
                <button type="submit">Add</button>
                <input type="hidden" name="csrfToken" value="${sessionScope.csrfToken}">
            </form>
        </section>
        <section>
            <output></output>
        </section>
    </section>

    <section>
        <%
            {
                Long personId = (Long) session.getAttribute("personId");
                if (personId != null) {

                    final ContactService contactService = new ContactService();
                    final List<Contact> contacts = contactService.queryContact(personId);
                    final TelephoneService telephoneService = new TelephoneService();
                    out.print("<section class=\"contact-list\">");
                    for (Contact contact : contacts) {
                        final List<Telephone> telephones = telephoneService.getAllTelephone(contact.getId());
                        Telephone telephone = null;
                        if (telephones.size() > 0) {
                            telephone = telephones.get(0);
                        }
                        out.print("<section>");
                        out.print("<span>");
                        out.print(contact.getId());
                        out.print("</span>");
                        out.print("<span>");
                        out.print(StringEscapeUtils.escapeHtml3(contact.getName()));
                        out.print("</span>");
                        out.print("<span>");
                        out.print(telephone.getPhone());
                        out.print("</span>");
                        out.print("<button onclick='deleteContact(" + contact.getId() + ")'>Delete</button>");
                        out.print("</section>");
                    }
                    out.print("</section>");
                }
            }
        %>
    </section>
</main>
<script language="JavaScript">
    function deleteContact(e) {
        let csrfToken = encodeURIComponent("${sessionScope.csrfToken}");
        let contactId = encodeURIComponent(e);
        let data = "csrfToken=" + csrfToken + "&contactId=" + contactId;

        fetch("contact_del", {
            method: "post",
            headers: {
                'Content-Type': "application/x-www-form-urlencoded"
            },
            body: data
        }).then(r => {
            if (r.status === 200) {
                alert("delete success");
                window.location = "dashboard.jsp";
            } else {
                alert("delete fail");
            }
        })
    }
</script>
</body>
</html>
