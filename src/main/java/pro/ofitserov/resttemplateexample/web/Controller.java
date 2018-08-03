package pro.ofitserov.resttemplateexample.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import pro.ofitserov.resttemplateexample.model.ML;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequestMapping("rest")
@Api(value = "rest-service", description = "Rest-template example")
public class Controller {

    private static final Logger logger = LoggerFactory.getLogger(Controller.class);

    @GetMapping("get")
    @ApiOperation(value = "get RestTemplate ")
    public ResponseEntity checkMLList() {

        final String uri = "http://localhost:8090//msa/get";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("legalId", "123");
        headers.add("fullName", "123");
        headers.add("birthDate", "123");

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
        logger.info("Result", result);
        return result;
    }

    @GetMapping("get2")
    @ApiOperation(value = "get WebClient")
    public ResponseEntity checkMLList2() {

        WebClient client = WebClient.create("http://localhost:8090/msa");

        Flux<ML> response = client.get().uri("/get").accept(MediaType.APPLICATION_JSON).retrieve()
                .bodyToFlux(ML.class);
        List<ML> mls = response.collectList().block();

        logger.info("mls ", mls);

        return new ResponseEntity(HttpStatus.OK);
    }
}