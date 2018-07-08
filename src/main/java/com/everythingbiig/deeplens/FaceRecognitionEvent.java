package com.everythingbiig.deeplens;

public class FaceRecognitionEvent {
    private float face;
    private float imageSize;
    private String imageBucket;
    private String imageKey;

    public FaceRecognitionEvent() {

    }
    public FaceRecognitionEvent(float face) {
        this.face = face;
    }
    public float getFace() {
        return face;
    }
    public void setFace(float face) {
        this.face = face;
    }

    public float getImageSize() {
        return imageSize;
    }

    public void setImageSize(float imageSize) {
        this.imageSize = imageSize;
    }

    public String getImageBucket() {
        return imageBucket;
    }

    public void setImageBucket(String imageBucket) {
        this.imageBucket = imageBucket;
    }

    public String getImageKey() {
        return imageKey;
    }

    public void setImageKey(String imageKey) {
        this.imageKey = imageKey;
    }

    public String toString() {
        return String.format("{face:%3.4f}",face);
    }

}
