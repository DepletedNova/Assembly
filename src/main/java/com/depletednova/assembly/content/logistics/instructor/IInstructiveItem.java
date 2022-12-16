package com.depletednova.assembly.content.logistics.instructor;

import com.jozufozu.flywheel.util.NonNullSupplier;

public interface IInstructiveItem {
	NonNullSupplier<? extends AbstractInstructionHandler> getHandler();
}
