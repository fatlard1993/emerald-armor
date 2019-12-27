package justfatlard.emerald_armor;

import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class EmeraldArmor implements ModInitializer {
	private final static String MOD_ID = "emerald-armor-justfatlard";

	public final static EmeraldArmorMaterial EMERALD_ARMOR_MATERIAL = new EmeraldArmorMaterial();

	public final static EmeraldArmorItem EMERALD_HELMET_ITEM = new EmeraldArmorItem(EquipmentSlot.HEAD);
	public final static EmeraldArmorItem EMERALD_CHESTPLATE_ITEM = new EmeraldArmorItem(EquipmentSlot.CHEST);
	public final static EmeraldArmorItem EMERALD_LEGGINGS_ITEM = new EmeraldArmorItem(EquipmentSlot.LEGS);
	public final static EmeraldArmorItem EMERALD_BOOTS_ITEM = new EmeraldArmorItem(EquipmentSlot.FEET);

	@Override
	public void onInitialize(){
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "emerald_helmet"), EMERALD_HELMET_ITEM);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "emerald_chestplate"), EMERALD_CHESTPLATE_ITEM);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "emerald_leggings"), EMERALD_LEGGINGS_ITEM);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "emerald_boots"), EMERALD_BOOTS_ITEM);
	}
}
