/**
 * 
 */
package com.quine.vertxdemo.handler;

import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.LoggerFactory;

import com.quine.vertxdemo.entity.Whisky;

import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

/**
 * @author Ivan Dejanovic
 *
 */
public class WhiskyRestHandler implements RestHandler{
	private final static org.slf4j.Logger logger = LoggerFactory.getLogger(WhiskyRestHandler.class);

	// Store our product
	private Map<Integer, Whisky> products = new LinkedHashMap<>();
	
	public WhiskyRestHandler() {
		createSomeData();
	}

	public void getAll(RoutingContext routingContext) {
		logger.info("Invoked getAll.");
		
		routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
				.end(Json.encodePrettily(products.values()));
	}

	public void get(RoutingContext routingContext) {
		final String id = routingContext.request().getParam("id");
		if (id == null) {
			logger.info("Invoked get with out id.");
			routingContext.response().setStatusCode(400).end();
		} else {
			logger.info("Invoked get with id: " + id);
			final Integer idAsInteger = Integer.valueOf(id);
			Whisky whisky = products.get(idAsInteger);
			if (whisky == null) {
				routingContext.response().setStatusCode(404).end();
			} else {
				routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
						.end(Json.encodePrettily(whisky));
			}
		}
	}

	public void post(RoutingContext routingContext) {
		logger.info("Invoked post.");
		
		final Whisky whisky = Json.decodeValue(routingContext.getBodyAsString(), Whisky.class);
		products.put(whisky.getId(), whisky);
		routingContext.response().setStatusCode(201).putHeader("content-type", "application/json; charset=utf-8")
				.end(Json.encodePrettily(whisky));
	}

	public void put(RoutingContext routingContext) {
		logger.info("Invoked put.");
		
		final String id = routingContext.request().getParam("id");
		JsonObject json = routingContext.getBodyAsJson();
		if (id == null || json == null) {
			routingContext.response().setStatusCode(400).end();
		} else {
			final Integer idAsInteger = Integer.valueOf(id);
			Whisky whisky = products.get(idAsInteger);
			if (whisky == null) {
				routingContext.response().setStatusCode(404).end();
			} else {
				whisky.setName(json.getString("name"));
				whisky.setOrigin(json.getString("origin"));
				routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
						.end(Json.encodePrettily(whisky));
			}
		}
	}

	public void delete(RoutingContext routingContext) {
		logger.info("Invoked delete.");
		
		String id = routingContext.request().getParam("id");
		if (id == null) {
			routingContext.response().setStatusCode(400).end();
		} else {
			Integer idAsInteger = Integer.valueOf(id);
			products.remove(idAsInteger);
		}
		routingContext.response().setStatusCode(204).end();
	}

	// Create some product
	private void createSomeData() {
		Whisky bowmore = new Whisky("Bowmore 15 Years Laimrig", "Scotland, Islay");
		products.put(bowmore.getId(), bowmore);
		Whisky talisker = new Whisky("Talisker 57° North", "Scotland, Island");
		products.put(talisker.getId(), talisker);
	}
}
