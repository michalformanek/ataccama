package cz.mformanek.ataccama.mapper;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class MapMapper implements RowMapper<Map<String, String>> {

    @Override
    public Map<String, String> mapRow(ResultSet rs, int rowNum) throws SQLException {
        final var result = new HashMap<String, String>();
        final var rsMetaData = rs.getMetaData();
        final var columnCount = rsMetaData.getColumnCount();
        for (int i = 1; i <= columnCount; i++) {
            final String key = rsMetaData.getColumnName(i);
            final String value = rs.getString(i);
            result.put(key, value);
        }

        return result;
    }

}
