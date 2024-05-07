package pl.haladyj.springaiintro.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.haladyj.springaiintro.model.Answer;
import pl.haladyj.springaiintro.model.GetCapitalRequest;
import pl.haladyj.springaiintro.model.GetCapitalResponse;
import pl.haladyj.springaiintro.model.Question;
import pl.haladyj.springaiintro.services.OpenAIService;

@RestController
public class QuestionController {

    private final OpenAIService openAIService;

    public QuestionController(OpenAIService openAIService) {
        this.openAIService = openAIService;
    }

    @PostMapping("/ask")
    public Answer askQuestion(@RequestBody Question question){
        return openAIService.getAnswer(question);
    }

    @PostMapping("/capital")
    public GetCapitalResponse getCapital(@RequestBody GetCapitalRequest getCapitalRequest){
        return openAIService.getCapital(getCapitalRequest);
    }

    @PostMapping("/capitalWithInfo")
    public Answer getCapitalWithInfo(@RequestBody GetCapitalRequest getCapitalRequest){
        return openAIService.getCapitalWithInfo(getCapitalRequest);
    }
}
