package io.cita.app.resource.v1;

import io.cita.app.model.dto.TagDto;
import io.cita.app.model.dto.request.ClientPageRequest;
import io.cita.app.model.dto.response.api.ApiResponse;
import io.cita.app.service.TagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("${app.api-version}" + "/tags")
@Slf4j
@RequiredArgsConstructor
public class TagResource {
	
	private final TagService tagService;
	
	@GetMapping
	public ResponseEntity<ApiResponse<Page<TagDto>>> findAll(@RequestParam final Map<String, String> params) {
		log.info("** Find all tags.. *");
		final var tags = this.tagService.findAll(ClientPageRequest.from(params));
		return ResponseEntity.ok(new ApiResponse<>(tags.toList().size(), HttpStatus.OK, true, tags));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<TagDto>> findById(@PathVariable final String id) {
		log.info("** Find by id.. *");
		return ResponseEntity.ok(new ApiResponse<>(1, HttpStatus.OK, true,
				this.tagService.findById(Integer.parseInt(id))));
	}
	
	@GetMapping("/identifier/{identifier}")
	public ResponseEntity<ApiResponse<TagDto>> findByIdentifier(@PathVariable final String identifier) {
		log.info("** Find by identifier.. *");
		return ResponseEntity.ok(new ApiResponse<>(1, HttpStatus.OK, true, 
				this.tagService.findByIdentifier(identifier.strip())));
	}
	
}



