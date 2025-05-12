package com.aitrip.web;

import com.aitrip.database.dto.prompt.PromptCreateDTO;
import com.aitrip.database.dto.prompt.PromptDTO;
import com.aitrip.database.dto.prompt.PromptEditDTO;
import com.aitrip.service.PromptService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/prompts")
public class PromptController {
    private final PromptService promptService;

    public PromptController(PromptService promptService) {
        this.promptService = promptService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllPrompts() {
        List<PromptDTO> allPromptsDTO = this.promptService.getAll();
        return ResponseEntity.ok().body(Map.of(
                "message", "Prompts retrieved successfully",
                "prompts", allPromptsDTO
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getPromptById(@PathVariable Long id) {
        PromptDTO promptDTO = this.promptService.getPromptById(id);
        return ResponseEntity.ok().body(Map.of(
                "message", "Prompt retrieved successfully",
                "prompt", promptDTO
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> editPromptById(@PathVariable Long id, @Valid @RequestBody PromptEditDTO promptEditDTO) {
        PromptDTO promptDTO = this.promptService.editPromptById(id, promptEditDTO);
        return ResponseEntity.ok().body(Map.of(
                "message", "Prompt updated successfully",
                "prompt", promptDTO
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deletePromptById(@PathVariable Long id) {
        Long deletedId = this.promptService.deletePromptById(id);
        return ResponseEntity.ok().body(Map.of(
                "message", "Prompt deleted successfully",
                "id", deletedId
        ));
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createPrompt(@Valid @RequestBody PromptCreateDTO promptCreateDTO) {
        PromptDTO promptDTO = this.promptService.createPrompt(promptCreateDTO);
        return ResponseEntity.ok().body(Map.of(
                "message", "Prompt created successfully",
                "prompt", promptDTO
        ));
    }
}
