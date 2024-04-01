package com.deividesantos.todosimple.repositories;

import com.deividesantos.todosimple.models.Task;
import com.deividesantos.todosimple.models.TaskProjection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {

    List<TaskProjection> findByUser_id(Long id);

}
