package dat3.openai_demo.repository;

import dat3.openai_demo.entity.JokeCache;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JokeCacheRepository extends JpaRepository<JokeCache, String> {
}

