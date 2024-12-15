package Project.Helper_SDUBot.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@Slf4j
@Data
public class helper {

    private Map<String, Object> helps;

    public helper(String language) throws IOException {

        switch(language){
            case "English":
                try {
                    ObjectMapper mapper = new ObjectMapper();
                    InputStream inputStream = getClass().getClassLoader().getResourceAsStream("EnglishCommands.json");
                    if (inputStream == null) {
                        throw new RuntimeException("EnglishCommands.json file not found in resources");
                    }
                    helps = mapper.readValue(inputStream, Map.class);
                } catch (IOException e) {
                    log.error("An error: {}", e.getMessage());
                }
                break;
            case "Russian":
                try {
                    ObjectMapper mapper = new ObjectMapper();
                    InputStream inputStream = getClass().getClassLoader().getResourceAsStream("RussianCommands.json");
                    if (inputStream == null) {
                        throw new RuntimeException("RussianCommands.json file not found in resources");
                    }
                    helps = mapper.readValue(inputStream, Map.class);
                } catch (IOException e) {
                    log.error("An error: {}", e.getMessage());
                }
            case "Kazakh":
                break;
        }

    }
}
