package edu.javacourse.register.dao;

import edu.javacourse.register.domain.PersonRequest;
import edu.javacourse.register.domain.PersonResponse;
import edu.javacourse.register.exception.CheckPersonException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CheckPersonDaoImpl implements CheckPersonDao {

    private static final String SQL_REQUEST = "select temporal from cr_address_person ap " +
            "inner join cr_person p on p.person_id = ap.person_id " +
            "inner join cr_address a on a.address_id = ap.address_id " +
            "where " +
            "CURRENT_DATE >= ap.start_date and (CURRENT_DATE <= ap.end_date or ap.end_date is null)" +
            "and upper(p.sur_name COLLATE \"en_US.UTF-8\") = upper(? COLLATE \"en_US.UTF-8\")  " +
            "and upper(p.given_name COLLATE \"en_US.UTF-8\") = upper(? COLLATE \"en_US.UTF-8\")  " +
            "and upper(patronymic COLLATE \"en_US.UTF-8\") = upper(? COLLATE \"en_US.UTF-8\")  " +
            "and p.date_of_birth = ? " +
            "and a.street_code = ?  " +
            "and upper(a.building COLLATE \"en_US.UTF-8\") = upper(? COLLATE \"en_US.UTF-8\")  ";;

    private Connection getConnection() throws SQLException {
        return ConnectionBuilder.getConnection();
    }

    @Override
    public PersonResponse checkPerson(PersonRequest personRequest) throws CheckPersonException {
        PersonResponse personResponse = new PersonResponse();

        try (Connection con = getConnection()) {

            PreparedStatement preparedStatement = con.prepareStatement(SQL_REQUEST);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                personResponse.setRegistered(Boolean.TRUE);
                personResponse.setTemporal(resultSet.getBoolean("temporal"));
            }

        } catch (SQLException e) {
            throw new CheckPersonException(e);
        }

        return personResponse;
    }
}
