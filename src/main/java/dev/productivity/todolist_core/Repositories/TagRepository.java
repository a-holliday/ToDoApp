package dev.productivity.todolist_core.Repositories;

import dev.productivity.todolist_core.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepository  extends JpaRepository<Tag, Long> {
    Optional<Tag> findByName(String name);
    void deleteByName(String name);
}
