package com.aivideostudio.pipeline;

public abstract class BasePipeline implements StudioPipeline {

    protected void title(String text) {
        System.out.println();
        System.out.println("--------------------------------");
        System.out.println(text);
        System.out.println("--------------------------------");
    }

    protected void info(String text){
        System.out.println("[INFO] " + text);
    }

    protected void success(String text){
        System.out.println("[ OK ] " + text);
    }
}