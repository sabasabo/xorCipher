import io.vertx.core.Vertx;

public class MainClass {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new XorParserVerticle());
        System.out.println(MainClass.class.getCanonicalName());
        System.out.println(MainClass.class.getPackage());
        System.out.println(XorParserVerticle.class.getCanonicalName());
        System.out.println(XorParserVerticle.class.getPackage());
    }
}
