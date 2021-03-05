package wz.mod.gacha;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ContentItems {
	
	public static Item KAK_COIN = new Item( new Item.Settings().group(ItemGroup.MISC));
	public static Item LOW_COIN =  new Item( new Item.Settings().group(ItemGroup.MISC));
	public static Item MID_COIN =  new Item( new Item.Settings().group(ItemGroup.MISC));
	public static Item HIGH_COIN = new Item( new Item.Settings().group(ItemGroup.MISC));
	public static Item MAX_COIN =  new Item( new Item.Settings().group(ItemGroup.MISC));
	
	public static void registerItems() {
		Registry.register(Registry.ITEM, new Identifier(WzUtils.MOD_ID, "kak_coin"), KAK_COIN);
		Registry.register(Registry.ITEM, new Identifier(WzUtils.MOD_ID, "low_coin"), LOW_COIN);
		Registry.register(Registry.ITEM, new Identifier(WzUtils.MOD_ID, "mid_coin"), MID_COIN);
		Registry.register(Registry.ITEM, new Identifier(WzUtils.MOD_ID, "high_coin"), HIGH_COIN);
		Registry.register(Registry.ITEM, new Identifier(WzUtils.MOD_ID, "max_coin"), MAX_COIN);
	}
	
}
