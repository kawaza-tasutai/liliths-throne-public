package com.lilithsthrone.main;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import com.lilithsthrone.controller.MainController;
import com.lilithsthrone.game.Game;
import com.lilithsthrone.game.Properties;
import com.lilithsthrone.game.character.CharacterUtils;
import com.lilithsthrone.game.character.NameTriplet;
import com.lilithsthrone.game.character.PlayerCharacter;
import com.lilithsthrone.game.character.QuestLine;
import com.lilithsthrone.game.character.body.valueEnums.Femininity;
import com.lilithsthrone.game.character.gender.Gender;
import com.lilithsthrone.game.character.race.RaceStage;
import com.lilithsthrone.game.character.race.RacialBody;
import com.lilithsthrone.game.dialogue.DialogueNodeOld;
import com.lilithsthrone.game.dialogue.MapDisplay;
import com.lilithsthrone.game.dialogue.responses.Response;
import com.lilithsthrone.game.dialogue.story.CharacterCreation;
import com.lilithsthrone.game.dialogue.utils.OptionsDialogue;
import com.lilithsthrone.game.inventory.enchanting.TFEssence;
import com.lilithsthrone.utils.Colour;
import com.lilithsthrone.utils.CreditsSlot;
import com.lilithsthrone.world.Generation;
import com.lilithsthrone.world.WorldType;
import com.lilithsthrone.world.places.PlaceType;

import javafx.application.Application;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * @since 0.1.0
 * @version 0.1.97
 * @author Innoxia
 */
public class Main extends Application {

	public static Game game;

	public static MainController mainController;

	public static Scene mainScene;

	public static Stage primaryStage;
	public static String author = "Innoxia";

	public static final String VERSION_NUMBER = "0.1.97.1",
			VERSION_DESCRIPTION = "Early Alpha";

	public static final Image WINDOW_IMAGE = new Image("/com/lilithsthrone/res/images/windowIcon32.png");

	private static Properties properties;
	
	public static String patchNotes =
			
//		"<h1 style='text-align:center;'>Version " + Main.VERSION_NUMBER + "</h1>"
//		+ "<h6 style='text-align:center;'><b style='color:" + Colour.GENERIC_TERRIBLE.toWebHexString() + ";'>Buggy Preview!</b></h6>"
//		+ "<h6 style='text-align:center;'><b style='color:" + Colour.GENERIC_BAD.toWebHexString() + ";'>Early Alpha!</b></h6>"
		
		"<p>"
			+ "Hello everyone! ^^"
		+ "</p>"
			
		+ "<p>"
			+ "This is the hotfixed version 0.1.97. Everything should be working as it did in 0.1.96.5, but now I have the vast majority of the necessary framework in place to add in the first of the multiple-partner scenes in the next release."
			+ " The multiple-partner sex scene which is found in slave's rooms at the moment is just for testing, and will still be quite buggy."
		+ "</p>"
		
		+ "<p>"
			+ "Thank you all for playing Lilith's Throne! And a very big thank you to all the people supporting me on Patreon!"
			+ " If you wanted to ask me any specific questions about the game, you can either find me on my blog, or on the Lilith's Throne Discord. You can find a link to the discord on my blog. ^^"
		+ "</p>"
			
		+ "</br>"

		+ "<list>"
			+ "<h6>v0.1.96.5</h6>"
			+"<li>Engine:</li>"
			+"<ul>Slightly improved enchanting mechanics to add in a limit to the number of enchantments possible on each item (default 100).</ul>"
				
			+"<li>Gameplay:</li>"
			+"<ul>Added a small section of the main quest (just finishing up Arthur moving into Lilaya's home).</ul>"
			+"<ul>Added the side-quest 'Arthur's Experiments', available after assigning a room to Arthur, and from which you obtain a way to change both the PC's and NPCs' sexual orientation.</ul>"
				
			+"<li>Sex:</li>"
			+"<ul>Reverted the removal of mutual orgasms.</ul>"
			+"<ul>Dominant NPCs will no longer respect your requests in non-consensual sex.</ul>"
			+"<ul>Added 'spread ass' action.</ul>"
				
			+"<li>Other:</li>"
			+"<ul>Legendary items, weapons, and clothing will no longer be removed from tiles.</ul>"
			+"<ul>Demonic wings now allow flight when grown to be large enough. (Flight only affects a couple of actions so far.)</ul>"
			+"<ul>Added correct player-partner use descriptions for all items.</ul>"
			+"<ul>Slightly adjusted item prices.</ul>"
			+"<ul>Very slightly changed Rose's 'Slavery' dialogue (the one about her and Lilaya's relationship).</ul>"
			+"<ul>Removed ability to cycle orientation in the phone's fetish screen (as this can now be achieved by using the new item).</ul>"
			+"<ul>Updated Kate's clothing and appearance.</ul>"
				
			+"<li>Contributors:</li>"
			+"<ul>Fixed alligator-morph eyes from having round to vertical pupils by default. (Rfpnj)</ul>"
				
			+"<li>Bugs:</li>"
			+"<ul>Jolnir's hat is no longer feminine.</ul>"
			+"<ul>Demon attackers will now correctly prefer to use their penis.</ul>"
			+"<ul>Nyan should now have correct hair/fur colour (black) when importing older games.</ul>"
			+"<ul>Fixed some unfinished reindeer overseer dialogue.</ul>"
			+"<ul>Fixed Lilaya's pregnancy dialogue to correctly reflect how pregnancies work in LT.</ul>"
			+"<ul>Fixed major bug in AbstractClothing's hashCode method that was causing crashes.</ul>"
			+"<ul>Fixed bug where physical resistance (and other effects) from clothing would not be removed when inventory was reset (most noticeably in character creation, when pressing start/back repeatedly).</ul>"
			+"<ul>Fixed old elixir and potion imports showing 'null' as their background icon.</ul>"
			+"<ul>Minor fix to Lilaya's post-creampie-sex scene.</ul>"
			+"<ul>Fixed minor inventory text formatting bug.</ul>"
			+"<ul>Temporarily stopped chemise from blocking groin areas until I can rework the clothing displacement system (there were some major issues with wearing chemise & pantyhose).</ul>"
		+ "</list>"

		+ "</br>"
		
		+ "<list>"
			+ "<h6>v0.1.96.9</h6>"
			+"<li>Engine:</li>"
			+"<ul>Added: Huge amount of engine support for multiple partners in sex. This required an enormous refactor of how sex is handled in the engine, which took up ~90% of the time this week.</ul>"
			+"<ul>Final sex mechanics (taking the form of 'lust' and stamina) have been half-added to the engine.</ul>"
				
			+"<li>Sex:</li>"
			+"<ul>You should now be able to use all of your hands/tails during sex.</ul>"
				
			+"<li>Gameplay:</li>"
			+"<ul>Added double room upgrade, so you can now house two slaves in each room.</ul>"
				
			+"<li>Clothing:</li>"
			+"<ul>Added: Women's leather jacket (feminine, over-torso slot). (I added this to complete Kate's new outfit.)</ul>"
			+"<ul>Added: Striped panties (feminine, groin slot).</ul>"
			+"<ul>Added: Striped bra (feminine, chest slot).</ul>"
			+"<ul>Added: Striped thigh-high socks (feminine, sock slot).</ul>"
				
			+"<li>UI:</li>"
			+"<ul>Moved weapons & jewellery into the 'equipped' panel, so all equipped items are now grouped into the same UI element.</ul>"
			+"<ul>Fixed bugs related to tooltip positioning causing some UI elements to become inaccessible.</ul>"
				
			+"<li>Other:</li>"
			+"<ul>Removed 'Wing' clothing slot. (I might add this again in the future, but I was struggling to think of any 'wing clothing' to add to that slot.)	</ul>"
			+"<ul>Tweaked drop rate of racial consumables (the ones that are able to be enchanted into transformatives are now a little more common).</ul>"
			+"<ul>Added more control over selecting font size in the option menu.</ul>"
				
			+"<li>Bugs:</li>"
			+"<ul>Minor formatting error fixes.</ul>"
			+"<ul>The sex action 'Spread ass' is now correctly available for the player.</ul>"
			+"<ul>NPCs should no longer get stuck on trying to transform your genitals after beating you in combat.</ul>"
			+"<ul>Enchanted weapons (those with with stat buffs) now correctly apply their buffs when importing a save/character.</ul>"
			+"<ul>Minor fix to default gender names (changed an instance of 'doll' to 'neuter').</ul>"
			+"<ul>Minor typo fixes.</ul>"
			+"<ul>Fixed hypno-watch and Arthur's package being found in alleyways.</ul>"
			+"<ul>You can no longer purchase Arthur's Room upgrade before progressing to the correct point in the main quest.</ul>"
			+"<ul>Fixed encyclopedia preview pictures not working.</ul>"
			+"<ul>Fixed an error in changing what your slave calls you through the 'Permissions' screen.</ul>"
			+"<ul>Fixed bug where pressing enter on a blank text field would cause the centre panel of the UI to go blank.</ul>"
		+ "</list>"

		+ "</br>"
		
		+ "<list>"
			+ "<h6>v0.1.97 (hotfix)</h6>"
			+"<li>UI:</li>"
			+"<ul>Minor fixes to some of the new elements in sex.</ul>"
			+"<ul>Fixed minor error in light theme's sex display.</ul>"
			
			+"<li>Other:</li>"
			+"<ul>Slaves with the 'unwilling fuck-toy' fetish will no longer lose affection from being assigned to the public stocks.</ul>"
			
			+"<li>Bugs:</li>"
			+"<ul>Fixed major bug where you couldn't interact with any items an NPC had in their inventory (making all shopkeepers unable to sell you anything).</ul>"
			+"<ul>Vicky no longer has her penile virginity.</ul>"
			+"<ul>Vicky will now correctly perform penetrative actions in her sex scene.</ul>"
			+"<ul>Fixed 'stop sex' being available in all scenes.</ul>"
			+"<ul>Fixed bug where you couldn't remove your own clothing in some sex scenes.</ul>"
			+"<ul>'Core lust' changes should no longer show up in the event log.</ul>"
			+"<ul>'Stop penetration' sex actions should now correctly work again.</ul>"
			+"<ul>Antler headband is no longer feminine.</ul>"
			+"<ul>Nyan will no longer stock Christmas-themed clothing.</ul>"
			+"<ul>Fixed incorrect & buggy orgasm descriptions.</ul>"
			+"<ul>Fixed 'Locked in stocks' sex's orifices being available when they shouldn't have been.</ul>"
		+ "</list>"
		;
	
	public static String disclaimer = "<h6 style='text-align: center; color:"+Colour.GENERIC_ARCANE.toWebHexString()+";'>You must read and agree to the following in order to play this game!</h6>"

			+ "<p>This game is a <b>fictional</b> text-based erotic RPG." + " All content contained within this game forms part of a fictional universe that is not related to real-life places, people or events.</br></br>"

			+ " All of the characters that appear in this story are fictional entities who have given their consent to appear and act in this story."
			+ " If you find yourself concerned for the characters in the story then please be reassured that they are all consenting adults who are speaking lines from a script."
			+ " None of the characters portrayed within this game were or are being harmed in any way during the construction or execution of this game."
			+ " Every character in the game is at least 18 years of age (or is magically the legal age needed to appear in erotic literature in whatever country you are playing this).</br></br>"

			+ "By agreeing to this disclaimer and playing this game you agree to be exposed to graphic sexual and adult content that is presented as part of the game's fictional universe."
			+ " Such content consists of, but is not limited to; graphic depictions of sex, inter-species sex (with fantasy creatures), sexual transformation,"
			+ " rape fantasy/implied lack of consent, mild physical violence, sexual violence, and drug use.</br>"
			+ "Extreme fetish content such as gore/extreme violence, scat, and under/questionable age, is <i>not</i> a part of this game.</br></br>"

			+ "By agreeing to this disclaimer and playing this game you also agree that you are <b>at least 18 years of age</b>,"
			+ " or at least the legal age for you to purchase and view pornographic material in your country if that age is over 18.</br></br>"

			+ "As a final note, the creators of this game wish to stress that the content presented within is entirely fictional and does not reflect any of their personal views or opinions."
			+ " This game has been made in the spirit of creating a piece of artistic interactive literature, and it is imperative that you maintain a clear distinction between reality and the fictional events depicted in this game.</p>";
	
	public static List<CreditsSlot> credits = new ArrayList<>();

	// World generation:
	public static Generation gen;
	@Override
	public void start(Stage primaryStage) throws Exception {

		credits.add(new CreditsSlot("Anonymous", "", 0, 6, 115+61, 37+21));
		

		credits.add(new CreditsSlot("fun_bot", "", 0, 0, 0, 2));
		credits.add(new CreditsSlot("Garth614", "", 0, 0, 0, 2));
		credits.add(new CreditsSlot("Dan", "", 0, 1, 0, 2));
		credits.add(new CreditsSlot("Ash", "", 0, 1, 0, 2));
		
		credits.add(new CreditsSlot("WodashGSJ", "", 0, 0, 2, 0));
		credits.add(new CreditsSlot("vasadariu", "", 0, 0, 1, 0));
		credits.add(new CreditsSlot("Casper &quot;Cdaser&quot; D.", "", 0, 0, 2, 0));
		credits.add(new CreditsSlot("Cryostorm", "", 0, 0, 2, 0));
		credits.add(new CreditsSlot("BLKCandy", "", 0, 0, 2, 0));
		credits.add(new CreditsSlot("Testostetyrone", "", 0, 0, 2, 0));
		credits.add(new CreditsSlot("Brandon Stach", "", 0, 0, 2, 0));
		credits.add(new CreditsSlot("CMPirate9867", "", 0, 0, 2, 0));

		credits.add(new CreditsSlot("Zakarin", "", 0, 0, 0, 1));
		credits.add(new CreditsSlot("Demonicgamer666", "", 0, 0, 0, 1));
		credits.add(new CreditsSlot("masterpuppet", "", 0, 0, 2, 0));

		credits.add(new CreditsSlot("Endness", "", 0, 0, 0, 1));
		
		
		
		credits.add(new CreditsSlot("Adhana Konker", "", 0, 0, 3, 0));
		credits.add(new CreditsSlot("Lexi <3", "", 0, 0, 0, 1));
		credits.add(new CreditsSlot("48days", "", 0, 0, 2, 4));
		credits.add(new CreditsSlot("Spaghetti Code", "", 0, 0, 2, 0));
		credits.add(new CreditsSlot("SchALLieS", "", 0, 0, 0, 5));
		credits.add(new CreditsSlot("Argmoe", "", 0, 0, 5, 0));
		credits.add(new CreditsSlot("HoneyNutQueerios", "", 0, 0, 3, 0));
		credits.add(new CreditsSlot("Arkhan", "", 0, 0, 6, 0));
		credits.add(new CreditsSlot("Avery", "", 0, 1, 4, 0));
		credits.add(new CreditsSlot("b00marrows", "", 0, 1, 5, 0));
		credits.add(new CreditsSlot("Baz GoldenClaw", "", 0, 0, 4, 0));
		credits.add(new CreditsSlot("Runehood66", "", 0, 0, 0, 4));
		credits.add(new CreditsSlot("Blackcanine", "", 0, 0, 4, 0));
		credits.add(new CreditsSlot("Blacktouch", "", 0, 0, 2, 4));
		credits.add(new CreditsSlot("Blue999", "", 0, 0, 0, 6));
		credits.add(new CreditsSlot("BreakerB", "", 0, 0, 3, 0));
		credits.add(new CreditsSlot("BRobort", "", 0, 0, 6, 0));
		credits.add(new CreditsSlot("BloodsailXXII", "", 0, 0, 0, 6));
		credits.add(new CreditsSlot("Burt", "", 0, 0, 4, 0));
		credits.add(new CreditsSlot("Calrak", "", 0, 0, 0, 5));
		credits.add(new CreditsSlot("CelestialNightmare", "", 0, 0, 0, 6));
		credits.add(new CreditsSlot("Sxythe", "", 0, 0, 0, 2));
		credits.add(new CreditsSlot("Lexi the slut", "", 0, 0, 0, 6));
		credits.add(new CreditsSlot("Vmpireassassin (Chloe)", "", 0, 0, 0, 1));
		credits.add(new CreditsSlot("crashtestdummy", "", 0, 0, 6, 0));
		credits.add(new CreditsSlot("Crimson", "", 0, 0, 0, 4));
		credits.add(new CreditsSlot("Cursed Rena", "", 0, 0, 1, 4));
		credits.add(new CreditsSlot("DeadMasterZero", "", 0, 0, 1, 0));
		credits.add(new CreditsSlot("Destont", "", 0, 0, 3, 0));
		credits.add(new CreditsSlot("Yllarius", "", 0, 0, 2, 0));
		credits.add(new CreditsSlot("John Scarlet", "", 0, 0, 0, 1));
		credits.add(new CreditsSlot("rinoskin", "", 0, 0, 0, 2));
		credits.add(new CreditsSlot("Alatar", "", 0, 0, 0, 2));
		credits.add(new CreditsSlot("Elmsdor", "", 0, 0, 0, 6));
		credits.add(new CreditsSlot("Gr33n B3ans", "", 0, 0, 0, 2));
		credits.add(new CreditsSlot("Farseeker", "", 0, 0, 5, 0));
		credits.add(new CreditsSlot("pupslut felix", "", 0, 0, 0, 4));
		credits.add(new CreditsSlot("Fenrakk101", "", 0, 0, 5, 0));
		credits.add(new CreditsSlot("Fiona", "", 0, 0, 0, 6));
		credits.add(new CreditsSlot("ForeverFree2MeTaMax", "", 0, 0, 6, 0));
		credits.add(new CreditsSlot("Niki Parks", "", 0, 0, 6, 0));
		credits.add(new CreditsSlot("Georgio154", "", 0, 0, 1, 4));
		credits.add(new CreditsSlot("glocknar", "", 0, 0, 3, 0));
		credits.add(new CreditsSlot("Krissy2017", "", 0, 0, 0, 4));
		credits.add(new CreditsSlot("Grakcnar", "", 0, 0, 4, 0));
		credits.add(new CreditsSlot("Hedgehog", "", 0, 0, 0, 3));
		credits.add(new CreditsSlot("Bocaj91", "", 0, 0, 0, 4));
		credits.add(new CreditsSlot("Krejil", "", 0, 0, 4, 0));
		credits.add(new CreditsSlot("suka", "", 0, 0, 6, 0));
		credits.add(new CreditsSlot("EnigmaticYoshi", "", 0, 0, 6, 0));
		credits.add(new CreditsSlot("HerrKommissar11", "", 0, 0, 1, 2));
		credits.add(new CreditsSlot("Kelly999", "", 0, 1, 3, 0));
		credits.add(new CreditsSlot("kenshin5491", "", 0, 0, 5, 0));
		credits.add(new CreditsSlot("Kestrel", "", 0, 0, 6, 0));
		credits.add(new CreditsSlot("Kiroberos", "", 0, 0, 0, 4));
		credits.add(new CreditsSlot("Kernog", "", 0, 0, 1, 0));
		credits.add(new CreditsSlot("Knight-Lord Xander", "", 0, 0, 0, 6));
		credits.add(new CreditsSlot("Chris Turpin", "", 0, 0, 4, 0));
		credits.add(new CreditsSlot("Pallid", "", 0, 0, 0, 5));
		credits.add(new CreditsSlot("Mr L", "", 0, 0, 4, 1));
		credits.add(new CreditsSlot("loveless", "", 0, 0, 0, 6));
		credits.add(new CreditsSlot("Vaddex", "", 0, 0, 0, 3));
		credits.add(new CreditsSlot("KingofKings", "", 0, 0, 0, 6));
		credits.add(new CreditsSlot("waaaghkus", "", 0, 0, 6, 0));
		credits.add(new CreditsSlot("Nightmare", "", 0, 0, 0, 5));
		credits.add(new CreditsSlot("AlphaOneBravo", "", 0, 0, 0, 6));
		credits.add(new CreditsSlot("Max Nobody", "", 0, 0, 0, 6));
		credits.add(new CreditsSlot("Muhaku", "", 0, 0, 4, 0));
		credits.add(new CreditsSlot("Nick LaBlue", "", 0, 0, 5, 0));
		credits.add(new CreditsSlot("Kvernik", "", 0, 0, 4, 0));
		credits.add(new CreditsSlot("Niko", "", 0, 0, 6, 0));
		credits.add(new CreditsSlot("Odd8Ball", "", 0, 0, 0, 5));
		credits.add(new CreditsSlot("Party Commissar", "", 0, 0, 4, 0));
		credits.add(new CreditsSlot("Rohsie", "", 0, 0, 0, 6));
		credits.add(new CreditsSlot("P.", "", 0, 0, 0, 4));
		credits.add(new CreditsSlot("Pierre Mura", "", 0, 0, 0, 6));
		credits.add(new CreditsSlot("Pokys", "", 0, 0, 6, 0));
		credits.add(new CreditsSlot("QQQ", "", 0, 0, 0, 6));
		credits.add(new CreditsSlot("Rakesh", "", 0, 0, 5, 0));
		credits.add(new CreditsSlot("Master's dumb bitch", "", 0, 0, 0, 6));
		credits.add(new CreditsSlot("redwulfen", "", 0, 0, 0, 5));
		credits.add(new CreditsSlot("RogueRandom", "", 0, 0, 3, 0));
		credits.add(new CreditsSlot("Horagen81", "", 0, 0, 0, 5));
		credits.add(new CreditsSlot("RyubosJ", "", 0, 0, 3, 0));
		credits.add(new CreditsSlot("Saladine the Legendary", "", 0, 0, 0, 6));
		credits.add(new CreditsSlot("S", "", 0, 0, 1, 5));
		credits.add(new CreditsSlot("Shas'O Dal'yth Kauyon Kais Taku", "", 0, 0, 6, 0));
		credits.add(new CreditsSlot("Crow Invictus", "", 0, 0, 5, 0));
		credits.add(new CreditsSlot("Sheltem", "", 0, 0, 4, 0));
		credits.add(new CreditsSlot("Silentark", "", 0, 0, 3, 0));
		credits.add(new CreditsSlot("Sorter", "", 0, 0, 0, 6));
		credits.add(new CreditsSlot("Spookermen", "", 0, 0, 0, 6));
		credits.add(new CreditsSlot("Swift Shot", "", 0, 0, 6, 0));
		credits.add(new CreditsSlot("TalonMort", "", 0, 0, 4, 0));
		credits.add(new CreditsSlot("Tanall", "", 0, 1, 3, 0));
		credits.add(new CreditsSlot("Tanner D.", "", 0, 0, 0, 5));
		credits.add(new CreditsSlot("Terrance", "", 0, 0, 3, 0));
		credits.add(new CreditsSlot("Jordan Aitken", "", 0, 0, 6, 0));
		credits.add(new CreditsSlot("T. Garou", "", 0, 0, 0, 4));
		credits.add(new CreditsSlot("Timmybond24", "", 0, 0, 0, 4));
		credits.add(new CreditsSlot("Torinir", "", 0, 0, 6, 0));
		credits.add(new CreditsSlot("Torsten015", "", 0, 0, 0, 5));
		credits.add(new CreditsSlot("TreenVall", "", 0, 0, 2, 0));
		credits.add(new CreditsSlot("triangleman", "", 0, 0, 5, 0));
		credits.add(new CreditsSlot("Antriad", "", 0, 0, 1, 2));
		credits.add(new CreditsSlot("Isidoros", "", 0, 0, 5, 0));
		credits.add(new CreditsSlot("Vaelin", "", 0, 0, 4, 0));
		credits.add(new CreditsSlot("iloveyouMiaoNiNi", "", 0, 0, 0, 6));
		credits.add(new CreditsSlot("Whatever", "", 0, 0, 4, 0));
		credits.add(new CreditsSlot("William Brown", "", 0, 0, 5, 1));
		credits.add(new CreditsSlot("Wolfregis", "", 0, 0, 0, 6));
		credits.add(new CreditsSlot("Nelson Adams", "", 0, 0, 6, 0));
		
		
		credits.sort(Comparator.comparing((CreditsSlot a) -> a.getName().toLowerCase()));
		
		
		Main.primaryStage = primaryStage;

		Main.primaryStage.getIcons().add(WINDOW_IMAGE);

		Main.primaryStage.setTitle("Lilith's Throne " + VERSION_NUMBER + " " + VERSION_DESCRIPTION);
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/lilithsthrone/res/fxml/main.fxml"));

		Pane pane = loader.load();

		mainScene = new Scene(pane);

		if (properties.lightTheme) {
			mainScene.getStylesheets().add("/com/lilithsthrone/res/css/stylesheet_light.css");
		} else {
			mainScene.getStylesheets().add("/com/lilithsthrone/res/css/stylesheet.css");
		}

		mainController = loader.getController();
		Main.primaryStage.setScene(mainScene);
		Main.primaryStage.show();
		Main.game = new Game();
		
		loader = new FXMLLoader(getClass().getResource("/com/lilithsthrone/res/fxml/main.fxml"));
		try {
			if (Main.mainScene == null) {
				pane = loader.load();
				Main.mainController = loader.getController();

				Main.mainScene = new Scene(pane);
				if (Main.getProperties().lightTheme)
					Main.mainScene.getStylesheets().add("/com/lilithsthrone/res/css/stylesheet_light.css");
				else
					Main.mainScene.getStylesheets().add("/com/lilithsthrone/res/css/stylesheet.css");
			}

			Main.primaryStage.setScene(Main.mainScene);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Main.game.setContent(new Response("", "", OptionsDialogue.MENU));
		
	}

	public static void main(String[] args) {
		
		// Create folders:
		File dir = new File("data/");
		dir.mkdir();
		dir = new File("data/saves");
		dir.mkdir();
		dir = new File("data/characters");
		dir.mkdir();
		
		// Load properties:
		if (new File("data/properties.xml").exists()) {
			try {
				properties = new Properties();
				properties.loadPropertiesFromXML();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else {
			properties = new Properties();
			properties.savePropertiesAsXML();
		}
		
		launch(args);
	}
	
	/**
	 * Starts a completely new game. Runs a new World Generation.
	 */
	public static void startNewGame(DialogueNodeOld startingDialogueNode) {
		
		Main.game = new Game();
		
		// Generate world:
		if (!(gen == null))
			if (gen.isRunning()) {
				gen.cancel();
			}

		gen = new Generation();

		gen.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent t) {
				
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/lilithsthrone/res/fxml/main.fxml"));
				Pane pane;
				try {
					if (Main.mainScene == null) {
						pane = loader.load();
						Main.mainController = loader.getController();

						Main.mainScene = new Scene(pane);
						if (Main.getProperties().lightTheme)
							Main.mainScene.getStylesheets().add("/com/lilithsthrone/res/css/stylesheet_light.css");
						else
							Main.mainScene.getStylesheets().add("/com/lilithsthrone/res/css/stylesheet.css");
					}

					Main.primaryStage.setScene(Main.mainScene);

				} catch (Exception e) {
					e.printStackTrace();
				}
				Main.game.setPlayer(new PlayerCharacter(new NameTriplet("Player"), "", 1, Gender.M_P_MALE, RacialBody.HUMAN, RaceStage.HUMAN, null, WorldType.EMPTY, PlaceType.GENERIC_MUSEUM));

				Main.game.initNewGame(startingDialogueNode);
				
				Main.game.endTurn(0);
				//Main.mainController.processNewDialogue();
			}
		});
		new Thread(gen).start();
	}
	
	public static boolean isVersionOlderThan(String versionToCheck, String versionToCheckAgainst) {
		String[] v1 = versionToCheck.split("\\.");
		String[] v2 = versionToCheckAgainst.split("\\.");
		
		try {
			if(Integer.valueOf(v1[0]) < Integer.valueOf(v2[0])) {
				return true;
			}
			
			if(Integer.valueOf((v1[1].length()==1?v1[1]+"0":v1[1])) < Integer.valueOf((v2[1].length()==1?v2[1]+"0":v2[1]))) {
				return true;
			}
			
			if(Integer.valueOf((v1[2].length()==1?v1[2]+"0":v1[2])) < Integer.valueOf((v2[2].length()==1?v2[2]+"0":v2[2]))) {
				return true;
			}
			
			if(v1.length<4) {
				if(v2.length<4) {
					return false;
				} else {
					return true;
				}
			}
			if(v2.length<4) {
				return false;
			}
			
			if(Integer.valueOf((v1[3].length()==1?v1[3]+"0":v1[3])) < Integer.valueOf((v2[3].length()==1?v2[3]+"0":v2[3]))) {
				return true;
			}
		} catch(Exception ex) {
			return true;
		}
		
		return false;
	}
	
	public static int getFontSize() {
		return properties.fontSize;
	}

	public static void setFontSize(int size) {
		properties.fontSize = size;
		properties.savePropertiesAsXML();
	}
	
	
	public static void quickSaveGame() {
		if (Main.game.isInCombat()) {
			Main.game.flashMessage(Colour.GENERIC_BAD, "Cannot quicksave while in combat!");
			
		} else if (Main.game.isInSex()) {
			Main.game.flashMessage(Colour.GENERIC_BAD, "Cannot quicksave while in sex!");
			
		} else if (Main.game.getCurrentDialogueNode().getMapDisplay()!=MapDisplay.NORMAL) {
			Main.game.flashMessage(Colour.GENERIC_BAD, "Can only quicksave in a normal scene!");
			
		} else {
			Main.getProperties().lastQuickSaveName = "QuickSave_"+Main.game.getPlayer().getName();
			saveGame("QuickSave_"+Main.game.getPlayer().getName(), true);
		}
	}

	public static void quickLoadGame() {
		String name = "QuickSave_"+Main.game.getPlayer().getName();
		
//		if(new File("data/saves/"+name+".lts").exists()) {
			loadGame(name);
//		} else {
//			loadGame(Main.getProperties().lastQuickSaveName);
//		}
	}

	public static void saveGame(String name, boolean allowOverwrite) {
		if (name.length()==0) {
			Main.game.flashMessage(Colour.GENERIC_BAD, "Name too short!");
			return;
		}
		if (name.length() > 32) {
			Main.game.flashMessage(Colour.GENERIC_BAD, "Name too long!");
			return;
		}
		if (!name.matches("[a-zA-Z0-9]+[a-zA-Z0-9' _]*")) {
			Main.game.flashMessage(Colour.GENERIC_BAD, "Incompatible characters!");
			return;
		}
		
		File dir = new File("data/");
		dir.mkdir();

		dir = new File("data/saves");
		dir.mkdir();
		
		boolean overwrite = false;
		if (dir.isDirectory()) {
			File[] directoryListing = dir.listFiles((path, filename) -> filename.endsWith(".lts"));
			if (directoryListing != null) {
				for (File child : directoryListing) {
					if (child.getName().equals(name+".lts")){
						if(!allowOverwrite) {
							Main.game.flashMessage(Colour.GENERIC_BAD, "Name already exists!");
							return;
						} else {
							overwrite = true;
						}
					}
				}
			}
		}
		
		File file = new File("data/saves/"+name+".lts");
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
//			long timeStart = System.nanoTime();
//			System.out.println(timeStart);
			
			try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)))) {
			  oos.writeObject(Main.game);
			  oos.close();
			}
			
//			System.out.println("Difference: "+(System.nanoTime()-timeStart)/1000000000f);

			properties.lastSaveLocation = name;//"data/saves/"+name+".lts";
			properties.nameColour = Femininity.valueOf(game.getPlayer().getFemininityValue()).getColour().toWebHexString();
			properties.name = game.getPlayer().getName();
			properties.level = game.getPlayer().getLevel();
			properties.money = game.getPlayer().getMoney();
			properties.arcaneEssences = game.getPlayer().getEssenceCount(TFEssence.ARCANE);
			properties.race = game.getPlayer().getRace().getName();
			properties.quest = game.getPlayer().getQuest(QuestLine.MAIN).getName();

			properties.savePropertiesAsXML();


		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		if(Main.game.getCurrentDialogueNode() == OptionsDialogue.SAVE_LOAD) {
			if(overwrite) {
				Main.game.setContent(new Response("", "", Main.game.getCurrentDialogueNode()), Colour.GENERIC_GOOD, "Save game overwritten!");
			} else {
				Main.game.setContent(new Response("", "", Main.game.getCurrentDialogueNode()), Colour.GENERIC_GOOD, "Game saved!");
			}
		} else if(name.equals("QuickSave_"+Main.game.getPlayer().getName())){
			Main.game.flashMessage(Colour.GENERIC_GOOD, "Quick saved!");
		}
	}

	public static void loadGame(String name) {
		
		File file = new File("data/saves/"+name+".lts");
		
		if (file.exists()) {
			try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
				Main.game = (Game) ois.readObject();
				Main.game.reloadContent();
				if (Main.game.getCurrentDialogueNode().getMapDisplay() == MapDisplay.OPTIONS) {
					Main.mainController.openOptions();
				}
				
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			
		} else {
			Main.game.flashMessage(Colour.GENERIC_BAD, "File not found...");
		}
	}
	
	public static void deleteGame(String name) {
		File file = new File("data/saves/"+name+".lts");

		if (file.exists()) {
			try {
				file.delete();
				Main.game.setContent(new Response("", "", Main.game.getCurrentDialogueNode()));
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			
		} else {
			Main.game.flashMessage(Colour.GENERIC_BAD, "File not found...");
		}
	}
	
	public static void deleteExportedGame(String name) {
		File file = new File("data/saves/"+name+".xml");

		if (file.exists()) {
			try {
				file.delete();
				Main.game.setContent(new Response("", "", Main.game.getCurrentDialogueNode()));
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			
		} else {
			Main.game.flashMessage(Colour.GENERIC_BAD, "File not found...");
		}
	}
	
	public static void deleteExportedCharacter(String name) {
		File file = new File("data/characters/"+name+".xml");

		if (file.exists()) {
			try {
				file.delete();
				Main.game.setContent(new Response("", "", Main.game.getCurrentDialogueNode()));
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			
		} else {
			Main.game.flashMessage(Colour.GENERIC_BAD, "File not found...");
		}
	}
	
	public static List<File> getSavedGames() {
		List<File> filesList = new ArrayList<>();
		
		File dir = new File("data/saves");
		if (dir.isDirectory()) {
			File[] directoryListing = dir.listFiles((path, name) -> name.endsWith(".lts"));
			if (directoryListing != null) {
				filesList.addAll(Arrays.asList(directoryListing));
			}
		}

		filesList.sort(Comparator.comparingLong(File::lastModified).reversed());
		
		return filesList;
	}
	
	public static List<File> getCharactersForImport() {
		List<File> filesList = new ArrayList<>();
		
		File dir = new File("data/characters");
		if (dir.isDirectory()) {
			File[] directoryListing = dir.listFiles((path, name) -> name.endsWith(".xml"));
			if (directoryListing != null) {
				filesList.addAll(Arrays.asList(directoryListing));
			}
		}

		filesList.sort(Comparator.comparingLong(File::lastModified).reversed());
		
		return filesList;
	}
	
	public static List<File> getSlavesForImport() {
		List<File> filesList = new ArrayList<>();
		
		File dir = new File("data/characters");
		if (dir.isDirectory()) {
			File[] directoryListing = dir.listFiles((path, name) -> name.endsWith(".xml"));
			if (directoryListing != null) {
				filesList.addAll(Arrays.asList(directoryListing));
			}
		}
		
		filesList.sort(Comparator.comparingLong(File::lastModified).reversed());
		
		return filesList;
	}
	
	public static List<File> getGamesForImport() {
		List<File> filesList = new ArrayList<>();
		
		File dir = new File("data/saves");
		if (dir.isDirectory()) {
			File[] directoryListing = dir.listFiles((path, name) -> name.endsWith(".xml"));
			if (directoryListing != null) {
				filesList.addAll(Arrays.asList(directoryListing));
			}
		}

		filesList.sort(Comparator.comparingLong(File::lastModified).reversed());
		
		return filesList;
	}
	
	public static void importCharacter(File file) {
		if (file != null) {
			try {
				Main.game.setPlayer(CharacterUtils.startLoadingCharacterFromXML());
				Main.game.setPlayer(CharacterUtils.loadCharacterFromXML(file, Main.game.getPlayer()));
				
				Main.game.getPlayer().getSlavesOwned().clear();
				Main.game.getPlayer().endPregnancy(false);
				
				Main.game.setRenderAttributesSection(true);
				Main.game.clearTextStartStringBuilder();
				Main.game.clearTextEndStringBuilder();
				Main.getProperties().setNewWeaponDiscovered(false);
				Main.getProperties().setNewClothingDiscovered(false);
				Main.getProperties().setNewItemDiscovered(false);
				Main.game.getPlayer().calculateStatusEffects(0);

				Main.game.initNewGame(CharacterCreation.START_GAME_WITH_IMPORT);
				
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public static Properties getProperties() {
		return properties;
	}

	public static void saveProperties() {
		properties.savePropertiesAsXML();
	}
}
