package com.papack.survivalstrategy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BooleanSupplier;

public class TickScheduler {

    private static final List<ScheduledTask> scheduledTasks = new LinkedList<>();
    private static final List<ConditionTask> conditionTasks = new LinkedList<>();

    private static final List<ScheduledTask> scheduledTasksToAdd = new LinkedList<>();
    private static final List<ConditionTask> conditionTasksToAdd = new LinkedList<>();

    // Registering delayed tasks
    // 遅延タスクの登録
    public static void setDelayTask(int ticksLater, Runnable task) {
        synchronized (scheduledTasksToAdd) {
            scheduledTasksToAdd.add(new ScheduledTask(ticksLater, task));
        }
    }

    // Registering a conditional task (without a key)
    // 条件付きタスクの登録（キーなし）
    public static void setConditionTask(BooleanSupplier condition, Runnable task) {
        setConditionTaskWithKey(condition, task, null);
    }

    // Registering conditional tasks (with keys)
    // 条件付きタスクの登録（キー付き）
    public static void setConditionTaskWithKey(BooleanSupplier condition, Runnable task, Object key) {
        synchronized (conditionTasksToAdd) {
            conditionTasksToAdd.add(new ConditionTask(condition, task, key));
        }
    }

    // Deleting conditional tasks (key specification)
    // 条件付きタスクの削除（キー指定）
    public static void cancelPendingConditionTask(Object key) {
        synchronized (conditionTasksToAdd) {
            conditionTasksToAdd.removeIf(task -> key != null && key.equals(task.key));
        }
        synchronized (conditionTasks) {
            conditionTasks.removeIf(task -> key != null && key.equals(task.key));
        }
    }

    // Are conditional tasks still scheduled? (key specification)
    // 条件付きタスクはまだスケジュールされていますか? （キー指定）
    public static boolean isPendingConditionTaskWithKey(Object key) {
        synchronized (conditionTasksToAdd) {
            for (ConditionTask task : conditionTasksToAdd) {
                if (key.equals(task.key)) return true;
            }
        }
        synchronized (conditionTasks) {
            for (ConditionTask task : conditionTasks) {
                if (key.equals(task.key)) return true;
            }
        }
        return false;
    }

    // conditionTasksToAdd と conditionTasks の両方をチェック
    // 指定したキー以外の条件付きタスクが存在するか？
    public static boolean hasOtherConditionTasks(Object excludedKey) {
        synchronized (conditionTasksToAdd) {
            for (ConditionTask task : conditionTasksToAdd) {
                if (excludedKey == null || !excludedKey.equals(task.key)) {
                    return true;
                }
            }
        }
        synchronized (conditionTasks) {
            for (ConditionTask task : conditionTasks) {
                if (excludedKey == null || !excludedKey.equals(task.key)) {
                    return true;
                }
            }
        }
        return false;
    }

    // Are there any uncompleted condition tasks (regardless of key)?
    // 未完了のコンディションタスクがあるか（キー問わず）
    public static boolean hasPendingConditionTasks() {
        synchronized (conditionTasksToAdd) {
            if (!conditionTasksToAdd.isEmpty()) return true;
        }
        synchronized (conditionTasks) {
            if (!conditionTasks.isEmpty()) return true;
        }
        return false;
    }

    // Judgment is made including tasks that have not yet been executed even though the condition has already been met
// 条件がすでに成立しているが未実行のタスクも含めて判定
    public static boolean hasPendingOrReadyConditionTasks() {
        // 追加待ちのタスク
        synchronized (conditionTasksToAdd) {
            if (!conditionTasksToAdd.isEmpty()) {
                return true;
            }
        }
        // すでに追加済みのタスク（条件成立済みを含む）
        synchronized (conditionTasks) {
            if (!conditionTasks.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    // Are there any delayed tasks (not yet executed or scheduled)?
    // 遅延したタスク（まだ実行またはスケジュールされていないタスク）はありますか?
    // Tasks scheduled to be executed immediately (registered with ticksLater = 0) will be excluded.
    // すぐに実行するようにスケジュールされたタスク (ticksLater = 0 で登録) は除外されます。
    public static boolean hasPendingDelayedTasks() {
        // Are there any delayed tasks to be added?
        synchronized (scheduledTasksToAdd) {
            if (!scheduledTasksToAdd.isEmpty()) return true;
        }
        // Are there any delayed tasks already queued (ticksRemaining > 0)?
        synchronized (scheduledTasks) {
            for (ScheduledTask t : scheduledTasks) {
                if (t.ticksRemaining > 0) return true;
            }
        }
        return false;
    }

    // Are there any delayed tasks (including those scheduled to run immediately at 0 tick)?
    // 遅延されたタスク（0 ティックで即時実行するようにスケジュールされているものを含む）はありますか?
    public static boolean hasPendingOrReadyDelayedTasks() {
        synchronized (scheduledTasksToAdd) {
            if (!scheduledTasksToAdd.isEmpty()) return true;
        }
        synchronized (scheduledTasks) {
            return !scheduledTasks.isEmpty();
        }
    }

    // Delete all conditional tasks
    // すべての条件付きタスクを削除する
    public static void clearAllConditionTasks() {
        synchronized (conditionTasksToAdd) {
            conditionTasksToAdd.clear();
        }
        synchronized (conditionTasks) {
            conditionTasks.clear();
        }
    }

    public static void tick() {

        synchronized (scheduledTasksToAdd) {
            scheduledTasks.addAll(scheduledTasksToAdd);
            scheduledTasksToAdd.clear();
        }

        synchronized (conditionTasksToAdd) {
            synchronized (conditionTasks) {
                conditionTasks.addAll(conditionTasksToAdd);
            }
            conditionTasksToAdd.clear();
        }

        // Processing delayed tasks
        synchronized (scheduledTasks) {
            Iterator<ScheduledTask> iterator = scheduledTasks.iterator();
            while (iterator.hasNext()) {
                ScheduledTask task = iterator.next();
                task.ticksRemaining--;
                if (task.ticksRemaining <= 0) {
                    task.task.run();
                    iterator.remove();
                }
            }
        }

        // Safe processing of conditional tasks
        List<ConditionTask> toRun = new ArrayList<>();
        synchronized (conditionTasks) {
            for (ConditionTask task : conditionTasks) {
                if (task.condition.getAsBoolean()) {
                    toRun.add(task);
                }
            }
        }
        for (ConditionTask task : toRun) {
            task.task.run();
            synchronized (conditionTasks) {
                conditionTasks.remove(task);
            }
        }
    }


    // Delayed Task Class
    private static class ScheduledTask {
        int ticksRemaining;
        Runnable task;

        ScheduledTask(int ticksRemaining, Runnable task) {
            this.ticksRemaining = ticksRemaining;
            this.task = task;
        }
    }

    // Conditional task class (with key)
    private static class ConditionTask {
        BooleanSupplier condition;
        Runnable task;
        Object key;

        ConditionTask(BooleanSupplier condition, Runnable task, Object key) {
            this.condition = condition;
            this.task = task;
            this.key = key;
        }
    }
}
