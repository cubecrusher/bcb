package cube.basedcount.bot.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class FlairlistCommand extends ListenerAdapter {
    @Override
    public void onSlashCommand(SlashCommandEvent event)
    {
        if (!event.getName().equals("flairlist")) return; // make sure we handle the right command
        EmbedBuilder embed = new EmbedBuilder();

        embed.setTitle("Flair List");
        embed.setColor(Color.YELLOW);
        embed.setDescription("**Take the test [here](https://www.politicalcompass.org/test), then choose a flair.**\n" +
                "You may not remove your Flair after setting it, but you will able to change it at any time.");
        embed.addField("`GrayCentrist`","You have no political opinion.",false);
        embed.addField("`ColoredCentrist`","You have opinions taken from all quadrants.",false);
        embed.addField("`AuthLeft`","You are obsessed with Communist ideas (Red q.).",false);
        embed.addField("`AuthCenter`","You have Fascist political views (Red/Blue q.).",false);
        embed.addField("`AuthRight`","You are a Monarchist (Blue q.).",false);
        embed.addField("`CenterRight`","You are in-between Monarchists and Capitalists (Blue/Purple q.).",false);
        embed.addField("`YellowLibRight`","You belong to the Capitalists (Purple q.).",false);
        embed.addField("`PurpleLibRight`","You are a Hardcore Capitalist (Purple q. *corner*).",false);
        embed.addField("`LibCenter`","You shall be classified as a Monke (Green/Purple q.).",false);
        embed.addField("`LibLeft`","You are an Anarchist (Green q.).",false);
        embed.addField("`OrangeLibLeft`","You are a Hardcore Anarchist (Green q. *corner*) \n*Warning: may cause hate towards bearer*.",false);
        embed.addField("`CenterLeft`","You could be described as a Democratic Socialist (Red/Green q.).",false);

        event.replyEmbeds(embed.build()).queue();
    }
}
