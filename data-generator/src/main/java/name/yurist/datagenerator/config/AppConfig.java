package name.yurist.datagenerator.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "app.generate")
@Configuration
@Data
public class AppConfig {

    private int cardQuantity;
    private int accountQuantity;

}
