package com.my.test;

import com.ads.cm.model.GetTasksModel;
import com.ads.cm.model.RegisterModel;
import org.junit.Test;

/**
 * Created by Administrator on 14-9-3.
 */
public class ExceptionTestRun {
    @Test
    public void testException() throws Exception {
                  new GetTasksModel().getAllTasks();

    }
}
