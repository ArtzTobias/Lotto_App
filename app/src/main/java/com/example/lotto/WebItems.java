package com.example.lotto;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WebItems extends AsyncTask<String, Void, String> {

    private StringBuilder builder1 = new StringBuilder();
    private String textfromWeb = null;
    private BufferedReader reader;

    public WebItems() {
        super();
    }

    @Override
    protected String doInBackground(String... strings) {

        String textWeb = null;

        while (textWeb == null) {

            try {
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();


                textWeb = readStream(urlConnection.getInputStream());

                System.out.println("Text der Website in class WebItems: " + textWeb);



            } catch (MalformedURLException e) {
                System.out.println("Fehler bei der Verbindung in class WebItems");
                textWeb = null;
            } catch (IOException e) {
                System.out.println("IOException in class WebItems");
                System.out.println(e.toString());
                textWeb = null;
            }

        }

        textfromWeb = textWeb;


        return null;
    }

    public void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    public String getTextfromWeb(){
        return textfromWeb;
    }

    private String readStream(InputStream in) {
        BufferedReader reader = null;
        StringBuffer response = new StringBuffer();

        try{
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";

            while((line = reader.readLine()) != null) {
                response.append(line);
            }
        } catch (IOException e) {
            System.out.println("Daten in class WebItems konnten nicht gelesen werden");
            return null;
        }finally{
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    System.out.println("Fehler beim schließen des readers in class WebItems");
                }
            }
        }
        return response.toString();
    }
}
