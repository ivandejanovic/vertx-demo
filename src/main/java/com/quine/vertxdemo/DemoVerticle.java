package com.quine.vertxdemo;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.ext.web.Router;

import com.quine.vertxdemo.router.DemoRouterFactory;

/**
 * @author Ivan Dejanovic
 *
 */

public class DemoVerticle extends AbstractVerticle {

	@Override
	public void start(Future<Void> fut) {
		// Create a router object.
		Router router = DemoRouterFactory.createRouter(vertx);
		// Get port number
		Integer port = config().getInteger("http.port", 8080);

		// Create the HTTP server and pass the "accept" method to the request handler.
		vertx.createHttpServer().requestHandler(router::accept).listen(port, result -> {
			if (result.succeeded()) {
				fut.complete();
			} else {
				fut.fail(result.cause());
			}
		});
	}
}