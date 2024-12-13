package vttp.batch5.ssf.noticeboard.repositories;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import jakarta.json.JsonObject;

@Repository
public class NoticeRepository {

	// TODO: Task 4
	// You can change the signature of this method by adding any number of parameters
	// and return any type
	// 
	/*
	 * Write the redis-cli command that you use in this method in the comment. 
	 * For example if this method deletes a field from a hash, then write the following
	 * redis-cli command 
	 * 	hdel myhashmap a_key
	 *
	 *
	 */

	// SET notice:<noticeId> '{"title":"Sample","poster":"rnadm@gmail.com","postDate":123456789,"Categories":["sports","meeting"],"text":"text"}'

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

	public void insertNotices(String noticeId, JsonObject jsonPayload) {
		System.out.println(noticeId);
        redisTemplate.opsForValue().set(noticeId, jsonPayload.toString());
	}

	public boolean isHelth() {
        try {
            System.out.println("======= rnadom key: ======"+redisTemplate.randomKey());
            return true; // Healthy
        } catch (Exception e) {
            return false; // Unhealthy
        }
	}

}
