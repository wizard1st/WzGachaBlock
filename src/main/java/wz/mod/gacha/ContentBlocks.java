package wz.mod.gacha;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import wz.mod.gacha.block.GachaBlock;

public class ContentBlocks {
	public static final Block GACHA_BLOCK = new GachaBlock(
			FabricBlockSettings
			.of(Material.METAL)
			.breakByTool(FabricToolTags.PICKAXES,0)
			.strength(1f)
			.sounds(BlockSoundGroup.METAL)
			.luminance(2));
	
	public static final BlockItem GACHA_BLOCKITEM = new BlockItem(GACHA_BLOCK,new FabricItemSettings().group(ItemGroup.MISC));
	
	public static void registerBlockItems() {
		Registry.register(Registry.ITEM, new Identifier(WzUtils.MOD_ID, "gacha_block"), GACHA_BLOCKITEM);
	}
	public static void registerBlocks() {
		Registry.register(Registry.BLOCK, new Identifier(WzUtils.MOD_ID,"gacha_block"), GACHA_BLOCK);
	}
}
