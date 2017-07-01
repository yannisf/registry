package fraglab.registry;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.ServletContext;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ManifestHelperTest {

    public static final String TEST_MANIFEST_MF = "/TEST_MANIFEST.MF";

    @Mock
    private ServletContext servletContext;

    @InjectMocks
    private ManifestHelper manifestHelper;

    @Test
    public void testWithManifest() {
        when(servletContext.getResourceAsStream(anyString()))
                .thenReturn(ManifestHelper.class.getResourceAsStream(TEST_MANIFEST_MF));

        manifestHelper.initialize();

        verify(servletContext).getResourceAsStream(anyString());

        assertThat(manifestHelper.getValue(ContextController.BUILD_TIME), equalTo("201707011657"));
        assertThat(manifestHelper.getValue("Built-By"), equalTo("user"));
        assertThat(manifestHelper.getValue("x"), equalTo(ManifestHelper.N_A));
    }

    @Test
    public void testWithoutManifest() {
        when(servletContext.getResourceAsStream(anyString())).thenReturn(null);

        manifestHelper.initialize();

        verify(servletContext).getResourceAsStream(anyString());
        assertThat(manifestHelper.getValue(ContextController.GIT_REV), equalTo(ManifestHelper.N_A));
    }

}
