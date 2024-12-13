package vttp.batch5.ssf.noticeboard.services;

import jakarta.json.Json;
import jakarta.json.JsonObject;

import java.io.StringReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import vttp.batch5.ssf.noticeboard.models.Notice;
import vttp.batch5.ssf.noticeboard.repositories.NoticeRepository;

@Service
public class NoticeService {

	RestTemplate restTemplate = new RestTemplate();

	@Autowired
	NoticeRepository noticeRepo;

    @Value("${server.url}")
    private String url;

	// TODO: Task 3
	// You can change the signature of this method by adding any number of parameters
	// and return any type
    public JsonObject postToNoticeServer(Notice notice) {
        JsonObject jsonNotice = Json.createObjectBuilder()
									.add("title", notice.getTitle())
									.add("poster", notice.getPoster())
									.add("postDate", notice.getPostDate().toEpochDay() * 86400000)
									.add("categories", Json.createArrayBuilder(notice.getCategories()))
									.add("text", notice.getText())
									.build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> body = new HttpEntity<>(jsonNotice.toString(), headers);

        try {
			System.out.println(body);
            String response = restTemplate.exchange(url, HttpMethod.POST, body, String.class).getBody();
			System.out.println("===================came here 2=======================");
			JsonObject jsonResponse = Json.createReader(new StringReader(response)).readObject();
			String id = jsonResponse.getString("id");
			noticeRepo.insertNotices(id, jsonResponse);
            System.out.println("=====================Response from API: " + jsonResponse);
			return jsonResponse;
        } catch (RestClientException e) {
			e.printStackTrace();
			JsonObject errorResponse = Json.createObjectBuilder()
										.add("message", e.getMessage())
										.build();
			return errorResponse;
        }
    }
	public boolean helth() {
		return noticeRepo.isHelth();
	}

}
