package com.fpm2025.messaging.rabbitmq.producer;

public final class RabbitMQQueues {
    
    // Voice processing
    public static final String VOICE_TRANSCRIPTION = "voice.transcription";
    
    // OCR processing
    public static final String OCR_PROCESS = "ocr.process";
    
    // AI tasks
    public static final String AI_CATEGORIZATION = "ai.categorization";
    public static final String AI_PREDICTION = "ai.prediction";
    
    // Report generation
    public static final String REPORT_GENERATE = "report.generate";
    
    // FCM push
    public static final String FCM_SEND = "fcm.send";
    
    private RabbitMQQueues() {
        throw new UnsupportedOperationException("Utility class");
    }
}