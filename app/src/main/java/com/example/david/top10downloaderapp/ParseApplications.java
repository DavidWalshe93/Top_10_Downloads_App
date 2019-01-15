package com.example.david.top10downloaderapp;

// David Walshe
// 14/01/2019

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;

public class ParseApplications {

    private static final String TAG = "ParseApplications";
    private ArrayList<FeedEntry> applications;

    ParseApplications() {
        this.applications = new ArrayList<>();
    }

    public ArrayList<FeedEntry> getApplications() {
        return applications;
    }

    boolean parse(String xmlData) {
        boolean status = true;
        FeedEntry currentRecord = null;
        boolean inEntry = false;
        String textValue = "";

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();  // XML Parser Object Factory
            factory.setNamespaceAware(true);                    //Set up factory object generator
            XmlPullParser xpp = factory.newPullParser();        //XML Parser Object
            xpp.setInput(new StringReader(xmlData));            //Give XML Parser content to parse
            int eventType = xpp.getEventType();                 //Get the first event in the XML
            while(eventType != XmlPullParser.END_DOCUMENT) {    //Check document hasn't reached the end
                String tagName = xpp.getName();
                switch(eventType) {
                    case XmlPullParser.START_TAG:
                        Log.d(TAG, "parse: Starting tag for " + tagName);
                        if("entry".equalsIgnoreCase(tagName)) { //Check to see if ur within an entry tag
                            inEntry = true;
                            currentRecord = new FeedEntry();
                        }
                        break;
                    case XmlPullParser.TEXT:        //Store the text from within an entry
                        textValue = xpp.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        Log.d(TAG, "parse: Ending tag for " + tagName);
                        if(inEntry) {               // If a tag matches a end tag of interest within entry, record the result in the FeedEntry Bean
                            if("entry".equalsIgnoreCase(tagName)) {
                                applications.add(currentRecord);
                                inEntry = false;
                            } else if("name".equalsIgnoreCase(tagName)) {
                                currentRecord.setName(textValue);
                            } else if("artist".equalsIgnoreCase(tagName)) {
                                currentRecord.setArtist(textValue);
                            } else if("releaseDate".equalsIgnoreCase(tagName)) {
                                currentRecord.setReleaseData(textValue);
                            } else if("summary".equalsIgnoreCase(tagName)) {
                                currentRecord.setSummary(textValue);
                            } else if("image".equals(tagName)) {
                                currentRecord.setImageURL(textValue);
                            }
                        }
                    default:
                        break;
                }
                eventType = xpp.next();     // Update control variable for the while loop
            }
            //Test Printout of parsed fields of XML
            for (FeedEntry app: applications) {
                Log.d(TAG, "\n***************************\n");
                Log.d(TAG, app.toString());
            }
        }
        catch(Exception e) {
            status = false;
            e.printStackTrace();
        }
        return status;
    }
}
