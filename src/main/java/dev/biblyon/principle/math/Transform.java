package dev.biblyon.principle.math;

import dev.biblyon.principle.ecs.Component;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class Transform {
    private Vector3f position = new Vector3f();
    private Quaternionf orientation = new Quaternionf();

    public Vector3f getPosition() {
        return position;
    }

    public Quaternionf getOrientation() {
        return orientation;
    }

    public Matrix4f getTransformMatrix() {
        return new Matrix4f().translationRotateScale(position, orientation, 1.f);
    }
}
