package cz.mformanek.ataccama.database.service;

import cz.mformanek.ataccama.database.dao.InformationSchemaDao;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DatabaseService {

    private final InformationSchemaDao informationSchemaDao;

    public List<Map<String, String>> getDataPreview(String schema, String table) {
        throw new NotImplementedException();
    }

    public List<Map<String, String>> getColumns(String schema, String table) {
        return informationSchemaDao.getColumns(schema, table);
    }

    public List<Map<String, String>> getTables(String schema) {
        return informationSchemaDao.getTables(schema);
    }

    public List<Map<String, String>> getSchemas() {
        return informationSchemaDao.getSchemas();
    }
}
