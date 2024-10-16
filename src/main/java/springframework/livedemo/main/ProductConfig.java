package springframework.livedemo.main;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({ "springframework.livedemo.core", "springframework.livedemo.main" })
public class ProductConfig {

}
