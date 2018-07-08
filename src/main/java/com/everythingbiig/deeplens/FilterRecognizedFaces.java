package com.everythingbiig.deeplens;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FilterRecognizedFaces implements RequestHandler<FaceRecognitionEvent, Void> {
    static final Logger logger = LogManager.getLogger(FilterRecognizedFaces.class);

    private static final float RECOGNITION_THRESHOLD = 0.90f;

    private static final AmazonS3 s3Client = AmazonS3ClientBuilder.defaultClient();

    @Override
    public Void handleRequest(FaceRecognitionEvent event, Context context) {
        logger.debug("Got event: " + event);
        if(event == null) {
            logger.debug("Got empty result");
        }
        applyThresholdFilter(event, context);
        return null;
    }

    protected void applyThresholdFilter(FaceRecognitionEvent event, Context context) {
        if(event.getFace() < RECOGNITION_THRESHOLD) {
            logger.debug("Result is below threshold: {} > RECOGNITION_THRESHOLD({})", event.getFace(), RECOGNITION_THRESHOLD);
            s3Client.deleteObject(event.getImageBucket(), event.getImageKey());
            logger.debug("Deleted face.");
        }
    }
}
