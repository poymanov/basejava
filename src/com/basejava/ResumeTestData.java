package com.basejava;

import com.basejava.model.*;

import java.time.LocalDate;
import java.util.*;

public class ResumeTestData {
    public static void main(String[] args) {
        EnumMap contacts = getContacts();

        EnumMap sections = new EnumMap(SectionType.class) {{
            put(SectionType.OBJECTIVE, getObjective());
            put(SectionType.PERSONAL, getPersonal());
            put(SectionType.ACHIEVEMENT, getAchievement());
            put(SectionType.QUALIFICATIONS, getQualifications());
            put(SectionType.EXPERIENCE, getExperience());
            put(SectionType.EDUCATION, getEducation());
        }};

        Resume resume = new Resume("Григорий Кислин");
        resume.setContacts(contacts);
        resume.setSections(sections);

        System.out.println(resume.getFullName() + "\n");

        // Вывод контактов
        for (HashMap.Entry<ContactType, Contact> item : resume.getContacts().entrySet()) {
            String contact = item.getKey().getTitle() + ": ";

            if (item.getKey() == ContactType.GITHUB ||
                    item.getKey() == ContactType.URL ||
                    item.getKey() == ContactType.LINKEDIN ||
                    item.getKey() == ContactType.STACKOVERFLOW) {
                contact += item.getValue().getLink();
            } else {
                contact += item.getValue().getTitle();
            }

            System.out.println(contact);
        }

        System.out.println("");

        // Вывод секций
        for (HashMap.Entry<SectionType, AbstractSection> item : resume.getSections().entrySet()) {
            System.out.println(item.getKey().getTitle());
            System.out.println(item.getValue() + "\n");
        }
    }

    private static EnumMap getContacts() {
        return new EnumMap(ContactType.class) {{
            put(ContactType.PHONE, new Contact("+7(921) 855-0482", "+7(921) 855-0482"));
            put(ContactType.SKYPE, new Contact("grigory.kislin", "grigory.kislin"));
            put(ContactType.EMAIL, new Contact("gkislin@yandex.ru", "gkislin@yandex.ru"));
            put(ContactType.LINKEDIN, new Contact("Профиль LinkedIn", "https://www.linkedin.com/in/gkislin"));
            put(ContactType.GITHUB, new Contact("Профиль GitHub", "https://github.com/gkislin"));
            put(ContactType.STACKOVERFLOW, new Contact("Профиль Stackoverflow", "https://stackoverflow.com/users/54198473"));
            put(ContactType.URL, new Contact("Домашняя страница", "http://gkislin.ru/"));
        }};
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
        ArrayList<OrganizationList> experienceList = new ArrayList<>();

        OrganizationItem item1 = new OrganizationItem("Автор проекта",
                "Создание, организация и проведение Java онлайн проектов и стажировок.",
                LocalDate.of(2013, 10, 1), null);


        OrganizationList organization1 = new OrganizationList("Java Online Projects", new ArrayList<>() {{
            add(item1);
        }});


        OrganizationItem item2 = new OrganizationItem("Старший разработчик (backend)",
                "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.",
                LocalDate.of(2014, 10, 1), LocalDate.of(2016, 1, 1));

        OrganizationList organization2 = new OrganizationList("Wrike", new ArrayList<>() {{
            add(item2);
        }});

        OrganizationItem item3 = new OrganizationItem("Java архитектор",
                "Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python",
                LocalDate.of(2012, 4, 1), LocalDate.of(2014, 10, 1));

        OrganizationList organization3 = new OrganizationList("RIT Center", new ArrayList<>() {{
            add(item3);
        }});


        OrganizationItem item4 = new OrganizationItem("Ведущий программист",
                "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM. Реализация RIA-приложения для администрирования, мониторинга и анализа результатов в области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5.",
                LocalDate.of(2010, 12, 1), LocalDate.of(2012, 4, 1));

        OrganizationList organization4 = new OrganizationList("Luxoft (Deutsche Bank)", new ArrayList<>() {{
            add(item4);
        }});


        OrganizationItem item5 = new OrganizationItem("Ведущий специалист",
                "Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" (GlassFish v2.1, v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). Реализация администрирования, статистики и мониторинга фреймворка. Разработка online JMX клиента (Python/ Jython, Django, ExtJS)",
                LocalDate.of(2008, 6, 1), LocalDate.of(2010, 12, 1));

        OrganizationList organization5 = new OrganizationList("Yota", new ArrayList<>() {{
            add(item5);
        }});

        OrganizationItem item6 = new OrganizationItem("Разработчик ПО",
                "Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS) частей кластерного J2EE приложения (OLAP, Data mining).",
                LocalDate.of(2007, 3, 1), LocalDate.of(2008, 6, 1));

        OrganizationList organization6 = new OrganizationList("Enkata", new ArrayList<>() {{
            add(item6);
        }});

        OrganizationItem item7 = new OrganizationItem("Разработчик ПО",
                "Разработка информационной модели, проектирование интерфейсов, реализация и отладка ПО на мобильной IN платформе Siemens @vantage (Java, Unix).",
                LocalDate.of(2005, 1, 1), LocalDate.of(2007, 2, 1));

        OrganizationList organization7 = new OrganizationList("Siemens AG", new ArrayList<>() {{
            add(item7);
        }});

        OrganizationItem item8 = new OrganizationItem("Инженер по аппаратному и программному тестированию",
                "Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM).",
                LocalDate.of(1997, 9, 1), LocalDate.of(2005, 1, 1));

        OrganizationList organization8 = new OrganizationList("Alcatel", new ArrayList<>() {{
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
        ArrayList<OrganizationList> educationList = new ArrayList<>();

        OrganizationItem item1 = new OrganizationItem("\"Functional Programming Principles in Scala\" by Martin Odersky",
                null, LocalDate.of(2013, 3, 1), LocalDate.of(2013, 5, 1));

        OrganizationList organization1 = new OrganizationList("Coursera", new ArrayList<>() {{
            add(item1);
        }});

        OrganizationItem item2 = new OrganizationItem("Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"",
                null, LocalDate.of(2011, 3, 1), LocalDate.of(2011, 4, 1));

        OrganizationList organization2 = new OrganizationList("Luxoft", new ArrayList<>() {{
            add(item2);
        }});

        OrganizationItem item3 = new OrganizationItem("3 месяца обучения мобильным IN сетям (Берлин)", null,
                LocalDate.of(2005, 1, 1), LocalDate.of(2005, 4, 1));

        OrganizationList organization3 = new OrganizationList("Siemens AG", new ArrayList<OrganizationItem>() {{
            add(item3);
        }});

        OrganizationItem item4 = new OrganizationItem("6 месяцев обучения цифровым телефонным сетям (Москва)", null,
                LocalDate.of(1997, 9, 1), LocalDate.of(1998, 3, 1));

        OrganizationList organization4 = new OrganizationList("Alcatel", new ArrayList<>() {{
            add(item4);
        }});

        OrganizationItem item5 = new OrganizationItem("Аспирантура (программист С, С++)", null,
                LocalDate.of(1993, 9, 1), LocalDate.of(1996, 7, 1));


        OrganizationItem item6 = new OrganizationItem("Инженер (программист Fortran, C)", null,
                LocalDate.of(1997, 9, 1), LocalDate.of(1993, 7, 1));

        OrganizationList organization5 = new OrganizationList("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики",
                new ArrayList<>() {{
                    add(item5);
                    add(item6);
                }});


        OrganizationItem item7 = new OrganizationItem("Закончил с отличием", null,
                LocalDate.of(1984, 9, 1), LocalDate.of(1997, 6, 1));

        OrganizationList organization6 = new OrganizationList("Заочная физико-техническая школа при МФТИ", new ArrayList<>() {{
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
