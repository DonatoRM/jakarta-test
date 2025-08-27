package es.donatodev.jakarta.test.repositories;

import java.util.List;

import es.donatodev.jakarta.test.models.User;

public class UsersRepository implements RepositoryDB<User> {

    @Override
    public List<User> listAll() {
        throw new UnsupportedOperationException("Unimplemented method 'listAll'");
    }

    @Override
    public User searchById(Long id) {
        throw new UnsupportedOperationException("Unimplemented method 'searchById'");
    }

    @Override
    public User save(User t) {
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public boolean delete(Long id) {
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

}
