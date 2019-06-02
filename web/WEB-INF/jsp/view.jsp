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
        <ul>
            <li><strong>Full Name:</strong> ${resume.fullName}</li>
        </ul>
    </div>
</main>
<jsp:include page="common/footer.jsp"/>
</body>
</html>