package pl.rsz.springproj.domain;

import java.io.Serializable;
import jakarta.persistence.Embeddable;

@Embeddable
public class Dimensions implements Serializable {

    private Float width;
    private Float height;
    private Float depth;

    public Dimensions() {
    }

    public Dimensions(Float width, Float height, Float depth) {
        this.width = width;
        this.height = height;
        this.depth = depth;
    }

    public void setDimensions(Float width, Float height, Float depth) {
        this.width = width;
        this.height = height;
        this.depth = depth;
    }

    public Float getWidth() {
        return width;
    }

    public void setWidth(Float width) {
        this.width = width;
    }

    public Float getHeight() {
        return height;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public Float getDepth() {
        return depth;
    }

    public void setDepth(Float depth) {
        this.depth = depth;
    }
}