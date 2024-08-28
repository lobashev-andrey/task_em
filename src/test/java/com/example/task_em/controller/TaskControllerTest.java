package com.example.task_em.controller;


import com.example.task_em.dto.CommentResponse;
import com.example.task_em.dto.CommentResponseList;
import com.example.task_em.dto.TaskResponse;
import com.example.task_em.dto.TaskResponseList;
import com.example.task_em.entity.Comment;
import com.example.task_em.entity.Task;
import com.example.task_em.entity.User;
import com.example.task_em.filter.TaskFilter;
import com.example.task_em.mapper.TaskMapper;
import com.example.task_em.service.TaskService;
import net.javacrumbs.jsonunit.JsonAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TaskControllerTest extends AbstractTestController {

    @MockBean
    private TaskService taskService;
    @MockBean
    private TaskMapper taskMapper;

    User user1;
    User user2;
    Task task1;
    Task task2;
    Comment comment1;
    Comment comment2;
    List<Task> tasks;
    CommentResponse commentResponse1;
    CommentResponse commentResponse2;
    CommentResponseList commentResponseList;
    TaskResponse taskResponse1;
    TaskResponse taskResponse2;
    TaskResponseList taskResponseList;

    @BeforeEach
    protected void createTestData() {
        user1 = createUser(1L);
        user2 = createUser(2L);
        task1 = createTask(1L, user1, user2);
        task2 = createTask(2L, user2, user1);
        comment1 = createComment(1L, user1, task1, "some_long_text");
        comment2 = createComment(2L, user2, task1, "another_long_text");
        task1.setComments(List.of(comment1, comment2));
        tasks = List.of(task1, task2);
        commentResponse1 = createCommentResponse(1L, user1, task1, "some_long_text");
        commentResponse2 = createCommentResponse(2L, user2, task1, "another_long_text");
        commentResponseList = new CommentResponseList(List.of(commentResponse1, commentResponse2));
        taskResponse1 = createTaskResponse(1L, user1, user2, commentResponseList);
        taskResponse2 = createTaskResponse(2L, user2, user1, new CommentResponseList(new ArrayList<>()));
        taskResponseList = new TaskResponseList(List.of(taskResponse1, taskResponse2));
    }

    @Test
    public void whenFindAll_thenReturnAllTasks() throws Exception{

        Mockito.when(taskService.filterBy(new TaskFilter())).thenReturn(tasks);
        Mockito.when(taskMapper.taskListToResponseList(tasks)).thenReturn(taskResponseList);

        var response = mockMvc.perform(get("/task"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
        response.setCharacterEncoding("UTF-8");
        String actualResponse = response.getContentAsString();

        String expectedResponse = StringTestUtils.readStringFromResource("response/find_all_tasks_response.json");

        Mockito.verify(taskService, Mockito.times(1)).filterBy(new TaskFilter());
        Mockito.verify(taskMapper, Mockito.times(1)).taskListToResponseList(tasks);

        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);
    }

    @Test
    public void whenFindById_thenReturnTaskById() throws Exception {

        Mockito.when(taskService.findById(1L)).thenReturn(task1);
        Mockito.when(taskMapper.taskToResponse(task1)).thenReturn(taskResponse1);

        var response = mockMvc.perform(get("/task/1"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
        response.setCharacterEncoding("UTF-8");
        String actualResponse = response.getContentAsString();

        String expectedResponse = StringTestUtils.readStringFromResource("response/find_task_by_id_response.json");

        Mockito.verify(taskService, Mockito.times(1)).findById(1L);
        Mockito.verify(taskMapper, Mockito.times(1)).taskToResponse(task1);

        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);
    }
}