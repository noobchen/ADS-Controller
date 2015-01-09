package com.ads.cm.util.weight;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-3-26
 * Time: 下午4:14
 * To change this template use File | Settings | File Templates.
 */
public class TaskWeightUtil {

    public static int getWeightIndex(List<HashMap<String, Integer>> tasks) {

        int sumWeight = 0;

        int[] taskWeights = new int[tasks.size()];


        for (int i = 0; i < tasks.size(); i++) {
            taskWeights[i] = tasks.get(i).get("task_weights");
            sumWeight += taskWeights[i];
        }

        Random random = new Random();
        int temp = random.nextInt(sumWeight);

        int sum = 0;
        for (int i = 0; i < taskWeights.length; i++) {
            sum += taskWeights[i];
            if (temp <= sum) {

                return i;

            }


        }

        return 0;
    }
}
