package mk.fr.firebaserealtimedatabase.Model;

/**
 * Created by Formation on 19/01/2018.
 */

public class Author {

    private String name;
    private String FirstName;
    private String nationality;

    public Author() {
    }

    public Author(String name, String firstName, String nationality) {
        this.name = name;
        FirstName = firstName;
        this.nationality = nationality;
    }

    public String getName() {
        return name;
    }

    public Author setName(String name) {
        this.name = name;
        return this;
    }

    public String getFirstName() {
        return FirstName;
    }

    public Author setFirstName(String firstName) {
        FirstName = firstName;
        return this;
    }

    public String getNationality() {
        return nationality;
    }

    public Author setNationality(String nationality) {
        this.nationality = nationality;
        return this;
    }
}
