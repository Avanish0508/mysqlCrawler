package com.example.crawlerr.controller;

import com.example.crawlerr.model.TableInfo;
import com.example.crawlerr.service.DatabaseCrawlerService;
import com.example.crawlerr.service.ModelGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/crawler")
public class CrawlerController {

    @Autowired
    private DatabaseCrawlerService crawlerService;

    @Autowired
    private ModelGeneratorService modelGeneratorService;

    @GetMapping("/run")
    public List<TableInfo> runCrawler() {
        // 1. Crawl DB and get metadata
        List<TableInfo> tableInfos = crawlerService.crawlDatabase();

        // 2. Generate model .java files based on metadata
        modelGeneratorService.generateModels(tableInfos);

        // 3. Return metadata JSON in the API response
        return tableInfos;
    }
}
