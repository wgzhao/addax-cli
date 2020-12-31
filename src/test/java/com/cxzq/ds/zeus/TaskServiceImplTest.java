package com.cxzq.ds.zeus;

import com.cxzq.ds.zeus.server.TaskService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskServiceImplTest
{
    @Autowired
    private TaskService taskService;

    @org.junit.Test
    public void colInfoStorageTask()
    {
        taskService.colInfoStorageTask("20201224151209658");
    }

    @org.junit.Test
    public void createTableTask()
    {
    }
}