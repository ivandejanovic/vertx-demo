/**
 * 
 */
package com.quine.vertxdemo.router;

import org.slf4j.LoggerFactory;

import com.quine.vertxdemo.handler.RestHandler;
import com.quine.vertxdemo.handler.RestHandlerFactory;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.StaticHandler;

/**
 * @author IvanDejanovic
 *
 */
public class DemoRouterFactory {
	private final static org.slf4j.Logger logger = LoggerFactory.getLogger(DemoRouterFactory.class);

	public static Router createRouter(Vertx vertx) {
		logger.info("Setting router endpoints.");
		
		// Create a router object.
		Router router = Router.router(vertx);
		RestHandler handler = RestHandlerFactory.createWhiskyRestHandler();

		// Bind "/" to our hello message - so we are still compatible.
		router.route("/").handler(routingContext -> {
			HttpServerResponse response = routingContext.response();
			response.putHeader("content-type", "text/html").end("<h1>Hello from my first Vert.x 3 application</h1>");
		});

		router.get("/api/whiskies").handler(handler::getAll);
		router.route("/api/whiskies*").handler(BodyHandler.create());
		router.post("/api/whiskies").handler(handler::post);
		router.get("/api/whiskies/:id").handler(handler::get);
		router.put("/api/whiskies/:id").handler(handler::put);
		router.delete("/api/whiskies/:id").handler(handler::delete);

		// Serve static resources from the /assets directory
		router.route("/assets/*").handler(StaticHandler.create("assets"));

		return router;
	}
}
