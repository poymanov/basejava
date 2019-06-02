<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<jsp:useBean id="resume" type="com.basejava.model.Resume" scope="request"/>
<head>
    <title>Resume - ${resume.uuid}</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/open-iconic/1.1.1/font/css/open-iconic-bootstrap.min.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<jsp:include page="common/header.jsp"/>
<main>
    <div class='container'>
        <h1>Resume - ${resume.uuid}</h1>
        <div class="mb-2">
            <a href="resume?uuid=${resume.uuid}&action=edit" class="btn btn-success">Edit</a>
            <a href="resume?uuid=${resume.uuid}&action=delete" class="btn btn-danger">Delete</a>
        </div>
        <ul>
            <li><strong>Full Name:</strong> ${resume.fullName}</li>
        </ul>
        <c:if test="${resume.contacts.size() > 0}">
            <h2>Contacts</h2>
            <ul>
                <c:forEach var="contactEntry" items="${resume.contacts}">
                    <jsp:useBean id="contactEntry" type="java.util.Map.Entry<com.basejava.model.ContactType, com.basejava.model.Contact>"/>
                    <li>
                        <strong>${contactEntry.key.title}</strong>: ${contactEntry.value.title}
                    </li>
                </c:forEach>
            </ul>
        </c:if>
    </div>
</main>
<jsp:include page="common/footer.jsp"/>
</body>
</html>