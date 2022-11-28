package com.depletednova.assembly.content.logistics.instructor;

import com.depletednova.assembly.registry.AContainerTypes;
import com.simibubi.create.foundation.gui.container.ContainerBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

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
	protected void addSlots() {
		this.addSlot(new SlotItemHandler(this.instructorInventory, 0, 151, 115));
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
}
