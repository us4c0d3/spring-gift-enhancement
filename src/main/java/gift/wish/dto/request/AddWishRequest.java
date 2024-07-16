<<<<<<<< HEAD:src/main/java/gift/wish/dto/request/AddWishRequest.java
package gift.wish.dto.request;
========
package gift.dto.wish.request;
>>>>>>>> 1304db5a (refactor(dto): DTO 디렉토리 구조 변경):src/main/java/gift/dto/wish/request/AddWishRequest.java

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AddWishRequest(
    @NotNull Long productId,
    @Positive(message = "상품 수량은 0보다 큰 수이어야 합니다.") Integer quantity
) {

}
