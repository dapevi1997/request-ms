package co.com.crediya.consumer;


import co.com.crediya.model.logger.LoggerGateway;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;
import java.io.IOException;


class RestConsumerTest {

    private static RestConsumer restConsumer;

    private static MockWebServer mockBackEnd;


    @BeforeAll
    static void setUp() throws IOException {
        mockBackEnd = new MockWebServer();
        mockBackEnd.start();
        var webClient = WebClient.builder().baseUrl(mockBackEnd.url("/").toString()).build();
        LoggerGateway loggerGateway = Mockito.mock(LoggerGateway.class);
        restConsumer = new RestConsumer(webClient, loggerGateway);
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockBackEnd.shutdown();
    }

    @Test
    @DisplayName("Validate the function getUserByEmail.")
    void validateTestGet() {
        String email = "mail@mail.com";
        String token = "sd54d54f5d4f5d4";

        String json = "{"
                + "\"idUser\": 1,"
                + "\"name\": \"Juan\","
                + "\"lastName\": \"Pérez\","
                + "\"email\": \"juan.perez@demo.com\","
                + "\"birthday\": \"1990-05-20\","
                + "\"address\": \"Calle 123, Ciudad\","
                + "\"documentId\": \"123456789\","
                + "\"phone\": \"3001234567\","
                + "\"baseSalary\": 2500.50,"
                + "\"idRole\": 2"
                + "}";

        mockBackEnd.enqueue(new MockResponse()
                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setResponseCode(HttpStatus.OK.value())
                .setBody(json));

        var response = restConsumer.getUserByEmail(email, token);

        StepVerifier.create(response)
                .expectNextMatches(findUserByEmailResponseDto -> findUserByEmailResponseDto.getName().equals("Juan"))
                .verifyComplete();
    }
}