package com.migu.schedule;


import com.migu.schedule.info.TaskExInfo;
import com.migu.schedule.info.TaskInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
        // TODO 2、如果该服务节点正运行任务，则将运行的任务移到任务挂起队列中，等待调度程序调度。
        store.removeRunning(nodeId);
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
        //TODO 将在挂起队列中的任务 或 运行在服务节点上的任务删除。
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

        //挂起任务调度
        List<TaskInfo> tasks = new ArrayList<>();
        if (store.existsTask()) {
            Map<Integer, TaskExInfo> storeTasks = store.getTasks();
            for(TaskExInfo info : storeTasks.values()) {
                TaskInfo task = new TaskInfo();
                tasks.add(task);
            }

        }

        //运行任务调度

//        E013;//成功
//        E014;//无合适

        return E002;
    }


    public int queryTaskStatus(List<TaskInfo> tasks) {
        // 查询任务状态列表
        if (tasks == null) {
            return E016;
        }

        TreeMap<Integer, TaskInfo> res = new TreeMap<>();
        for (TaskInfo task : tasks) {
            if (store.containsTask(task.getTaskId())) {
                task.setNodeId(-1);
            }
            res.put(task.getTaskId(), task);
        }
        tasks.clear();
        tasks.addAll(res.values());
        return E015;
    }

}
