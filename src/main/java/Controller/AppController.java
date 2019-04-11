package Controller;

import dao.DriverDAO;
import dao.RaceDAO;
import model.Driver;
import model.Race;
import org.hibernate.engine.jdbc.connections.internal.DriverManagerConnectionProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityExistsException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLDataException;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping(value = "/racing")
public class AppController {

    private final DriverDAO driverDAO;
    private final RaceDAO raceDAO;
    @Autowired
    public AppController(DriverDAO driverDAO, RaceDAO raceDAO) {
        this.driverDAO = driverDAO;
        this.raceDAO = raceDAO;
    }

    //wyswietlenie wszystkich kierowcow
    @RequestMapping(value = "/driver/", method = RequestMethod.GET)
    public List<Driver> getDrivers() {
        System.out.println("ASDSADSAADS");
        return driverDAO.findAll();
    }

    //dodanie kierowcy
    @RequestMapping(value = "driver/add", method = RequestMethod.POST)
    public void addDriver(@RequestParam(value = "firstName") String firstName,
                          @RequestParam(value = "lastName") String lastName,
                          @RequestParam(value = "nickname") String nickname,
                          @RequestParam(value = "nationality") String nationality,
                          @RequestParam(value = "height") double height,
                          @RequestParam(value = "wins") int wins) throws SQLDataException {
        //Driver d2 =(Driver)driverDAO.findOne(driverDAO.getDriverByNickname(nickname));
        //if(d2 != null ) throw new SQLDataException("Driver already exist");
        Driver d = new Driver(firstName, lastName, nickname, nationality, height, wins);
        driverDAO.create(d);
    }


    /*
    @ExceptionHandler({IllegalArgumentException.class, Exception.class})
    void handleBadRequests(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value(), "Please try again and with a non empty string as 'name'");
    }
     */

    //usuniecie kierowcy
    @RequestMapping(value = "driver{id}", method = RequestMethod.DELETE)
    public void deleteDriver(@RequestParam("id") long id) {
        Driver d = (Driver) driverDAO.findOne(id);
        driverDAO.delete(d);
    }

    //usuniecie wszystkich kierowcow
    @RequestMapping(value = "driver/all", method = RequestMethod.DELETE)
    public void deleteAllDriver() {
        List<Driver> drivers = (List<Driver>) driverDAO.findAll();
        for (Driver d : drivers) {
            driverDAO.delete(d);
        }
    }

    //wyswietlenie kierowcy
    @RequestMapping(value = "driver{id}", method = RequestMethod.GET)
    public Driver getDriver(@RequestParam("id") long id) {
        Driver d = (Driver)  driverDAO.findOne(id);
        if(d == null) throw new EntityExistsException("Non existing");
        return d;
    }

    //aktualizacja kierowcy
    @RequestMapping(value = "driver/update", method = RequestMethod.PUT)
    public void updateDriver(@RequestParam(value = "firstName") String firstName,
                             @RequestParam(value = "lastName") String lastName,
                             @RequestParam(value = "nickname") String nickname,
                             @RequestParam(value = "nationality") String nationality,
                             @RequestParam(value = "height") double height,
                             @RequestParam(value = "wins") int wins) {

        Driver dd = (Driver) driverDAO.findOne(driverDAO.getDriverByNickname(nickname));
        dd.setFirstName(firstName);
        dd.setLastName(lastName);
        dd.setNationality(nationality);
        dd.setHeight(height);
        dd.setWins(wins);
        driverDAO.update(dd);
    }

    //dodanie wyscigu
    @RequestMapping(value = "race/add", method = RequestMethod.POST)
    public void addRace(@RequestParam(value = "city") String city,
                        @RequestParam(value = "country") String country,
                        @RequestParam(value = "create_year") int create,
                        @RequestParam(value = "in_use") Boolean inuse,
                        @RequestParam(value = "laps") int laps,
                        @RequestParam(value = "length") int length,
                        @RequestParam(value = "name") String name) {
        Race r = new Race(name, city, country, length, create, laps, inuse);
        raceDAO.create(r);
    }

    //usuniecie wyscigu
    @RequestMapping(value = "race{id}", method = RequestMethod.DELETE)
    public void deleteRace(@RequestParam("id") long id) {
        Race r = (Race) raceDAO.findOne(id);
        raceDAO.delete(r);
    }

    //usuniecie wszystkich wyscigow
    @RequestMapping(value = "race/all", method = RequestMethod.DELETE)
    public void deleteAllRace() {
        List<Race> races = (List<Race>) raceDAO.findAll();
        for (Race r : races) {
            raceDAO.delete(r);
        }
    }

    //wyswietlenie wyscigu
    @RequestMapping(value = "race{id}", method = RequestMethod.GET)
    public Race getRace(@RequestParam("id") long id) {
        return (Race) raceDAO.findOne(id);
    }

    //wyswietlenie wszystkich wyscigu
    @RequestMapping(value = "/race/", method = RequestMethod.GET)
    public List<Race> getRaces() {
        System.out.println("ASDSADSAADS");
        return raceDAO.findAll();
    }

    //aktualizacja wyscigu
    @RequestMapping(value = "race/add", method = RequestMethod.PUT)
    public void updateRace(@RequestParam(value = "city") String city,
                           @RequestParam(value = "country") String country,
                           @RequestParam(value = "create_year") int create,
                           @RequestParam(value = "in_use") Boolean inuse,
                           @RequestParam(value = "laps") int laps,
                           @RequestParam(value = "length") int length,
                           @RequestParam(value = "name") String name) {
        Race r = raceDAO.getRaceByName(name);
        r.setCity(city);
        r.setCountry(country);
        r.setCreateYear(create);
        r.setInUse(inuse);
        r.setLaps(laps);
        r.setLength(length);
        raceDAO.update(r);
    }

    //dodaj kierowce do wyscigu
    @RequestMapping(value = "/race/", method = RequestMethod.POST)
    public Driver addDriverToRace(@RequestParam("raceId") long raceId,
                                  @RequestParam("driverId") long driverId) {
        Driver d = (Driver) driverDAO.findOne(driverId);
        Race r = (Race) raceDAO.findOne(raceId);
        List<Race> races = d.getRaces();
        races.add(r);
        d.setRaces(races);
        driverDAO.update(d);
        return d;
    }
    @RequestMapping(value = "/driver/find/name{like}", method = RequestMethod.GET)
    public List<Driver> racesWithLapsLessThen(@RequestParam("like") String like)
    {
        List<Driver> drivers = driverDAO.getDriverLike(like);
        return drivers;
    }


    //sortuj kierowcow wg nazwiska
    @RequestMapping(value = "/driver/lastname{desc}", method = RequestMethod.GET)
    public List<Driver> racesWithLapsLessThen(@RequestParam("desc") Boolean desc)
    {
        List<Driver> drivers = driverDAO.getDriverByLastName(desc);
        return drivers;
    }

    //wyscigi z liczba okrazen mniejsza niz
    @RequestMapping(value = "/race/laps{laps}", method = RequestMethod.GET)
    public List<Race> racesWithLapsLessThen(@RequestParam("laps") int laps)
    {
        List<Race> races = raceDAO.getRaceWithLapsLessThen(laps);
        return races;
    }




}
