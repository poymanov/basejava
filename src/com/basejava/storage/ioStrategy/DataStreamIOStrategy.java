package com.basejava.storage.ioStrategy;

import com.basejava.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class DataStreamIOStrategy implements IOStrategy {

    @Override
    public void output(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());

            writeWithExceptions(resume.getContacts().entrySet(), dos, item -> {
                dos.writeUTF(item.getKey().name());
                dos.writeUTF(item.getValue().getTitle());
            });

            writeWithExceptions(resume.getSections().entrySet(), dos, item -> {
                String name = item.getKey().name();
                SectionType type = SectionType.valueOf(name);
                AbstractSection section = item.getValue();

                dos.writeUTF(name);

                switch (type) {
                    case OBJECTIVE:
                    case PERSONAL:
                        dos.writeUTF(((TextSection) section).getTitle());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        writeWithExceptions(((ListSection) section).getItems(), dos, dos::writeUTF);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        writeOrganizationSection(dos, ((OrganizationSection) section).getItems());
                        break;
                    default:
                        break;
                }
            });
        }
    }

    @Override
    public Resume input(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);

            readWithExceptions(dis, () -> {
                ContactType contactType = ContactType.valueOf(dis.readUTF());
                resume.addContact(contactType, dis.readUTF());
            });

            readWithExceptions(dis, () -> {
                SectionType section = SectionType.valueOf(dis.readUTF());

                switch (section) {
                    case OBJECTIVE:
                    case PERSONAL:
                        resume.addSection(section, new TextSection(dis.readUTF()));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        readListSection(dis, resume, section);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        readOrganizationSection(dis, resume, section);
                        break;
                    default:
                        break;
                }
            });

            return resume;
        }
    }

    private void writeOrganizationSection(DataOutputStream dos, List<Organization> items) throws IOException {
        writeWithExceptions(items, dos, item -> {
            String url = item.getUrl();
            dos.writeUTF(item.getTitle());
            dos.writeUTF(url == null ? "" : url);

            writeWithExceptions(item.getItems(), dos, subitem -> {
                String description = subitem.getDescription();
                dos.writeUTF(subitem.getTitle());
                dos.writeUTF(description == null ? "" : description);
                dos.writeUTF(subitem.getPeriodFrom().toString());
                dos.writeUTF(subitem.getPeriodTo() == null ? "" : subitem.getPeriodTo().toString());
            });
        });
    }

    private void readListSection(DataInputStream dis, Resume resume, SectionType type) throws IOException {
        ArrayList<String> data = new ArrayList<>();
        readWithExceptions(dis, () -> data.add(dis.readUTF()));
        resume.addSection(type, new ListSection(data));
    }

    private void readOrganizationSection(DataInputStream dis, Resume resume, SectionType type) throws IOException {
        ArrayList<Organization> organization = new ArrayList<>();

        readWithExceptions(dis, () -> {
            String title = dis.readUTF();
            String url = dis.readUTF();

            if (url.equals("")) {
                url = null;
            }

            List<Position> positions = new ArrayList<>();

            readWithExceptions(dis, () -> {
                String itemTitle = dis.readUTF();
                String description = dis.readUTF();

                if (description.equals("")) {
                    description = null;
                }

                String periodFromString = dis.readUTF();
                String periodToString = dis.readUTF();

                LocalDate periodFrom = LocalDate.parse(periodFromString);
                LocalDate periodTo = periodToString.isEmpty() ? null : LocalDate.parse(periodToString);

                positions.add(new Position(itemTitle, description, periodFrom, periodTo));
            });

            organization.add(new Organization(title, url, positions));
        });

        resume.addSection(type, new OrganizationSection(organization));
    }

    private <T> void writeWithExceptions(Collection<T> collection, DataOutputStream dos, DataWriter<T> dataWriter) throws IOException {
        dos.writeInt(collection.size());
        for (T item: collection) {
            dataWriter.write(item);
        }
    }

    private void readWithExceptions(DataInputStream dis, DataReader dataReader) throws IOException {
        int size = dis.readInt();

        for (int i = 0; i < size; i++) {
            dataReader.read();
        }
    }
}
