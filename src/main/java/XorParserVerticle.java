import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;

import java.util.List;

public class XorParserVerticle extends AbstractVerticle {

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        Router router = Router.router(vertx);

        router.get("/crackMe/:encryptedText/:keySize*").handler(routingContext -> {
            String encryptedTextCommaSeperated = routingContext.request().params().get("encryptedText");
            String[] aa = encryptedTextCommaSeperated.split(",");
            char[] text = new char[aa.length];
            for (int i = 0; i < aa.length; i++) {
                int integer = Integer.valueOf(aa[i]);
                text[i] = (char)integer;
            }
            Integer keySize = Integer.valueOf(routingContext.request().params().get("keySize"));
            List<String> results = XorCipher.guessKey(text, keySize);
            routingContext.response().end(Json.encode(results));
        });
        router.route("/*").handler(StaticHandler.create("public").setWebRoot("public/xor-cipher-angular/dist/xor-cipher-angular"));
        vertx.createHttpServer()
                .requestHandler(router::accept)
                .listen(8080, result -> {
                    if (result.succeeded()) {
                        startFuture.complete();
                    } else {
                        startFuture.fail(result.cause());
                    }
                });


    }

    @Override
    public void start() throws Exception {
        Router router = Router.router(getVertx());
    }
}
