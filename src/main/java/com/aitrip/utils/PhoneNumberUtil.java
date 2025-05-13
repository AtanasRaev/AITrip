package com.aitrip.utils;

import com.aitrip.exception.external.phone.InvalidPhoneNumberException;
import com.aitrip.exception.external.phone.PhoneValidationServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;
import org.json.JSONException;
import org.json.JSONObject;

@Component
public class PhoneNumberUtil {

    private final RestClient restClient;
    private final String apiKey;

    public PhoneNumberUtil(RestClient restClient, @Value("${app.numverify.api.key}") String apiKey) {
        this.restClient = restClient;
        this.apiKey = apiKey;
    }

    public void validatePhoneNumber(String phoneNumber) {
        try {
            String url = UriComponentsBuilder
                    .fromUriString("http://apilayer.net/api/validate")
                    .queryParam("access_key", apiKey)
                    .queryParam("number", phoneNumber)
                    .toUriString();

            String response = restClient.get()
                    .uri(url)
                    .retrieve()
                    .body(String.class);

            if (response == null) {
                throw new InvalidPhoneNumberException("Empty response from validation service.");
            }

            JSONObject json = new JSONObject(response);

            boolean isValid = json.optBoolean("valid", false);
            if (!isValid) {
                throw new InvalidPhoneNumberException("Invalid phone number: " + phoneNumber);
            }

        } catch (HttpClientErrorException | HttpServerErrorException e) {
            throw new PhoneValidationServiceException("HTTP error from Numverify API: " + e.getStatusCode(), e);
        } catch (RestClientException e) {
            throw new PhoneValidationServiceException("Error connecting to Numverify API", e);
        } catch (JSONException e) {
            throw new PhoneValidationServiceException("Invalid JSON from Numverify API", e);
        }
    }
}
