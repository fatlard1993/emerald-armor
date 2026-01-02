package justfatlard.emerald_armor;

import eu.pb4.polymer.core.api.item.PolymerItem;
import eu.pb4.polymer.core.api.item.PolymerItemUtils;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.EquippableComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.item.equipment.EquipmentAssetKeys;
import net.minecraft.registry.RegistryKey;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import xyz.nucleoid.packettweaker.PacketContext;

public class EmeraldArmorItem extends Item implements PolymerItem {
	private final Item polymerItem;
	private final EquipmentSlot slot;

	// Custom equipment asset key
	private static final RegistryKey<?> EMERALD_EQUIPMENT_KEY = RegistryKey.of(
		EquipmentAssetKeys.REGISTRY_KEY,
		Identifier.of("minecraft", "emerald")
	);

	public EmeraldArmorItem(EquipmentType type, int defense, Item.Settings settings, Item polymerItem) {
		super(settings.equippable(type.getEquipmentSlot()));
		this.polymerItem = polymerItem;
		this.slot = type.getEquipmentSlot();
	}

	@Override
	public Item getPolymerItem(ItemStack itemStack, PacketContext context) {
		return this.polymerItem;
	}

	@Override
	public ItemStack getPolymerItemStack(ItemStack itemStack, TooltipType tooltipType, PacketContext context) {
		ItemStack out = PolymerItemUtils.createItemStack(itemStack, tooltipType, context);

		// Create custom equippable component with our emerald equipment asset
		@SuppressWarnings("unchecked")
		EquippableComponent customEquippable = EquippableComponent.builder(this.slot)
			.equipSound(SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND)
			.model((RegistryKey) EMERALD_EQUIPMENT_KEY)
			.build();

		out.set(DataComponentTypes.EQUIPPABLE, customEquippable);

		// Remove dyed color since we're using a custom texture now
		out.remove(DataComponentTypes.DYED_COLOR);

		return out;
	}
}
