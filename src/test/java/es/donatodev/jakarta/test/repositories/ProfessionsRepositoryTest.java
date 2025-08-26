package es.donatodev.jakarta.test.repositories;

import es.donatodev.jakarta.test.models.Profession;
import es.donatodev.jakarta.test.utils.DBConector;
import org.junit.jupiter.api.*;
import org.mockito.*;
import java.sql.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;




class ProfessionsRepositoryTest {

    @Test
    void testListAllReturnsProfessions() throws Exception {
        // Arrange
        Statement mockStatement = mock(Statement.class);
        ResultSet mockResultSet = mock(ResultSet.class);
        Connection mockConnection = mock(Connection.class);

        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);

        // Simulate two rows
        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getLong("id")).thenReturn(1L, 2L);
        when(mockResultSet.getString("name")).thenReturn("Engineer", "Doctor");

        // Mock DBConector.getConnection()
        try (MockedStatic<DBConector> dbConectorMock = mockStatic(DBConector.class)) {
            dbConectorMock.when(DBConector::getConnection).thenReturn(mockConnection);

            ProfessionsRepository repo = new ProfessionsRepository();

            // Act
            List<Profession> professions = repo.listAll();

            // Assert
            assertNotNull(professions);
            assertEquals(2, professions.size());
            assertEquals(1L, professions.get(0).getId());
            assertEquals("Engineer", professions.get(0).getName());
            assertEquals(2L, professions.get(1).getId());
            assertEquals("Doctor", professions.get(1).getName());
        }
    }

    @Test
    void testListAllReturnsNullWhenNoRows() throws Exception {
        Statement mockStatement = mock(Statement.class);
        ResultSet mockResultSet = mock(ResultSet.class);
        Connection mockConnection = mock(Connection.class);

        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);

        // Simulate no rows
        when(mockResultSet.next()).thenReturn(false);

        try (MockedStatic<DBConector> dbConectorMock = mockStatic(DBConector.class)) {
            dbConectorMock.when(DBConector::getConnection).thenReturn(mockConnection);

            ProfessionsRepository repo = new ProfessionsRepository();

            List<Profession> professions = repo.listAll();

            assertNull(professions);
        }
    }

    @Test
    void testListAllHandlesSQLException() throws Exception {
        Connection mockConnection = mock(Connection.class);

        when(mockConnection.createStatement()).thenThrow(new SQLException("DB error"));

        try (MockedStatic<DBConector> dbConectorMock = mockStatic(DBConector.class)) {
            dbConectorMock.when(DBConector::getConnection).thenReturn(mockConnection);

            ProfessionsRepository repo = new ProfessionsRepository();

            List<Profession> professions = repo.listAll();

            assertNull(professions);
        }
    }
}