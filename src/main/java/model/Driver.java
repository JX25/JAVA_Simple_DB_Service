package model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "driver")
public class Driver implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="driver_id")
    private Long driver_id;

    @Column(name="firstName")
    private String firstName;

    @Column(name="nickname", unique=true)
    private String nickname;

    @Column(name="lastName")
    private String lastName;

    @Column(name="nationality")
    private String nationality;

    @Column(name="height")
    private double height;

    @Column(name="wins")
    private int wins;

    @ManyToMany(cascade = {
            CascadeType.ALL,
            CascadeType.MERGE
    })
    @JoinTable(name = "race_driver",
            joinColumns = @JoinColumn(name = "driver_id"),
            inverseJoinColumns = @JoinColumn(name = "race_id")
    )
    private List<Race> races;

    public Driver()
    {
        this.races = new ArrayList<>();
    }

    public Driver(String firstName, String lastName, String nickname,
                  String nationality, double height, int wins)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nickname = nickname;
        this.nationality = nationality;
        this.height = height;
        this.wins = wins;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public List<Race> getRaces() {
        return races;
    }

    public void setRaces(List<Race> races) {
        this.races = races;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "id=" + driver_id +
                ", firstName='" + firstName + '\'' +
                ", nickname='" + nickname + '\'' +
                ", lastName='" + lastName + '\'' +
                ", nationality='" + nationality + '\'' +
                ", height=" + height +
                ", wins=" + wins +
                ", races=" + races +
                '}';
    }
}
