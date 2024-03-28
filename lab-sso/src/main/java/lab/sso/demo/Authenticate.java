package lab.sso.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/token")
@Slf4j
public class Authenticate {

    @Value("${application.idp-url}")
    private String idpUrl;

    @PostMapping
    public ResponseEntity<String> token(@RequestBody User user) {

        log.info("User [{}]", user);

        HttpHeaders headers = new HttpHeaders();
        RestTemplate rt = new RestTemplate();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String,String> formData = new LinkedMultiValueMap<>();
        formData.add("client_id", user.clientId);
        formData.add("client_secret", user.clientSecret);
        formData.add("username", user.userName);
        formData.add("password", user.password);
        formData.add("grant_type", user.grantType);

        HttpEntity<MultiValueMap<String,String>> entity = new HttpEntity<MultiValueMap<String, String>>(formData, headers);
        return rt.postForEntity(idpUrl, entity, String.class);

    }

    public record User(String password, String clientId, String clientSecret, String grantType, String userName){}

}
