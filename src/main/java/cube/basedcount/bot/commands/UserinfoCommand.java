package cube.basedcount.bot.commands;

import com.google.gson.Gson;
import net.dv8tion.jda.api.EmbedBuilder;
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

public class UserinfoCommand extends ListenerAdapter {
    @Override
    public void onSlashCommand(SlashCommandEvent event) {
        Gson gson = new Gson();
        ArrayList<Object> BasedpillsList = new ArrayList<>();
        ArrayList<Object> CringepillsList = new ArrayList<>();
        String basedPillsString = "";
        String cringePillsString = "";
        if (!event.getName().equals("userinfo")) return; // make sure we handle the right command
        else {
            int basedcount = 0;
            String target = event.getOption("user").getAsMember().getAsMention();
            String targetId = event.getOption("user").getAsMember().getId();
            String targetTitle = event.getOption("user").getAsMember().getEffectiveName();
            String targetTag = event.getOption("user").getAsUser().getAsTag();
            String targetPfp = event.getOption("user").getAsUser().getAvatarUrl();
            String Basedpills = "";
            String Cringepills = "";
            String flair = "";
            String whatHappened = "";
            String existsStr = "";
            boolean isException = false;
            boolean exists = true;
            boolean aok = false;
            boolean isBot = event.getOption("user").getAsUser().isBot();

            System.out.println(targetTag);

            EmbedBuilder eb = new EmbedBuilder();

            JSONObject jsonObject;
            JSONArray jsonArray = null;

            JSONObject jsonObject2;
            JSONArray jsonArray2;

            if (targetId.equals("927545433251794954")) isBot = false;


                try {
                    HttpRequest getrequest = HttpRequest.newBuilder() // Get ID
                            .uri(new URI("https://basedcounter-default-rtdb.europe-west1.firebasedatabase.app/basedcounter/" + targetId + "/id.json"))
                            .GET()
                            .build();
                    HttpResponse<String> getresponse = HttpClient.newBuilder().build().send(getrequest, HttpResponse.BodyHandlers.ofString());


                    HttpRequest getrequest2 = HttpRequest.newBuilder() // Get basedcount
                            .uri(new URI("https://basedcounter-default-rtdb.europe-west1.firebasedatabase.app/basedcounter/" + targetId + "/count.json"))
                            .GET()
                            .build();
                    HttpResponse<String> getresponse2 = HttpClient.newBuilder().build().send(getrequest2, HttpResponse.BodyHandlers.ofString());

                    HttpRequest getrequest3 = HttpRequest.newBuilder() // Get basedpills
                            .uri(new URI("https://basedcounter-default-rtdb.europe-west1.firebasedatabase.app/basedpills/" + targetId + ".json"))
                            .GET()
                            .build();
                    HttpResponse<String> getresponse3 = HttpClient.newBuilder().build().send(getrequest3, HttpResponse.BodyHandlers.ofString());

                    HttpRequest getrequest4 = HttpRequest.newBuilder() // Get cringepills
                            .uri(new URI("https://basedcounter-default-rtdb.europe-west1.firebasedatabase.app/cringepills/" + targetId + ".json"))
                            .GET()
                            .build();
                    HttpResponse<String> getresponse4 = HttpClient.newBuilder().build().send(getrequest4, HttpResponse.BodyHandlers.ofString());

                    HttpRequest getrequest5 = HttpRequest.newBuilder() // Get flair
                            .uri(new URI("https://basedcounter-default-rtdb.europe-west1.firebasedatabase.app/flairthing/" + targetId + ".json"))
                            .GET()
                            .build();
                    HttpResponse<String> getresponse5 = HttpClient.newBuilder().build().send(getrequest5, HttpResponse.BodyHandlers.ofString());

                    System.out.println("getresponse: "+getresponse.body());
                    System.out.println("getresponse2: "+getresponse2.body());
                    try {
                        if (getresponse2.body() != null || !getresponse2.body().isBlank()) basedcount = parseInt(getresponse2.body());
                        //if (getresponse.body() != null) targetTag = getresponse.body();
                        if (!getresponse5.body().equals("null")) {
                            flair = getresponse5.body();
                            flair = flair.replace("\"","");
                        }
                        else flair = "Unflaired";
                        Basedpills = getresponse3.body();
                        Cringepills = getresponse4.body();
                        existsStr+=getresponse.body();

                        if (Basedpills.equals("null")) Basedpills = "[\"*none*\"]";
                        if (Cringepills.equals("null")) Cringepills = "[\"*none*\"]";

                    } catch (NumberFormatException ignored){}

                    if (existsStr.length()==0) exists = false;

                    System.out.println("ExistsStrLength: "+existsStr.length());
                    System.out.println("Existing BasedPills: "+Basedpills);
                    System.out.println("Flair: "+flair);
                    System.out.println("Existing CringePills: "+Cringepills);
                    System.out.println("Basedcount of target: " + basedcount);
                    System.out.println("Name of target: " + targetTag);
                    System.out.println("Exists: "+exists);
                    if (exists && !isBot) {
                        try {
                            basedPillsString = "{\"" + targetId + "\":" + Basedpills + "}";

                            jsonObject = new JSONObject(basedPillsString);
                            jsonArray = jsonObject.getJSONArray(targetId);

                            System.out.println("JsonArray: " + jsonArray);

                            if (jsonArray != null) {

                                //Iterating JSON array
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    //Adding each element of JSON array into ArrayList
                                    BasedpillsList.add(jsonArray.get(i));
                                }
                                basedPillsString = BasedpillsList.toString();
                                basedPillsString = basedPillsString.replace("[", "")
                                        .replace("]", "");
                            }

                            //basedPillsString = "{"+ jsonArray + "}";
                            System.out.println("New BasedPillsString: " + basedPillsString);


                            cringePillsString = "{\"" + targetId + "\":" + Cringepills + "}";

                            jsonObject2 = new JSONObject(cringePillsString);
                            jsonArray2 = jsonObject2.getJSONArray(targetId);

                            System.out.println("JsonArray2: " + jsonArray2);

                            if (jsonArray2 != null) {

                                //Iterating JSON array
                                for (int i = 0; i < jsonArray2.length(); i++) {

                                    //Adding each element of JSON array into ArrayList
                                    CringepillsList.add(jsonArray2.get(i));
                                }
                                cringePillsString = CringepillsList.toString();
                                cringePillsString = cringePillsString.replace("[", "")
                                        .replace("]", "");
                            }

                            //basedPillsString = "{"+ jsonArray + "}";
                            System.out.println("New BasedPillsString: " + cringePillsString);
                            aok = true;

                        } catch (Exception e) {

                            e.printStackTrace();
                            if (!aok) {
                                whatHappened = e.getClass().getCanonicalName();
                                System.out.println(whatHappened);

                                System.out.println("EXCEPTION: CODE FROM CATCH HERE");
                                isException = true;

                                jsonObject = new JSONObject(basedPillsString);
                                jsonArray = jsonObject.getJSONArray(targetId);

                                if (jsonArray != null) {

                                    //Iterating JSON array
                                    for (int i = 0; i < jsonArray.length(); i++) {

                                        //Adding each element of JSON array into ArrayList
                                        BasedpillsList.add(jsonArray.get(i));
                                    }
                                    basedPillsString = BasedpillsList.toString();
                                    basedPillsString = basedPillsString.replace("[", "")
                                            .replace("]", "");
                                }
                            }

                            //basedPillsString = "{"+ jsonArray + "}";
                            System.out.println("EXC New BasedPillsString: " + basedPillsString);


                            cringePillsString = "{\"" + targetId + "\":" + Cringepills + "}";

                            jsonObject2 = new JSONObject(cringePillsString);
                            jsonArray2 = jsonObject2.getJSONArray(targetId);

                            System.out.println("EXC JsonArray2: " + jsonArray2);

                            if (jsonArray2 != null) {

                                for (int i = 0; i < jsonArray2.length(); i++) {
                                    CringepillsList.add(jsonArray2.get(i));

                                }
                                cringePillsString = CringepillsList.toString();
                                cringePillsString = cringePillsString.replace("[", "")
                                        .replace("]", "");
                            }

                            //basedPillsString = "{"+ jsonArray + "}";
                            System.out.println("EXC New CringePillsString: " + cringePillsString);
                            System.out.println(basedPillsString);

                            System.out.println("EXC JsonArray: " + jsonArray);

                            //basedPillsString = "{"+ jsonArray + "}";
                            System.out.println("EXC New BasedPillsString: " + basedPillsString);

                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            System.out.println("Userinfo used for user id "+target);

                if (exists && !isBot) {

                    eb.setTitle("**" + targetTag + "**");
                    eb.setThumbnail(targetPfp);
                    eb.setColor(Color.CYAN);
                    eb.setDescription("**Flair**: `" + flair + "`\n" +
                            "**Basedcount**: " + basedcount);
                    eb.addField("**Basedpills**: ", basedPillsString, false);
                    eb.addField("**Cringepills**: ", cringePillsString, false);
                    if (isException)
                        eb.setFooter("If you can see this, I messed something up. \nLatest exception: `" + whatHappened + "`");
                } else if (!exists && !isBot) {
                    eb.setTitle("**" + targetTag + "**");
                    eb.setThumbnail(targetPfp);
                    eb.setColor(Color.CYAN);
                    eb.setDescription("**Flair**: `Unflaired`\n" +
                            "**Basedcount**: 0");
                    eb.addField("**Basedpills**: ", "*none*", false);
                    eb.addField("**Cringepills**: ", "*none*", false);
                    eb.setFooter("Note: This user does not exist in the bot's database.");
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
