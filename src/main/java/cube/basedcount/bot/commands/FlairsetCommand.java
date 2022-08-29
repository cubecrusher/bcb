package cube.basedcount.bot.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static java.lang.Integer.parseInt;

public class FlairsetCommand extends ListenerAdapter {
    @Override
    public void onSlashCommand(SlashCommandEvent event) {
        if (!event.getName().equals("flairset")) return; // make sure we handle the right command
        else {
            String flair = event.getOption("flair").getAsString();
            String oldFlair = "";
            String sender = event.getMember().getAsMention();
            String senderId = event.getMember().getId();
            String senderTitle = event.getMember().getEffectiveName();

            EmbedBuilder eb = new EmbedBuilder();

            try {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(new URI("https://basedcounter-default-rtdb.europe-west1.firebasedatabase.app/flairthing/" + senderId + ".json"))
                        .GET()
                        .build();
                HttpResponse<String> response = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
                oldFlair = response.body();

                System.out.println("Flair of target: " + oldFlair);
                System.out.println("New flair: "+ flair);

                HttpRequest requests = HttpRequest.newBuilder()
                        .uri(new URI("https://basedcounter-default-rtdb.europe-west1.firebasedatabase.app/flairthing/" + senderId + ".json?print=silent"))
                        .PUT(HttpRequest.BodyPublishers.ofString("\""+flair+"\""))
                        .build();
                HttpResponse<String> responses = HttpClient.newBuilder().build().send(requests, HttpResponse.BodyHandlers.ofString());
                System.out.println(responses.body());

                } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println("Flairset used by "+sender);

            if (!oldFlair.equals("\""+flair+"\"")) {
                eb.setTitle("**" + senderTitle + "** is now **"+flair+"**!");
                eb.setColor(Color.GREEN);
                eb.setDescription(sender + " has just changed their flair.\n");

            } else {
                eb.setTitle("Hold up there, buddy!");
                eb.setColor(Color.RED);
                eb.setDescription("You already have this very Flair set.");
            }

            MessageEmbed embed = eb.build();

            event.deferReply(false).addEmbeds(embed).queue();
        }
    }
}
