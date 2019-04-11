package pl.jakwoz.projekt10v2;


import junit.framework.AssertionFailedError;
import model.Driver;
import model.Race;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.NestedServletException;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class AppControllerTest {

    @Autowired
    private MockMvc mvc;

    private Driver driver;
    private Race race;
    private Long driverId;

    @Before
    public void setUp() throws Exception {
        driver = new Driver("firstName","lastName","nickname","nationality",2.00,100);
        race = new Race("name","city","country",3000,1909,100,true);
    }

    @After
    public void tearDown() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("racing/driver/all"));
        mvc.perform(MockMvcRequestBuilders.delete("racing/race/all"));
    }

    @Test
    @Rollback(true)
    public void testAddDriver() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/racing/driver/add")
                .param("firstName", driver.getFirstName())
                .param("lastName", driver.getLastName())
                .param("nickname", driver.getNickname())
                .param("nationality", driver.getNationality())
                .param("height",String.valueOf(driver.getHeight()))
                .param("wins",String.valueOf(driver.getWins()))
                .accept(MediaType.ALL))
                .andExpect(status().isOk());
    }


    @Rollback(true)
    @Test
    public void testAddDriverDuplicate() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/racing/driver/add")
                .param("firstName", driver.getFirstName())
                .param("lastName", driver.getLastName())
                .param("nickname", driver.getNickname())
                .param("nationality", driver.getNationality())
                .param("height",String.valueOf(driver.getHeight()))
                .param("wins",String.valueOf(driver.getWins()))
                .accept(MediaType.APPLICATION_JSON);
        mvc.perform(request);
    }

    @Test
    public void testAddDriverMissingData() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/racing/driver/add")
                .param("firstName", driver.getFirstName())
                .param("lastName", driver.getLastName())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @Transactional
    public void testGetDriver() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/racing/driver?id=1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test(expected = NestedServletException.class)
    public void testDeleteNoExistingDriver() throws Exception{
        mvc.perform(MockMvcRequestBuilders.delete("/racing/driver?id=0")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    @Rollback(true)
    public void testAddRace() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/racing/race/add")
                .param("name", race.getName())
                .param("city", race.getCity())
                .param("country", race.getCountry())
                .param("length", String.valueOf(race.getLength()))
                .param("create_year",String.valueOf(race.getCreateYear()))
                .param("laps",String.valueOf(race.getLaps()))
                .param("in_use",race.getInUse().toString())
                .accept(MediaType.ALL))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void testGetRace() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/racing/race?id=3")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test(expected = NestedServletException.class)
    public void testDeleteNotExistingRace() throws Exception{
        mvc.perform(MockMvcRequestBuilders.delete("/racing/race?id=0")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testRaceMissingData() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/racing/race/add")
                .param("name", race.getName())
                .param("city", race.getCity())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Rollback(true)
    @Test
    public void testAddRaceDuplicate() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/racing/driver/add")
                .param("name", race.getName())
                .param("city", race.getCity())
                .param("country", race.getCountry())
                .param("length", String.valueOf(race.getLength()))
                .param("create_year",String.valueOf(race.getCreateYear()))
                .param("laps",String.valueOf(race.getLaps()))
                .param("in_use",race.getInUse().toString())
                .accept(MediaType.APPLICATION_JSON);
        mvc.perform(request);
    }


}
