package com.siri.backend;

import com.siri.backend.dto.GetResponseDTO;
import com.siri.backend.dto.PostResponseDTO;
import com.siri.backend.models.WebSiteCrawlModel;
import com.siri.backend.services.Environment;
import com.siri.backend.services.WebCrawler;
import com.siri.backend.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static spark.Spark.*;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static final Map<String, WebSiteCrawlModel> crawlResults = new HashMap<>();
    private static final Map<String, String> websites = new HashMap<>();
    private static final ExecutorService executorService = Executors.newFixedThreadPool(20);
    private static final int ID_LENGTH = 8;

    public static void main(String[] args) {
        Gson gson = new Gson();

        get("/crawl/:id", (req, res) -> {
            String id = req.params("id");
            WebSiteCrawlModel response = crawlResults.get(id);
            if (response == null) {
                res.status(404);
                return "ID not found";
            }
            res.type("application/json");
            return gson.toJson(new GetResponseDTO(response.getId(), response.getStatus(), response.getUrls()));
        });

        post("/crawl", (req, res) -> {
            JsonObject body = gson.fromJson(req.body(), JsonObject.class);
            JsonElement keywordElement = body.get("keyword");

            //Early return to prevent unnecessary processing
            if (keywordElement == null ) {
                res.status(400);
                return "keyword is required";
            }
            String keywordValue = keywordElement.getAsString();
            if (keywordValue.length() < 4 || keywordValue.length() > 32) {
                res.status(400);
                return "Keyword must be between 4 and 32 characters";
            }
            final String searchTerm = keywordValue.toLowerCase();
            String id = Utils.generateAlphanumericCode(ID_LENGTH);

            //One instance per keyword
            WebCrawler crawler = new WebCrawler(
                    id,
                    crawlResults,
                    websites,
                    new Environment()
            );

            //Verify if the keyword is already being searched or has been searched before
            for (Map.Entry<String, WebSiteCrawlModel> entry : crawlResults.entrySet()) {
                if (entry.getValue().getKeyword().equals(searchTerm)) {
                    res.type("application/json");
                    res.status(200);
                    res.body(gson.toJson(new PostResponseDTO(entry.getKey())));
                    return res.body();
                }
            }

            Date startDate = new Date();
            logger.info("Received request for keyword: {} at {}", searchTerm, startDate);

            WebSiteCrawlModel model = new WebSiteCrawlModel(id, searchTerm, "active", new HashSet<>());
            crawlResults.put(id, model);

            //Return the ID immediately
            res.type("application/json");
            res.status(200);
            res.body(gson.toJson(new PostResponseDTO(id)));

            //Start the crawling process in a separate thread pool
            executorService.submit(() -> {
                try {
                    crawler.search(searchTerm);
                    WebSiteCrawlModel updatedModel = new WebSiteCrawlModel(id, searchTerm, "done", crawlResults.get(id).getUrls());
                    crawlResults.put(id, updatedModel);
                } catch (IOException e) {
                    WebSiteCrawlModel errorResponse = new WebSiteCrawlModel(id, searchTerm, "error", new HashSet<>());
                    crawlResults.put(id, errorResponse);
                }
                logger.info("Crawling done for keyword: {} at {}", searchTerm, new Date());
                logger.info("Time taken: {} seconds", (new Date().getTime() - startDate.getTime()) / 1000);

            });
            return res.body();
        });
    }
}
