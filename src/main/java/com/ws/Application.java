package com.ws;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/***
 * @author only2dhir (Dhiraj Ray)
 * original source https://github.com/only2dhir/spring-websocket-without-stomp
 * original post https://www.devglan.com/spring-boot/spring-websocket-integration-example-without-stomp
 *
 * modified by saulpalv (Saul Palazuelos)
 *
 * Use raw websocket by defining your own messaging protocol to connect to spring websocket using TextWebSocketHandler.
 * WITHOUT STOMP AND SOCKJS
 */

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
