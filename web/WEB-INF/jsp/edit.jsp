<%@ page import="com.basejava.model.ContactType" %>
<%@ page import="com.basejava.model.SectionType" %>
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

            <div>
                <h2>Contacts</h2>
                <c:forEach var="type" items="<%=ContactType.values()%>">
                    <div class="form-group">
                        <label for="${type.title}">${type.title}</label>
                        <input name="${type.title}" type="text" class="form-control" id="${type.title}" placeholder="Enter ${type.title}" value="${resume.getContact(type).title}">
                    </div>
                </c:forEach>
            </div>

            <div>
                <c:forEach var="type" items="<%=SectionType.values()%>">
                    <h2>${type.title}</h2>

                    <c:choose>
                        <c:when test = "${type == SectionType.PERSONAL || type == SectionType.OBJECTIVE}">
                            <div class="form-group">
                                <input name="${type.name()}" type="text" class="form-control" placeholder="Enter value" value="${resume.sections.get(type).title}">
                            </div>
                        </c:when>

                        <c:when test = "${type == SectionType.ACHIEVEMENT || type == SectionType.QUALIFICATIONS}">
                            <div class="list-block">
                                <button type="button" class="btn btn-success add-input-list mb-2" data-type="${SectionType.ACHIEVEMENT.name()}"><span class="oi oi-plus"></span></button>

                                <div class="inputs">
                                    <c:forEach var="item" items="${resume.sections.get(type).items}">
                                        <div class="form-group d-flex">
                                            <input name="${type.name()}" type="text" class="form-control mr-2" placeholder="Enter value" value="${item}">
                                            <button type="button" class="btn btn-danger delete-input"><span class="oi oi-trash"></span></button>
                                        </div>
                                    </c:forEach>
                                </div>
                            </div>
                        </c:when>

                        <c:when test = "${type == SectionType.EDUCATION || type == SectionType.EXPERIENCE}">
                            <div class="organization-block">
                                <button type="button" class="btn btn-success add-organization mb-2" data-type="${type.name()}"><span class="oi oi-plus"></span></button>
                                <div class="organizations-list">
                                    <c:forEach var="organization" items="${resume.sections.get(type).items}">
                                        <div class="organization form-group">
                                            <div class="d-flex mb-3">
                                                <input name="${type.name()}" type="text" placeholder="Title" class="form-control mr-2" value="${organization.title}" required>
                                                <button type="button" class="btn btn-danger delete-organization"><span class="oi oi-trash"></span></button>
                                            </div>
                                            <div class="positions pl-4">
                                                <div class="positions-list">
                                                    <c:forEach var="position" items="${organization.items}">
                                                        <div class="position">
                                                            <div class="d-flex mb-2">
                                                                <button type="button" class="btn btn-danger delete-position mr-2"><span class="oi oi-trash"></span></button>
                                                                <input name="${type.name()}" type="text" placeholder="Title" class="form-control mr-2" value="${position.title}" required>
                                                                <input name="${type.name()}" type="date" placeholder="Period from" class="form-control mr-2" value="${position.periodFrom}" required>
                                                                <input name="${type.name()}" type="date" placeholder="Period to" class="form-control" value="${position.periodTo}">
                                                            </div>
                                                            <div>
                                                                <textarea name="${type.name()}" class="form-control" rows="5">${position.description}</textarea>
                                                            </div>
                                                            <input name="${type.name()}" type="hidden" value="---">
                                                            <hr>
                                                        </div>
                                                    </c:forEach>
                                                </div>
                                                <input name="${type.name()}" type="hidden" value="-">
                                                <button type="button" class="btn btn-success add-position" data-type="${type.name()}"><span class="oi oi-plus"></span></button>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </div>
                            </div>
                        </c:when>

                        <c:otherwise>
                            -
                        </c:otherwise>
                    </c:choose>

                </c:forEach>
            </div>

            <button type="submit" class="btn btn-primary">Submit</button>
            <button class="btn btn-success" onclick="window.history.back()">Back</button>
        </form>
    </div>
</main>
<jsp:include page="common/footer.jsp"/>
<script
        src="https://code.jquery.com/jquery-3.4.1.min.js"
        integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="
        crossorigin="anonymous"></script>
<script src="${pageContext.request.contextPath}/js/script.js"></script>
</body>
</html>