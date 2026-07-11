package com.papack.survivalstrategy.debug;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.permissions.Permissions;

public class DevCommand {

    public static void developmentCommands(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext ignore, Commands.CommandSelection ignore1) {
        dispatcher.register(Commands.literal("ssreset")

                // Permission level
                .requires(source -> source.permissions().hasPermission(Permissions.COMMANDS_MODERATOR))

                // Add the targetPlayer list to the arguments
                .then(Commands.argument("target", EntityArgument.player()))

                //
                .executes(context -> {
                            ServerPlayer targetPlayer = EntityArgument.getPlayer(context, "target");
                            return resetPlayerDataPool(context, targetPlayer);
                        }
                ));
    }


    private static int resetPlayerDataPool(CommandContext<CommandSourceStack> ignore, ServerPlayer serverPlayer) {
        serverPlayer.sendSystemMessage(Component.literal("survivalstrategy: reset"), false);
        return 1;
    }
}