package gift.product.option.controller;

import gift.product.option.dto.request.CreateOptionRequest;
import gift.product.option.service.OptionService;
import jakarta.validation.Valid;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products/{product_id}/options")
public class OptionController {

    private final OptionService optionService;

    public OptionController(OptionService optionService) {
        this.optionService = optionService;
    }

    @PostMapping
    public ResponseEntity<Long> addOption(@PathVariable("product_id") Long productId,
        @RequestBody @Valid CreateOptionRequest request) {
        Long id = optionService.createOption(request);
        URI location = URI.create("/api/products/" + productId + "/options" + id);
        return ResponseEntity.created(location).build();
    }
}