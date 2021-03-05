package wz.mod.gacha;

import net.fabricmc.api.ModInitializer;

public class CoreModGacha implements ModInitializer {
	
	@Override
	public void onInitialize() {
		WzUtils.CreateConfig();
		ContentBlocks.registerBlocks();
		ContentBlocks.registerBlockItems();
		ContentItems.registerItems();
	}
}
