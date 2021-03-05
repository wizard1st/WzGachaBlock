package wz.mod.gacha;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.PotionItem;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.text.BaseText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import wz.mod.gacha.block.GachaItemType;

public class WzUtils {

	public static String MOD_ID = "wzgacha";
	public static String MINECRAFT_PATH = "wzmodconfig";
	public static String GACHA_ITEM_PATH = "gacha";
	public static String[] GACHA_FILE_NAME = { "kak.txt", "low.txt", "mid.txt", "high.txt", "max.txt" };

	public static void CreateConfig() {
		try {
			Path path = Paths.get(MINECRAFT_PATH);
			if (!Files.exists(path)) {
				Files.createDirectories(path);
			}
			Path gacha_path = Paths.get(MINECRAFT_PATH + "\\" + GACHA_ITEM_PATH);
			if (!Files.exists(gacha_path)) {
				Files.createDirectories(gacha_path);
			}
			for (String s : GACHA_FILE_NAME) {
				File configFile = new File(WzUtils.MINECRAFT_PATH + "\\" + WzUtils.GACHA_ITEM_PATH + "\\" + s);
				if (!configFile.exists()) {
					configFile.createNewFile();
				}
			}
		} catch (IOException e) {
		}
	}

	public static List<ItemStack> stackFromFile(String fileName) {
		List<ItemStack> list = new ArrayList<ItemStack>();
		Path gacha_path = Paths.get(MINECRAFT_PATH + "\\" + GACHA_ITEM_PATH + "\\" + fileName);

		if (Files.exists(gacha_path)) {
			File file = new File(gacha_path.toUri());
			Scanner reader = null;
			try {
				reader = new Scanner(file);
				while (reader.hasNextLine()) {
					ItemStack stackItem = new ItemStack(Items.AIR);
					String line = reader.nextLine();
					String[] item = line.split("___");
					GachaItemType type = GachaItemType.NONE;
					if (item[0].split(":")[1].equals("item")) {
						type = GachaItemType.ITEM;
					} else if ((item[0].split(":")[1].equals("potion"))) {
						type = GachaItemType.POTION;
					}
					stackItem = createItem(type, item[1], item[2]);
					stackItem.setCount(Integer.valueOf(item[3]));
					list.add(stackItem);
				}
				reader.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				reader.close();
				list.add(new ItemStack(Items.AIR));
			}
		}
		return list;
	}

	private static ItemStack createItem(GachaItemType type, String id, String enchant) {
		ItemStack retItem = new ItemStack(Items.AIR);
		enchant = enchant.replace("{", "").replace("}", "");
		Item item = Registry.ITEM.get(new Identifier(id));
		HashMap<Enchantment, Integer> enchantListLevel = new HashMap<>();
		switch (type) {
		case ITEM:
			if (!enchant.trim().equals("")) {
				String[] ench = enchant.split("_,_");
				for (String s : ench) {
					String[] en = s.replace("[", "").replace("]", "").split(",");
					String eId = en[0].split(":")[1];
					String eLv = en[1].split(":")[1];
					Enchantment enchantData = Registry.ENCHANTMENT.get(new Identifier(eId));
					enchantListLevel.put(enchantData, Integer.valueOf(eLv));
				}
			}
			retItem = new ItemStack(item);
			if (!enchantListLevel.isEmpty()) {
				for (Entry<Enchantment, Integer> entry : enchantListLevel.entrySet()) {
					retItem.addEnchantment(entry.getKey(), entry.getValue());
				}
			}

			break;
		case POTION:
			if (!enchant.trim().equals("")) {
				List<StatusEffectInstance> effList = new ArrayList<StatusEffectInstance>();
				String[] eList = enchant.split("_,_");
				for (String s : eList) {
					String[] p = s.replace("[", "").replace("]", "").split(",");
					
					StatusEffect eff = Registry.STATUS_EFFECT.get(new Identifier(p[0].split(":")[1]));
					int duration = Integer.valueOf(p[1].split(":")[1]);
					int amplifier = Integer.valueOf(p[2].split(":")[1]);
					StatusEffectInstance effInstance = new StatusEffectInstance(eff, duration, amplifier);
					effList.add(effInstance);
				}
				retItem = new ItemStack(item);
				retItem.setCustomName(Text.of("GACHA POTION"));
				retItem = PotionUtil.setCustomPotionEffects(retItem, effList);
			}
			break;
		default:
			break;
		}
		return retItem;
	}

}
