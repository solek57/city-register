package edu.javacourse.register.web;

import edu.javacourse.register.dao.CheckPersonDaoImpl;
import edu.javacourse.register.util.PoolConnectionBuilder;
import edu.javacourse.register.domain.PersonRequest;
import edu.javacourse.register.domain.PersonResponse;
import edu.javacourse.register.exception.CheckPersonException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/check")
@Singleton
public class CheckPersonService {
    private static final Logger logger = LoggerFactory.getLogger(CheckPersonService.class);

    private CheckPersonDaoImpl dao;

    @PostConstruct
    public void init() {
        logger.info("SERVICE is created");
        dao = new CheckPersonDaoImpl();
        dao.setConnectionBuilder(new PoolConnectionBuilder());
    }

    @POST
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public PersonResponse checkPerson(@PathParam("id") Integer id, PersonRequest request) throws CheckPersonException {
        logger.info(request.toString());
        logger.info("id: " + id);
        return dao.checkPerson(request);
    }
}
