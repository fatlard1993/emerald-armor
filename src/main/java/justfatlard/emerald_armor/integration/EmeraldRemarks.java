package justfatlard.emerald_armor.integration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import justfatlard.emerald_armor.Main;
import justfatlard.village_quests.api.DialogueRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

/**
 * What a village says to somebody wearing its money.
 *
 * <p>Registered with Village Quests when that mod is present. The joke is
 * structural rather than written: that mod's whole argument is that standing is
 * not a currency and cannot be bought, and this mod lets a player put on two
 * hundred and sixteen emeralds. So the village looks at the most expensive
 * thing anyone has ever worn through it and is not moved, because the thing it
 * measures was never for sale. Nobody here is impressed and nobody is rude
 * about it either. They are tradespeople looking at a material.
 *
 * <p>The numbers are the mods' own. A full set is twenty-four emerald blocks,
 * which is two hundred and sixteen emeralds, for exactly the twenty points of
 * protection diamond gives. What it actually buys is enchantability of twenty
 * against diamond's ten, which is the armorer's and the toolsmith's point and
 * the only honest defence of the stuff.
 *
 * <p>Emerald tools are reached by registry id rather than by compiling against
 * them: one optional mod noticing another is not worth a second soft
 * dependency, and the same trick is how useful-hoe reaches the emerald hoe.
 *
 * <p>This class must only be touched behind a mod-loaded check. It refers to
 * Village Quests types directly, so loading it without that mod present throws.
 */
public final class EmeraldRemarks {
	private EmeraldRemarks() {}

	private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(Main.MOD_ID);

	private static final String TOOLS_MOD = "emerald-tools-justfatlard";

	/** Sometimes. A village that says this every single time is a village with one joke. */
	private static final double REMARK_CHANCE = 0.3;

	private static final EquipmentSlot[] WORN = {
		EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};

	private static boolean isOurArmor(ItemStack stack) {
		return stack.is(Main.EMERALD_HELMET) || stack.is(Main.EMERALD_CHESTPLATE)
			|| stack.is(Main.EMERALD_LEGGINGS) || stack.is(Main.EMERALD_BOOTS);
	}

	/** Anything out of the tools mod, by id, since it may not be installed at all. */
	private static boolean isTheirTool(ItemStack stack) {
		Item item = stack.getItem();
		Identifier id = BuiltInRegistries.ITEM.getKey(item);
		return id != null && id.getNamespace().equals(TOOLS_MOD);
	}

	/** How much of it they have on, and whether any of it is a tool. */
	private record Worn(int pieces, boolean tool) {
		boolean any() {
			return pieces > 0 || tool;
		}

		boolean fullSet() {
			return pieces >= 4;
		}
	}

	private static Worn worn(ServerPlayer player) {
		int pieces = 0;
		for (EquipmentSlot slot : WORN) {
			if (isOurArmor(player.getItemBySlot(slot))) pieces++;
		}

		boolean tool = isTheirTool(player.getMainHandItem()) || isTheirTool(player.getOffhandItem());
		return new Worn(pieces, tool);
	}

	private record Node(String text, String walkAway, List<Branch> branches) {}

	private record Branch(String label, Node node) {}

	/** What the villager opens with, built from how much of it is actually on. */
	private interface Opening {
		Node build(Worn worn);
	}

	private record Topic(String id, int minReputation, boolean needsTool, String question, Opening opening) {}

	private static Node close(String text, String walkAway) {
		return new Node(text, walkAway, List.of());
	}

	private static Node node(String text, String walkAway, Branch... branches) {
		return new Node(text, walkAway, List.of(branches));
	}

	private static Branch then(String label, Node node) {
		return new Branch(label, node);
	}

	/** "the whole set", or "that helmet", because the specific thing is the funnier thing. */
	private static String much(Worn worn) {
		if (worn.fullSet()) return "the whole set";
		if (worn.pieces() > 1) return "that much of it";
		if (worn.pieces() == 1) return "that piece";
		return "that tool";
	}

	public static void register() {
		topics("armorer", List.of(
			new Topic("ea_protection", 0, false, "What do you make of what I'm wearing?", worn ->
				node("Professionally? It will stop what diamond stops. Not a point more. I have sat with the numbers because "
						+ "people keep asking me, and " + much(worn) + " protects you exactly as well as the stuff you can dig up.",
					"Good to know.",
					then("Then why would anyone make it?",
						close("Because it takes an enchantment about twice as well as diamond does, and that is the whole answer. "
								+ "If you were going to put something good on it, it was worth it. If you were not, you are wearing "
								+ "a wall's worth of shopping.",
							"I'll put something good on it.")),
					then("It's more durable, at least.",
						close("By a hair. Four in thirty-something. You will notice that the day after you notice nothing else.",
							"A hair, then.")))),

			new Topic("ea_cost", 20, false, "Do you know what this cost?", worn ->
				node("Twenty-four blocks for a full set, so a bit over two hundred emeralds all told. I know because I priced it "
						+ "out the first week somebody wore one through here, out of professional curiosity and a bit of spite.",
					"That's about right.",
					then("Was it worth it?",
						close("You are asking a man who works in iron whether two hundred emeralds was worth it. "
								+ "*goes back to the bench* It is very fine work. I would not have made it.",
							"Fair."))))));

		topics("toolsmith", List.of(
			new Topic("ea_tool", 0, true, "Have you seen one of these before?", worn ->
				node("Once or twice. Cuts a little quicker than diamond, lasts a little longer, hits a little harder. "
						+ "Every one of those is a little.",
					"It does the job.",
					then("So it's not worth making?",
						close("I did not say that. Put it on the table and it takes an enchantment like nothing else you will "
								+ "carry. That is not a little. That is the only reason the thing exists and half the people "
								+ "holding one have not worked it out.",
							"Noted."))))));

		topics("cleric", List.of(
			new Topic("ea_wearing", 15, false, "Does it bother you, what I'm wearing?", worn ->
				node("No. Should it? *looks properly* It is very green. I have buried people who owned less than one of "
						+ "your sleeves, and I do not think that is your doing or your fault.",
					"That's a lot to sit with.",
					then("It doesn't seem to impress anyone here.",
						close("What would it impress us into? We are not for sale and neither is what you have got with us. "
								+ "You could take " + much(worn) + " off in the square and be the same person to me. "
								+ "That is not a criticism. It is rather the nicest thing I know how to say.",
							"I'll take it as one."))))));

		LOGGER.info("Registered emerald remarks with Village Quests");

		topics("farmer", List.of(
			new Topic("ea_plain", 0, false, "You've not said anything about the armour.", worn ->
				node("Was I meant to? *goes back to it* It is green and it is on you and my wheat does not care either way.",
					"Fair enough.",
					then("Most people say something.",
						close("Most people are not stood in a field at this hour. I will tell you what I told the last one: "
								+ "if you fall in the ditch wearing " + much(worn) + ", you will go down the same as anybody.",
							"I'll mind the ditch."))))));
	}

	/**
	 * One profession's remarks, offered only while the player actually has some
	 * of it on, and only sometimes even then.
	 */
	private static void topics(String profession, List<Topic> topics) {
		for (Topic topic : topics) {
			DialogueRegistry.registerRichDialogueHandler(topic.id(), (villager, player, id) -> {
				Worn seen = worn(player);
				// Taken off between the screen opening and the click, which is a
				// thing people do, and there is nothing to talk about now.
				if (!seen.any()) {
					return DialogueRegistry.Reply.of("*looks at where it was* ...You have taken it off. Never mind me.")
						.walkAway("Never mind.");
				}
				return reply(topic.opening().build(seen));
			});
		}

		DialogueRegistry.registerProfessionDialogue(profession, (villager, player, reputation) -> {
			ThreadLocalRandom rng = ThreadLocalRandom.current();
			if (rng.nextDouble() >= REMARK_CHANCE) return List.of();

			Worn seen = worn(player);
			if (!seen.any()) return List.of();

			List<Topic> fitting = new ArrayList<>();
			for (Topic topic : topics) {
				if (reputation < topic.minReputation()) continue;
				// The toolsmith's remark is about the thing in your hand, so it needs
				// one there; everything else is about what you have on.
				if (topic.needsTool() && !seen.tool()) continue;
				if (!topic.needsTool() && seen.pieces() == 0) continue;
				fitting.add(topic);
			}
			if (fitting.isEmpty()) return List.of();

			Topic picked = fitting.get(rng.nextInt(fitting.size()));
			return List.of(new DialogueRegistry.DialogueOption(picked.id(),
				Component.literal(picked.question()), picked.minReputation(), Integer.MAX_VALUE));
		});
	}

	private static DialogueRegistry.Reply reply(Node node) {
		DialogueRegistry.Reply reply = DialogueRegistry.Reply.of(node.text());
		if (node.walkAway() != null) reply.walkAway(node.walkAway());
		for (Branch branch : node.branches()) {
			reply.option(branch.label(), (villager, player, id) -> reply(branch.node()));
		}
		return reply;
	}
}
