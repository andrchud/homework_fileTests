package tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.JsonFile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.InputStreamReader;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тестирование чтения json архива")
public class JsonTests {
    private final ClassLoader cl = JsonTests.class.getClassLoader();

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("Проверка что json фалй содержит информацию о фильме")
    void processJsonFile() throws Exception {
        try (InputStreamReader isr = new InputStreamReader(cl.getResourceAsStream("JsonFile.json"))) {
            JsonFile data = objectMapper.readValue(isr, JsonFile.class);
            assertThat(data.getTitle()).isEqualTo("Матрица");
            assertThat(data.getYear()).isEqualTo(1999);
            assertThat(data.getCast()).containsExactly("Киану Ривз", "Лоуренс Фишбёрн", "Кэрри-Энн Мосс");
            assertThat(data.getRating()).isEqualTo(8.7f);
        }

    }
}
