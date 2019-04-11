package dao;


import model.Driver;
import model.Race;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository
public class DriverDAO extends AbstractDAO {
    public DriverDAO() {
        setClazz(Driver.class);
    }

    public Long getDriverByNickname(String nick)
    {
        return (Long) entityManager.createQuery("SELECT d.driver_id FROM Driver d WHERE d.nickname=:nickname")
                .setParameter("nickname",nick)
                .getSingleResult();
    }

    public List<Driver> getDriverLike(String likes)
    {
        return entityManager.createQuery("SELECT  d FROM Driver d WHERE d.lastName LIKE :likes").setParameter("likes","%"+likes+"%").getResultList();
    }

    public List<Driver> getDriverByLastName(Boolean desc)
    {
        if(desc == true)
            return (List<Driver>) entityManager.createQuery("SELECT d FROM Driver d ORDER BY d.lastName DESC ").getResultList();
        return (List<Driver>) entityManager.createQuery("SELECT d FROM Driver d ORDER BY d.lastName ").getResultList();
    }

    public List<Driver> getDriverByNationality(String nationality)
    {
        return (List<Driver>) entityManager.createQuery("SELECT d FROM Driver d WHERE d.nationality=:nat")
                .setParameter("nat",nationality).getResultList();
    }

    public List<Driver> getDriverHigherThen(double height)
    {
        return (List<Driver>) entityManager.createQuery("SELECT d FROM Driver d WHERE d.height>:height")
                .setParameter("height",height).getResultList();
    }

    public List<Driver> getDriverLowerThen(double height)
    {
        return (List<Driver>) entityManager.createQuery("SELECT d FROM Driver d WHERE d.height<=:height")
                .setParameter("height",height).getResultList();
    }

    public List<Driver> getDriverWithWinsMoreThen(int wins)
    {
        return (List<Driver>) entityManager.createQuery("SELECT d FROM Driver d WHERE d.wins>:wins")
                .setParameter("wins",wins).getResultList();
    }

    public List<Driver> getDriverWithWinsLessThen(int wins)
    {
        return (List<Driver>) entityManager.createQuery("SELECT d FROM Driver d WHERE d.wins<=:wins")
                .setParameter("wins",wins).getResultList();
    }

    public List<Race> getDriverRaces(String nick)
    {
        Driver driver = (Driver)findOne(getDriverByNickname(nick));
        return driver.getRaces();
    }


}
