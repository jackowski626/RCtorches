package io.github.jackowski626.rctorches.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Environment(EnvType.CLIENT)
@Mixin(net.minecraft.item.PickaxeItem.class)
public class TorchMixin {
    /*@Inject(method="PickaxeItem", at=@At("HEAD"))
    public void greetings(CallbackInfo c) {
        System.out.println("Mixined pickaxe");
    }*/


    private static final MinecraftClient client = MinecraftClient.getInstance();


    public ActionResult useOnBlock(ItemUsageContext context) {

        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();
        if (context.getSide() != Direction.DOWN /*&& world.getBlockState(blockPos.up()).isAir()*/) {
            /*BlockPos pos = blockPos.up();
            if (context.getSide() == Direction.EAST) {
                pos = blockPos.east();
            } else if (context.getSide() == Direction.SOUTH) {
                pos = blockPos.south();
            } else if (context.getSide() == Direction.WEST) {
                pos = blockPos.west();
            } else if (context.getSide() == Direction.NORTH) {
                pos = blockPos.north();
            }
            world.setBlockState(blockPos, Blocks.TORCH.getDefaultState());*/
            Inventory inv = context.getPlayer().inventory;
            for (int i = 0; i < inv.size(); i ++) {
                if (inv.getStack(i).getItem() == Items.TORCH) {
                    System.out.println("player has torches");
                    //inv.getStack(i).useOnBlock(context);
                    //inv.getStack(i).getItem().

                    //client.interactionManager.pickFromInventory(i);
                    if (context.getPlayer().inventory.main.size() != 36) {
                        System.out.println("Unsupported modification to inventory size");
                        return ActionResult.PASS;
                    }
                    ItemStack stack = context.getPlayer().inventory.getMainHandStack().copy();
                    System.out.println(stack.getName());
                    context.getPlayer().inventory.setStack(context.getPlayer().inventory.selectedSlot, Items.TORCH.getDefaultStack());
                    context.getPlayer().inventory.getMainHandStack().useOnBlock(context);
                    context.getPlayer().inventory.setStack(context.getPlayer().inventory.selectedSlot, stack);
                    context.getPlayer().inventory.removeStack(i, 1);
                    return ActionResult.success(true);
                }
            }
        }
        System.out.println("right clicked, " + context.getHand().name());

        return ActionResult.PASS;
    }
}
