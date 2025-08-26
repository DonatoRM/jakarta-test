package es.donatodev.jakarta.test.repositories;

import java.util.List;

public interface RepositoryDB<T> {
    List<T> listAll();
    T searchById(Long id);
    void save(T t);
    void delete(Long id);
}
