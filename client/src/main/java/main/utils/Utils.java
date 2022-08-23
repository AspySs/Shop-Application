package main.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

public class Utils {
    public static <T> T getRequest(String url, Class<T> class_) {
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> entity = template.getForEntity(url, String.class);
        T object;

        try {
            ObjectMapper mapper = new ObjectMapper();
            object = mapper.readValue(entity.getBody(), class_);
            return object;
        } catch (JsonProcessingException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    public static ResponseEntity<String> postRequest(String url, String token, String requestBody, HttpMethod method, MediaType mediaType) {
/*        if(token == null){
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "Method allows authorized users");
        }*/
        HttpHeaders headers = new HttpHeaders();
        RestTemplate template = new RestTemplate();
        if (mediaType != null) {
            headers.setContentType(mediaType);
        }
        if (token != null) {
            headers.setBearerAuth(token);
        }
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
        return template.exchange(url, method, entity, String.class);
        //return template.postForEntity(url, httpEntity, String.class);
    }
}
