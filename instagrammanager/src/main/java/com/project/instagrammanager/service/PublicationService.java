package com.project.instagrammanager.service;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.project.instagrammanager.exception.PublicationNotFoundException;
import com.project.instagrammanager.model.Publication;
import com.project.instagrammanager.repo.PublicationRepo;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.BasicJsonParser;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;

@Service
public class PublicationService {
    private final PublicationRepo publicationRepo;

    private String access_token = "EAAEEeOUg1dUBAEikfiPlQJgMEcMRrkBwa5JyLmbSDEoHccnVl3eoOmhTCZCZAZBYYIi8tsgcWy0OoG419fLmkJX8MyoaD3msjEOjiIKJxdAQSlkMlbYJqZCRJrz8T7h7XP4glWzyfkYsr3UDwiDseYfT5BLNUR5ZCtZAIZCzdemWC48G7LMKXBZA";

    private String facebook_id="104477918555673";
    private String instagram_id="17841449497619919";



    @Autowired
    public PublicationService(PublicationRepo publicationRepo) {
        this.publicationRepo = publicationRepo;
    }

    public Publication addPublication(Publication publication) {
        return publicationRepo.save(publication);
    }

    public List<Publication> findAllPublications() {
        return publicationRepo.findAll();
    }
    public Publication updatePublication(Publication publication) {
        return publicationRepo.save(publication);
    }

    public void deletePublication(Long id){
        publicationRepo.deleteById(id);
    }

    public Publication findPublicationById(Long id) {
        return publicationRepo.findById(id).orElseThrow(() -> new PublicationNotFoundException(id + "not found"));
    }

    public List<Publication> findAllPosted() throws IOException, JSONException, ParseException {
        List<Publication> result = new ArrayList<>();

        URL url = new URL("https://graph.facebook.com/v12.0/"+instagram_id+"/media?access_token="+access_token);
        HttpURLConnection http = (HttpURLConnection)url.openConnection();
        BufferedReader br = new BufferedReader(new InputStreamReader((http.getInputStream())));
        StringBuilder sb = new StringBuilder();

        String output;
        while ((output = br.readLine()) != null) {
            sb.append(output);
        }

        JSONObject obj = new JSONObject(sb.toString());

        JSONArray o = obj.getJSONArray("data");
        for (int i = 0; i < o.length(); i++)
            result.add(getPostedById(o.getJSONObject(i).getString("id")));

        http.disconnect();
        return result;
    }

    private Publication getPostedById(String id) throws IOException, JSONException, ParseException {
        URL url1 = new URL("https://graph.facebook.com/v12.0/"+id+"/?fields=caption,media_url,timestamp&access_token="+access_token);
        HttpURLConnection http = (HttpURLConnection)url1.openConnection();
        BufferedReader br = new BufferedReader(new InputStreamReader((http.getInputStream())));
        StringBuilder sb = new StringBuilder();

        String output;
        while ((output = br.readLine()) != null) {
            sb.append(output);
        }

        JSONObject obj = new JSONObject(sb.toString());
        int f = 2;

        String caption;
        String media_url;
        media_url = obj.getString("media_url");
        String time = obj.getString("timestamp");

        Date date_to_post = new Date();

        try
        {
            caption = obj.getString("caption");
        }
        catch(JSONException e)
        {
            caption = "";
        }
        return new Publication(media_url, caption, date_to_post);
    }
}

