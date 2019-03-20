import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];

    void clear() {
        Arrays.fill(storage, 0, size(), null);
    }

    void save(Resume resume) {
        int size = size();

        storage[size] = resume;
    }

    Resume get(String uuid) {
        for (Resume resume: getAll()) {
            if (resume.uuid.contains(uuid)) {
                return resume;
            }
        }

        return null;
    }

    void delete(String uuid) {
        int deletedIndex = 0;
        boolean hasDeletedIndex = false;

        for (int i = 0; i < size(); i++) {
            if (storage[i].uuid.contains(uuid)) {
                deletedIndex = i;
                hasDeletedIndex = true;
                break;
            }
        }

        if (!hasDeletedIndex) {
            return;
        }

        storage[deletedIndex] = null;

        for (int i = ++deletedIndex; i < 10000; i++) {
            if (storage[i] == null) {
                break;
            }

            int newPosition = i;
            newPosition--;

            storage[newPosition] = storage[i];
            storage[i] = null;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOf(storage, size());
    }

    int size() {
        for (int i = 0; i < 10000; i++) {
            if (storage[i] == null) {
                return i;
            }
        }

        return storage.length;
    }
}
