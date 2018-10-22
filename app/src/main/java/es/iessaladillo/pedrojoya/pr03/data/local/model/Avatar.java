package es.iessaladillo.pedrojoya.pr03.data.local.model;

import androidx.annotation.DrawableRes;

/**
 * Avatar model class
 */
// DO NOT TOUCH
public class Avatar {

    @DrawableRes
    private final int imageResId;
    private final String name;

    public Avatar(int imageResId, String name) {
        this.imageResId = imageResId;
        this.name = name;
    }

    public void setId(long id) {
        // ESTO LO HAS CAMBIADO SIN QUERER. DEBE SER this.id = id
        long id1 = id;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getName() {
        return name;
    }

}
