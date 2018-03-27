package com.spacerovka.tryelastic;

import com.spacerovka.tryelastic.model.Article;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TryelasticApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void testInsert() {
		HttpEntity<String> article = getStringHttpEntity();
		ResponseEntity<Article> response = this.restTemplate.postForEntity("/articles", article, Article.class);
		assertEquals("John", response.getBody().getAuthor());
		assertEquals("test", response.getBody().getTitle());
		assertNotNull(response.getBody().getId());
	}

	@Test
	public void testGet() {
		HttpEntity<String> article = getStringHttpEntity();
		ResponseEntity<Article> savedArticle = this.restTemplate.postForEntity("/articles", article, Article.class);
		ResponseEntity<Article> response = this.restTemplate.getForEntity("/articles/" + savedArticle.getBody().getId(), Article.class);
		assertEquals("John", response.getBody().getAuthor());
		assertEquals("test", response.getBody().getTitle());
	}

	@Test
	public void testPut() {
		HttpEntity<String> article = getStringHttpEntity();
		Article savedArticle = this.restTemplate.postForEntity("/articles", article, Article.class).getBody();
		ResponseEntity<Article> updatedArticle = this.restTemplate.exchange("/articles/" + savedArticle.getId(), HttpMethod.PUT, getUpdateHttpEntity(savedArticle.getId()), Article.class);
		assertEquals("Nicolas", updatedArticle.getBody().getAuthor());
		assertEquals("test", updatedArticle.getBody().getTitle());
	}

	private HttpEntity<String> getStringHttpEntity() {
		String requestJson = "{\"title\":\"test\",\"author\":\"John\"}";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return new HttpEntity<String>(requestJson,headers);
	}

	private HttpEntity<String> getUpdateHttpEntity(String id) {
		String requestJson = "{\"id\":\"" + id + "\",\"title\":\"test\",\"author\":\"Nicolas\"}";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return new HttpEntity<String>(requestJson,headers);
	}

}
