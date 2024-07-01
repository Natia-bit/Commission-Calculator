package commission.mapper;

import commission.entity.Commission;
import commission.entity.CommissionType;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CommissionRowMapper implements RowMapper<Commission> {

    @Override
    public Commission mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Commission(rs.getLong(1),
                rs.getObject(2, CommissionType.class),
                rs.getDouble(3),
                rs.getLong(4));
    }
}
