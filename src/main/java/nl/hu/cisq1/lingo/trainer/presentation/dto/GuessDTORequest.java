package nl.hu.cisq1.lingo.trainer.presentation.dto;

import javax.validation.constraints.NotNull;

public class GuessDTORequest {
    @NotNull(message = "Guess can't be null, you have to enter something.")
    public String attempt;
}
