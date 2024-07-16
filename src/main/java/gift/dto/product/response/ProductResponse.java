package gift.dto.product.response;

public record ProductResponse(
    Long id,
    String name,
    Integer price,
    String imageUrl
) {

    public ProductResponse {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
    }
}