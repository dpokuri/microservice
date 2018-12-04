package com.tl.booking.image.entity.constant.enums;

public enum CloudinaryParameter {

    CROP_FILL_PAD("CROP_FILL_PAD", "fill_pad"),
    CROP_THUMB("CROP_THUMB", "thumb"),
    CROP_SCALE("CROP_FILL_PAD", "scale"),
    CROP_CROP("CROP_CROP", "crop"),
    CROP_FIT("CROP_FIT", "fit"),
    QUALITY_AUTO("QUALITY_AUTO", "auto");

    private String name;
    private String value;

    CloudinaryParameter(String name, String value)
    {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
