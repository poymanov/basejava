package com.basejava.web;

import com.basejava.Config;
import com.basejava.model.*;
import com.basejava.storage.Storage;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class ResumeServlet extends HttpServlet {
    private Storage storage = Config.get().getStorage();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String uuid = request.getParameter("uuid");
        String sectionPersonal = request.getParameter("PERSONAL");
        String sectionObjective = request.getParameter("OBJECTIVE");
        String[] sectionAchievements = request.getParameterValues("ACHIEVEMENT");
        String[] sectionQualifications = request.getParameterValues("QUALIFICATIONS");
        String fullName = request.getParameter("fullName");

        Resume resume;
        boolean isNew = false;

        if (uuid != null && uuid.trim().length() != 0) {
            resume = storage.get(uuid);
            resume.setFullName(fullName);
        } else {
            isNew = true;
            resume = new Resume(fullName);
        }

        for (ContactType type: ContactType.values()) {
            String value = request.getParameter(type.getTitle());
            if (value != null && value.trim().length() != 0) {
                resume.addContact(type, value);
            } else {
                resume.getContacts().remove(type);
            }
        }

        if (sectionPersonal != null && sectionPersonal.trim().length() != 0) {
            resume.addSection(SectionType.PERSONAL, new TextSection(sectionPersonal));
        } else {
            resume.getSections().remove(SectionType.PERSONAL);
        }

        if (sectionObjective != null && sectionObjective.trim().length() != 0) {
            resume.addSection(SectionType.OBJECTIVE, new TextSection(sectionObjective));
        } else {
            resume.getSections().remove(SectionType.OBJECTIVE);
        }

        if (sectionAchievements.length > 0) {
            resume.addSection(SectionType.ACHIEVEMENT, new ListSection(new ArrayList<>(Arrays.asList(sectionAchievements))));
        } else {
            resume.getSections().remove(SectionType.ACHIEVEMENT);
        }

        if (sectionQualifications.length > 0) {
            resume.addSection(SectionType.QUALIFICATIONS, new ListSection(new ArrayList<>(Arrays.asList(sectionQualifications))));
        } else {
            resume.getSections().remove(SectionType.QUALIFICATIONS);
        }

        if (isNew) {
            storage.save(resume);
        } else {
            storage.update(resume);
        }

        response.sendRedirect("resume");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");

        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }

        Resume resume;
        String template;

        switch (action) {
            case "create":
                resume = new Resume(null, null);
                template = "/WEB-INF/jsp/edit.jsp";
                break;
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
                resume = storage.get(uuid);
                template = "/WEB-INF/jsp/view.jsp";
                break;
            case "edit":
                resume = storage.get(uuid);
                template = "/WEB-INF/jsp/edit.jsp";
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }

        request.setAttribute("resume", resume);

        request.getRequestDispatcher(template).forward(request, response);
    }
}
