package controller;

import javax.swing.*;
import javax.swing.border.*;
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

    // window
    private final Color activeWindowBorder;

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
    private final Color sidebarActiveMenuHoverBackground;
    private final Color sidebarActiveMenuHoverForeground;
    private final Color sidebarActiveMenuHighlight;
    private final Color sidebarActiveMenuBackground;

    // combobox
    private final Color comboboxBackground;
    private final Color comboboxHoverBackground;

    // border
    private final EmptyBorder sidebarMenuBorder;
    private final CompoundBorder sidebarActiveMenuBorder;
    private final EmptyBorder inactiveWindowBorder;
    private final LineBorder windowBorder;

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
            Color sidebarHighlightMenuHoverBackground,
            Color sidebarActiveMenuHoverForeground,
            Color sidebarActiveMenuHoverBackground,
            Color sidebarActiveMenuHighlight,
            Color sidebarActiveMenuBackground,
            Color activeWindowBorder,
            Color comboboxBackground,
            Color comboboxHoverBackground
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
        this.sidebarActiveMenuHoverBackground = sidebarActiveMenuHoverBackground;
        this.sidebarActiveMenuHoverForeground = sidebarActiveMenuHoverForeground;
        this.sidebarActiveMenuHighlight = sidebarActiveMenuHighlight;
        this.sidebarActiveMenuBackground = sidebarActiveMenuBackground;

        this.comboboxBackground = comboboxBackground;
        this.comboboxHoverBackground = comboboxHoverBackground;

        this.activeWindowBorder = activeWindowBorder;
        windowBorder = new LineBorder(activeWindowBorder);

        // create some border
        Border outsideBorder = new MatteBorder(0,6,0,0, sidebarActiveMenuHighlight);
        Border insideBorder = new EmptyBorder(12,18,12,24);

        sidebarActiveMenuBorder = BorderFactory.createCompoundBorder(outsideBorder, insideBorder);
        sidebarMenuBorder = new EmptyBorder(12,24,12,24);

        inactiveWindowBorder = new EmptyBorder(1,1,1,1);
    }

    public Color getComboboxBackground() {
        return comboboxBackground;
    }

    public Color getComboboxHoverBackground() {
        return comboboxHoverBackground;
    }

    public LineBorder getWindowBorder() {
        return windowBorder;
    }

    public Color getActiveWindowBorder() {
        return activeWindowBorder;
    }

    public Color getSidebarActiveMenuBackground() {
        return sidebarActiveMenuBackground;
    }

    public Color getSidebarActiveMenuHoverBackground() {
        return sidebarActiveMenuHoverBackground;
    }

    public Color getSidebarActiveMenuHoverForeground() {
        return sidebarActiveMenuHoverForeground;
    }

    public Color getSidebarActiveMenuHighlight() {
        return sidebarActiveMenuHighlight;
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

    public Border getSidebarMenuBorder() {
        return sidebarMenuBorder;
    }

    public Border getSidebarActiveMenuBorder() {
        return sidebarActiveMenuBorder;
    }

    public EmptyBorder getInactiveWindowBorder() {
        return inactiveWindowBorder;
    }
}
