package justfatlard.emerald_armor;

import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Main implements ModInitializer {
	private final static String MOD_ID = "emerald-armor-justfatlard";

	public final static EmeraldArmorMaterial EMERALD_ARMOR_MATERIAL = new EmeraldArmorMaterial();

	public final static ArmorItem EMERALD_HELMET = new ArmorItem(Main.EMERALD_ARMOR_MATERIAL, EquipmentSlot.HEAD, new Item.Settings().maxCount(1).group(ItemGroup.COMBAT));
	public final static ArmorItem EMERALD_CHESTPLATE = new ArmorItem(Main.EMERALD_ARMOR_MATERIAL, EquipmentSlot.CHEST, new Item.Settings().maxCount(1).group(ItemGroup.COMBAT));
	public final static ArmorItem EMERALD_LEGGINGS = new ArmorItem(Main.EMERALD_ARMOR_MATERIAL, EquipmentSlot.LEGS, new Item.Settings().maxCount(1).group(ItemGroup.COMBAT));
	public final static ArmorItem EMERALD_BOOTS = new ArmorItem(Main.EMERALD_ARMOR_MATERIAL, EquipmentSlot.FEET, new Item.Settings().maxCount(1).group(ItemGroup.COMBAT));

	@Override
	public void onInitialize(){
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "emerald_helmet"), EMERALD_HELMET);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "emerald_chestplate"), EMERALD_CHESTPLATE);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "emerald_leggings"), EMERALD_LEGGINGS);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "emerald_boots"), EMERALD_BOOTS);
	}
}
