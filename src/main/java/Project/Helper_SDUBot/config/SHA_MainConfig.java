package Project.Helper_SDUBot.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Data
@PropertySource("application.properties")
public class SHA_MainConfig {
    //Values for configuration
    @Value("${bot.name}")
    private String botName;

    @Value("${bot.key}")
    private String token;
}
