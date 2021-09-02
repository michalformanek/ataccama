package cz.mformanek.ataccama.database.service;

import cz.mformanek.ataccama.database.dao.DataPreviewDao;
import cz.mformanek.ataccama.database.dao.InformationSchemaDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class DatabaseService {

    private final InformationSchemaDao informationSchemaDao;
    private final DataPreviewDao dataPreviewDao;

    public List<Map<String, String>> getDataPreview(String schema, String table) {
        log.debug("Obtaining data for table {} in schema {}", table, schema);
        return dataPreviewDao.getDataPreview(schema, table);
    }

    public List<Map<String, String>> getColumns(String schema, String table) {
        log.debug("Listing columns for table {} in schema {}", table, schema);
        return informationSchemaDao.getColumns(schema, table);
    }

    public List<Map<String, String>> getTables(String schema) {
        log.debug("Listing tables in schema {}", schema);
        return informationSchemaDao.getTables(schema);
    }

    public List<Map<String, String>> getSchemas() {
        log.debug("Listing schemas");
        return informationSchemaDao.getSchemas();
    }
}
