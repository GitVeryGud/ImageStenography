package img.math;

import java.util.Objects;
import static img.math.Maths.*;

public class Vector3 implements Cloneable {
    public float x;
    public float y;
    public float z;

    public Vector3() {
    }

    public Vector3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3(Vector3 other) {
        this(other.x, other.y, other.z);
    }

    public Vector3(float xyz) {
        this(xyz, xyz, xyz);
    }

    public Vector3 set(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    public Vector3 set(Vector3 other) {
        return set(other.x, other.y, other.z);
    }

    public Vector3 set(float xyz) {
        return set(xyz, xyz, xyz);
    }

    public Vector3 add(float x, float y, float z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    public Vector3 add(Vector3 other) {
        return add(other.x, other.y, other.z);
    }

    public Vector3 add(float value) {
        return this.add(value, value, value);
    }

    public Vector3 sub(float x, float y, float z) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return this;
    }

    public Vector3 sub(Vector3 other) {
        return sub(other.x, other.y, other.z);
    }

    public Vector3 sub(float value) {
        return sub(value, value, value);
    }

    public Vector3 mul(float x, float y, float z) {
        this.x *= x;
        this.y *= y;
        this.z *= z;
        return this;
    }

    public Vector3 mul(float s) {
        return mul(s, s, s);
    }

    public Vector3 mul(Vector3 other) {
        return mul(other.x, other.y, other.z);
    }

    public Vector3 negate() {
        return mul(-1);
    }

    public Vector3 div(float x, float y, float z) {
        this.x /= x;
        this.y /= y;
        this.z /= z;
        return this;
    }

    public Vector3 div(float s) { return div(s, s, s); }

    public Vector3 div(Vector3 other) {
        return div(other.x, other.y, other.z);
    }

    public float dot(float x, float y, float z) {
        return this.x * x + this.y * y + this.z * z;
    }

    public float dot(Vector3 other) {
        return dot(other.x, other.y, other.z);
    }

    public float sizeSqr() {
        return x * x + y * y + z * z;
    }

    public float size() {
        return (float) Math.sqrt(sizeSqr());
    }

    public Vector3 clone() {
        return new Vector3(x, y, z);
    }

    public Vector3 normalize() {
        return div(size());
    }

    public Vector3 abs() {
        x = Math.abs(x);
        y = Math.abs(y);
        z = Math.abs(z);
        return this;
    }

    public Vector3 clamp(float min, float max) {
        x = Maths.clamp(x, min, max);
        y = Maths.clamp(y, min, max);
        z = Maths.clamp(z, min, max);
        return this;
    }

    public Vector3 lerp(Vector3 v, float a) {
        return mul(1 - a).add(mul(v, a));
    }

    public boolean equals(Vector3 o, float epsilon) {
        if (this == o) return true;
        if (o == null) return false;

        return floatEquals(x, o.x, epsilon)
                && floatEquals(y, o.y, epsilon)
                && floatEquals(z, o.z, epsilon);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (o.getClass() != Vector3.class) return false;

        return equals((Vector3) o, EPSILON);
    }

    @Override
    public int hashCode() {
        float h = 1f / EPSILON;
        return Objects.hash((int) x * h, (int) y * h, (int) x * h);
    }

    @Override
    public String toString() {
        return String.format("(%.3f, %.3f, %.3f)", x, y, z);
    }

    public static Vector3 add(float x1, float y1, float z1, float x2, float y2, float z2) {
        return new Vector3(x1 + x2, y1 + y2, z1 + z2);
    }

    public static Vector3 add(Vector3 v, float x, float y, float z) {
        return add(v.x, v.y, v.z, x, y, z);
    }

    public static Vector3 add(float x, float y, float z, Vector3 v) {
        return add(x, y, z, v.x, v.y, v.z);
    }

    public static Vector3 add(Vector3 v1, Vector3 v2) {
        return add(v1.x, v1.y, v1.z, v2.x, v2.y, v2.z);
    }

    public static Vector3 add(float xyz1, float xyz2) {
        return add(xyz1, xyz1, xyz1, xyz2, xyz2, xyz2);
    }

    public static Vector3 add(Vector3 v, float xyz) {
        return add(v.x, v.y, v.z, xyz, xyz, xyz);
    }

    public static Vector3 add(float xyz, Vector3 v) {
        return add(xyz, xyz, xyz, v.x, v.y, v.z);
    }

    public static Vector3 sub(float x1, float y1, float z1, float x2, float y2, float z2) {
        return new Vector3(x1 - x2, y1 - y2, z1 - z2);
    }

    public static Vector3 sub(Vector3 v, float x, float y, float z) {
        return sub(v.x, v.y, v.z, x, y, z);
    }

    public static Vector3 sub(float x, float y, float z, Vector3 v) {
        return sub(x, y, z, v.x, v.y, v.z);
    }

    public static Vector3 sub(Vector3 v1, Vector3 v2) {
        return sub(v1.x, v1.y, v1.z, v2.x, v2.y, v2.z);
    }

    public static Vector3 sub(float xyz1, float xyz2) {
        return sub(xyz1, xyz1, xyz1, xyz2, xyz2, xyz2);
    }

    public static Vector3 sub(Vector3 v, float xyz) {
        return sub(v.x, v.y, v.z, xyz, xyz, xyz);
    }

    public static Vector3 sub(float xyz, Vector3 v) {
        return sub(xyz, xyz, xyz, v.x, v.y, v.z);
    }

    public static Vector3 mul(float x1, float y1, float z1, float x2, float y2, float z2) {
        return new Vector3(x1 * x2, y1 * y2, z1 * z2);
    }

    public static Vector3 mul(Vector3 v, float x, float y, float z) {
        return mul(v.x, v.y, v.z, x, y, z);
    }

    public static Vector3 mul(float x, float y, float z, Vector3 v) {
        return mul(x, y, z, v.x, v.y, v.z);
    }

    public static Vector3 mul(Vector3 v1, Vector3 v2) {
        return mul(v1.x, v1.y, v1.z, v2.x, v2.y, v2.z);
    }

    public static Vector3 mul(float xyz1, float xyz2) {
        return mul(xyz1, xyz1, xyz1, xyz2, xyz2, xyz2);
    }

    public static Vector3 mul(Vector3 v, float xyz) {
        return mul(v.x, v.y, v.z, xyz, xyz, xyz);
    }

    public static Vector3 mul(float xyz, Vector3 v) {
        return mul(xyz, xyz, xyz, v.x, v.y, v.z);
    }

    public static Vector3 negate(Vector3 v) {
        return mul(v, -1);
    }

    public static Vector3 div(float x1, float y1, float z1, float x2, float y2, float z2) {
        return new Vector3(x1 / x2, y1 / y2, z1 / z2);
    }

    public static Vector3 div(Vector3 v, float x, float y, float z) {
        return div(v.x, v.y, v.z, x, y, z);
    }

    public static Vector3 div(float x, float y, float z, Vector3 v) {
        return div(x, y, z, v.x, v.y, v.z);
    }

    public static Vector3 div(Vector3 v1, Vector3 v2) {
        return div(v1.x, v1.y, v1.z, v2.x, v2.y, v2.z);
    }

    public static Vector3 div(float xyz1, float xyz2) {
        return div(xyz1, xyz1, xyz1, xyz2, xyz2, xyz2);
    }

    public static Vector3 div(Vector3 v, float xyz) {
        return div(v.x, v.y, v.z, xyz, xyz, xyz);
    }

    public static Vector3 div(float xyz, Vector3 v) {
        return div(xyz, xyz, xyz, v.x, v.y, v.z);
    }

    public static Vector3 normalize(Vector3 v) {
        return v.clone().normalize();
    }

    public static Vector3 clamp(Vector3 v, float min, float max) {
        return v.clone().clamp(min, max);
    }

    public static Vector3 lerp(Vector3 v1, Vector3 v2, float a) {
        return v1.clone().lerp(v2, a);
    }

    public static Vector3 pow(Vector3 v, float p) {
        return new Vector3((float) Math.pow(v.x, p), (float) Math.pow(v.y, p), (float) Math.pow(v.z, p));
    }

    public static Vector3 sqrt(Vector3 v) {
        return new Vector3((float) Math.sqrt(v.x), (float) Math.sqrt(v.y), (float) Math.sqrt(v.y));
    }

    public static Vector3 abs(Vector3 v) {
        return v.clone().abs();
    }
}