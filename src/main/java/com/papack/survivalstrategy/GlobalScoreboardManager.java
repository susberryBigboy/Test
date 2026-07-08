package com.papack.survivalstrategy;

import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.ScoreHolder;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.DisplaySlot;
import net.minecraft.world.scores.criteria.ObjectiveCriteria;

public class GlobalScoreboardManager {

    private static final String OBJECTIVE_NAME = "custom_player_data";
    private static Objective objective;

    /**
     * スコアボードの初期化（サーバー起動時などに1回だけ呼ぶ）
     */
    public static void initScoreboard(MinecraftServer server) {
        Scoreboard scoreboard = server.getScoreboard();

        // すでに存在するか確認し、なければ新規作成
        objective = scoreboard.getObjective(OBJECTIVE_NAME);
        if (objective == null) {
            objective = scoreboard.addObjective(
                    OBJECTIVE_NAME,
                    ObjectiveCriteria.DUMMY,
                    Component.literal("§e【Remaining Time】"), // サイドバーのタイトル
                    ObjectiveCriteria.RenderType.INTEGER,
                    false, // 自動で初期化するかどうか
                    null   // ナンバーフォーマット（デフォルト）
            );
        }

        // 作成した目的を「サイドバー」に表示するよう設定
        scoreboard.setDisplayObjective(DisplaySlot.SIDEBAR, objective);
    }

    /**
     * 特定のプレイヤーのデータを更新する
     * （全プレイヤーの画面にリアルタイムで反映されます）
     */
    public static void updatePlayerData(ServerPlayer player, int newValue) {

        Scoreboard scoreboard = player.level().getServer().getScoreboard();

        if (objective != null) {
            scoreboard.getOrCreatePlayerScore(ScoreHolder.forNameOnly(player.getScoreboardName()), objective)
                    .set(newValue);
        }
    }

    public static void updatePlayerData(IModPropertiesServerPlayer iPlayer, int newValue) {

        if (iPlayer instanceof ServerPlayer serverPlayer) {
            updatePlayerData(serverPlayer, newValue);
        }
    }


    /**
     * サーバーからスコアボード自体を完全に削除したい場合
     */
    public static void removeScoreboard(MinecraftServer server) {
        Scoreboard scoreboard = server.getScoreboard();
        Objective obj = scoreboard.getObjective(OBJECTIVE_NAME);
        if (obj != null) {
            scoreboard.removeObjective(obj);
        }
    }
}