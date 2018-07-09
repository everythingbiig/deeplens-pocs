package com.everythingbiig.deeplens;

public class FaceRecognitionEvent {
    private float face;
    private float image_size;
    private String image_bucket;
    private String image_key;

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

    public float getImage_size() {
        return image_size;
    }

    public void setImage_size(float image_size) {
        this.image_size = image_size;
    }

    public String getImage_bucket() {
        return image_bucket;
    }

    public void setImage_bucket(String image_bucket) {
        this.image_bucket = image_bucket;
    }

    public String getImage_key() {
        return image_key;
    }

    public void setImage_key(String image_key) {
        this.image_key = image_key;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{\n");
        sb.append("\tface:").append(face).append(",")
                .append("\timage_size:").append(getImage_size()).append("\n")
                .append("\timage_bucket:").append(getImage_bucket()).append("\n")
                .append("\timage_key:").append(getImage_key()).append("\n");
        sb.append("}");
        return sb.toString();
    }

}
