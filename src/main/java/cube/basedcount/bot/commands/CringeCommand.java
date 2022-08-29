package cube.basedcount.bot.commands;

import com.google.gson.Gson;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class CringeCommand extends ListenerAdapter {
    @Override
    public void onSlashCommand(SlashCommandEvent event) {
        Gson gson = new Gson();
        ArrayList<Object> cringepillsList = new ArrayList<>();
        ArrayList<Object> basedpillsList = new ArrayList<>();
        String pillsString = "";
        if (!event.getName().equals("cringe")) return; // make sure we handle the right command
        else {
            Integer basedcount = 0;
            String target = event.getOption("user").getAsMember().getAsMention();
            String targetTitle = event.getOption("user").getAsMember().getEffectiveName();
            String sender = event.getMember().getAsMention();
            String targetId = event.getOption("user").getAsMember().getId();
            String targetTag = event.getOption("user").getAsUser().getAsTag();
            String pill;
            String pills = "";
            String pillsc = "";
            boolean isBot = event.getOption("user").getAsUser().isBot();
            if (targetId.equals("927545433251794954")) isBot = false;

            try {
                pill = event.getOption("pill").getAsString();
            } catch (Exception e) {
                pill = null;
            }
            String pillmsg;
            EmbedBuilder eb = new EmbedBuilder();

            if (pill != null) pillmsg = " and **" + pill + "**-pilled.";
            else pillmsg = ".";

            System.out.println("Cringe used by "+sender+" for user id "+target);

            JSONObject jsonObject;
            JSONArray jsonArray = null;
            HttpRequest putrequest3;

            if (!sender.equals(target) && !isBot) {
                try {
                    HttpRequest getrequest = HttpRequest.newBuilder()
                            .uri(new URI("https://basedcounter-default-rtdb.europe-west1.firebasedatabase.app/basedcounter/" + targetId + "/name.json"))
                            .GET()
                            .build();
                    HttpResponse<String> getresponse = HttpClient.newBuilder().build().send(getrequest, HttpResponse.BodyHandlers.ofString());

                    HttpRequest getrequest2 = HttpRequest.newBuilder()
                            .uri(new URI("https://basedcounter-default-rtdb.europe-west1.firebasedatabase.app/basedcounter/" + targetId + "/count.json"))
                            .GET()
                            .build();
                    HttpResponse<String> getresponse2 = HttpClient.newBuilder().build().send(getrequest2, HttpResponse.BodyHandlers.ofString());

                    HttpRequest getrequest3 = HttpRequest.newBuilder() // Get basedpills
                            .uri(new URI("https://basedcounter-default-rtdb.europe-west1.firebasedatabase.app/cringepills/" + targetId + ".json"))
                            .GET()
                            .build();
                    HttpResponse<String> getresponse3 = HttpClient.newBuilder().build().send(getrequest3, HttpResponse.BodyHandlers.ofString());

                    System.out.println(getresponse2.body());

                    try {
                        if (getresponse2.body() != null) basedcount = parseInt(getresponse2.body());
                    } catch (NumberFormatException ignored) {}


                    //targetTag = getresponse.body();


                    System.out.println("Old Basedcount of target: " + basedcount);

                    pillsc = getresponse3.body();
                    System.out.println("Existing Pills: "+pillsc);

                    basedcount--;
                    System.out.println("New Basedcount of target: " + basedcount);
                    System.out.println("Name of target: " + targetTag);

                    try {
                        if (!(pillsc.isBlank() || pillsc.equals("null")) && pill == null) { // Existing pills aren't null  --  No pill was specified
                            pillsString = "{\"" + targetId + "\":" + pillsc + "}";
                            jsonObject = new JSONObject(pillsString);
                            jsonArray = jsonObject.getJSONArray(targetId);
                            System.out.println("JsonArray: " + jsonArray);
                            System.out.println("New PillsString: " + pillsString);

                        } else if (pill != null) {               // A pill was specified
                            pillsString = "{\"" + targetId + "\":" + pills + "}";
                            jsonObject = new JSONObject(pillsString);
                            jsonArray = jsonObject.getJSONArray(targetId);
                            jsonArray.put(pill);
                            System.out.println("JsonArray: " + jsonArray);
                            System.out.println("New PillsString: " + pillsString);
                        } // No need to do anything if existing pills are null

                    } catch (Exception e) {
                        System.out.println("EXCEPTION: CODE FROM CATCH HERE");

                        if (!(pillsc.isBlank() || pillsc.equals("null")) && pill == null) {
                            pillsString = "{\"" + targetId + "\":[\"" + pill + "\"]}";
                            System.out.println("Pillsstring: "+pillsString);

                            jsonObject = new JSONObject(pillsString);
                            jsonArray = jsonObject.getJSONArray(targetId);

                            System.out.println("JsonArray: " + jsonArray);

                            //pillsString = "{"+ jsonArray + "}";
                            System.out.println("New PillsString: " + pillsString);
                        } else if (pill != null) {
                            pillsString = "{\"" + targetId + "\":[\"" + pill + "\"]}";
                            System.out.println("Pillsstring: "+pillsString);

                            jsonObject = new JSONObject(pillsString);
                            jsonArray = jsonObject.getJSONArray(targetId);

                            System.out.println("JsonArray: " + jsonArray);

                            //pillsString = "{"+ jsonArray + "}";
                            System.out.println("New PillsString: " + pillsString);
                        }


                        e.printStackTrace();
                    }

                    try {
                        System.out.println("JsonArray: " + jsonArray);
                        System.out.println("New PillsString: " + pillsString);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    HttpRequest putrequest = HttpRequest.newBuilder()
                            .uri(new URI("https://basedcounter-default-rtdb.europe-west1.firebasedatabase.app/basedcounter/" + targetId + "/name.json?print=silent"))
                            .PUT(HttpRequest.BodyPublishers.ofString("\""+targetTag+"\""))
                            .build();
                    HttpResponse<String> putresponse = HttpClient.newBuilder().build().send(putrequest, HttpResponse.BodyHandlers.ofString());
                    System.out.println("response1: "+putresponse.body());


                    HttpRequest putrequest2 = HttpRequest.newBuilder()
                            .uri(new URI("https://basedcounter-default-rtdb.europe-west1.firebasedatabase.app/basedcounter/" + targetId + "/count.json?print=silent"))
                            .PUT(HttpRequest.BodyPublishers.ofString("" + basedcount))
                            .build();
                    HttpResponse<String> putresponse2 = HttpClient.newBuilder().build().send(putrequest2, HttpResponse.BodyHandlers.ofString());

                    System.out.println("response2: "+putresponse2.body());

                    putrequest3 = HttpRequest.newBuilder()
                            .uri(new URI("https://basedcounter-default-rtdb.europe-west1.firebasedatabase.app/cringepills/" + targetId + ".json?print=silent"))
                            //.method("PATCH",HttpRequest.BodyPublishers.ofString("" + jsonArray + ""))
                            .PUT(HttpRequest.BodyPublishers.ofString("" + jsonArray + ""))
                            .build();

                    HttpResponse<String> putresponse3 = HttpClient.newBuilder().build().send(putrequest3, HttpResponse.BodyHandlers.ofString());

                    System.out.println("response3: " + putresponse3.body());

                    HttpRequest putrequest4 = HttpRequest.newBuilder()
                            .uri(new URI("https://basedcounter-default-rtdb.europe-west1.firebasedatabase.app/basedcounter/" + targetId + "/id.json?print=silent"))
                            .PUT(HttpRequest.BodyPublishers.ofString("\""+targetTag+"\""))
                            .build();
                    HttpResponse<String> putresponse4 = HttpClient.newBuilder().build().send(putrequest4, HttpResponse.BodyHandlers.ofString());
                    System.out.println("response4: "+putresponse.body());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }



            if (targetId.equals("927545433251794954") && !isBot) {
                eb.setTitle("Nice try, chucklenuts!");
                eb.setColor(Color.RED);
                eb.setDescription("This bot cannot be Cringe. It is impossible.\n" +
                        "It will never be Cringe, unlike you, mortal. Choose another target.");
            } else if (!target.equals(sender) && !isBot) {
                eb.setTitle("**" + targetTitle + "** is now **Cringe**!");
                eb.setThumbnail("https://cdn.discordapp.com/attachments/928356660567769118/928358837277327450/unknown.png");
                eb.setColor(Color.RED);
                eb.setDescription("" + sender + " has just rated " + target + " as Cringe" + pillmsg + "\n" +
                        target + "'s Basedcount is now " + basedcount + ".");
            } else if (target.equals(sender) && !isBot) {
                eb.setTitle("**" + targetTitle + "** has just committed **Self-destruction 100**!");
                eb.setThumbnail("https://cdn.discordapp.com/attachments/928356660567769118/928358837277327450/unknown.png");
                eb.setColor(Color.RED);
                eb.setDescription("" + sender + " has just rated themselves as Cringe" + pillmsg + "\n" +
                        sender + "'s Basedcount is now " + basedcount + ".");
            } else if (isBot) {
                eb.setTitle("Bots aren't people.");
                eb.setColor(Color.RED);
                eb.setDescription("Bots may not be rated or examined for Thoughtcrimes.\n" +
                        "Except for <@927545433251794954>, of course.");
            }

            MessageEmbed embed = eb.build();

            event.deferReply(false).addEmbeds(embed).queue();
        }
    }
}
