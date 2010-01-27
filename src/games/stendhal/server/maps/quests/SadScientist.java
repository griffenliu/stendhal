package games.stendhal.server.maps.quests;

import games.stendhal.server.entity.npc.ConversationPhrases;
import games.stendhal.server.entity.npc.ConversationStates;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.action.SetQuestAction;
import games.stendhal.server.entity.npc.condition.AndCondition;
import games.stendhal.server.entity.npc.condition.PlayerHasItemWithHimCondition;
import games.stendhal.server.entity.npc.condition.QuestInStateCondition;
import games.stendhal.server.entity.npc.condition.QuestNotStartedCondition;

import java.util.Arrays;

public class SadScientist extends AbstractQuest {
	
	public static final String QUEST_SLOT = "sad_scientist";


	@Override
	public String getName() {
		return "TheSadScientist";
	}

	@Override
	public String getSlotName() {
		return QUEST_SLOT;
	}

	/* (non-Javadoc)
	 * @see games.stendhal.server.maps.quests.AbstractQuest#addToWorld()
	 */
	@Override
	public void addToWorld() {
		super.addToWorld();
		prepareQuestSteps();
	}

	private void prepareQuestSteps() {
		prepareScientist();
		prepareMayor();
	}

	private void prepareScientist() {
		SpeakerNPC npc = npcs.get("Some scientist");
		startOfQuest(npc);
		//player returns after start
		AndCondition condition = new AndCondition(
				new QuestInStateCondition(QUEST_SLOT, "start"),
				new AndCondition(
						new PlayerHasItemWithHimCondition("emerald"), 
						new PlayerHasItemWithHimCondition("obsidian"),
						new PlayerHasItemWithHimCondition("sapphire"),
						new PlayerHasItemWithHimCondition("carbuncle",2),
						new PlayerHasItemWithHimCondition("gold bar",20),
						new PlayerHasItemWithHimCondition("mithril bar"),
						new PlayerHasItemWithHimCondition("shadow legs")));
		npc.add(ConversationStates.ATTENDING, ConversationPhrases.QUEST_MESSAGES,
				condition,
				ConversationStates.ATTENDING, 
				"",
				null);
	}

	private void startOfQuest(SpeakerNPC npc) {
		//offer the quest
		npc.add(ConversationStates.ATTENDING,
				ConversationPhrases.QUEST_MESSAGES, 
				new QuestNotStartedCondition(QUEST_SLOT),
				ConversationStates.QUEST_OFFERED,
				"So...looks like you want to help me?",null);
		//accept the quest
		npc.add(ConversationStates.QUEST_OFFERED,
				ConversationPhrases.YES_MESSAGES,
				null,
				ConversationStates.QUEST_STARTED,
				"My wife is living in Semos City. She loves gems. Can you bring me some #gems that I need to make a pair of precious #legs?" ,
				null);
		// #gems
		npc.add(ConversationStates.QUEST_STARTED,
				Arrays.asList("gem","gems"),
				null,
				ConversationStates.QUEST_STARTED,
				"I need an emerald, an obsidian, a sapphire, 2 carbuncles, 20 gold bars, one mithril bar, and I need a pair of shadow legs as the base to add the gems to. Can you do that for my wife? " ,
				null);
		// #legs
		npc.add(ConversationStates.QUEST_STARTED,
				Arrays.asList("leg","legs"),
				null,
				ConversationStates.QUEST_STARTED,
				"Jewelled legs. I need an emerald, an obsidian, a sapphire, 2 carbuncles, 20 gold bars, one mithril bar, and I need a pair of shadow legs as the base to add the gems to. Can you do that for my wife? Can you bring what I need? " ,
				null);
		//yes, no after start of quest
		npc.add(ConversationStates.QUEST_STARTED,
				ConversationPhrases.YES_MESSAGES,
				null,
				ConversationStates.ATTENDING,
				"I am waiting, semos man." ,
				new SetQuestAction(QUEST_SLOT, "start"));
		npc.add(ConversationStates.QUEST_STARTED,
				ConversationPhrases.NO_MESSAGES,
				null,
				ConversationStates.QUEST_STARTED,
				"Go away before I kill you!" ,
				null);
		//reject the quest
		npc.add(ConversationStates.QUEST_OFFERED,
				ConversationPhrases.NO_MESSAGES,
				null,
				ConversationStates.ATTENDING,
				"If you change your mind please ask me again..." ,
				null);
	}

	private void prepareMayor() {
		// TODO Auto-generated method stub
		
	}
	
}
