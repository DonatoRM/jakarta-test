package es.donatodev.jakarta.test.repositories;

import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import es.donatodev.jakarta.test.models.Profession;
import es.donatodev.jakarta.test.utils.DBConector;

public class ProfessionsRepository implements RepositoryDB<Profession> {
    
    @Override
    public List<Profession> listAll() {
        List<Profession> professions = null;
        try(Statement stmt=DBConector.getConnection().createStatement();
            ResultSet rs=stmt.executeQuery("SELECT * FROM professions")) {
                while(rs.next()) {
                    if (professions == null) {
                        professions = new ArrayList<>();
                    }
                    Profession newProfession = createNewProfession(rs);
                    professions.add(newProfession);
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return professions;
    }

    
    @Override
    public void delete(Long id) {
        if(id>0) {
            Profession professionSearch=searchById(id);
            if(professionSearch!=null) {
                try(PreparedStatement stmt=DBConector.getConnection().prepareStatement("DELETE FROM professions WHERE id=?")) {
                    stmt.setLong(1, id);
                    stmt.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    @Override
    public void save(Profession t) {
       // TODO: Hacer para el próximo día        
    }
    
    @Override
    public Profession searchById(Long id) {
        Profession profession = null;
        if(id>0) {
            try(PreparedStatement stmt=DBConector.getConnection().prepareStatement("SELECT * FROM professions WHERE id=?")) {
                stmt.setLong(1,id);
                try(ResultSet rs=stmt.executeQuery()) {
                    if(rs.next()) {
                        profession = createNewProfession(rs);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return profession;
    }

    private Profession createNewProfession(ResultSet rs) throws SQLException {
        Profession newProfession = new Profession();
        newProfession.setId(rs.getLong("id"));
        newProfession.setName(rs.getString("name"));
        return newProfession;
    }

}