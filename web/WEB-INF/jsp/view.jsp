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
                    <li>
                        <strong>${contactEntry.key.title}</strong>: ${contactEntry.value.title}
                    </li>
                </c:forEach>
            </ul>
        </c:if>
        <c:if test="${resume.sections.size() > 0}">
            <c:forEach var="sectionEntry" items="${resume.sections}">
                <jsp:useBean id="sectionEntry" type="java.util.Map.Entry<com.basejava.model.SectionType, com.basejava.model.AbstractSection>"/>
                <h2>${sectionEntry.key.title}</h2>

                <c:choose>
                    <c:when test="${(sectionEntry.key.name().equals('PERSONAL') || sectionEntry.key.name().equals('OBJECTIVE'))}">
                        ${sectionEntry.value.title}
                    </c:when>
                    <c:when test="${(sectionEntry.key.name().equals('ACHIEVEMENT') || sectionEntry.key.name().equals('QUALIFICATIONS'))}">
                        <ul>
                            <c:forEach var="item" items="${sectionEntry.value.items}">
                                <li>${item}</li>
                            </c:forEach>
                        </ul>
                    </c:when>
                    <c:when test="${(sectionEntry.key.name().equals('EXPERIENCE') || sectionEntry.key.name().equals('EDUCATION'))}">
                        <ul>
                            <li>
                                <c:forEach var="organization" items="${sectionEntry.value.items}">
                                    <h3>${organization.title}</h3>
                                    <ul>
                                        <c:forEach var="position" items="${organization.items}">
                                            <li>${position.title} (${position.periodFrom} - ${position.periodTo})<br>${position.description}</li>
                                        </c:forEach>
                                    </ul>
                                </c:forEach>
                            </li>
                        </ul>
                    </c:when>
                    <c:otherwise>
                        -
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </c:if>
    </div>
</main>
<jsp:include page="common/footer.jsp"/>
</body>
</html>