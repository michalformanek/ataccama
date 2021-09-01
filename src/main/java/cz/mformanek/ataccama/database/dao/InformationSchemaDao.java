package cz.mformanek.ataccama.database.dao;

import cz.mformanek.ataccama.mapper.MapMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class InformationSchemaDao extends NamedParameterJdbcDaoSupport {

    public List<Map<String, String>> getSchemas() {
        //We could get medatata from connection.getMetadata().getCatalogs(), which gives less information about schema
        final String query = "SELECT * FROM INFORMATION_SCHEMA.SCHEMATA";
        return getNamedParameterJdbcTemplate().query(query, new MapMapper());
    }

    public List<Map<String, String>> getTables(String schema) {
        final var query = "SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = :schema";
        final var parameters = new MapSqlParameterSource("schema", schema);
        return getNamedParameterJdbcTemplate().query(query, parameters, new MapMapper());
    }

    public List<Map<String, String>> getColumns(String schema, String name) {
        final var query = "SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = :schema AND TABLE_NAME = :name";
        final var parameters = new MapSqlParameterSource("schema", schema)
                .addValue("name", name);
        return getNamedParameterJdbcTemplate().query(query, parameters, new MapMapper());
    }
}
