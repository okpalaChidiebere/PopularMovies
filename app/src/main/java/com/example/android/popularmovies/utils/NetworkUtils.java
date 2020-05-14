package com.example.android.popularmovies.utils;

import android.net.Uri;
import android.util.Log;

import com.example.android.popularmovies.model.Movies;
import com.example.android.popularmovies.model.Review;
import com.example.android.popularmovies.model.Trailer;

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


    /*Url components*/
    private static final String BASE_MOVIEdb_REQUEST_URL =
            "https://api.themoviedb.org/3";
    private static final String DISCOVER_MOVIE = "discover/movie";
    // The url component to access movies
    private static final String MOVIE_URL = "movie/";
    // The url component to access videos
    private static final String VIDEO = "videos";
    // The url component to access reviews
    private static final String REVIEW = "reviews";
    private static final String API_KEY = "api_key";
    private static final String API_KEY_VALUE = "3bc5452a3c521649c0b418627ea7ddab";
    private static final String SORT_BY_KEY = "sort_by";
    private static final String MOVIE_ID = "id";
    private static final String TRAILER_NAME ="name";
    private static final String TRAILER_ID = "key";
    private static final String AUTHOR = "author";
    private static final String CONTENT = "content";

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
                long movieID = currentArrayPosition.optLong(MOVIE_ID);

                Movies newMoviesObjectInstance = new Movies(movieTitle, thumbnail, overview, rating, releaseDate, movieID);
                movies.add(newMoviesObjectInstance);

               // System.out.println("title: " + movieTitle + " overview: " + overview + " vote: " + rating + " releaseDate: " + releaseDate + " image: " + thumbnail);

            }

        }catch (JSONException e) {
            Log.e("JsonUtils", "Problem parsing the earthquake JSON results", e);
        }

        return movies;
    }

    public static List<Trailer> parseTrailersJson(String trailerJSONResponse) {

        ArrayList<Trailer> trailers = new ArrayList<>();

        try {

            JSONObject jsonObjRoot = new JSONObject(trailerJSONResponse);

            JSONArray results = jsonObjRoot.getJSONArray(KEY_RESULTS);

            for (int i = 0; i < results.length(); i++) {

                // Add a new trailer for every index in the results array
                trailers.add(new Trailer());

                // Get the trailer at the current index and turn it into an json object
                JSONObject currentMovieObject = results.optJSONObject(i);

                /* Use the json object to fill the trailer with the appropriate data*/
                trailers.get(i).setName(currentMovieObject.optString(TRAILER_NAME));
                trailers.get(i).setThumbnail(buildYouTubeUrl(currentMovieObject.optString(TRAILER_ID)));

            }

        }catch (JSONException e) {
            Log.e("JsonUtils", "Problem parsing the trailer JSON results", e);
        }

        return trailers;
    }

    public static List<Review> parseReviewJson(String reviewJSONResponse) {

        ArrayList<Review> reviews = new ArrayList<>();

        try {

            JSONObject jsonObjRoot = new JSONObject(reviewJSONResponse);

            JSONArray results = jsonObjRoot.getJSONArray(KEY_RESULTS);

            for (int i = 0; i < results.length(); i++) {

                // Add a new review for every index in the results array
                reviews.add(new Review());

                // Get the review at the current index and turn it into an json object
                JSONObject currentMovieObject = results.optJSONObject(i);

                /* Use the json object to fill the trailer with the appropriate data*/
                reviews.get(i).setAuthor(currentMovieObject.optString(AUTHOR));
                reviews.get(i).setContent(currentMovieObject.optString(CONTENT));
            }

        }catch (JSONException e) {
            Log.e("JsonUtils", "Problem parsing the review JSON results", e);
        }

        return reviews;
    }

    private static String buildImageUrl(String posterPath) {

        final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/";

        final String IMAGE_SIZE = "w185/";

        final String POSTER_PATH = posterPath.substring(posterPath.lastIndexOf("/") + 1);

        String FINAL_IMAGE_URL = IMAGE_BASE_URL + IMAGE_SIZE + POSTER_PATH;


        return FINAL_IMAGE_URL;
    }

    private static String buildYouTubeUrl(String youTubeKey) {

        final String YOUTUBE_BASE_URL = "https://www.youtube.com/watch?v=";

        String FINAL_YOUTUBE_URL = YOUTUBE_BASE_URL + youTubeKey;


        return FINAL_YOUTUBE_URL;
    }

    public static String buildMovieUrl(String sortBy) {

        // parse breaks apart the URI string that's passed into its parameter
        Uri baseUri = Uri.parse(BASE_MOVIEdb_REQUEST_URL);

        // buildUpon prepares the baseUri that we just parsed so we can add query parameters to it
        Uri.Builder uriBuilder = baseUri.buildUpon();

        // Append query parameter and its value. For example, the `api-key=someRandomNumber`
        uriBuilder.appendEncodedPath(MOVIE_URL)
                .appendEncodedPath(sortBy)
                .appendQueryParameter(API_KEY, API_KEY_VALUE);

        //https://api.themoviedb.org/3/movie/porpularity?api_key=someNUmber
        return uriBuilder.toString();
    }

    public static String buildReviewUrl(long movieID){
        // parse breaks apart the URI string that's passed into its parameter
        Uri baseUri = Uri.parse(BASE_MOVIEdb_REQUEST_URL);

        // buildUpon prepares the baseUri that we just parsed so we can add query parameters to it
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendEncodedPath(MOVIE_URL)
                .appendEncodedPath(Long.toString(movieID))
                .appendEncodedPath(REVIEW)
                .appendQueryParameter(API_KEY, API_KEY_VALUE)
                .build();

        //http://api.themoviedb.org/3/movie/419704/reviews?api_key=someNumber
        return uriBuilder.toString();
    }

    public static String buildTrailerUrl(long movieID){
        Uri baseUri = Uri.parse(BASE_MOVIEdb_REQUEST_URL);

        // buildUpon prepares the baseUri that we just parsed so we can add query parameters to it
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendEncodedPath(MOVIE_URL)
                .appendEncodedPath(Long.toString(movieID))
                .appendEncodedPath(VIDEO)
                .appendQueryParameter(API_KEY, API_KEY_VALUE)
                .build();

        //http://api.themoviedb.org/3/movie/419704/videos?api_key=someNumber
        return uriBuilder.toString();
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

    /**
     * Query the MOVIEdb dataset and return a list of {@link Trailer} objects.
     */
    public static List<Trailer> fetchTrailersData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        Log.i(LOG_TAG, "TEST: fetching and extracting Review Json data");

        // Extract relevant fields from the JSON response and create a list of {@link Movie}s
        List<Trailer> trailers = parseTrailersJson(jsonResponse);

        // Return the list of {@link Movie}s
        return trailers;
    }

    /**
     * Query the MOVIEdb dataset and return a list of {@link Trailer} objects.
     */
    public static List<Review> fetchReviewsData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        Log.i(LOG_TAG, "TEST: fetching and extracting Review Json data");

        // Extract relevant fields from the JSON response and create a list of {@link Movie}s
        List<Review> reviews = parseReviewJson(jsonResponse);

        // Return the list of {@link Movie}s
        return reviews;
    }

}
