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
            <h2>Contacts</h2>
            <c:forEach var="type" items="<%=ContactType.values()%>">
                <div class="form-group">
                    <label for="${type.title}">${type.title}</label>
                    <input name="${type.title}" type="text" class="form-control" id="${type.title}" placeholder="Enter ${type.title}" value="${resume.getContact(type).getTitle()}">
                </div>
            </c:forEach>

            <div>
                <h2>${SectionType.PERSONAL.title}</h2>
                <div class="form-group">
                    <input name="${SectionType.PERSONAL.name()}" type="text" class="form-control" placeholder="Enter value" value="${resume.sections.get(SectionType.PERSONAL).title}">
                </div>
            </div>

            <div>
                <h2>${SectionType.OBJECTIVE.title}</h2>
                <div class="form-group">
                    <input name="${SectionType.OBJECTIVE.name()}" type="text" class="form-control" placeholder="Enter value" value="${resume.sections.get(SectionType.OBJECTIVE).title}">
                </div>
            </div>

            <div class="list-block">
                <h2>${SectionType.ACHIEVEMENT.title}</h2>

                <button type="button" class="btn btn-success add-input-list mb-2" data-type="${SectionType.ACHIEVEMENT.name()}"><span class="oi oi-plus"></span></button>

                <div class="inputs">
                    <c:forEach var="item" items="${resume.sections.get(SectionType.ACHIEVEMENT).items}">
                        <div class="form-group d-flex">
                            <input name="${SectionType.ACHIEVEMENT.name()}" type="text" class="form-control" placeholder="Enter value" value="${item}">
                            <button type="button" class="btn btn-danger delete-input"><span class="oi oi-trash"></span></button>
                        </div>
                    </c:forEach>
                </div>
            </div>

            <div class="list-block">
                <h2>${SectionType.QUALIFICATIONS.title}</h2>

                <button type="button" class="btn btn-success add-input-list mb-2" data-type="${SectionType.QUALIFICATIONS.name()}"><span class="oi oi-plus"></span></button>

                <div class="inputs">
                    <c:forEach var="item" items="${resume.sections.get(SectionType.QUALIFICATIONS).items}">
                        <div class="form-group d-flex">
                            <input name="${SectionType.QUALIFICATIONS.name()}" type="text" class="form-control" placeholder="Enter value" value="${item}">
                            <button type="button" class="btn btn-danger delete-input"><span class="oi oi-trash"></span></button>
                        </div>
                    </c:forEach>
                </div>
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