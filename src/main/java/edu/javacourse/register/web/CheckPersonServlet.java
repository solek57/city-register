package edu.javacourse.register.web;

import edu.javacourse.register.dao.CheckPersonDaoImpl;
import edu.javacourse.register.util.PoolConnectionBuilder;
import edu.javacourse.register.domain.PersonRequest;
import edu.javacourse.register.domain.PersonResponse;
import edu.javacourse.register.exception.CheckPersonException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet(name = "checkPerson", urlPatterns = {"/checkPerson"}, loadOnStartup = 1)
public class CheckPersonServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(CheckPersonServlet.class);

    CheckPersonDaoImpl checkPersonDao;

    @Override
    public void init() throws ServletException {
        logger.info("servlet is created");
        checkPersonDao = new CheckPersonDaoImpl();
        checkPersonDao.setConnectionBuilder(new PoolConnectionBuilder());
        logger.info("DB init success");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String surname = req.getParameter("surname");

        PersonRequest personRequest = new PersonRequest();
        personRequest.setSurName(surname);
        personRequest.setGivenName("Павел");
        personRequest.setPatronymic("Николаевич");
        personRequest.setDateOfBirth(LocalDate.of(1995, 3, 18));
        personRequest.setBuilding("10");
        personRequest.setStreetCode(1);
        personRequest.setExtension("2");
        personRequest.setApartment("121");

        try {
            PersonResponse personResponse = checkPersonDao.checkPerson(personRequest);
            if (personResponse.isRegistered())
                resp.getWriter().write("Registered");
            else
                resp.getWriter().write("not registered");
        } catch (CheckPersonException e) {
            e.printStackTrace();
        }

    }
}
