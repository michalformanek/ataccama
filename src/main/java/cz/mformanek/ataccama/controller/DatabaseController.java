package cz.mformanek.ataccama.controller;

import cz.mformanek.ataccama.database.service.DatabaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "database/{databaseName}")
@RequiredArgsConstructor
public class DatabaseController {

    private final DatabaseService databaseService;

    @GetMapping("/schemas")
    public List<Map<String, String>> schemas(){
        return databaseService.getSchemas();
    }

    @GetMapping("/tables")
    public List<Map<String, String>> tables(){
        return databaseService.getTables("test");
    }

    @GetMapping("/columns")
    public List<Map<String, String>> columns(){
        return databaseService.getColumns("test","tablice");
    }

    @GetMapping("/data")
    public List<Map<String, String>> data(){
        //What is meant by "Data preview of the table"? Actual values or some random mocks
        return databaseService.getDataPreview("test","tablice");
    }
}
