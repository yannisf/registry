package fraglab.factory;

import com.lowagie.text.pdf.BaseFont;
import org.springframework.beans.factory.SmartFactoryBean;
import org.xhtmlrenderer.pdf.ITextRenderer;

public class ITextRendererFactoryBean implements SmartFactoryBean<ITextRenderer> {

    private static final String FONTS_OPEN_SANS_REGULAR = "/fonts/OpenSans-Regular.ttf";
    private static final String FONTS_OPEN_SANS_BOLD = "/fonts/OpenSans-Bold.ttf";
    private static final String FONTS_OPEN_SANS_ITALIC = "/fonts/OpenSans-Italic.ttf";
    private static final String FONTS_OPEN_SANS_BOLD_ITALIC = "/fonts/OpenSans-BoldItalic.ttf";
    private static final String FONTS_DIDACT_GOTHIC = "/fonts/DidactGothic.ttf";

    @Override
    public ITextRenderer getObject() throws Exception {
        ITextRenderer renderer = new ITextRenderer();
        renderer.getFontResolver().addFont(FONTS_OPEN_SANS_REGULAR, BaseFont.IDENTITY_H, true);
        renderer.getFontResolver().addFont(FONTS_OPEN_SANS_BOLD, BaseFont.IDENTITY_H, true);
        renderer.getFontResolver().addFont(FONTS_OPEN_SANS_ITALIC, BaseFont.IDENTITY_H, true);
        renderer.getFontResolver().addFont(FONTS_OPEN_SANS_BOLD_ITALIC, BaseFont.IDENTITY_H, true);
        renderer.getFontResolver().addFont(FONTS_DIDACT_GOTHIC, BaseFont.IDENTITY_H, true);
        return renderer;
    }

    @Override
    public Class<?> getObjectType() {
        return ITextRenderer.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    @Override
    public boolean isPrototype() {
        return true;
    }

    @Override
    public boolean isEagerInit() {
        return false;
    }

}
