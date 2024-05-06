package pl.haladyj.springaiintro.services;

import pl.haladyj.springaiintro.model.Answer;
import pl.haladyj.springaiintro.model.Question;

public interface OpenAIService {

    String getAnswer(String question);

    Answer getAnswer(Question question);
}
