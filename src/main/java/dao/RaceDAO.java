package dao;

import model.Driver;
import model.Race;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RaceDAO extends  AbstractDAO{

    public RaceDAO() {
        setClazz(Race.class);
    }

    public Race getRaceByName(String name) {
        return (Race) entityManager.createQuery("SElECT r FROM Race r WHERE r.name=:raceName")
                .setParameter("raceName", name)
                .getSingleResult();
    }

    public List<Race> getRaceByCity(String city) {
        return (List<Race>) entityManager.createQuery("SElECT r FROM Race r WHERE r.city=:city")
                .setParameter("city", city).getResultList();
    }

    public List<Race> getRaceByCountry(String country) {
        return (List<Race>) entityManager.createQuery("SElECT r FROM Race r WHERE r.country=:country")
                .setParameter("country", country).getResultList();
    }

    public List<Race> getRaceLongerThen(int length) {
        return (List<Race>) entityManager.createQuery("SElECT r FROM Race r WHERE r.length>:length")
                .setParameter("length", length).getResultList();
    }

    public List<Race> getRaceShorterThen(int length) {
        return (List<Race>) entityManager.createQuery("SElECT r FROM Race r WHERE r.length<=:length")
                .setParameter("length", length).getResultList();
    }

    public List<Race> getRaceYoungerThen(int year) {
        return (List<Race>) entityManager.createQuery("SElECT r FROM Race r WHERE r.create_year>:year")
                .setParameter("year", year).getResultList();
    }

    public List<Race> getRaceWithLapsLessThen(int laps) {
        return (List<Race>) entityManager.createQuery("SElECT r FROM Race r WHERE r.laps<=:laps")
                .setParameter("laps", laps).getResultList();
    }

    public List<Race> getRaceWithStatusInUse(Boolean status) {
        return (List<Race>) entityManager.createQuery("SElECT r FROM Race r WHERE r.in_use=:status")
                .setParameter("status", status)
                .getResultList();
    }

  /*  public List<Driver> getRaceDrivers(String name)
    {
        Race race = getRaceByName(name);
        return race.getDrivers();
    }*/
}
