package tfip.ssf.assessment2.Services;

import java.io.StringReader;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import tfip.ssf.assessment2.Model.Item;
import tfip.ssf.assessment2.Model.Quotation;

@Service
public class QuotationService {
    
    public Quotation getQuotations(List<String> items) throws Exception {
        Gson gson = new Gson();
        com.google.gson.JsonArray jsonArray = gson.toJsonTree(items).getAsJsonArray();

        //https://quotation.chuklee.com/
        RequestEntity<String> req = RequestEntity.post("https://quotation.chuklee.com/quotation")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .body(jsonArray.toString(),String.class);
        
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = template.exchange(req, String.class);
        String payload = resp.getBody();
        int statusCode = resp.getStatusCode().value();
        System.out.printf("Status code: %d\n", statusCode);
        System.out.printf("Payload: %s\n", payload);

        JsonReader reader = Json.createReader(new StringReader(payload));
        JsonObject json = reader.readObject();

        //Return null if response object contains error
        if (json.containsKey("error")) {
            return null;
        }
        //Parse JsonObject into Quotation class
        Quotation quotation = new Quotation();
        try {
            quotation.setQuoteId(json.getString("quoteId"));
            JsonArray quotations = json.getJsonArray("quotations");
            for (int i=0;i<quotations.size();i++) {
                JsonObject quote = quotations.getJsonObject(i);
                String name = quote.getString("item");
                Float price = (float) quote.getJsonNumber("unitPrice").doubleValue();
                quotation.addQuotation(name, price);
            }
        } catch (HttpClientErrorException ex) {
            return null;
        }
        return quotation;
    }

    public Float calculatePrice(Quotation quotation, List<Item> cartList) {
        Float price = 0.0f;
        for (Item item : cartList) {
            price += quotation.getQuotation(item.getItemName())*item.getQuantity();
        }
        return price;
    }
}
