package app;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;

public class BrowserOpener {
    public static void goWebsite(JLabel website, final String url, String text) {
        website.setText(text);
    	website.setForeground(Color.BLUE);
    	Font font = website.getFont();
    	Map<TextAttribute, Object> attributes = new HashMap<>(font.getAttributes());
    	attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        website.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        website.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                    try {
                            Desktop.getDesktop().browse(new URI(url));
                    } catch (URISyntaxException | IOException ex) {
                            //It looks like there's a problem
                    }
            }
        });
    }
}
