package cz.mformanek.ataccama.service;

import cz.mformanek.ataccama.mapper.MapMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DataService {

    private final JdbcTemplate jdbcTemplate;

    @SneakyThrows
    public List<Map<String, String>> getDataPreview(String schema, String name) {
        final var query = String.format("SELECT * FROM %s.%s LIMIT 10", schema, name); //SQL Injection
        return jdbcTemplate.query(query, new MapMapper());
    }
}
