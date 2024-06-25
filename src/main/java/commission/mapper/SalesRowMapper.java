package commission.mapper;

import commission.entity.Sale;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SalesRowMapper implements RowMapper<Sale> {

    @Override
    public Sale mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Sale(rs.getLong(1),
                rs.getDouble(2),
                rs.getTimestamp(3),
                rs.getLong(4));
    }
}
