package springframework.container_di.mock;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({ "springframework.container_di.mock",
                "springframework.container_di.core" })
public class MockConfig {

}
