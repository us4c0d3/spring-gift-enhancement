package gift.dto.product.response;

public record ProductResponse(
    Long id,
    String name,
    Integer price,
    String imageUrl,
    String categoryName
) {

}