/**
 * 
 */
package com.quine.vertxdemo.handler;

/**
 * @author Ivan Dejanovic
 *
 */
public class RestHandlerFactory {
	public static RestHandler createWhiskyRestHandler() {
		return new WhiskyRestHandler();
	}
}
