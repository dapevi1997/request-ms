package co.com.crediya.consumer.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

class RestConsumerConfigTest {

    private static final String BASE_URL = "http://localhost:8080";
    private static final int TIMEOUT = 5000;

    private RestConsumerConfig config;
    private WebClient.Builder builder;
    private WebClient webClient;

    @BeforeEach
    void setUp() {
        builder = Mockito.mock(WebClient.Builder.class);
        webClient = Mockito.mock(WebClient.class);
        config = new RestConsumerConfig(BASE_URL, TIMEOUT);
    }

    @Test
    void shouldInitializeValuesInConstructor() {
        assertThat(config).isNotNull();
    }

    @Test
    void get(){
        // Act
        Mockito.when(builder.baseUrl(anyString())).thenReturn(builder);
        Mockito.when(builder.defaultHeader(any(), anyString())).thenReturn(builder);
        Mockito.when(builder.clientConnector(any())).thenReturn(builder);
        Mockito.when(builder.build()).thenReturn(webClient);

        // Act
        WebClient webClientResult = config.getWebClient(builder);

        //Assert
        assertNotNull(webClientResult);
    }
}
