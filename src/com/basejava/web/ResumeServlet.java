package com.basejava.web;

import com.basejava.Config;
import com.basejava.exceptions.NotExistedStorageException;
import com.basejava.model.Resume;
import com.basejava.storage.AbstractStorage;
import com.basejava.storage.SqlStorage;

import java.io.IOException;

public class ResumeServlet extends javax.servlet.http.HttpServlet {

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

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
        SqlStorage storage = new SqlStorage(Config.get().getDbUrl(), Config.get().getDbUser(), Config.get().getDbPassword());
        storage.clear();

        storage.save(new Resume("uuid1", "Test Name"));
        storage.save(new Resume("uuid2", "Test Name 2"));
        storage.save(new Resume("uuid3", "Test Name 3"));

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
