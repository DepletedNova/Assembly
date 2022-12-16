package com.depletednova.assembly.content.logistics.instructor;

import com.depletednova.assembly.Assembly;
import com.depletednova.assembly.registry.AContainerTypes;
import com.simibubi.create.foundation.gui.container.ContainerBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiConsumer;

public class InstructorContainer extends ContainerBase<InstructorTileEntity> {
	public ItemStackHandler instructorInventory;
	
	public InstructorContainer(MenuType<?> type, int id, Inventory inv, FriendlyByteBuf extraData) {
		super(type, id, inv, extraData);
	}
	
	public InstructorContainer(MenuType<?> type, int id, Inventory inv, InstructorTileEntity contentHolder) {
		super(type, id, inv, contentHolder);
	}
	
	public static InstructorContainer create(int id, Inventory inv, InstructorTileEntity te) {
		return new InstructorContainer(AContainerTypes.INSTRUCTOR.get(), id, inv, te);
	}
	
	@Override
	protected InstructorTileEntity createOnClient(FriendlyByteBuf extraData) {
		BlockPos readBlockPos = extraData.readBlockPos();
		CompoundTag readNbt = extraData.readNbt();
		
		ClientLevel world = Minecraft.getInstance().level;
		BlockEntity tileEntity = world.getBlockEntity(readBlockPos);
		if (tileEntity instanceof InstructorTileEntity instructor) {
			instructor.readClient(readNbt);
			return instructor;
		}
		
		return null;
	}
	
	@Override
	protected void initAndReadInventory(InstructorTileEntity contentHolder) {
		this.instructorInventory = new ItemStackHandler(1);
	}
	
	@Override
	public ItemStack quickMoveStack(Player player, int slot) {
		return ItemStack.EMPTY;
	}
	
	@Override
	protected void addSlots() {
		this.addSlot(new InstructorHandlerSlot(this.instructorInventory, 0, 151, 115));
		for (InstructorTabRegistry.TabRegistry<? extends AbstractInstructorTab> registry : InstructorTabRegistry.registries) {
			registry.acceptSlots(this);
		}
	}
	
	@Override
	public Slot addSlot(Slot slot) {
		return super.addSlot(slot);
	}
	
	@Override
	protected void saveData(InstructorTileEntity contentHolder) {
	
	}
	
	protected static class InstructorHandlerSlot extends SlotItemHandler {
		public InstructorHandlerSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
			super(itemHandler, index, xPosition, yPosition);
		}
		
		@Override
		public boolean mayPlace(ItemStack stack) {
			return (stack.getItem() instanceof IInstructiveItem) && super.mayPlace(stack);
		}
		
		@Override
		public ItemStack safeInsert(ItemStack stack, int stackLimit) {
			
			return super.safeInsert(stack, stackLimit);
		}
		
		@Override
		public void onTake(Player player, ItemStack stack) {
			
			super.onTake(player, stack);
		}
	}
}
