import io.vertx.core.Vertx;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.concurrent.TimeUnit;

/**
 * @Author: chenzejin
 * @Date: 2022-05-13
 */
@ExtendWith(VertxExtension.class)
public class JunitTest {

    Vertx vertx = Vertx.vertx();

    @Test
    void start_server() {
        vertx.createHttpServer()
                .requestHandler(req -> req.response().end("Ok"))
                .listen(16969, ar -> {
                    // (we can check here if the server started or not)
                    System.out.println(ar.result());
                });
    }

    @Test
    void start_http_server() throws Throwable {
        // 允许等待其他线程中的操作通知完成，并且支持接收断言失败以将测试标记为失败。
        VertxTestContext testContext = new VertxTestContext();

        // succeedingThenComplete返回一个预期会成功的异步结果处理程序，然后使测试上下文通过。
        vertx.createHttpServer()
                .requestHandler(req -> req.response().end())
                .listen(16969)
                .onComplete(testContext.succeedingThenComplete());

        // awaitCompletion具有 a 的语义，如果在测试通过之前等待延迟到期，则java.util.concurrent.CountDownLatch返回。false
        Assertions.assertThat(testContext.awaitCompletion(5, TimeUnit.SECONDS)).isTrue();

        // 如果上下文捕获了一个（可能是异步的）错误，那么在完成后我们必须抛出失败异常以使测试失败。
        if (testContext.failed()) {
            throw testContext.causeOfFailure();
        }
    }


}
