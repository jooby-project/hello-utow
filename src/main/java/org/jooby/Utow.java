package org.jooby;

import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.PathHandler;
import io.undertow.util.Headers;

public class Utow {

  public static void main(final String[] args) {
    HttpHandler handler = new HttpHandler() {

      @Override
      public void handleRequest(final HttpServerExchange exchange) throws Exception {
        if (exchange.isInIoThread()) {
          exchange.dispatch(this);
          return;
        }
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
        exchange.getResponseSender().send("Hello World");
      }
    };
    Undertow server = Undertow.builder()
        .addHttpListener(8080, "localhost")
        .setHandler(new PathHandler().addPrefixPath("/plaintext", handler))
        .setWorkerThreads(200)
        .build();
    server.start();
  }

}
