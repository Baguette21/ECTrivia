package com.ectrvia.trivia.controller;

import com.ectrvia.trivia.model.Category;
import com.ectrvia.trivia.model.Question;
import com.ectrvia.trivia.service.CategoryService;
import com.ectrvia.trivia.service.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private QuestionService questionService;

    @GetMapping
    public ResponseEntity<?> getAllCategories() {
        try {
            List<Category> categories = categoryService.getAllActiveCategories();
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            logger.error("Failed to get categories", e);
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", safeError(e)));
        }
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<?> getCategory(@PathVariable Long categoryId) {
        try {
            Category category = categoryService.getCategoryById(categoryId);
            if (category == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(category);
        } catch (Exception e) {
            logger.error("Failed to get category: {}", categoryId, e);
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", safeError(e)));
        }
    }

    @GetMapping("/{categoryId}/questions")
    public ResponseEntity<?> getQuestionsForCategory(@PathVariable Long categoryId,
                                                      @RequestParam(required = false) Integer limit) {
        try {
            List<Question> questions = questionService.getQuestionsForCategory(categoryId, limit);
            return ResponseEntity.ok(questions);
        } catch (Exception e) {
            logger.error("Failed to get questions for category: {}", categoryId, e);
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", safeError(e)));
        }
    }

    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody Map<String, String> request) {
        try {
            String name = request.get("name");
            String description = request.get("description");
            Category category = categoryService.createCategory(name, description);
            return ResponseEntity.status(HttpStatus.CREATED).body(category);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", safeError(e)));
        } catch (Exception e) {
            logger.error("Failed to create category", e);
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", safeError(e)));
        }
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long categoryId) {
        try {
            categoryService.deleteCategory(categoryId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", safeError(e)));
        } catch (Exception e) {
            logger.error("Failed to delete category {}", categoryId, e);
            return ResponseEntity.internalServerError().body(Map.of("error", safeError(e)));
        }
    }

    @PostMapping("/{categoryId}/questions")
    public ResponseEntity<?> addCategoryQuestions(@PathVariable Long categoryId,
                                                  @RequestBody Map<String, Object> request) {
        try {
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> questionsData = (List<Map<String, Object>>) request.get("questions");
            if (questionsData == null || questionsData.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("error", "No questions provided"));
            }

            int addedCount = 0;
            for (Map<String, Object> q : questionsData) {
                String questionText = (String) q.get("questionText");
                @SuppressWarnings("unchecked")
                List<String> answers = (List<String>) q.get("answers");
                Integer correctIndex = parseInteger(q.get("correctAnswerIndex"), "correctAnswerIndex");
                Integer timerSeconds = parseInteger(q.getOrDefault("timerSeconds", 15), "timerSeconds");

                questionService.addQuestionToCategory(categoryId, questionText, answers, correctIndex, timerSeconds);
                addedCount++;
            }

            int totalQuestions = questionService.getQuestionsForCategory(categoryId, null).size();
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("addedCount", addedCount, "totalQuestions", totalQuestions));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", safeError(e)));
        } catch (Exception e) {
            logger.error("Failed to add questions to category {}", categoryId, e);
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", safeError(e)));
        }
    }

    @DeleteMapping("/{categoryId}/questions/{questionId}")
    public ResponseEntity<?> deleteCategoryQuestion(@PathVariable Long categoryId, @PathVariable Long questionId) {
        try {
            questionService.deleteCategoryQuestion(categoryId, questionId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", safeError(e)));
        } catch (Exception e) {
            logger.error("Failed to delete question {} from category {}", questionId, categoryId, e);
            return ResponseEntity.internalServerError().body(Map.of("error", safeError(e)));
        }
    }

    @PutMapping("/{categoryId}/questions/{questionId}")
    public ResponseEntity<?> updateCategoryQuestion(@PathVariable Long categoryId,
                                                    @PathVariable Long questionId,
                                                    @RequestBody Map<String, Object> request) {
        try {
            String questionText = (String) request.get("questionText");
            @SuppressWarnings("unchecked")
            List<String> answers = (List<String>) request.get("answers");
            Integer correctIndex = parseInteger(request.get("correctAnswerIndex"), "correctAnswerIndex");
            Integer timerSeconds = parseInteger(request.getOrDefault("timerSeconds", 15), "timerSeconds");

            Question updated = questionService.updateCategoryQuestion(
                    categoryId, questionId, questionText, answers, correctIndex, timerSeconds);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", safeError(e)));
        } catch (Exception e) {
            logger.error("Failed to update question {} in category {}", questionId, categoryId, e);
            return ResponseEntity.internalServerError().body(Map.of("error", safeError(e)));
        }
    }

    private Integer parseInteger(Object value, String fieldName) {
        if (value == null) {
            return null;
        }
        if (value instanceof Integer intValue) {
            return intValue;
        }
        if (value instanceof Number numberValue) {
            return numberValue.intValue();
        }
        try {
            return Integer.parseInt(value.toString());
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Invalid numeric value for " + fieldName);
        }
    }

    private String safeError(Exception e) {
        String message = e.getMessage();
        return (message == null || message.isBlank()) ? "Request failed" : message;
    }
}
