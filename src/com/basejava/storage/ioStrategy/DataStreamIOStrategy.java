package com.basejava.storage.ioStrategy;

import com.basejava.DataWriter;
import com.basejava.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

            writeWithException(resume.getSections(), dos, new DataWriter());
        }
    }

    private void writeWithException(Map<SectionType, AbstractSection> sections, DataOutputStream dos, DataWriter dataWriter) throws IOException {
        for (Map.Entry<SectionType, AbstractSection> entry : sections.entrySet()) {
            dos.writeUTF(entry.getKey().name());
            dataWriter.write(entry.getKey(), entry.getValue(), dos);
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
                    case PERSONAL:
                        resume.addSection(section, new TextSection(dis.readUTF()));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        readListSection(dis, resume, section, dis.readInt());
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        readOrganizationSection(dis, resume, section);
                        break;
                    default:
                        break;
                }
            }

            return resume;
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
                LocalDate periodTo = periodToString.isEmpty() ? null : LocalDate.parse(periodToString);

                organizationItems.add(new OrganizationItem(itemTitle, description, periodFrom, periodTo));
            }

            organizationList.add(new OrganizationList(title, organizationItems));
        }

        resume.addSection(type, new OrganizationSection(organizationList));
    }
}
