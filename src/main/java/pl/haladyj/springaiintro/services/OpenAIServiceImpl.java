package pl.haladyj.springaiintro.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.parser.BeanOutputParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import pl.haladyj.springaiintro.model.Answer;
import pl.haladyj.springaiintro.model.GetCapitalRequest;
import pl.haladyj.springaiintro.model.GetCapitalResponse;
import pl.haladyj.springaiintro.model.Question;

import java.util.Map;

@Service
public class OpenAIServiceImpl implements OpenAIService{

    private final ChatClient chatClient;

    public OpenAIServiceImpl(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @Value("classpath:templates/get-capital-prompt.st")
    private Resource getCapitalResource;

    @Value("classpath:templates/get-capital-with-info-prompt.st")
    private Resource getCapitalWithInfoResource;

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public String getAnswer(String question) {
        PromptTemplate promptTemplate = new PromptTemplate(question);
        Prompt prompt = promptTemplate.create();
        ChatResponse response = chatClient.call(prompt);

        return response.getResult().getOutput().getContent();
    }

    @Override
    public Answer getAnswer(Question question) {
        PromptTemplate promptTemplate = new PromptTemplate(question.question());
        Prompt prompt = promptTemplate.create();
        ChatResponse response = chatClient.call(prompt);

        return new Answer(response.getResult().getOutput().getContent());
    }

    @Override
    public GetCapitalResponse getCapital(GetCapitalRequest getCapitalRequest) {
        BeanOutputParser<GetCapitalResponse> parser = new BeanOutputParser<>(GetCapitalResponse.class);
        String format = parser.getFormat();

        PromptTemplate promptTemplate = new PromptTemplate(getCapitalResource);
        Prompt prompt = promptTemplate.create(Map.of("stateOrCountry",getCapitalRequest.stateOrCountry(), "format",format));
        ChatResponse response = chatClient.call(prompt);

        return parser.parse(response.getResult().getOutput().getContent());
    }

    @Override
    public Answer getCapitalWithInfo(GetCapitalRequest getCapitalRequest) {
        PromptTemplate promptTemplate = new PromptTemplate(getCapitalWithInfoResource);
        Prompt prompt = promptTemplate.create(Map.of("stateOrCountry",getCapitalRequest.stateOrCountry()));
        ChatResponse response = chatClient.call(prompt);

        return new Answer(response.getResult().getOutput().getContent());
    }
}
