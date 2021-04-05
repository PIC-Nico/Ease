package controller;

import java.awt.*;
import java.util.ArrayList;

public class Themes {
    private static final Themes OBJ = new Themes();
    private final ArrayList<Theme> themes = new ArrayList<>();

    private Themes() {
        themes.add(new Theme(
                "Hell",
                new Color(0xF3F3F3), // background
                new Color(0x000000), // foreground
                new Color(0xDEDEDE), // window control hover
                new Color(0xE81123), // window close hover
                new Color(0x0061BD), // active text field
                new Color(0xE81123), // important text
                new Color(0xFFFFFF), // text background
                new Color(0xE3E3E3), // button background
                new Color(0xD5D5D5), // button hover
                new Color(0x929292), // light foreground
                new Color(0xE23545), // important button background
                new Color(0xFFFFFF), // important button foreground
                new Color(0xBB1A29), // important button hover
                new Color(0x4C875D), // highlight button background
                new Color(0xFFFFFF), // highlight button foreground
                new Color(0x3E6546), // highlight button hover
                new Color(0xE23545), // important tooltip background
                new Color(0xFFFFFF), // important tooltip foreground
                new Color(0xE3E3E3), // sidebar background
                new Color(0x000000), // sidebar menu hover foreground
                new Color(0xF3F3F3), // sidebar menu hover background
                new Color(0xFFFFFF), // sidebar dangerous menu hover foreground
                new Color(0xE23545), // sidebar dangerous menu hover background
                new Color(0xFFFFFF), // sidebar highlight menu hover foreground
                new Color(0x3065AF), // sidebar highlight menu hover background
                new Color(0x000000), // sidebar active menu hover foreground
                new Color(0xF3F3F3), // sidebar active menu hover background
                new Color(0x3065AF), // sidebar active menu highlight background
                new Color(0xF3F3F3), // sidebar active menu background
                new Color(0xBDBDBD), // window border
                new Color(0xF3F3F3), // combobox background
                new Color(0xE3E3E3)  // combobox hover background
        ));

        themes.add(new Theme(
                "Dunkel",
                new Color(0x222222), // background
                new Color(0xFFFFFF), // foreground
                new Color(0x444444), // window control hover
                new Color(0xE81123), // window close hover
                new Color(0x3065AF), // active text field
                new Color(0xFF7979), // important text
                new Color(0x3B3B3B), // text background
                new Color(0x3B3B3B), // button background
                new Color(0x656565), // button hover
                new Color(0xC2C2C2), // foreground
                new Color(0xE23545), // important button background
                new Color(0xFFFFFF), // important button foreground
                new Color(0xBB1A29), // important button hover
                new Color(0x4C875D), // highlight button background
                new Color(0xFFFFFF), // highlight button foreground
                new Color(0x3E6546), // highlight button hover
                new Color(0xE23545), // important tooltip background
                new Color(0xFFFFFF), // important tooltip foreground
                new Color(0x2D2D2D), // sidebar background
                new Color(0xFFFFFF), // sidebar menu hover foreground
                new Color(0x222222), // sidebar menu hover background
                new Color(0xFFFFFF), // sidebar dangerous menu hover foreground
                new Color(0xE23545), // sidebar dangerous menu hover background
                new Color(0xFFFFFF), // sidebar highlight menu hover foreground
                new Color(0x3065AF), // sidebar highlight menu hover background
                new Color(0xFFFFFF), // sidebar active menu hover foreground
                new Color(0x222222), // sidebar active menu hover background
                new Color(0x3065AF), // sidebar active menu highlight background
                new Color(0x222222), // sidebar active menu background
                new Color(0x656565), // window border
                new Color(0x222222), // combobox background
                new Color(0x3B3B3B)  // combobox hover background
        ));
    }

    /**
     * Get the singleton instance.
     * @return Object
     */
    public static Themes getInstance() {
        return OBJ;
    }

    /**
     * This will return the theme by name.
     * @param name Name of the requested theme.
     * @return The requested theme or null (not found).
     */
    public Theme getTheme(String name) {
        // search the theme
        for (Theme x : themes) {
            if (x.getName().equals(name)) {
                return x;
            }
        }

        // requested theme was not found
        return null;
    }

    /**
     * This will return with a list containing all available color themes.
     * @return List containing all available color themes.
     */
    public ArrayList<Theme> getThemes() {
        return themes;
    }
}
