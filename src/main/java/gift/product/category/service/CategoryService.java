package gift.product.category.service;

import gift.exception.category.CategoryNotFoundException;
import gift.product.category.dto.request.CreateCategoryRequest;
import gift.product.category.dto.request.UpdateCategoryRequest;
import gift.product.category.dto.response.CategoryResponse;
import gift.product.category.entity.Categories;
import gift.product.category.entity.Category;
import gift.product.category.repository.CategoryRepository;
import gift.util.mapper.CategoryMapper;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public List<CategoryResponse> getAllCategories() {
        List<Category> categoryList = categoryRepository.findAll();
        return categoryList.stream()
            .map(CategoryMapper::toResponse)
            .toList();
    }

    @Transactional(readOnly = true)
    public CategoryResponse getCategory(Long id) {
        Category category = categoryRepository.findById(id)
            .orElseThrow(CategoryNotFoundException::new);
        return CategoryMapper.toResponse(category);
    }

    @Transactional
    public Long createCategory(CreateCategoryRequest request) {
        Category newCategory = new Category(request.name(), request.color(), request.description(),
            request.imageUrl());

        Categories categories = categoryRepository.findAllCategories();
        categories.validate(newCategory);

        return categoryRepository.save(newCategory).getId();
    }

    @Transactional
    public void updateCategory(Long id, UpdateCategoryRequest request) {
        Category category = categoryRepository.findById(id)
            .orElseThrow(CategoryNotFoundException::new);
        category.setName(request.name());
    }

    @Transactional
    public void deleteCategory(Long id) {
        validateCategory(id);
        categoryRepository.deleteById(id);
    }

    // for update
    @Transactional(readOnly = true)
    protected void validateCategory(Category category) {
        if (categoryRepository.existsByName(category.getName())) {
            throw new CategoryAlreadyExistException();
        }
    }

    // for delete
    @Transactional(readOnly = true)
    protected void validateCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new CategoryNotFoundException();
        }
    }
}