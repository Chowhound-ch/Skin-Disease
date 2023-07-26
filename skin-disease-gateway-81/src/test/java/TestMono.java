import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

/**
 * @author : Chowhound
 * @since : 2023/7/25 - 2:51
 */
public class TestMono {

    @Test
    public void testMono() {
         Mono<String> mono = Mono.just("hello");
         new Thread(() -> {
             // 怎么向mono中增加数据
                mono.subscribe(System.out::println);

         }).start();
         mono.subscribe(System.out::println);
    }
}
