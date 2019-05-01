package com.basejava.storage;

import com.basejava.exceptions.StorageException;
import com.basejava.model.Contact;
import com.basejava.model.ContactType;
import com.basejava.model.Resume;

import java.io.*;
import java.util.EnumMap;
import java.util.Map;

public class DataStreamIOStrategy implements IOStrategy {

    @Override
    public void output(Resume resume, OutputStream os) {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());

            Map<ContactType, Contact> contacts = resume.getContacts();
            dos.writeInt(contacts.size());

            for (Map.Entry<ContactType, Contact> entry: contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue().getTitle());
            }


        } catch (IOException e) {
            throw new RuntimeException("Failed to write file");
        }
    }

    @Override
    public Resume input(InputStream is) {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);

            Map<ContactType, Contact> contacts = new EnumMap<>(ContactType.class);

            int size = dis.readInt();

            for (int i = 0; i < size; i++) {
                ContactType contactType = ContactType.valueOf(dis.readUTF());
                contacts.put(contactType, new Contact(dis.readUTF()));
            }

            resume.setContacts(contacts);

            return resume;
        } catch (IOException e) {
            throw new StorageException("Error read resume", null, e);
        }
    }
}
