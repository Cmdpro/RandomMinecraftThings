package com.cmdpro.random_silly_stuff;

import com.cmdpro.databank.worldgui.WorldGuiEntity;
import com.cmdpro.random_silly_stuff.registries.WorldGuiRegistry;
import com.cmdpro.random_silly_stuff.videos.VideoWorldGui;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class SillyCommands {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher){
        dispatcher.register(Commands.literal(RandomSillyStuff.MODID)
                .requires(source -> source.hasPermission(4))
                .then(Commands.literal("spawn_video")
                        .then(Commands.argument("video", StringArgumentType.string())
                            .executes((command) -> {
                                return spawn_video(command);
                            }))
                )
        );
    }
    private static int spawn_video(CommandContext<CommandSourceStack> command){
        if(command.getSource().getEntity() instanceof Player) {
            Player player = (Player) command.getSource().getEntity();
            WorldGuiEntity entity = new WorldGuiEntity(player.level(), player.getEyePosition(), WorldGuiRegistry.VIDEO.get());
            player.level().addFreshEntity(entity);
            if (entity.gui instanceof VideoWorldGui worldGui) {
                worldGui.video = command.getArgument("video", String.class);
            }
        }
        return Command.SINGLE_SUCCESS;
    }
}
