package UI.Assets;

import UI.*;
import UI.Assets.CustomFont;
import UI.Panel.*;

import javax.swing.*;

import java.awt.*;

public class CustomLabel extends javax.swing.JLabel {

    // Constructores simples
    protected CustomLabel(String text) {
        super(text);
    }

    public static class Regular extends CustomLabel {

        public Regular(String text, int alignment, float size) {

            super(text);

            setForeground(Palette.WHITE);
            setHorizontalAlignment(alignment);
            setFont(CustomFont.interRegular.deriveFont(size));

        }

    }

    public static class Medium extends CustomLabel {

        public Medium(String text, int alignment, float size) {

            super(text);

            setForeground(Palette.WHITE);
            setHorizontalAlignment(alignment);
            setFont(CustomFont.interMedium.deriveFont(size));

        }

    }

    public static class Semi extends CustomLabel {

        public Semi(String text, int alignment, float size) {

            super(text);

            setForeground(Palette.WHITE);
            setHorizontalAlignment(alignment);
            setFont(CustomFont.interSemiBold.deriveFont(size));

        }

    }

    public static class Bold extends CustomLabel {

        public Bold(String text, int alignment, float size) {

            super(text);

            setForeground(Palette.WHITE);
            setHorizontalAlignment(alignment);
            setFont(CustomFont.interBold.deriveFont(size));

        }

    }

}
