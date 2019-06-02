<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Resumes</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/open-iconic/1.1.1/font/css/open-iconic-bootstrap.min.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <jsp:include page="common/header.jsp"/>
    <main>
        <div class='container'>
            <h1>Resumes</h1>
            <table class='table table-bordered'>
                <tr>
                    <th>Uuid</th>
                    <th>Full Name</th>
                    <th></th>
                    <th></th>
                    <th></th>
                </tr>
                <c:forEach items="${resumes}" var="resume">
                    <jsp:useBean id="resume" type="com.basejava.model.Resume"/>
                    <tr>
                        <td>${resume.uuid}</td>
                        <td>${resume.fullName}</td>
                        <td><a href="resume?uuid=${resume.uuid}&action=view"><span class="oi oi-eye"></span></a></td>
                        <td><a href="resume?uuid=${resume.uuid}&action=edit"><span class="oi oi-pencil"></span></a></td>
                        <td><a href="resume?uuid=${resume.uuid}&action=delete"><span class="oi oi-trash"></span></a></td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </main>
    <jsp:include page="common/footer.jsp"/>
</body>
</html>

