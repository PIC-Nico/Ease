package controller;

import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Theme {
    private final String name;

    // background
    private final Color background;
    private final Color buttonBackground;

    // text
    private final Color foreground;
    private final Color lightForeground;
    private final Color textBackground;
    private final Color importantButtonForeground;
    private final Color highlightButtonForeground;

    // window controls
    private final Color windowControlHover;
    private final Color windowCloseHover;

    // highlight colors
    private final Color activeTextField;
    private final Color importantText;
    private final Color buttonHover;
    private final Color importantButtonBackground;
    private final Color importantButtonHover;
    private final Color highlightButtonBackground;
    private final Color highlightButtonHover;
    private final Color importantTooltipBackground;
    private final Color importantTooltipForeground;

    // menu/sidebar
    private final Color sidebarBackground;
    private final Color sidebarMenuHoverBackground;
    private final Color sidebarMenuHoverForeground;
    private final Color sidebarDangerousMenuHoverBackground;
    private final Color sidebarDangerousMenuHoverForeground;
    private final Color sidebarHighlightMenuHoverBackground;
    private final Color sidebarHighlightMenuHoverForeground;

    // border
    private final EmptyBorder sidebarMenuBorder = new EmptyBorder(12,24,12,24);

    public Theme(
            String name,
            Color background,
            Color foreground,
            Color windowControlHover,
            Color windowCloseHover,
            Color activeTextField,
            Color importantText,
            Color textBackground,
            Color buttonBackground,
            Color buttonHover,
            Color lightForeground,
            Color importantButtonBackground,
            Color importantButtonForeground,
            Color importantButtonHover,
            Color highlightButtonBackground,
            Color highlightButtonForeground,
            Color highlightButtonHover,
            Color importantTooltipBackground,
            Color importantTooltipForeground,
            Color sidebarBackground,
            Color sidebarMenuHoverForeground,
            Color sidebarMenuHoverBackground,
            Color sidebarDangerousMenuHoverForeground,
            Color sidebarDangerousMenuHoverBackground,
            Color sidebarHighlightMenuHoverForeground,
            Color sidebarHighlightMenuHoverBackground
    ) {
        this.name = name;

        this.background = background;
        this.foreground = foreground;

        this.windowControlHover = windowControlHover;
        this.windowCloseHover = windowCloseHover;

        this.activeTextField = activeTextField;
        this.importantText = importantText;
        this.lightForeground = lightForeground;
        this.textBackground = textBackground;

        this.buttonBackground = buttonBackground;
        this.buttonHover = buttonHover;

        this.importantButtonBackground = importantButtonBackground;
        this.importantButtonForeground = importantButtonForeground;
        this.importantButtonHover = importantButtonHover;
        this.highlightButtonBackground = highlightButtonBackground;
        this.highlightButtonForeground = highlightButtonForeground;
        this.highlightButtonHover = highlightButtonHover;
        this.importantTooltipBackground = importantTooltipBackground;
        this.importantTooltipForeground = importantTooltipForeground;

        this.sidebarBackground = sidebarBackground;
        this.sidebarMenuHoverForeground = sidebarMenuHoverForeground;
        this.sidebarMenuHoverBackground = sidebarMenuHoverBackground;
        this.sidebarDangerousMenuHoverForeground = sidebarDangerousMenuHoverForeground;
        this.sidebarDangerousMenuHoverBackground = sidebarDangerousMenuHoverBackground;
        this.sidebarHighlightMenuHoverBackground = sidebarHighlightMenuHoverBackground;
        this.sidebarHighlightMenuHoverForeground = sidebarHighlightMenuHoverForeground;
    }

    public Color getSidebarHighlightMenuHoverBackground() {
        return sidebarHighlightMenuHoverBackground;
    }

    public Color getSidebarHighlightMenuHoverForeground() {
        return sidebarHighlightMenuHoverForeground;
    }

    public Color getSidebarMenuHoverForeground() {
        return sidebarMenuHoverForeground;
    }

    public Color getSidebarDangerousMenuHoverForeground() {
        return sidebarDangerousMenuHoverForeground;
    }

    public Color getSidebarDangerousMenuHoverBackground() {
        return sidebarDangerousMenuHoverBackground;
    }

    public Color getSidebarMenuHoverBackground() {
        return sidebarMenuHoverBackground;
    }

    public Color getSidebarBackground() {
        return sidebarBackground;
    }

    public Color getImportantTooltipBackground() {
        return importantTooltipBackground;
    }

    public Color getImportantTooltipForeground() {
        return importantTooltipForeground;
    }

    public Color getHighlightButtonForeground() {
        return highlightButtonForeground;
    }

    public Color getHighlightButtonBackground() {
        return highlightButtonBackground;
    }

    public Color getHighlightButtonHover() {
        return highlightButtonHover;
    }

    public Color getImportantButtonHover() {
        return importantButtonHover;
    }

    public Color getImportantButtonForeground() {
        return importantButtonForeground;
    }

    public Color getImportantButtonBackground() {
        return importantButtonBackground;
    }

    public Color getLightForeground() {
        return lightForeground;
    }

    public Color getButtonHover() {
        return buttonHover;
    }

    public Color getButtonBackground() {
        return buttonBackground;
    }

    public Color getTextBackground() {
        return textBackground;
    }

    public Color getImportantText() {
        return importantText;
    }

    public Color getActiveTextField() {
        return activeTextField;
    }

    public Color getWindowControlHover() {
        return windowControlHover;
    }

    public Color getWindowCloseHover() {
        return windowCloseHover;
    }

    public String getName() {
        return name;
    }

    public Color getBackground() {
        return background;
    }

    public Color getForeground() {
        return foreground;
    }

    public EmptyBorder getSidebarMenuBorder() {
        return sidebarMenuBorder;
    }
}
