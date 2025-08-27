package es.donatodev.jakarta.test.models;

public class User {
    private Long id;
    private String name;
    private String lastName;
    private String middleLastName;
    private Profession profession;
    
    public User() {
    }
    public User(Long id, String name, String lastName, String middleLastName, Profession profession) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.middleLastName = middleLastName;
        this.profession = profession;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getMiddleLastName() {
        return middleLastName;
    }
    public void setMiddleLastName(String middleLastName) {
        this.middleLastName = middleLastName;
    }
    public Profession getProfession() {
        return profession;
    }
    public void setProfession(Profession profession) {
        this.profession = profession;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
        result = prime * result + ((middleLastName == null) ? 0 : middleLastName.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (lastName == null) {
            if (other.lastName != null)
                return false;
        } else if (!lastName.equals(other.lastName))
            return false;
        if (middleLastName == null) {
            if (other.middleLastName != null)
                return false;
        } else if (!middleLastName.equals(other.middleLastName))
            return false;
        return true;
    }
    @Override
    public String toString() {
        return id + " | " + name + " | " + lastName + " | =" + middleLastName
                + " --> " + profession.getName();
    }
}
