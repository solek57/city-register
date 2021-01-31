package edu.javacourse.register.dao;

import edu.javacourse.register.domain.PersonRequest;
import edu.javacourse.register.domain.PersonResponse;
import edu.javacourse.register.exception.CheckPersonException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.time.LocalDate;

public class CheckPersonDaoImplTest {
    PersonRequest personRequest;
    CheckPersonDaoImpl checkPersonDao;
    PersonResponse personResponse;

    @BeforeClass
    public static void init() throws SQLException, IOException, URISyntaxException {
        DBInit.initDB();

    }

    @Test
    public void checkPerson() throws CheckPersonException {

        personRequest = new PersonRequest();

        personRequest.setSurName("Васильев");
        personRequest.setGivenName("Павел");
        personRequest.setPatronymic("Николаевич");
        personRequest.setDateOfBirth(LocalDate.of(1995, 3, 18));
        personRequest.setBuilding("10");
        personRequest.setStreetCode(1);
        personRequest.setExtension("2");
        personRequest.setApartment("121");
        checkPersonDao = new CheckPersonDaoImpl();
        checkPersonDao.setConnectionBuilder(new ConnectionBuilderImpl());

        personResponse = checkPersonDao.checkPerson(personRequest);

        Assert.assertTrue(personResponse.isRegistered());
        Assert.assertFalse(personResponse.isTemporal());

    }
}