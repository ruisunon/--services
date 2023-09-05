package ai.sxr.shoppingla.product.api;

import ai.sxr.shoppingla.product.models.Category;
import ai.sxr.shoppingla.product.models.dto.CreateCategoryDto;
import ai.sxr.shoppingla.product.models.response.CategoryListResponse;
import ai.sxr.shoppingla.product.models.response.CategoryResponse;
import ai.sxr.shoppingla.product.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/categories")
    public ResponseEntity<CategoryResponse> create(@Valid @RequestBody CreateCategoryDto createCategoryDto) {
        Category category = categoryService.create(createCategoryDto);

        return ResponseEntity.ok(new CategoryResponse(category));
    }

    @GetMapping("/categories")
    public ResponseEntity<CategoryListResponse> getAll() {
        List<Category> categories = categoryService.findAll();

        return ResponseEntity.ok(new CategoryListResponse(categories));
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<CategoryResponse> getById(@PathVariable("id") long id) {
        Optional<Category> categoryData = categoryService.findById(id);
        if (categoryData.isPresent()) {
            return new ResponseEntity<>(new CategoryResponse(categoryData.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


//    @Test
//    void shouldCreateTutorial() throws Exception {
//        Tutorial tutorial = new Tutorial(1, "Spring Boot @WebMvcTest", "Description", true);
//
//        mockMvc.perform(post("/api/tutorials").contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(tutorial)))
//                .andExpect(status().isCreated())
//                .andDo(print());
//    }
//
//    @Test
//    void shouldReturnTutorial() throws Exception {
//        long id = 1L;
//        Tutorial tutorial = new Tutorial(id, "Spring Boot @WebMvcTest", "Description", true);
//
//        when(tutorialRepository.findById(id)).thenReturn(Optional.of(tutorial));
//        mockMvc.perform(get("/api/tutorials/{id}", id)).andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(id))
//                .andExpect(jsonPath("$.title").value(tutorial.getTitle()))
//                .andExpect(jsonPath("$.description").value(tutorial.getDescription()))
//                .andExpect(jsonPath("$.published").value(tutorial.isPublished()))
//                .andDo(print());
//    }
//
//    @Test
//    void shouldReturnNotFoundTutorial() throws Exception {
//        long id = 1L;
//
//        when(tutorialRepository.findById(id)).thenReturn(Optional.empty());
//        mockMvc.perform(get("/api/tutorials/{id}", id))
//                .andExpect(status().isNotFound())
//                .andDo(print());
//    }
//
//    @Test
//    void shouldReturnListOfTutorials() throws Exception {
//        List<Tutorial> tutorials = new ArrayList<>(
//                Arrays.asList(new Tutorial(1, "Spring Boot @WebMvcTest 1", "Description 1", true),
//                        new Tutorial(2, "Spring Boot @WebMvcTest 2", "Description 2", true),
//                        new Tutorial(3, "Spring Boot @WebMvcTest 3", "Description 3", true)));
//
//        when(tutorialRepository.findAll()).thenReturn(tutorials);
//        mockMvc.perform(get("/api/tutorials"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.size()").value(tutorials.size()))
//                .andDo(print());
//    }
//
//    @Test
//    void shouldReturnListOfTutorialsWithFilter() throws Exception {
//        List<Tutorial> tutorials = new ArrayList<>(
//                Arrays.asList(new Tutorial(1, "Spring Boot @WebMvcTest", "Description 1", true),
//                        new Tutorial(3, "Spring Boot Web MVC", "Description 3", true)));
//
//        String title = "Boot";
//        MultiValueMap<String, String> paramsMap = new LinkedMultiValueMap<>();
//        paramsMap.add("title", title);
//
//        when(tutorialRepository.findByTitleContaining(title)).thenReturn(tutorials);
//        mockMvc.perform(get("/api/tutorials").params(paramsMap))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.size()").value(tutorials.size()))
//                .andDo(print());
//    }
//
//    @Test
//    void shouldReturnNoContentWhenFilter() throws Exception {
//        String title = "BezKoder";
//        MultiValueMap<String, String> paramsMap = new LinkedMultiValueMap<>();
//        paramsMap.add("title", title);
//
//        List<Tutorial> tutorials = Collections.emptyList();
//
//        when(tutorialRepository.findByTitleContaining(title)).thenReturn(tutorials);
//        mockMvc.perform(get("/api/tutorials").params(paramsMap))
//                .andExpect(status().isNoContent())
//                .andDo(print());
//    }
//
//    @Test
//    void shouldUpdateTutorial() throws Exception {
//        long id = 1L;
//
//        Tutorial tutorial = new Tutorial(id, "Spring Boot @WebMvcTest", "Description", false);
//        Tutorial updatedtutorial = new Tutorial(id, "Updated", "Updated", true);
//
//        when(tutorialRepository.findById(id)).thenReturn(Optional.of(tutorial));
//        when(tutorialRepository.save(any(Tutorial.class))).thenReturn(updatedtutorial);
//
//        mockMvc.perform(put("/api/tutorials/{id}", id).contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(updatedtutorial)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.title").value(updatedtutorial.getTitle()))
//                .andExpect(jsonPath("$.description").value(updatedtutorial.getDescription()))
//                .andExpect(jsonPath("$.published").value(updatedtutorial.isPublished()))
//                .andDo(print());
//    }
//
//    @Test
//    void shouldReturnNotFoundUpdateTutorial() throws Exception {
//        long id = 1L;
//
//        Tutorial updatedtutorial = new Tutorial(id, "Updated", "Updated", true);
//
//        when(tutorialRepository.findById(id)).thenReturn(Optional.empty());
//        when(tutorialRepository.save(any(Tutorial.class))).thenReturn(updatedtutorial);
//
//        mockMvc.perform(put("/api/tutorials/{id}", id).contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(updatedtutorial)))
//                .andExpect(status().isNotFound())
//                .andDo(print());
//    }
//
//    @Test
//    void shouldDeleteTutorial() throws Exception {
//        long id = 1L;
//
//        doNothing().when(tutorialRepository).deleteById(id);
//        mockMvc.perform(delete("/api/tutorials/{id}", id))
//                .andExpect(status().isNoContent())
//                .andDo(print());
//    }
//
//    @Test
//    void shouldDeleteAllTutorials() throws Exception {
//        doNothing().when(tutorialRepository).deleteAll();
//        mockMvc.perform(delete("/api/tutorials"))
//                .andExpect(status().isNoContent())
//                .andDo(print());
//    }
    ////////////////////////////////////////////////////

//    @PostMapping("/tutorials")
//    public ResponseEntity<Tutorial> createTutorial(@RequestBody Tutorial tutorial) {
//        try {
//            Tutorial _tutorial = tutorialRepository.save(new Tutorial(tutorial.getTitle(), tutorial.getDescription(), false));
//            return new ResponseEntity<>(_tutorial, HttpStatus.CREATED);
//        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    @PutMapping("/tutorials/{id}")
//    public ResponseEntity<Tutorial> updateTutorial(@PathVariable("id") long id, @RequestBody Tutorial tutorial) {
//        Optional<Tutorial> tutorialData = tutorialRepository.findById(id);
//
//        if (tutorialData.isPresent()) {
//            Tutorial _tutorial = tutorialData.get();
//            _tutorial.setTitle(tutorial.getTitle());
//            _tutorial.setDescription(tutorial.getDescription());
//            _tutorial.setPublished(tutorial.isPublished());
//            return new ResponseEntity<>(tutorialRepository.save(_tutorial), HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
//
//    @DeleteMapping("/tutorials/{id}")
//    public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") long id) {
//        try {
//            tutorialRepository.deleteById(id);
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    @DeleteMapping("/tutorials")
//    public ResponseEntity<HttpStatus> deleteAllTutorials() {
//        try {
//            tutorialRepository.deleteAll();
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//
//    }
//
//    @GetMapping("/tutorials/published")
//    public ResponseEntity<List<Tutorial>> findByPublished() {
//        try {
//            List<Tutorial> tutorials = tutorialRepository.findByPublished(true);
//
//            if (tutorials.isEmpty()) {
//                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//            }
//            return new ResponseEntity<>(tutorials, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
}