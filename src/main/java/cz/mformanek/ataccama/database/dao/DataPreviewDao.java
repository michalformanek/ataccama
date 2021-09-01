package cz.mformanek.ataccama.database.dao;

import cz.mformanek.ataccama.database.mapper.MapMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class DataPreviewDao {

    private final JdbcTemplate jdbcTemplate;

    public List<Map<String, String>> getDataPreview(String schema, String name) {
        final var query = String.format("SELECT * FROM %s.%s LIMIT 10", schema, name); //SQL Injection
        return jdbcTemplate.query(query, new MapMapper());
    }
}
