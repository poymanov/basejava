<%@ page import="com.basejava.model.ContactType" %>
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
        <h1>Edit - ${resume.uuid}</h1>
        <form method="post">
            <input type="hidden" name="uuid" value="${resume.uuid}">
            <div class="form-group">
                <label for="fullName">Full Name</label>
                <input name="fullName" type="text" class="form-control" id="fullName" placeholder="Enter Full Name" value="${resume.fullName}">
            </div>
            <h2>Contacts</h2>
            <c:forEach var="type" items="<%=ContactType.values()%>">
                <div class="form-group">
                    <label for="${type.title}">${type.title}</label>
                    <input name="${type.title}" type="text" class="form-control" id="${type.title}" placeholder="Enter ${type.title}" value="${resume.getContact(type).getTitle()}">
                </div>
            </c:forEach>

            <c:forEach var="sectionEntry" items="${resume.sections}">
                <jsp:useBean id="sectionEntry" type="java.util.Map.Entry<com.basejava.model.SectionType, com.basejava.model.AbstractSection>"/>
                <h2>${sectionEntry.key.title}</h2>

                <c:choose>
                    <c:when test="${(sectionEntry.key.name().equals('PERSONAL') || sectionEntry.key.name().equals('OBJECTIVE'))}">
                        <div class="form-group">
                            <input name="${sectionEntry.key.name()}" type="text" class="form-control" placeholder="Enter ${sectionEntry.key.name()}" value="${sectionEntry.value.title}">
                        </div>
                    </c:when>
                    <c:when test="${(sectionEntry.key.name().equals('ACHIEVEMENT') || sectionEntry.key.name().equals('QUALIFICATIONS'))}">
                        <c:forEach var="item" items="${sectionEntry.value.items}">
                            <div class="form-group">
                                <input name="${sectionEntry.key.name()}" type="text" class="form-control" placeholder="Enter ${sectionEntry.key.name()}" value="${item}">
                            </div>
                        </c:forEach>
                    </c:when>
                </c:choose>
            </c:forEach>

            <button type="submit" class="btn btn-primary">Submit</button>
            <button class="btn btn-success" onclick="window.history.back()">Back</button>
        </form>
    </div>
</main>
<jsp:include page="common/footer.jsp"/>
</body>
</html>