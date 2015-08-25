package muhpackage;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Job {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String position;

    @Column(nullable = false)
    private String location;

    public Job() {
        // default constructor
    }


    public Job(String position, String location) {
        this.position = position;
        this.location = location;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
