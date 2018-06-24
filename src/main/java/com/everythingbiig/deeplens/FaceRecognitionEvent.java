package com.everythingbiig.deeplens;

public class FaceRecognitionEvent {
    private float face;
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
    public String toString() {
        return String.format("{face:%3.4f}",face);
    }

}
