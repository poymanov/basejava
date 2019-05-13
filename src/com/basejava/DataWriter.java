package com.basejava;

import com.basejava.model.*;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

public class DataWriter {
    public void write(SectionType sectionType, AbstractSection section, DataOutputStream dos) throws IOException {
        switch (sectionType) {
            case OBJECTIVE:
            case PERSONAL:
                writeTextSection(section, dos);
                break;
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                writeListSection(section, dos);
                break;
            case EXPERIENCE:
            case EDUCATION:
                writeOrganizationSection(section, dos);
                break;
            default:
                break;
        }
    }

    private void writeTextSection(AbstractSection section, DataOutputStream dos) throws IOException {
        dos.writeUTF(((TextSection) section).getTitle());
    }

    private void writeListSection(AbstractSection section, DataOutputStream dos) throws IOException {
        List<String> items = ((ListSection) section).getItems();
        dos.writeInt(items.size());

        for (String item : items) {
            dos.writeUTF(item);
        }
    }

    private void writeOrganizationSection(AbstractSection section, DataOutputStream dos) throws IOException {
        List <OrganizationList> items = ((OrganizationSection) section).getItems();
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
}
