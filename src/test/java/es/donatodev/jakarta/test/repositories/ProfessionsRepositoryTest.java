package es.donatodev.jakarta.test.repositories;
import es.donatodev.jakarta.test.models.Profession;
import org.junit.jupiter.api.*;
import org.mockito.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProfessionsRepositoryTest {

    @Mock
    private ProfessionsRepository repository;

    @BeforeAll
    void initMocks() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListAll() {
        List<Profession> mockProfessions = Arrays.asList(
            new Profession(1L, "Engineer"),
            new Profession(2L, "Doctor")
        );
        when(repository.listAll()).thenReturn(mockProfessions);

        List<Profession> professions = repository.listAll();
        assertNotNull(professions);
        assertEquals(2, professions.size());
        assertEquals("Engineer", professions.get(0).getName());
        assertEquals("Doctor", professions.get(1).getName());
    }
    @Test
    void testSearchById() {
        Profession mockProfession = new Profession(1L, "Engineer");
        when(repository.searchById(1L)).thenReturn(mockProfession);

        Profession profession = repository.searchById(1L);
        assertNotNull(profession);
        assertEquals("Engineer", profession.getName());
    }

    @Test
    void testSaveInsert() {
        Profession newProfession = new Profession(null, "Artist");
        Profession savedProfession = new Profession(3L, "Artist");
        when(repository.save(newProfession)).thenReturn(savedProfession);

        Profession saved = repository.save(newProfession);
        assertNotNull(saved.getId());
        assertEquals("Artist", saved.getName());
    }

    @Test
    void testSaveUpdate() {
        Profession profession = new Profession(1L, "Architect");
        when(repository.save(profession)).thenReturn(profession);

        Profession updated = repository.save(profession);
        assertEquals("Architect", updated.getName());
    }

    @Test
    void testDelete() {
        when(repository.delete(1L)).thenReturn(true);

        boolean deleted = repository.delete(1L);
        assertTrue(deleted);
    }

    @Test
    void testDeleteNonExisting() {
        when(repository.delete(9999L)).thenReturn(false);

        boolean deleted = repository.delete(9999L);
        assertFalse(deleted);
    }
}
