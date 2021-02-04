package edu.javacourse.register.dao;

import edu.javacourse.register.domain.PersonRequest;
import edu.javacourse.register.domain.PersonResponse;
import edu.javacourse.register.exception.CheckPersonException;
import edu.javacourse.register.util.ConnectionBuilder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CheckPersonDaoImpl implements CheckPersonDao {


    private ConnectionBuilder connectionBuilder;

    public void setConnectionBuilder(ConnectionBuilder connectionBuilder) {
        this.connectionBuilder = connectionBuilder;
}

    /*public CheckPersonDaoImpl() {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }*/

    private static final String SQL_REQUEST =
            "select temporal from cr_address_person ap " +
                    "inner join cr_person p on p.person_id = ap.person_id " +
                    "inner join cr_address a on a.address_id = ap.address_id " +
                    "where " +
                    "CURRENT_DATE >= ap.start_date and (CURRENT_DATE <= ap.end_date or ap.end_date is null)" +
                    "and upper(p.sur_name ) = upper(? )  " +
                    "and upper(p.given_name) = upper(?)  " +
                    "and upper(patronymic ) = upper(?)  " +
                    "and p.date_of_birth = ? " +
                    "and upper(a.building) = upper(?)  ";

    private Connection getConnection() throws SQLException {
        return connectionBuilder.getConnection();
    }

    @Override
    public PersonResponse checkPerson(PersonRequest personRequest) throws CheckPersonException {
        PersonResponse personResponse = new PersonResponse();

        String sql = SQL_REQUEST;

        if (personRequest.getExtension() != null)
            sql += " and upper(a.extension) = upper(?) ";
        else
            sql += " and a.extension is null ";


        if (personRequest.getApartment() != null)
            sql += " and upper(a.apartment) = upper(?) ";
        else
            sql += " and a.extension is null ";

        try (Connection con = getConnection()) {

            PreparedStatement preparedStatement = con.prepareStatement(sql);

            int count = 1;
            preparedStatement.setString(count++, personRequest.getSurName());
            preparedStatement.setString(count++, personRequest.getGivenName());
            preparedStatement.setString(count++, personRequest.getPatronymic());
            preparedStatement.setDate(count++, java.sql.Date.valueOf(personRequest.getDateOfBirth()));
            preparedStatement.setString(count++, personRequest.getBuilding());

            if (personRequest.getExtension() != null)
                preparedStatement.setString(count++, personRequest.getExtension());

            if (personRequest.getApartment() != null)
                preparedStatement.setString(count++, personRequest.getApartment());


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
