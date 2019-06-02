package com.basejava.web;

import com.basejava.Config;
import com.basejava.exceptions.NotExistedStorageException;
import com.basejava.model.Resume;
import com.basejava.storage.Storage;

import java.io.IOException;

public class ResumeServlet extends javax.servlet.http.HttpServlet {
    private Storage storage = Config.get().getStorage();

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        String htmlStructure = "<html>" +
                "<head>" +
                "<title>Resumes</title>" +
                "<link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css\">" +
                "</head>" +
                "<body><div class='container'>{content}</div></body>" +
                "</html>";

        String content = "<h1>Resumes</h1><table class='table table-bordered'><tr><th>Uuid</th><th>Full Name</th></tr>{tableContent}</table>";

        String tableContent = getTableContent(request.getParameter("uuid"));
        content = content.replace("{tableContent}", tableContent);

        htmlStructure = htmlStructure.replace("{content}", content);
        response.getWriter().write(htmlStructure);
    }

    private String getTableContent(String uuid) {
        StringBuilder tableContent = new StringBuilder();

        if (uuid != null) {
            try {
                Resume resume = storage.get(uuid);
                String resumeRow = "<tr><td>" + resume.getUuid() + "</td><td>" + resume.getFullName() + "</td></tr>";
                tableContent.append(resumeRow);
            } catch (NotExistedStorageException e) {
                System.out.println(e.getMessage());
            }
        } else {
            for (Resume resume : storage.getAllSorted()) {
                String resumeRow = "<tr><td>" + resume.getUuid() + "</td><td>" + resume.getFullName() + "</td></tr>";
                tableContent.append(resumeRow);
            }
        }

        return tableContent.toString();
    }
}
