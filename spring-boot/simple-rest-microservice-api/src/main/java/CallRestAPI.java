import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class CallRestAPI {
public static void main(String args[]){
    String xmlString = "<Transactions><Transaction><id>102</id><account>108392</account><amount>392.4</amount></Transaction>" +
            "<Transaction><id>103</id><account>108492</account><amount>32.4</amount></Transaction>" +
            "<Transaction><id>104</id><account>108392</account><amount>3232</amount></Transaction>" +
            "</Transactions>";

    RestTemplate restTemplate =  new RestTemplate();
    //Create a list for the message converters
    List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
    //Add the String Message converter
    messageConverters.add(new StringHttpMessageConverter());
    //Add the message converters to the restTemplate
    restTemplate.setMessageConverters(messageConverters);


    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_XML);
    HttpEntity<String> request = new HttpEntity<String>(xmlString, headers);

    final ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8080/transactions/insertBulk", request, String.class);
    System.out.println(response);
}
}
