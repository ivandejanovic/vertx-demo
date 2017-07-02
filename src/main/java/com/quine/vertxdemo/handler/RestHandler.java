/**
 * 
 */
package com.quine.vertxdemo.handler;

import io.vertx.ext.web.RoutingContext;

/**
 * @author Ivan Dejanovic
 *
 */
public interface RestHandler {
	public void getAll(RoutingContext routingContext);

	public void get(RoutingContext routingContext);

	public void post(RoutingContext routingContext);

	public void put(RoutingContext routingContext);

	public void delete(RoutingContext routingContext);
}
