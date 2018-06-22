package com.migu.schedule;


import com.migu.schedule.info.TaskExInfo;
import com.migu.schedule.info.TaskInfo;

import java.util.*;

import static com.migu.schedule.constants.ReturnCodeKeys.*;

/*
 *类名和方法不能修改
 */
@SuppressWarnings("ALL")
public class Schedule {

    private static final ScheduleStore store = new ScheduleStore();

    public int init() {
        // 系统初始化
        store.clear();
        return E001;
    }


    public int registerNode(int nodeId) {
        // 服务节点注册
        if (nodeId <= 0) {
            return E004;
        }

        if (store.containsNode(nodeId)) {
            return E005;
        }

        store.addNode(nodeId);
        return E003;
    }

    public int unregisterNode(int nodeId) {
        // 服务节点注销
        if (nodeId <= 0) {
            return E004;
        }

        if (!store.containsNode(nodeId)) {
            return E007;
        }
        store.removeNode(nodeId);
        return E006;
    }


    public int addTask(int taskId, int consumption) {
        // 添加任务
        if (taskId <= 0) {
            return E009;
        }

        if (store.containsTask(taskId)) {
            return E010;
        }

        store.addTask(taskId, consumption);
        return E008;
    }


    public int deleteTask(int taskId) {
        // 删除任务
        if (taskId <= 0) {
            return E009;
        }
        if (!store.containsTask(taskId)) {
            return E012;
        }

        store.removeTask(taskId);
        return E011;
    }


    public int scheduleTask(int threshold) {
        // 任务调度
        if (threshold <= 0) {
            return E002;
        }
        store.setThreshold(threshold);

        //挂起任务
        Queue<TaskExInfo> tasks = new PriorityQueue<>();
        if (store.existsTask()) {
            Map<Integer, TaskExInfo> storeTasks = store.getTasks();
            for (TaskExInfo info : storeTasks.values()) {
                tasks.add(info);
            }
        }

        Integer nodeId = store.findMinNode();
        List<TaskExInfo> running = store.getRunning(nodeId);

        //任务调度
        boolean suc = false;
        while (!suc || !tasks.isEmpty()) {
            TaskExInfo task = tasks.poll();
            Integer minNode = store.findMinNode();
            List<TaskExInfo> taskExInfos = store.getRunning(minNode);
            task.setNodeId(minNode);
            taskExInfos.add(task);
            suc = store.validate(nodeId);

            store.putSames(task);

            if (!suc && tasks.isEmpty()) {
                return E014;
            }
        }

        store.sortSames();
        tasks.clear();

        return E013;
    }


    public int queryTaskStatus(List<TaskInfo> tasks) {
        // 查询任务状态列表
        if (tasks == null) {
            return E016;
        }

        List<TaskExInfo> res = new ArrayList<>();
        tasks.clear();

        store.addAllRunning(tasks);

        tasks.sort(store.comparator);

        return E015;
    }

}
