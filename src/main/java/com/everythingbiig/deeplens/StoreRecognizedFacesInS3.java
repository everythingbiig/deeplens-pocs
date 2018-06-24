package com.everythingbiig.deeplens;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

public class StoreRecognizedFacesInS3 {
//    static final Logger logger = LogManager.getLogger(StoreRecognizedFacesInS3.class);

    private static final float RECOGNITION_THRESHOLD = 0.75f;

//    private static final AmazonS3 s3Client = AmazonS3ClientBuilder.defaultClient();

    private static final String OUTPUT_BUCKET = "com.everythingbiig.deeplens/faces";

    private static final String OUTPUT_KEY = "recognized_face.jpg";


    public static void main(String[] args) {
        // DO NOTHING
    }

    public void handleEvent(FaceRecognitionEvent event, Context context) {
        log("Got event: " + event);
        if(event == null) {
            log("Got empty result");
        }
        if(event.getFace() > RECOGNITION_THRESHOLD) {
            log("Result is above threshold: " + event.getFace() + " > RECOGNITION_THRESHOLD(" + RECOGNITION_THRESHOLD+")");
//            s3Client.putObject(OUTPUT_BUCKET, OUTPUT_KEY, FILE);
//            s3Client.copyObject("com.everythingbiig.deeplens/faces", "julioheadshot.png", OUTPUT_BUCKET, OUTPUT_KEY);
            log("Stored face.");
        }

    }
    private static void log(String msg) {
        System.out.println(msg);
    }
}
