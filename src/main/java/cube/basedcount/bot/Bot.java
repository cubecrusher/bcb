package cube.basedcount.bot;

import cube.basedcount.bot.commands.*;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Bot extends ListenerAdapter {
    public static void main(String[] args) throws Exception {
        //String token = readFile("./token.txt", StandardCharsets.US_ASCII);
        String token = "OTI3NTQ1NDMzMjUxNzk0OTU0.YdLyAA.70ZtqySxFW2IqN1dIrNBWmMcUe4";
        JDA api = JDABuilder.createDefault(token)
                .addEventListeners((new PingCommand()))
                .addEventListeners((new BasedCommand()))
                .addEventListeners((new CringeCommand()))
                .addEventListeners((new FlairlistCommand()))
                .addEventListeners((new SetdelayCommand()))
                .addEventListeners((new FlairsetCommand()))
                .addEventListeners((new UserinfoCommand()))
                .addEventListeners((new BoardBCommand()))
                .addEventListeners((new BoardCCommand()))
                .addEventListeners((new BotInfo()))
                .build()
                .awaitReady();

        api.getPresence().setActivity(Activity.watching("you is Big Brother"));

        List<CommandData> cmds = new ArrayList<CommandData>();
        OptionData data;

        cmds.add(new CommandData("based", "Appreciate a user and give them a pill.")
                .addOption(OptionType.USER,"user","User which you want to assume Based.",true)
                .addOption(OptionType.STRING,"pill","Give a Based Pill. Usually consists of 1 to 3 words (example: Red(pilled), Pro-gamer(pilled), etc). ",false));

        cmds.add(new CommandData("cringe", "Condemn a user and give them a pill.")
                .addOption(OptionType.USER,"user","User which you want to assume Cringe.",true)
                .addOption(OptionType.STRING,"pill","Give a Cringe Pill. Usually consists of 1 to 3 words (example: Blue(pilled), Anime(pilled), etc). ",false));

        //cmds.add(new CommandData("basedboard", "See the top-10 in the Basedboard."));

        //cmds.add(new CommandData("cringeboard", "See the low-10 in the Cringeboard."));

        cmds.add(new CommandData("ping", "Bot tardiness check."));

        cmds.add(new CommandData("flairlist", "Check all suitable Flairs for you to use."));

        cmds.add(new CommandData("botinfo", "Credits and feedback."));

        //cmds.add(new CommandData("setdelay", "Set the delay a user has to endure before they can Rate a target again.")
                //.addOption(OptionType.STRING,"Delay","Time a user has to wait to Rate a target again (in minutes).",true));

        cmds.add(new CommandData("flairset", "Because Unflaired must not exist.")
                .addOptions(new OptionData(OptionType.STRING,"flair","Choose which quadrant you belong to.",true)
                        .addChoice("GrayCentrist","GrayCentrist")
                        .addChoice("ColoredCentrist","ColoredCentrist")
                        .addChoice("AuthLeft","AuthLeft")
                        .addChoice("AuthCenter","AuthCenter")
                        .addChoice("AuthRight","AuthRight")
                        .addChoice("CenterRight","CenterRight")
                        .addChoice("YellowLibRight","YellowLibRight")
                        .addChoice("PurpleLibRight","PurpleLibRight")
                        .addChoice("LibCenter","LibCenter")
                        .addChoice("LibLeft","LibLeft")
                        .addChoice("OrangeLibLeft","OrangeLibLeft")
                        .addChoice("CenterLeft","CenterLeft")));

        cmds.add(new CommandData("userinfo", "Checks a user's Flair and Basedcount.")
                .addOption(OptionType.USER,"user","User which you want to examine for Thoughtcrimes.",true));

        //api.updateCommands().addCommands(cmds).queue(); // add commands globally (Don't use twice.)
        //api.updateCommands().queue(); // update commands globally

        api.getGuildById("820343404173066251").updateCommands().addCommands(cmds).queue();
        api.getGuildById("382978903175856129").updateCommands().addCommands(cmds).queue();
        api.getGuildById("977651434990501908").updateCommands().addCommands(cmds).queue(); // 983467978047754280
        api.getGuildById("983467978047754280").updateCommands().addCommands(cmds).queue();
        api.getGuildById("798137122790834177").updateCommands().addCommands(cmds).queue(); // remove Objects.requireNonNull if things don't work
    }

    static String readFile(String path, Charset encoding)
            throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }
}
