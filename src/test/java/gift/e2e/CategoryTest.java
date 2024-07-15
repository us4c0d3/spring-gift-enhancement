package gift.e2e;

import static org.assertj.core.api.Assertions.assertThat;

import gift.dto.category.CategoryResponse;
import java.net.URI;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/insert_three_categories.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class CategoryTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void Port() {
        assertThat(port).isNotZero();
    }

    @Test
    @DisplayName("get all categories test")
    void getAllCategoriesTest() {
        // given
        var url = "http://localhost:" + port + "/api/categories";
        var request = new RequestEntity<>(HttpMethod.GET, URI.create(url));

        // when
        var actualResponse = restTemplate.exchange(request, String.class);

        // then
        assertThat(actualResponse.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(actualResponse.getBody()).isNotNull();
    }

    @Test
    @DisplayName("get id 1L category test")
    void getId1LCategoryTest() {
        // given
        var url = "http://localhost:" + port + "/api/categories/1";
        var request = new RequestEntity<>(HttpMethod.GET, URI.create(url));

        // when
        var actualResponse = restTemplate.exchange(request, CategoryResponse.class);

        // then
        assertThat(actualResponse.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(actualResponse.getBody()).isNotNull();
        assertThat(actualResponse.getBody().id()).isEqualTo(1L);
        assertThat(actualResponse.getBody().name()).isEqualTo("Category 1");
    }

    @Test
    @DisplayName("get category by not exist id test")
    void getCategoryByNotExistIdTest() {
        // given
        var url = "http://localhost:" + port + "/api/categories/999";
        var request = new RequestEntity<>(HttpMethod.GET, URI.create(url));

        // when
        var actualResponse = restTemplate.exchange(request, CategoryResponse.class);

        // then
        assertThat(actualResponse.getStatusCode().is4xxClientError()).isTrue();
    }
}
