package com.basejava.storage.ioStrategy;

import com.basejava.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataStreamIOStrategy implements IOStrategy {

    @Override
    public void output(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());

            Map<ContactType, Contact> contacts = resume.getContacts();
            dos.writeInt(contacts.size());

            writeWithExceptions(contacts, dos, (ds, key, value) -> {
                dos.writeUTF(((Contact) value).getTitle());
            });

            writeWithExceptions(resume.getSections(), dos, (ds, key, value) -> {
                SectionType section = SectionType.valueOf(key.name());

                switch (section) {
                    case OBJECTIVE:
                    case PERSONAL:
                        dos.writeUTF(((TextSection) value).getTitle());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        writeListSection(dos, ((ListSection) value).getItems());
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        writeOrganizationSection(dos, ((OrganizationSection) value).getItems());
                        break;
                    default:
                        break;
                }
            });
        }
    }

    private <Key extends Enum, Value> void writeWithExceptions(Map<Key, Value> collection, DataOutputStream dos, DataWriter dataWriter) throws IOException {
        for (Map.Entry<Key, Value> entry: collection.entrySet()) {
            dos.writeUTF(entry.getKey().name());
            dataWriter.write(dos, entry.getKey(), entry.getValue());
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

    private void writeOrganizationSection(DataOutputStream dos, List<OrganizationList> items) throws IOException {
        dos.writeInt(items.size());

        for (OrganizationList item : items) {
            dos.writeUTF(item.getTitle());

            dos.writeInt(item.getItems().size());

            for (OrganizationItem subitem : item.getItems()) {
                dos.writeUTF(subitem.getTitle());
                dos.writeUTF(subitem.getDescription());
                dos.writeUTF(subitem.getPeriodFrom().toString());
                dos.writeUTF(subitem.getPeriodTo() == null ? "" : subitem.getPeriodTo().toString());
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
                LocalDate periodTo = periodToString.isEmpty() ? null : LocalDate.parse(periodToString);

                organizationItems.add(new OrganizationItem(itemTitle, description, periodFrom, periodTo));
            }

            organizationList.add(new OrganizationList(title, organizationItems));
        }

        resume.addSection(type, new OrganizationSection(organizationList));
    }
}
