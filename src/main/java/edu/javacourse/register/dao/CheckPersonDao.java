package edu.javacourse.register.dao;

import edu.javacourse.register.domain.PersonRequest;
import edu.javacourse.register.domain.PersonResponse;
import edu.javacourse.register.exception.CheckPersonException;

public interface CheckPersonDao {
    public PersonResponse checkPerson(PersonRequest personRequest) throws CheckPersonException;
}
