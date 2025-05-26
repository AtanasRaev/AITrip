package com.aitrip.service.impl;

import com.aitrip.database.dto.openAI.OpenAIResponseDTO;
import com.aitrip.database.dto.plan.PlanCreateDTO;
import com.aitrip.database.dto.plan.PlanPageDTO;
import com.aitrip.database.dto.prompt.PromptDTO;
import com.aitrip.exception.plan.NullPlanCreateDTOException;
import com.aitrip.exception.prompt.EmptySystemPromptException;
import com.aitrip.exception.prompt.EmptyUserPromptException;
import com.aitrip.exception.prompt.NullPromptException;
import com.aitrip.service.OpenAIService;
import com.aitrip.service.PromptService;
import com.amadeus.resources.FlightOfferSearch;
import com.amadeus.resources.HotelOffer;
import com.openai.client.OpenAIClient;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class OpenAIServiceImpl implements OpenAIService {
    private final OpenAIClient client;
    private final PromptService promptService;
    private final ModelMapper modelMapper;


    @Override
    public PlanPageDTO createPlan(PlanCreateDTO planCreateDTO, FlightOfferSearch[] flights, HotelOffer[] hotelOffers) {
        if (planCreateDTO == null) {
            throw new NullPlanCreateDTOException();
        }

        PromptDTO prompt = getAndValidatePrompt(planCreateDTO.getPlanName());

        String userPrompt = setVariables(planCreateDTO, prompt.getUserPrompt(), flights, hotelOffers);
        String systemPrompt = setVariables(planCreateDTO, prompt.getSystemPrompt(), flights, hotelOffers);

        prompt.setUserPrompt(userPrompt);
        prompt.setSystemPrompt(systemPrompt);

        OpenAIResponseDTO response = sendPrompt(prompt);

        //TODO: Finish the implementation
        return null;
    }

    private OpenAIResponseDTO sendPrompt(PromptDTO promptDTO) {
        validatePrompt(promptDTO);

        ChatCompletionCreateParams params = setupOpenAIParams(promptDTO);
        ChatCompletion chatCompletion = this.client.chat().completions().create(params);
        return this.modelMapper.map(chatCompletion, OpenAIResponseDTO.class);
    }

    private static ChatCompletionCreateParams setupOpenAIParams(PromptDTO promptDTO) {
        ChatCompletionCreateParams.Builder paramsBuilder = ChatCompletionCreateParams.builder()
                .model(promptDTO.getModel())
                .addSystemMessage(promptDTO.getSystemPrompt())
                .addUserMessage(promptDTO.getUserPrompt());

        if (promptDTO.getMaxCompletionTokens() != null) {
            paramsBuilder.maxCompletionTokens(promptDTO.getMaxCompletionTokens());
        }

        if (promptDTO.getTemperature() != null) {
            paramsBuilder.temperature(promptDTO.getTemperature());
        }

        if (promptDTO.getTopP() != null) {
            paramsBuilder.topP(promptDTO.getTopP());
        }

        if (promptDTO.getFrequencyPenalty() != null) {
            paramsBuilder.frequencyPenalty(promptDTO.getFrequencyPenalty());
        }

        if (promptDTO.getPresencePenalty() != null) {
            paramsBuilder.presencePenalty(promptDTO.getPresencePenalty());
        }

        if (promptDTO.getReasoningEffort() != null) {
            paramsBuilder.reasoningEffort(promptDTO.getReasoningEffort());
        }

        return paramsBuilder.build();
    }

    private PromptDTO getAndValidatePrompt(String planName) {
        return this.promptService.getPromptByPlanName(planName);
    }

    private void validatePrompt(PromptDTO promptDTO) {
        if (promptDTO == null) {
            throw new NullPromptException();
        }

        if (promptDTO.getUserPrompt() == null || promptDTO.getUserPrompt().isEmpty()) {
            throw new EmptyUserPromptException();
        }

        if (promptDTO.getSystemPrompt() == null || promptDTO.getSystemPrompt().isEmpty()) {
            throw new EmptySystemPromptException();
        }
    }

    private String setVariables(PlanCreateDTO planCreateDTO, String prompt, FlightOfferSearch[] flights, HotelOffer[] hotelOffers) {
        Map<String, String> replacements = buildReplacementsMap(planCreateDTO, flights, hotelOffers );

        for (Map.Entry<String, String> entry : replacements.entrySet()) {
            String placeholder = "{" + entry.getKey() + "}";
            String value = entry.getValue();

            if (value != null) {
                prompt = prompt.replace(placeholder, value);
            }
        }

        return prompt;
    }

    private Map<String, String> buildReplacementsMap(PlanCreateDTO planCreateDTO, FlightOfferSearch[] flights, HotelOffer[] hotelOffers ) {
        Map<String, String> replacements = new HashMap<>();

        replacements.put("origin", planCreateDTO.getOrigin());
        replacements.put("destination", planCreateDTO.getDestination());
        replacements.put("startDate", planCreateDTO.getStartDate() != null ?
                planCreateDTO.getStartDate().toString() : null);
        replacements.put("endDate", planCreateDTO.getEndDate() != null ?
                planCreateDTO.getEndDate().toString() : null);
        replacements.put("budget", planCreateDTO.getBudget() != null ?
                planCreateDTO.getBudget().toString() : null);

        replacements.put("adults", String.valueOf(planCreateDTO.getAdults()));
        replacements.put("children", String.valueOf(planCreateDTO.getChildren()));

        StringBuilder flightsSb = new StringBuilder();
        for (int i = 0; i < flights.length; i++) {
            flightsSb.append("Flight #").append(i + 1).append("\n");

            FlightOfferSearch flight = flights[i];
            FlightOfferSearch.Itinerary[] itineraries = flight.getItineraries();
            if (itineraries.length > 0) {
                flightsSb.append("Outbound:\n");
                appendItinerary(itineraries[0], flightsSb, flight);
            }
            if (itineraries.length > 1) {
                flightsSb.append("\nReturn:\n");
                appendItinerary(itineraries[1], flightsSb, flight);
            }


        }
        replacements.put("flights", flightsSb.toString());

        return replacements;
    }

    private void appendItinerary(FlightOfferSearch.Itinerary itinerary, StringBuilder flightsSb, FlightOfferSearch flight) {
        FlightOfferSearch.SearchSegment[] segments = itinerary.getSegments();

        for (FlightOfferSearch.SearchSegment segment : segments) {
            flightsSb.append(segment.getDeparture().getIataCode())
                    .append(" ")
                    .append(segment.getDeparture().getAt())
                    .append(" -> ")
                    .append(segment.getArrival().getIataCode())
                    .append(" ")
                    .append(segment.getArrival().getAt())
                    .append(" ")
                    .append("(Duration: ").append(getItineraryDuration(segment.getDuration()))
                    .append(")\n");
        }

        String duration = getItineraryDuration(itinerary.getDuration());

        flightsSb.append("Total flight duration: ")
                .append(duration)
                .append("\n");

        flightsSb.append("Stops: ")
                .append(segments.length - 1)
                .append("\n");

        flightsSb.append("Price: ")
                .append(flight.getPrice().getTotal())
                .append(" ")
                .append(flight.getPrice().getCurrency())
                .append("\n\n");
    }

    private String getItineraryDuration(String isoDuration) {
        Duration duration = Duration.parse(isoDuration);

        int hours = duration.toHoursPart();
        int minutes = duration.toMinutesPart();

        return String.format("%02d:%02d", hours, minutes);
    }

}
