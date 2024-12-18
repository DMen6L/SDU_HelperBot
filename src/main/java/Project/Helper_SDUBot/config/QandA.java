package Project.Helper_SDUBot.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.yaml.snakeyaml.tokens.ScalarToken;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Data
public class QandA {

    private Map<String, Object> QandAs;

    public QandA(String language) throws RuntimeException {

        switch (language) {
            case "English":
                try{
                    ObjectMapper mapper = new ObjectMapper();
                    InputStream inputStream = getClass().getClassLoader().getResourceAsStream("English.json");
                    if (inputStream == null) {
                        throw new RuntimeException("English.json file not found in resources");
                    }
                    QandAs = mapper.readValue(inputStream, Map.class);
                } catch (IOException e) {
                    log.error("An error: {}", e.getMessage());
                }
                break;
            case "Russian":
                try {
                    ObjectMapper mapper = new ObjectMapper();
                    InputStream inputStream = getClass().getClassLoader().getResourceAsStream("Russian.json");
                    if (inputStream == null) {
                        throw new RuntimeException("Russian.json file not found in resources");
                    }
                    QandAs = mapper.readValue(inputStream, Map.class);
                } catch (IOException e) {
                    log.error("An error: {}", e.getMessage());
                }
                break;
            case "Kazakh":
                try {
                    ObjectMapper mapper = new ObjectMapper();
                    InputStream inputStream = getClass().getClassLoader().getResourceAsStream("Kazakh.json");
                    if (inputStream == null) {
                        throw new RuntimeException("Kazakh.json file not found in resources");
                    }
                    QandAs = mapper.readValue(inputStream, Map.class);
                } catch (IOException e) {
                    log.error("An error: {}", e.getMessage());
                }
                break;
            default:
                QandAs = new HashMap<>();
                break;
        }
    }
}
