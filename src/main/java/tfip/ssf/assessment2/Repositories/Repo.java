package tfip.ssf.assessment2.Repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class Repo {
    
    @Autowired @Qualifier("my-redis")
	private RedisTemplate<String, String> redisTemplate;
    
}
