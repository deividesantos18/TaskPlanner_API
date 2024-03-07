package com.deividesantos.todosimple.services;

import com.deividesantos.todosimple.models.Task;
import com.deividesantos.todosimple.models.User;
import com.deividesantos.todosimple.repositories.TaskRepository;
import com.deividesantos.todosimple.services.exception.DataBindingViolationException;
import com.deividesantos.todosimple.services.exception.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserService userService;

    public Task findbyid(Long id) {
        Optional<Task> task = this.taskRepository.findById(id);
        return task.orElseThrow(() -> new ObjectNotFoundException("Task não encontrada ! id:" + id +
                "Tipo" + Task.class.getName()));
    }


    public List<Task> fidAllByUserid(Long userid){
        List<Task> tasks= this.taskRepository.findByUser_id(userid);
        return tasks;
    }

    @Transactional
    public Task create(Task obj) {
        User user = this.userService.findbyid(obj.getUser().getId());
        obj.setId(null);
        obj.setUser(user);
        obj = this.taskRepository.save(obj);
        return obj;
    }


    @Transactional
    public Task update(Task obj) {
        Task newtask = findbyid(obj.getId());
        newtask.setDescription(obj.getDescription());
        return this.taskRepository.save(newtask);
    }

    public void delete(Long id) {
        findbyid(id);
        try {
            this.taskRepository.deleteById(id);
        } catch (Exception e) {
            throw new DataBindingViolationException("Não é possivel deletar tarefa");
        }

    }
}