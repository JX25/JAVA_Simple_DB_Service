package model;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table( name="race" )
public class Race implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="race_id")
    private long race_id;

    @Column(name="name", unique = true)
    private String name;

    @Column(name="city")
    private String city;

    @Column(name="country")
    private String country;

    @Column(name="length")
    private int length;

    @Column(name="createYear")
    private int createYear;

    @Column(name="laps")
    private int laps;

    @Column(name="inUse")
    private Boolean inUse;

  /*  @ManyToMany(cascade = {
            CascadeType.ALL,
            CascadeType.MERGE
    })
    @JoinTable(name = "race_driver",
            joinColumns = @JoinColumn(name = "race_id"),
            inverseJoinColumns = @JoinColumn(name = "driver_id")
    )
    private List<Driver> drivers;

    public Race()
    {
        this.drivers = new ArrayList<>();
    }*/

    public Race()
    {

    }

    public Race(String name, String city, String country,
                int length, int createYear, int laps,
                Boolean inUse)
    {
        this.name = name;
        this.city = city;
        this.country = country;
        this.length = length;
        this.createYear = createYear;
        this.laps = laps;
        this.inUse = inUse;
       // this.drivers = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Race{" +
                "race_id=" + race_id +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", length=" + length +
                ", createYear=" + createYear +
                ", laps=" + laps +
                ", inUse=" + inUse +
               // ", drivers=" + drivers +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getCreateYear() {
        return createYear;
    }

    public void setCreateYear(int createYear) {
        this.createYear = createYear;
    }

    public int getLaps() {
        return laps;
    }

    public void setLaps(int laps) {
        this.laps = laps;
    }

    public Boolean getInUse() {
        return inUse;
    }

    public void setInUse(Boolean inUse) {
        this.inUse = inUse;
    }

  /*  public List<Driver> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<Driver> drivers) {
        this.drivers = drivers;
    }*/
}
