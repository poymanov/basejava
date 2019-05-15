package com.basejava;

import com.basejava.model.*;

import java.time.LocalDate;
import java.util.*;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume resume = new Resume("Григорий Кислин");
        setContacts(resume);
        setSections(resume);

        System.out.println(resume.getFullName() + "\n");

        // Вывод контактов
        for (Map.Entry<ContactType, Contact> item : resume.getContacts().entrySet()) {
            System.out.println(item.getKey().getTitle() + ": " + item.getValue().getTitle());
        }

        System.out.println("");

        // Вывод секций
        for (Map.Entry<SectionType, AbstractSection> item : resume.getSections().entrySet()) {
            System.out.println(item.getKey().getTitle());
            System.out.println(item.getValue() + "\n");
        }
    }

    private static Map<ContactType, Contact> getContacts() {
        Map<ContactType, Contact> contacts = new EnumMap<>(ContactType.class);

        contacts.put(ContactType.PHONE, new Contact("+7(921) 855-0482"));
        contacts.put(ContactType.SKYPE, new Contact("grigory.kislin"));
        contacts.put(ContactType.EMAIL, new Contact("gkislin@yandex.ru"));
        contacts.put(ContactType.LINKEDIN, new Contact("https://www.linkedin.com/in/gkislin"));
        contacts.put(ContactType.GITHUB, new Contact("https://github.com/gkislin"));
        contacts.put(ContactType.STACKOVERFLOW, new Contact("https://stackoverflow.com/users/54198473"));
        contacts.put(ContactType.URL, new Contact("http://gkislin.ru/"));

        return contacts;
    }

    private static void setContacts(Resume resume) {
        resume.addContact(ContactType.PHONE, "+7(921) 855-0482");
        resume.addContact(ContactType.SKYPE, "grigory.kislin");
        resume.addContact(ContactType.EMAIL, "gkislin@yandex.ru");
        resume.addContact(ContactType.LINKEDIN, "https://www.linkedin.com/in/gkislin");
        resume.addContact(ContactType.GITHUB, "https://github.com/gkislin");
        resume.addContact(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/54198473");
        resume.addContact(ContactType.URL, "http://gkislin.ru/");
    }

    private static void setSections(Resume resume) {
        resume.addSection(SectionType.OBJECTIVE, getObjective());
        resume.addSection(SectionType.PERSONAL, getPersonal());
        resume.addSection(SectionType.ACHIEVEMENT, getAchievement());
        resume.addSection(SectionType.QUALIFICATIONS, getQualifications());
        resume.addSection(SectionType.EXPERIENCE, getExperience());
        resume.addSection(SectionType.EDUCATION, getEducation());
    }

    private static TextSection getObjective() {
        return new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");
    }

    private static TextSection getPersonal() {
        return new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.");
    }

    private static ListSection getAchievement() {
        ArrayList<String> data = new ArrayList<>();
        data.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.");
        data.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        data.add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.");
        data.add("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.");
        data.add("Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).");
        data.add("Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");

        return new ListSection(data);
    }

    private static ListSection getQualifications() {
        ArrayList<String> data = new ArrayList<>();
        data.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        data.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        data.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle, MySQL, SQLite, MS SQL, HSQLDB");
        data.add("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy, XML/XSD/XSLT, SQL, C/C++, Unix shell scripts, Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements).");
        data.add("Python: Django.");
        data.add("JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js");
        data.add("Scala: SBT, Play2, Specs2, Anorm, Spray, Akka");
        data.add("Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, OAuth1, OAuth2, JWT.");
        data.add("Инструменты: Maven + plugin development, Gradle, настройка Ngnix, администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, iReport, OpenCmis, Bonita, pgBouncer.");
        data.add("Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования, архитектурных шаблонов, UML, функционального программирования");
        data.add("Родной русский, английский \"upper intermediate\"");

        return new ListSection(data);
    }

    private static OrganizationSection getExperience() {
        ArrayList<Organization> experienceList = new ArrayList<>();

        Position item1 = new Position("Автор проекта",
                "Создание, организация и проведение Java онлайн проектов и стажировок.",
                LocalDate.of(2013, 10, 1), null);


        Organization organization1 = new Organization("Java Online Projects", new ArrayList<Position>() {{
            add(item1);
        }});


        Position item2 = new Position("Старший разработчик (backend)",
                "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.",
                LocalDate.of(2014, 10, 1), LocalDate.of(2016, 1, 1));

        Organization organization2 = new Organization("Wrike", new ArrayList<Position>() {{
            add(item2);
        }});

        Position item3 = new Position("Java архитектор",
                "Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python",
                LocalDate.of(2012, 4, 1), LocalDate.of(2014, 10, 1));

        Organization organization3 = new Organization("RIT Center", new ArrayList<Position>() {{
            add(item3);
        }});


        Position item4 = new Position("Ведущий программист",
                "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM. Реализация RIA-приложения для администрирования, мониторинга и анализа результатов в области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5.",
                LocalDate.of(2010, 12, 1), LocalDate.of(2012, 4, 1));

        Organization organization4 = new Organization("Luxoft (Deutsche Bank)", new ArrayList<Position>() {{
            add(item4);
        }});


        Position item5 = new Position("Ведущий специалист",
                "Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" (GlassFish v2.1, v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). Реализация администрирования, статистики и мониторинга фреймворка. Разработка online JMX клиента (Python/ Jython, Django, ExtJS)",
                LocalDate.of(2008, 6, 1), LocalDate.of(2010, 12, 1));

        Organization organization5 = new Organization("Yota", new ArrayList<Position>() {{
            add(item5);
        }});

        Position item6 = new Position("Разработчик ПО",
                "Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS) частей кластерного J2EE приложения (OLAP, Data mining).",
                LocalDate.of(2007, 3, 1), LocalDate.of(2008, 6, 1));

        Organization organization6 = new Organization("Enkata", new ArrayList<Position>() {{
            add(item6);
        }});

        Position item7 = new Position("Разработчик ПО",
                "Разработка информационной модели, проектирование интерфейсов, реализация и отладка ПО на мобильной IN платформе Siemens @vantage (Java, Unix).",
                LocalDate.of(2005, 1, 1), LocalDate.of(2007, 2, 1));

        Organization organization7 = new Organization("Siemens AG", new ArrayList<Position>() {{
            add(item7);
        }});

        Position item8 = new Position("Инженер по аппаратному и программному тестированию",
                "Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM).",
                LocalDate.of(1997, 9, 1), LocalDate.of(2005, 1, 1));

        Organization organization8 = new Organization("Alcatel", new ArrayList<Position>() {{
            add(item8);
        }});

        experienceList.add(organization1);
        experienceList.add(organization2);
        experienceList.add(organization3);
        experienceList.add(organization4);
        experienceList.add(organization5);
        experienceList.add(organization6);
        experienceList.add(organization7);
        experienceList.add(organization8);

        return new OrganizationSection(experienceList);
    }

    private static OrganizationSection getEducation() {
        ArrayList<Organization> educationList = new ArrayList<>();

        Position item1 = new Position("\"Functional Programming Principles in Scala\" by Martin Odersky",
                null, LocalDate.of(2013, 3, 1), LocalDate.of(2013, 5, 1));

        Organization organization1 = new Organization("Coursera", new ArrayList<Position>() {{
            add(item1);
        }});

        Position item2 = new Position("Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"",
                null, LocalDate.of(2011, 3, 1), LocalDate.of(2011, 4, 1));

        Organization organization2 = new Organization("Luxoft", new ArrayList<Position>() {{
            add(item2);
        }});

        Position item3 = new Position("3 месяца обучения мобильным IN сетям (Берлин)", null,
                LocalDate.of(2005, 1, 1), LocalDate.of(2005, 4, 1));

        Organization organization3 = new Organization("Siemens AG", new ArrayList<Position>() {{
            add(item3);
        }});

        Position item4 = new Position("6 месяцев обучения цифровым телефонным сетям (Москва)", null,
                LocalDate.of(1997, 9, 1), LocalDate.of(1998, 3, 1));

        Organization organization4 = new Organization("Alcatel", new ArrayList<Position>() {{
            add(item4);
        }});

        Position item5 = new Position("Аспирантура (программист С, С++)", null,
                LocalDate.of(1993, 9, 1), LocalDate.of(1996, 7, 1));


        Position item6 = new Position("Инженер (программист Fortran, C)", null,
                LocalDate.of(1997, 9, 1), LocalDate.of(1993, 7, 1));

        Organization organization5 = new Organization("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики",
                new ArrayList<Position>() {{
                    add(item5);
                    add(item6);
                }});

        Position item7 = new Position("Закончил с отличием", null,
                LocalDate.of(1984, 9, 1), LocalDate.of(1997, 6, 1));

        Organization organization6 = new Organization("Заочная физико-техническая школа при МФТИ", new ArrayList<Position>() {{
            add(item7);
        }});

        educationList.add(organization1);
        educationList.add(organization2);
        educationList.add(organization3);
        educationList.add(organization4);
        educationList.add(organization5);
        educationList.add(organization6);

        return new OrganizationSection(educationList);
    }

}
