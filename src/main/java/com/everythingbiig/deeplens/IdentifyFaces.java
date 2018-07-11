package com.everythingbiig.deeplens;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.*;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;

public class IdentifyFaces implements RequestHandler<FaceRecognitionEvent, Void> {
    static final Logger logger = LogManager.getLogger(IdentifyFaces.class);

    private static final float RECOGNITION_THRESHOLD = 0.80f;

    private static final float SIMILARITY_THRESHOLD = 80.00f;

    private static final AmazonS3 s3Client = AmazonS3ClientBuilder.defaultClient();

    private static final AmazonRekognition recogClient = AmazonRekognitionClientBuilder.defaultClient();

    private static final AmazonSimpleEmailService sesClient = AmazonSimpleEmailServiceClientBuilder.defaultClient();
    
    private static final String targetImageKey = System.getProperty("identification.targetImageKey");

    private static final List<String> toAddresses = Arrays.asList(new String[]{System.getProperty("identification.toEmail")});

    @Override
    public Void handleRequest(FaceRecognitionEvent event, Context context) {
        logger.debug("Got event: " + event);
        if(event == null) {
            logger.debug("Got empty result");
        }
        CompareFacesRequest request = new CompareFacesRequest();

        request.setSimilarityThreshold(SIMILARITY_THRESHOLD);
        request.setSourceImage(createImage(event.getImage_bucket(), event.getImage_key()));
        request.setTargetImage(createImage(event.getImage_bucket(), targetImageKey));
        logger.debug("Comparing request: %s", request.toString());
        CompareFacesResult result = recogClient.compareFaces(request);
        List<CompareFacesMatch> matches = result.getFaceMatches();
        if(matches != null && matches.size() > 0) {
            CompareFacesMatch match = matches.iterator().next();
            logger.debug("Found a match with similarity of %d", match.getSimilarity());
            ComparedFace face = match.getFace();
            logger.debug("Confidence of match: %d", face.getConfidence());
            sesClient.sendEmail(createEmailRequest(event, match));
        }
        return null;
    }

    private SendEmailRequest createEmailRequest(FaceRecognitionEvent event, CompareFacesMatch match) {
        SendEmailRequest request = new SendEmailRequest();
        request.setConfigurationSetName("default");

        Destination destination = new Destination();
        destination.setToAddresses(toAddresses);
        request.setDestination(destination);

        Message msg = new Message();

        Content subject = new Content();
        subject.setData("Recognized someone in the house!");
        msg.setSubject(subject);

        Body body = new Body();
        Content text = new Content();
        text.setData(String.format("Recognized someone matching: %s\n%f", event.getImage_key(), match.getFace().getConfidence()));
        body.setText(text);
        msg.setBody(body);

        request.setMessage(msg);
        request.setSource(System.getProperty("identification.fromEmail"));
        return request;
    }

    private Image createImage(String bucket, String key) {
        Image image = new Image();
        S3Object sourceObj = new S3Object();
        sourceObj.setBucket(bucket);
        sourceObj.setName(key);
        image.setS3Object(sourceObj);
        return image;
    }
}
