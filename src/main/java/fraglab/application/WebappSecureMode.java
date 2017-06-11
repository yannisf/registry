package fraglab.application;

public class WebappSecureMode {

    public static final String SECURE = "secure";

    private WebappSecureMode() { }

    public  static boolean isSecure() {
        boolean secure = true;

        String secureProperty = System.getProperty(SECURE);

        if (secureProperty == null) {
            secure = true;
        } else if (secureProperty.equals("false")) {
            secure = false;
        }

        return secure;
    }

}
