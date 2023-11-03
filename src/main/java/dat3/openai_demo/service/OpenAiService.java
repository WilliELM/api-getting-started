package dat3.openai_demo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dat3.openai_demo.entity.JokeCache;
import dat3.openai_demo.dtos.MyJokeResponse;
import dat3.openai_demo.dtos.OpenApiResponse;
import dat3.openai_demo.repository.JokeCacheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class OpenAiService {

  @Value("${app.api-key}")
  private String API_KEY;

  @Autowired
  private JokeCacheRepository jokeCacheRepository;

  private final WebClient client = WebClient.create();
  private final String URL = "https://api.openai.com/v1/completions";
  private final String FIXED_PROMPT = "Give me a cool joke involving: ";
  private final ObjectMapper mapper = new ObjectMapper();

  public MyJokeResponse getAJoke(String prompt) throws URISyntaxException, JsonProcessingException {
    Optional<JokeCache> cachedJoke = jokeCacheRepository.findById(prompt);
    if (cachedJoke.isPresent()) {
      return new MyJokeResponse(cachedJoke.get().getJoke(), FIXED_PROMPT + prompt);
    }

    String inputPrompt = FIXED_PROMPT + prompt;

    Map<String, Object> body = new HashMap<>();
    body.put("model", "text-davinci-003");
    body.put("prompt", inputPrompt);
    body.put("temperature", 1);
    body.put("max_tokens", 50);
    body.put("top_p", 1);
    body.put("frequency_penalty", 0.2);
    body.put("presence_penalty", 0);

    String json = mapper.writeValueAsString(body);

    OpenApiResponse response = client.post()
            .uri(new URI(URL))
            .header("Authorization", "Bearer " + API_KEY)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(json))
            .retrieve()
            .bodyToMono(OpenApiResponse.class)
            .block();

    String joke = response.getChoices().get(0).getText();

    JokeCache jokeCache = new JokeCache();
    jokeCache.setPrompt(prompt);
    jokeCache.setJoke(joke);
    jokeCacheRepository.save(jokeCache);

    return new MyJokeResponse(joke, inputPrompt);
  }
}
