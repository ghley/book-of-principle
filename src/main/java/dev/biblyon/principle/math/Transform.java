package dev.biblyon.principle.math;

import org.joml.Quaternionf;
import org.joml.Vector3f;

public class Transform {
    private Vector3f position;
    private Quaternionf orientation;

    public Vector3f getPosition() {
        return position;
    }

    public Quaternionf getOrientation() {
        return orientation;
    }

    public void set(Transform transform) {
        this.position.set(transform.position);
        this.orientation.set(transform.orientation);
    }


}
