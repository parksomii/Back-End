package edu.allinone.sugang.router;

import edu.allinone.sugang.handler.EnrollmentWebFluxHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;

@Configuration
public class WebFluxRouterConfig {

    /**
     * WebFlux 라우팅 설정 메서드
     *
     * @param handler 수강신청 핸들러
     * @return RouterFunction<ServerResponse> 라우팅 설정
     */
    @Bean
    public RouterFunction<ServerResponse> route(EnrollmentWebFluxHandler handler) {
        return RouterFunctions
                // POST 요청을 /api/reactive/enrollment 경로로 라우팅하고, handler.enroll 메서드를 호출
                .route(POST("/api/reactive/enrollment"), handler::enroll)
                // GET 요청을 /api/reactive/enrollment/position 경로로 라우팅하고, handler.getPosition 메서드를 호출
                .andRoute(GET("/api/reactive/enrollment/position"), handler::getPosition);
    }
}
