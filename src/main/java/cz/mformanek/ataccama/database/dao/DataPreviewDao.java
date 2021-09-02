package cz.mformanek.ataccama.database.dao;

import cz.mformanek.ataccama.database.mapper.MapMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.isAlphanumeric;

@Component
@RequiredArgsConstructor
public class DataPreviewDao {

    private final JdbcTemplate jdbcTemplate;

    @SneakyThrows
    public List<Map<String, String>> getDataPreview(String schema, String name) {
        if (!isAlphanumeric(name) && !isAlphanumeric(schema)) {
            return new ArrayList<>(); //Or throw exception
        }
        final var query = String.format("SELECT * FROM `%s`.`%s` LIMIT 10", schema, name);
        return jdbcTemplate.query(query, new MapMapper());
    }
}
