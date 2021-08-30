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
public class TableService {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public List<Map<String, String>> getTables(String schema) {
        final var query = "SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = :schema";
        final var parameters = new MapSqlParameterSource("schema", schema);
        return jdbcTemplate.query(query, parameters, new MapMapper());
    }

}
