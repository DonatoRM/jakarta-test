package es.donatodev.jakarta.test.repositories;

import java.util.List;

public interface RepositoryDB<T> {
    List<T> listAll();
    T searchById(Long id);
    T save(T t);
    boolean delete(Long id);
}
