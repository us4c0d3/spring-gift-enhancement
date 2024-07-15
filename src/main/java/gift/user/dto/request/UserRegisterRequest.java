package gift.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record UserRegisterRequest(
    @Email String email,
    @NotEmpty String password
) {

}