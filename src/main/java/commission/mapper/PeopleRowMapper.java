package commission.mapper;

import commission.entity.Person;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PeopleRowMapper implements RowMapper<Person> {
    @Override
    public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Person(rs.getLong(1),
                rs.getString(2),
                rs.getString(3));
    }

}
