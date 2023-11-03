package dat3.openai_demo.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class OpenApiResponse {
  public String id;
  public String object;
  public long created;
  public String model;
  public List<Choice> choices;
  public Usage usage;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getObject() {
    return object;
  }

  public void setObject(String object) {
    this.object = object;
  }

  public long getCreated() {
    return created;
  }

  public void setCreated(long created) {
    this.created = created;
  }

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public List<Choice> getChoices() {
    return choices;
  }

  public void setChoices(List<Choice> choices) {
    this.choices = choices;
  }

  public Usage getUsage() {
    return usage;
  }

  public void setUsage(Usage usage) {
    this.usage = usage;
  }

  public static class Choice {
    public String text;
    public int index;
    public int logprobs;
    public String finish_reason;

    public String getText() {
      return text;
    }

    public void setText(String text) {
      this.text = text;
    }

    public int getIndex() {
      return index;
    }

    public void setIndex(int index) {
      this.index = index;
    }

    public int getLogprobs() {
      return logprobs;
    }

    public void setLogprobs(int logprobs) {
      this.logprobs = logprobs;
    }

    public String getFinish_reason() {
      return finish_reason;
    }

    public void setFinish_reason(String finish_reason) {
      this.finish_reason = finish_reason;
    }
  }

  public static class Usage {
    @JsonProperty("prompt_tokens")
    public int promptTokens;
    @JsonProperty("completion_tokens")
    public int completionTokens;
    @JsonProperty("total_tokens")
    public int totalTokens;

    public int getPromptTokens() {
      return promptTokens;
    }

    public void setPromptTokens(int promptTokens) {
      this.promptTokens = promptTokens;
    }

    public int getCompletionTokens() {
      return completionTokens;
    }

    public void setCompletionTokens(int completionTokens) {
      this.completionTokens = completionTokens;
    }

    public int getTotalTokens() {
      return totalTokens;
    }

    public void setTotalTokens(int totalTokens) {
      this.totalTokens = totalTokens;
    }
  }
}