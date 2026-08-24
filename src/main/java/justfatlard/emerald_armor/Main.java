package justfatlard.emerald_armor;

import justfatlard.pandorical.api.ItemRegistration;
import justfatlard.pandorical.api.PandoricalApi;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAssets;

import java.util.Map;

public class Main implements ModInitializer {
	public static final String MOD_ID = "emerald-armor-justfatlard";

	private static final TagKey<Item> REPAIR_TAG = TagKey.create(
		Registries.ITEM, Identifier.fromNamespaceAndPath(MOD_ID, "emerald_armor_repair_items")
	);

	// Defense positioned between iron and diamond
	public static final ArmorMaterial EMERALD_ARMOR_MATERIAL = new ArmorMaterial(
		37,                                    // durability
		Map.of(                                // defense per slot
			ArmorType.HELMET, 3,
			ArmorType.CHESTPLATE, 8,
			ArmorType.LEGGINGS, 6,
			ArmorType.BOOTS, 3
		),
		20,                                    // enchantability
		SoundEvents.ARMOR_EQUIP_DIAMOND,       // equip sound
		2.0f,                                  // toughness
		0.0f,                                  // knockback resistance
		REPAIR_TAG,                            // repair ingredient
		// This mod's own equipment definition, which it has always shipped at
		// assets/minecraft/equipment/emerald.json and never pointed at: the material named
		// diamond's instead, so the armour on a wearer was diamond-coloured whatever the
		// item in the slot looked like.
		ResourceKey.create(EquipmentAssets.ROOT_ID, Identifier.withDefaultNamespace("emerald"))
	);

	private static ResourceKey<Item> keyOf(String name) {
		return ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MOD_ID, name));
	}

	private static final ResourceKey<CreativeModeTab> COMBAT_TAB = ResourceKey.create(
		Registries.CREATIVE_MODE_TAB, Identifier.fromNamespaceAndPath("minecraft", "combat")
	);

	public static final Item EMERALD_HELMET = new EmeraldArmorItem(
		new Item.Properties()
			.setId(keyOf("emerald_helmet"))
			.humanoidArmor(EMERALD_ARMOR_MATERIAL, ArmorType.HELMET)
	);

	public static final Item EMERALD_CHESTPLATE = new EmeraldArmorItem(
		new Item.Properties()
			.setId(keyOf("emerald_chestplate"))
			.humanoidArmor(EMERALD_ARMOR_MATERIAL, ArmorType.CHESTPLATE)
	);

	public static final Item EMERALD_LEGGINGS = new EmeraldArmorItem(
		new Item.Properties()
			.setId(keyOf("emerald_leggings"))
			.humanoidArmor(EMERALD_ARMOR_MATERIAL, ArmorType.LEGGINGS)
	);

	public static final Item EMERALD_BOOTS = new EmeraldArmorItem(
		new Item.Properties()
			.setId(keyOf("emerald_boots"))
			.humanoidArmor(EMERALD_ARMOR_MATERIAL, ArmorType.BOOTS)
	);

	@Override
	public void onInitialize() {
		if (PandoricalApi.isAvailable()) {
			for (String name : new String[] { "emerald_helmet", "emerald_chestplate", "emerald_leggings", "emerald_boots" }) {
				PandoricalApi.content().registerItem(MOD_ID + ":" + name, new ItemRegistration()
					.model(MOD_ID + ":item/" + name)
					.maxStackSize(1));
			}
			PandoricalApi.content().registerModAssets(MOD_ID);
		}

		Registry.register(BuiltInRegistries.ITEM, keyOf("emerald_helmet"), EMERALD_HELMET);
		Registry.register(BuiltInRegistries.ITEM, keyOf("emerald_chestplate"), EMERALD_CHESTPLATE);
		Registry.register(BuiltInRegistries.ITEM, keyOf("emerald_leggings"), EMERALD_LEGGINGS);
		Registry.register(BuiltInRegistries.ITEM, keyOf("emerald_boots"), EMERALD_BOOTS);

		CreativeModeTabEvents.modifyOutputEvent(COMBAT_TAB).register(entries -> {
			entries.accept(EMERALD_HELMET);
			entries.accept(EMERALD_CHESTPLATE);
			entries.accept(EMERALD_LEGGINGS);
			entries.accept(EMERALD_BOOTS);
		});

		System.out.println("[emerald-armor] Loaded emerald-armor (server-side with Pandorical)");
	}
}
