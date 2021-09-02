package cz.mformanek.ataccama.database.dao;

import cz.mformanek.ataccama.database.mapper.MapMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class InformationSchemaDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<Map<String, String>> getSchemas() {
        //We could get metadata from connection.getMetadata()..., which gives less information
        final String query = "SELECT * FROM INFORMATION_SCHEMA.SCHEMATA";
        return namedParameterJdbcTemplate.query(query, new MapMapper());
    }

    public List<Map<String, String>> getTables(String schema) {
        final var query = "SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = :schema";
        final var parameters = new MapSqlParameterSource("schema", schema);
        return namedParameterJdbcTemplate.query(query, parameters, new MapMapper());
    }

    public List<Map<String, String>> getColumns(String schema, String table) {
        final var query = "SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = :schema AND TABLE_NAME = :table";
        final var parameters = new MapSqlParameterSource("schema", schema)
                .addValue("table", table);
        return namedParameterJdbcTemplate.query(query, parameters, new MapMapper());
    }
}
