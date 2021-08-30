package cz.mformanek.ataccama.controller;

import cz.mformanek.ataccama.service.SchemaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "database")
@RequiredArgsConstructor
public class DatabaseController {

    private final SchemaService databaseService;

    @GetMapping("/schemas")
    public List<Map<String, String>> schemas(){
        return databaseService.getSchemas();
    }
}
