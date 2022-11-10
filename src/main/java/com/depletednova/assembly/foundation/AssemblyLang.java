package com.depletednova.assembly.foundation;

import com.depletednova.assembly.Assembly;
import com.simibubi.create.foundation.utility.Lang;
import com.simibubi.create.foundation.utility.LangBuilder;
import net.minecraft.network.chat.TextComponent;

public class AssemblyLang {
	public static LangBuilder lang() { return Lang.builder(Assembly.MOD_ID); }
	public static LangBuilder translate(String key, Object... args) { return lang().translate(key, args); }
	
	public static TextComponent translateText(String key) { return new TextComponent(Assembly.MOD_ID + key); }
}
