import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    private int size;

    void clear() {
        Arrays.fill(storage, 0, size(), null);
        size = 0;
    }

    void save(Resume resume) {
        storage[size] = resume;
        size++;
    }

    Resume get(String uuid) {
        for (Resume resume: getAll()) {
            if (resume.uuid.equals(uuid)) {
                return resume;
            }
        }

        return null;
    }

    void delete(String uuid) {
        int deletedIndex = -1;

        for (int i = 0; i < size; i++) {
            if (storage[i].uuid.equals(uuid)) {
                deletedIndex = i;
                break;
            }
        }

        if (deletedIndex < 0) {
            return;
        }

        storage[deletedIndex] = null;

        for (int i = 0; i < size; i++) {
            storage[i] = storage[i + 1];
        }

        size--;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    int size() {
        return size;
    }
}
