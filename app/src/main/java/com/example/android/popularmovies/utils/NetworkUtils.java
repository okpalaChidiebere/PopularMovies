package com.example.android.popularmovies.utils;

import android.util.Log;

import com.example.android.popularmovies.model.Movies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class NetworkUtils {

    public static final String LOG_TAG = NetworkUtils.class.getName();

    /*JSON parse Keys*/
    private static final String KEY_RESULTS = "results";
    private static final String KEY_TITLE = "title";
    private static final String KEY_OVERVIEW = "overview";
    private static final String KEY_VOTE_AVERAGE = "vote_average";
    private static final String KEY_RELEASE_DATE = "release_date";
    private static final String KEY_POSTER_PATH = "poster_path";

    private NetworkUtils() {
    }

    public static List<Movies> parseMoviesJson(String newsJSONResponse) {

        ArrayList<Movies> movies = new ArrayList<>();

        try {
            JSONObject jsonObjRoot = new JSONObject(newsJSONResponse);

            JSONArray results = jsonObjRoot.getJSONArray(KEY_RESULTS);

            for (int i = 0; i < results.length(); i++) {

                JSONObject currentArrayPosition = results.getJSONObject(i);

                String movieTitle = currentArrayPosition.getString(KEY_TITLE);
                String overview = currentArrayPosition.getString(KEY_OVERVIEW);
                int rating = currentArrayPosition.getInt(KEY_VOTE_AVERAGE);
                String releaseDate = currentArrayPosition.getString(KEY_RELEASE_DATE);
                String thumbnail = buildImageUrl(currentArrayPosition.getString(KEY_POSTER_PATH));

                Movies newMoviesObjectInstance = new Movies(movieTitle, thumbnail, overview, rating, releaseDate);
                movies.add(newMoviesObjectInstance);

               // System.out.println("title: " + movieTitle + " overview: " + overview + " vote: " + rating + " releaseDate: " + releaseDate + " image: " + thumbnail);

            }

        }catch (JSONException e) {
            Log.e("JsonUtils", "Problem parsing the earthquake JSON results", e);
        }

        return movies;
    }

    private static String buildImageUrl(String posterPath) {

        final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/";

        final String IMAGE_SIZE = "w185/";

        final String POSTER_PATH = posterPath.substring(posterPath.lastIndexOf("/") + 1);

        String FINAL_IMAGE_URL = IMAGE_BASE_URL + IMAGE_SIZE + POSTER_PATH;


        return FINAL_IMAGE_URL;
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setReadTimeout(10000 /* milliseconds */);
        urlConnection.setConnectTimeout(15000 /* milliseconds */);
        urlConnection.setRequestMethod("GET");
        urlConnection.connect();

        InputStream inputStream = null;
        try {
            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = urlConnection.getInputStream();

                Scanner scanner = new Scanner(inputStream);
                scanner.useDelimiter("\\A");

                boolean hasInput = scanner.hasNext();
                if (hasInput) {
                   return scanner.next();
                } else {
                return null;
                }
            } else {
                /*probably Unauthorized query(401) or query not found (404)*/
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
        //return jsonResponse;
    }

    /**
     * Query the Guardian dataset and return a list of {@link Movies} objects.
     */
    public static List<Movies> fetchEarthquakeData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        Log.i(LOG_TAG, "TEST: fetching and extracting the Json data");

        // Extract relevant fields from the JSON response and create a list of {@link Movie}s
        List<Movies> news = parseMoviesJson(jsonResponse);

        // Return the list of {@link Movie}s
        return news;
    }

}
