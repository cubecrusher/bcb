package cube.basedcount.bot.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class BotInfo extends ListenerAdapter {
    @Override
    public void onSlashCommand(SlashCommandEvent event){
        if (!event.getName().equals("botinfo")) return; // make sure we handle the right command
        else {
            EmbedBuilder eb = new EmbedBuilder();
            String targetPfp = "https://cdn.discordapp.com/attachments/928356660567769118/929815114234855474/bcb.png";

            eb.setTitle("**Based Count Bot**");
            eb.setThumbnail(targetPfp);
            eb.setColor(Color.YELLOW);
            eb.addField("Inspired by:","*u/basedcount_bot* @ *r/politicalcompassmemes*.\n",false);
            eb.addField("Created by:","*cubecrusher#5187* and *Tasty Kiwi#1234*.\n",false);
            eb.addField("Also:","Basedboard and command cooldown coming '''''soon'''''.\n",false);
            eb.setFooter("We suck at servers, so we don't have one.\nSend feedback to DM's.");

            MessageEmbed embed = eb.build();
            event.deferReply(false).addEmbeds(embed).queue();

        }
    }
}
