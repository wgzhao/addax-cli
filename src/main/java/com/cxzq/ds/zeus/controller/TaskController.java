package com.cxzq.ds.zeus.controller;

import com.cxzq.ds.zeus.config.ServerResponse;
import com.cxzq.ds.zeus.server.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yangkai
 */
@RestController
@RequestMapping("/task")
public class TaskController
{
    @Autowired
    private AsyncManage asyncManage;

    @GetMapping(value = "readSource")
    public ServerResponse<String> readingSourceTask(String mainTaskId)
    {
        asyncManage.asyncMethod(mainTaskId);
        return  ServerResponse.createBySuccessMessage("请求成功");
    }
}

@Component
class AsyncManage{
    @Autowired
    private TaskService taskService;
    @Async
    public void asyncMethod(String mainTaskId){
        taskService.colInfoStorageTask(mainTaskId);
    }
}