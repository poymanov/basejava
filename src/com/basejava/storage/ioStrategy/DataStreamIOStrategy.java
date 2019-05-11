package com.basejava.storage.ioStrategy;

import com.basejava.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.basejava.model.SectionType.*;

public class DataStreamIOStrategy implements IOStrategy {

    @Override
    public void output(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());

            Map<ContactType, Contact> contacts = resume.getContacts();
            dos.writeInt(contacts.size());

            for (Map.Entry<ContactType, Contact> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue().getTitle());
            }

            for (Map.Entry<SectionType, AbstractSection> entry : resume.getSections().entrySet()) {
                dos.writeUTF(entry.getKey().name());

                switch (entry.getKey()) {
                    case OBJECTIVE:
                        dos.writeUTF(((TextSection) resume.getSections().get(OBJECTIVE)).getTitle());
                        break;
                    case PERSONAL:
                        dos.writeUTF(((TextSection) resume.getSections().get(PERSONAL)).getTitle());
                        break;
                    case ACHIEVEMENT:
                        writeListSection(dos, ((ListSection) resume.getSections().get(SectionType.ACHIEVEMENT)).getItems());
                        break;
                    case QUALIFICATIONS:
                        writeListSection(dos, ((ListSection) resume.getSections().get(QUALIFICATIONS)).getItems());
                        break;
                    case EXPERIENCE:
                        writeOrganizationSection(dos, ((OrganizationSection) resume.getSections().get(EXPERIENCE)).getItems());
                        break;
                    case EDUCATION:
                        writeOrganizationSection(dos, ((OrganizationSection) resume.getSections().get(EDUCATION)).getItems());
                        break;
                    default:
                        continue;
                }
            }
        }
    }

    @Override
    public Resume input(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);

            int contactsSize = dis.readInt();

            for (int i = 0; i < contactsSize; i++) {
                ContactType contactType = ContactType.valueOf(dis.readUTF());
                resume.addContact(contactType, dis.readUTF());
            }

            while (dis.available() > 0) {
                SectionType section = SectionType.valueOf(dis.readUTF());

                switch (section) {
                    case OBJECTIVE:
                        resume.addSection(OBJECTIVE, new TextSection(dis.readUTF()));
                        break;
                    case PERSONAL:
                        resume.addSection(PERSONAL, new TextSection(dis.readUTF()));
                        break;
                    case ACHIEVEMENT:
                        readListSection(dis, resume, ACHIEVEMENT, dis.readInt());
                        break;
                    case QUALIFICATIONS:
                        readListSection(dis, resume, QUALIFICATIONS, dis.readInt());
                        break;
                    case EXPERIENCE:
                        readOrganizationSection(dis, resume, EXPERIENCE);
                        break;
                    case EDUCATION:
                        readOrganizationSection(dis, resume, EDUCATION);
                        break;
                    default:
                        break;
                }
            }

            return resume;
        }
    }

    private void writeOrganizationSection(DataOutputStream dos, List<OrganizationList> items) throws IOException {
        dos.writeInt(items.size());

        for (OrganizationList item : items) {
            dos.writeUTF(item.getTitle());

            dos.writeInt(item.getItems().size());

            for (OrganizationItem subitem : item.getItems()) {
                dos.writeUTF(subitem.getTitle());
                dos.writeUTF(subitem.getDescription());
                dos.writeUTF(subitem.getPeriodFrom().toString());

                if (subitem.getPeriodTo() != null) {
                    dos.writeUTF(subitem.getPeriodTo().toString());
                } else {
                    dos.writeUTF("-");
                }
            }
        }
    }

    private void writeListSection(DataOutputStream dos, List<String> items) throws IOException {
        dos.writeInt(items.size());

        for (String item : items) {
            dos.writeUTF(item);
        }
    }

    private void readListSection(DataInputStream dis, Resume resume, SectionType type, int size) throws IOException {
        ArrayList<String> data = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            data.add(dis.readUTF());
        }

        resume.addSection(type, new ListSection(data));
    }

    private void readOrganizationSection(DataInputStream dis, Resume resume, SectionType type) throws IOException {
        ArrayList<OrganizationList> organizationList = new ArrayList<>();

        int allSize = dis.readInt();

        for (int i = 0; i < allSize; i++) {
            String title = dis.readUTF();

            List<OrganizationItem> organizationItems = new ArrayList<>();

            int itemsSize = dis.readInt();

            for (int k = 0; k < itemsSize; k++) {
                String itemTitle = dis.readUTF();
                String description = dis.readUTF();

                String periodFromString = dis.readUTF();
                String periodToString = dis.readUTF();

                LocalDate periodFrom = LocalDate.parse(periodFromString);
                LocalDate periodTo = null;

                if (!periodToString.equals("-")) {
                    periodTo = LocalDate.parse(periodToString);
                }

                organizationItems.add(new OrganizationItem(itemTitle, description, periodFrom, periodTo));
            }

            organizationList.add(new OrganizationList(title, organizationItems));
        }

        resume.addSection(type, new OrganizationSection(organizationList));
    }
}
