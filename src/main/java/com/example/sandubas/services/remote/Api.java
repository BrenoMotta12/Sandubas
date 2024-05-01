package com.example.sandubas.services.remote;

import com.example.sandubas.gui.util.Alerts;
import com.example.sandubas.model.Product;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class Api {
    private final static String URLBase = "http://localhost:8081/";

    public static List<Product> getAllProducts() throws IOException, InterruptedException {
        List<Product> list = new ArrayList<>();
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URLBase + "products")).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper mapper = new ObjectMapper();
            list = mapper.readValue(response.body(), new TypeReference<List<Product>>() {});
            return list;
        } catch (Exception ex) {
            Alerts.showAlert("IO Exception", "Error loading view", ex.getMessage(), Alert.AlertType.ERROR);
            System.out.println(ex.getMessage());
            return list;
        }
    }
}
