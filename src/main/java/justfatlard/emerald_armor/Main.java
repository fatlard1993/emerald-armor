package justfatlard.emerald_armor;

import eu.pb4.polymer.core.api.item.PolymerItemGroupUtils;
import eu.pb4.polymer.resourcepack.api.PolymerResourcePackUtils;
import net.fabricmc.api.ModInitializer;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.item.ItemGroups;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;

public class Main implements ModInitializer {
	public static final String MOD_ID = "emerald-armor-justfatlard";

	// Base durability for emerald armor (higher than diamond's 33)
	public static final int BASE_DURABILITY = 37;

	// Defense values (between iron and diamond)
	public static final int HELMET_DEFENSE = 3;
	public static final int CHESTPLATE_DEFENSE = 8;
	public static final int LEGGINGS_DEFENSE = 6;
	public static final int BOOTS_DEFENSE = 3;

	// Armor toughness (same as diamond)
	public static final double ARMOR_TOUGHNESS = 2.0;

	// Helper method to create registry key
	private static RegistryKey<Item> keyOf(String name) {
		return RegistryKey.of(RegistryKeys.ITEM, Identifier.of(MOD_ID, name));
	}

	// Helper method to create armor attribute modifiers
	private static AttributeModifiersComponent createArmorAttributes(int defense, double toughness, AttributeModifierSlot slot) {
		return AttributeModifiersComponent.builder()
			.add(
				EntityAttributes.ARMOR,
				new EntityAttributeModifier(
					Identifier.of(MOD_ID, "armor_protection"),
					defense,
					EntityAttributeModifier.Operation.ADD_VALUE
				),
				slot
			)
			.add(
				EntityAttributes.ARMOR_TOUGHNESS,
				new EntityAttributeModifier(
					Identifier.of(MOD_ID, "armor_toughness"),
					toughness,
					EntityAttributeModifier.Operation.ADD_VALUE
				),
				slot
			)
			.build();
	}

	// Armor items using equippable() + attribute modifiers for Polymer compatibility
	// Using leather armor as Polymer item to enable custom color rendering on player model
	public static final Item EMERALD_HELMET = new EmeraldArmorItem(
		EquipmentType.HELMET,
		HELMET_DEFENSE,
		new Item.Settings()
			.registryKey(keyOf("emerald_helmet"))
			.maxCount(1)
			.maxDamage(EquipmentType.HELMET.getMaxDamage(BASE_DURABILITY))
			.attributeModifiers(createArmorAttributes(HELMET_DEFENSE, ARMOR_TOUGHNESS, AttributeModifierSlot.HEAD)),
		Items.LEATHER_HELMET
	);

	public static final Item EMERALD_CHESTPLATE = new EmeraldArmorItem(
		EquipmentType.CHESTPLATE,
		CHESTPLATE_DEFENSE,
		new Item.Settings()
			.registryKey(keyOf("emerald_chestplate"))
			.maxCount(1)
			.maxDamage(EquipmentType.CHESTPLATE.getMaxDamage(BASE_DURABILITY))
			.attributeModifiers(createArmorAttributes(CHESTPLATE_DEFENSE, ARMOR_TOUGHNESS, AttributeModifierSlot.CHEST)),
		Items.LEATHER_CHESTPLATE
	);

	public static final Item EMERALD_LEGGINGS = new EmeraldArmorItem(
		EquipmentType.LEGGINGS,
		LEGGINGS_DEFENSE,
		new Item.Settings()
			.registryKey(keyOf("emerald_leggings"))
			.maxCount(1)
			.maxDamage(EquipmentType.LEGGINGS.getMaxDamage(BASE_DURABILITY))
			.attributeModifiers(createArmorAttributes(LEGGINGS_DEFENSE, ARMOR_TOUGHNESS, AttributeModifierSlot.LEGS)),
		Items.LEATHER_LEGGINGS
	);

	public static final Item EMERALD_BOOTS = new EmeraldArmorItem(
		EquipmentType.BOOTS,
		BOOTS_DEFENSE,
		new Item.Settings()
			.registryKey(keyOf("emerald_boots"))
			.maxCount(1)
			.maxDamage(EquipmentType.BOOTS.getMaxDamage(BASE_DURABILITY))
			.attributeModifiers(createArmorAttributes(BOOTS_DEFENSE, ARMOR_TOUGHNESS, AttributeModifierSlot.FEET)),
		Items.LEATHER_BOOTS
	);

	@Override
	public void onInitialize() {
		// Register mod assets with Polymer resource pack system
		PolymerResourcePackUtils.addModAssets(MOD_ID);
		PolymerResourcePackUtils.markAsRequired();

		// Register items
		Registry.register(Registries.ITEM, keyOf("emerald_helmet"), EMERALD_HELMET);
		Registry.register(Registries.ITEM, keyOf("emerald_chestplate"), EMERALD_CHESTPLATE);
		Registry.register(Registries.ITEM, keyOf("emerald_leggings"), EMERALD_LEGGINGS);
		Registry.register(Registries.ITEM, keyOf("emerald_boots"), EMERALD_BOOTS);

		// Create Polymer item group (access via /polymer creative)
		ItemGroup emeraldArmorGroup = PolymerItemGroupUtils.builder()
			.displayName(Text.literal("Emerald Armor"))
			.icon(() -> new ItemStack(EMERALD_CHESTPLATE))
			.entries((context, entries) -> {
				entries.add(new ItemStack(EMERALD_HELMET));
				entries.add(new ItemStack(EMERALD_CHESTPLATE));
				entries.add(new ItemStack(EMERALD_LEGGINGS));
				entries.add(new ItemStack(EMERALD_BOOTS));
			})
			.build();
		PolymerItemGroupUtils.registerPolymerItemGroup(Identifier.of(MOD_ID, "emerald_armor"), emeraldArmorGroup);

		// Add armor to vanilla Combat creative tab
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> {
			entries.add(EMERALD_HELMET);
			entries.add(EMERALD_CHESTPLATE);
			entries.add(EMERALD_LEGGINGS);
			entries.add(EMERALD_BOOTS);
		});

		System.out.println("[emerald-armor] Loaded emerald-armor (server-side with Polymer)");
	}
}
