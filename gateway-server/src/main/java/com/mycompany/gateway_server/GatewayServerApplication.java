package com.mycompany.gateway_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayServerApplication.class, args);
	}


	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		return 	builder.routes()
				.route(
						r -> r// following route gives the error
								.path("/salon/api/users/**","/salon/api/user/**")
								.filters(f -> f
										.rewritePath("/salon/(?<segment>.*)","/${segment}"))
								.uri("lb://USER-SERVICE"))

				.route(p->p
						.path("/salon/api/service-offering/salon-owner/**","/salon/api/service-offering/**")
						.filters(r->r
								.rewritePath("/salon/(?<segment>.*)","/${segment}")
						)
						.uri("lb://SERVICE-OFFERING"))


				.route(p->p
						.path("/salon/api/salon/**","/salon/api/salons/**")
						.filters(r->r
								.rewritePath("/salon/(?<segment>.*)","/${segment}")
						)
						.uri("lb://SALOON-SERVICE"))



				.route(p->p
						.path("/salon/api/payments/**","/salon/api/payment/**")
						.filters(r->r
								.rewritePath("/salon/(?<segment>.*)","/${segment}")
						)
						.uri("lb://PAYMENT-SERVICE"))





				.route(p->p
						.path("/salon/api/categories/**","/salon/api/categories/salon-owner/**")
						.filters(r->r
								.rewritePath("/salon/(?<segment>.*)","/${segment}")
						)
						.uri("lb://CATEGORY-SERVICE"))

			.route(p->p
						.path("/salon/api/booking/**")
						.filters(r->r
								.rewritePath("/salon/(?<segment>.*)","/${segment}")
						)
						.uri("lb://BOOKING-SERVICE"))


				.build();







	}





}
