package com.example.tasks.suite;

import com.example.tasks.controllers.TaskControllerTests;
import com.example.tasks.services.TaskServiceTests;
import org.junit.runner.RunWith;

@RunWith(org.junit.runners.Suite.class)
@org.junit.runners.Suite.SuiteClasses({
        TaskServiceTests.class,
        TaskControllerTests.class
})
public class Suite {
}
