package cz.mformanek.ataccama.service;

import cz.mformanek.ataccama.mapper.MapMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ColumnService {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public List<Map<String, String>> getColumns(String schema, String name) {
        final var query = "SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = :schema AND TABLE_NAME = :name";
        final var parameters = new MapSqlParameterSource("schema", schema).addValue("name", name);
        return jdbcTemplate.query(query, parameters, new MapMapper());
    }

}
