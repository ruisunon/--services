package ai.sxr.shoppingla.product.api;

import ai.sxr.shoppingla.product.models.Category;
import ai.sxr.shoppingla.product.service.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoryController.class)
class CategoryControllerTest {
    @MockBean
    private CategoryService categoryService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateCategory() throws Exception {
        Category category = new Category(1L, "Book", LocalDate.now(), LocalDate.now(), new HashSet<>());

        mockMvc.perform(post("/api/categories").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(category)))
                .andExpect(status().isCreated())
                .andDo(print());
    }
    @Test
    void shouldReturnCategory() throws Exception {
        long id = 1L;
        Category tutorial =new Category(1L, "Book", LocalDate.now(), LocalDate.now(), new HashSet<>());

        when(categoryService.findById(id)).thenReturn(Optional.of(tutorial));
//        mockMvc.perform(get("/api/categories/{id}", id)).andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(id))
//                .andExpect(jsonPath("$.name").value(tutorial.getName()))
//                .andExpect(jsonPath("$.description").value(tutorial.getDescription()))
//                .andDo(print());
    }

    @Test
    void shouldReturnNotFoundTutorial() throws Exception {
        long id = 1L;

        when(categoryService.findById(id)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/categories/{id}", id))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

//    @Test
//    void shouldReturnListOfTutorials() throws Exception {
//        List< Category> tutorials = new ArrayList<>(
//                Arrays.asList(new  Category(1, "Spring Boot @WebMvcTest 1", "Description 1", true),
//                        new  Category(2, "Spring Boot @WebMvcTest 2", "Description 2", true),
//                        new  Category(3, "Spring Boot @WebMvcTest 3", "Description 3", true)));
//
//        when(categoryService.findAll()).thenReturn(tutorials);
//        mockMvc.perform(get("/api/tutorials"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.size()").value(tutorials.size()))
//                .andDo(print());
//    }
//
//    @Test
//    void shouldReturnListOfTutorialsWithFilter() throws Exception {
//        List< Category> tutorials = new ArrayList<>(
//                Arrays.asList(new  Category(1, "Spring Boot @WebMvcTest", "Description 1", true),
//                        new Tutorial(3, "Spring Boot Web MVC", "Description 3", true)));
//
//        String title = "Boot";
//        MultiValueMap<String, String> paramsMap = new LinkedMultiValueMap<>();
//        paramsMap.add("title", title);
//
//        when(categoryService.findByTitleContaining(title)).thenReturn(tutorials);
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
//        List<Category> tutorials = Collections.emptyList();
//
//        when(categoryService.findByTitleContaining(title)).thenReturn(tutorials);
//        mockMvc.perform(get("/api/tutorials").params(paramsMap))
//                .andExpect(status().isNoContent())
//                .andDo(print());
//    }
//
//    @Test
//    void shouldUpdateTutorial() throws Exception {
//        long id = 1L;
//
//        Category tutorial = new  Category(id, "Spring Boot @WebMvcTest", "Description", false);
//        Category updatedtutorial = new  Category(id, "Updated", "Updated", true);
//
//        when(categoryService.findById(id)).thenReturn(Optional.of(tutorial));
//        when(categoryService.save(any(Tutorial.class))).thenReturn(updatedtutorial);
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
//        Category updatedtutorial = new  Category(id, "Updated", "Updated", true);
//
//        when(categoryService.findById(id)).thenReturn(Optional.empty());
//        when(categoryService.save(any(Tutorial.class))).thenReturn(updatedtutorial);
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
//        doNothing().when(categoryService).deleteById(id);
//        mockMvc.perform(delete("/api/tutorials/{id}", id))
//                .andExpect(status().isNoContent())
//                .andDo(print());
//    }
//
//    @Test
//    void shouldDeleteAllTutorials() throws Exception {
//        doNothing().when(categoryService).deleteAll();
//        mockMvc.perform(delete("/api/tutorials"))
//                .andExpect(status().isNoContent())
//                .andDo(print());
//    }
}