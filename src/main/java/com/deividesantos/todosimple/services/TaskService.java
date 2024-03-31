package com.deividesantos.todosimple.services;

import com.deividesantos.todosimple.Security.UserSpringSecurity;
import com.deividesantos.todosimple.models.Task;
import com.deividesantos.todosimple.models.User;
import com.deividesantos.todosimple.models.Enums.ProfileEnums;
import com.deividesantos.todosimple.repositories.TaskRepository;
import com.deividesantos.todosimple.services.exception.AuthorizationException;
import com.deividesantos.todosimple.services.exception.DataBindingViolationException;
import com.deividesantos.todosimple.services.exception.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserService userService;

    public Task findbyid(Long id) {
        Task task = this.taskRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Task não encontrada ! id:" + id +
                "Tipo" + Task.class.getName()));

        UserSpringSecurity userSpringSecurity= UserService.authenticated();
        if(Objects.isNull(userSpringSecurity)
        || !userSpringSecurity.hasRole(ProfileEnums.ADMIN)&& !hasTask(userSpringSecurity, task))
        throw new AuthorizationException("Acesso Negado");
        return task;        
    }


    public List<Task> fidAllByUserid(Long userid){
        UserSpringSecurity userSpringSecurity=UserService.authenticated();
        if(Objects.isNull(userSpringSecurity))
        throw new AuthorizationException("Acesso Negado!");

        List<Task> tasks= this.taskRepository.findByUser_id(userSpringSecurity.getId());
        return tasks;
    }

    @Transactional
    public Task create(Task obj) {
        UserSpringSecurity userSpringSecurity=UserService.authenticated();
        if(Objects.isNull(userSpringSecurity))
        throw new AuthorizationException("Acesso Negado");

        User user = this.userService.findbyid(userSpringSecurity.getId());
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
    public Boolean hasTask (UserSpringSecurity user,Task task){
     return task.getUser().getId().equals(user.getId());
    }
}