package learn.qzy.examplespringbootconsumer;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ExampleSpringbootConsumerApplicationTests {

    @Resource
    private ExampleServiceImpl exampleService;
    @Test
    void contextLoads() {
        exampleService.test();
    }

}
