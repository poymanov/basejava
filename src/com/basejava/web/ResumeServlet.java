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

        for (SectionType type: SectionType.values()) {
            switch (type) {
                case OBJECTIVE:
                case PERSONAL:
                    String value = request.getParameter(type.name());

                    if (value != null && value.trim().length() != 0) {
                        resume.addSection(type, new TextSection(value));
                    } else {
                        deleteSectionIfNotExist(resume, type);
                    }
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    String[] values = request.getParameterValues(type.name());

                    if (values != null) {
                        resume.addSection(type, new ListSection(new ArrayList<>(Arrays.asList(values))));
                    } else {
                        deleteSectionIfNotExist(resume, type);
                    }

                    break;
                case EXPERIENCE:
                case EDUCATION:
                    break;
                default:
                    break;
            }
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

    private void deleteSectionIfNotExist(Resume resume, SectionType type) {
        if (resume.getSections().get(type) != null) {
            resume.getSections().remove(type);
        }
    }
}
