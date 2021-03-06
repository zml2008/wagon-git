package net.trajano.wagon.git.test;

import static org.junit.Assert.assertEquals;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import net.trajano.wagon.git.GitHubPagesWagon;
import net.trajano.wagon.git.internal.GitUri;

import org.junit.Test;
import org.xbill.DNS.CNAMERecord;
import org.xbill.DNS.Lookup;
import org.xbill.DNS.Type;

public class NetworkLookupTests {
    @Test
    public void testGithubPages() throws Exception {
        final GitUri uri = new GitHubPagesWagon().buildGitUri(URI
                .create("http://site.trajano.net/foo"));
        assertEquals("ssh://git@github.com/trajano/foo.git",
                uri.getGitRepositoryUri());
    }

    @Test
    public void testGithubPagesWithCname() throws Exception {
        final GitUri uri = new GitHubPagesWagon().buildGitUri(URI
                .create("http://twitter.github.io/bootstrap"));
        assertEquals("ssh://git@github.com/twitter/bootstrap.git",
                uri.getGitRepositoryUri());
    }

    @Test
    public void testSiteTrajanoNet() throws Exception {
        final Lookup lookup = new Lookup("site.trajano.net", Type.CNAME);
        lookup.run();
        assertEquals("trajano.github.io.", ((CNAMERecord) lookup.run()[0])
                .getTarget().toString());
    }

    @Test
    public void testUrlRedirectToFolder() throws Exception {

        final HttpURLConnection urlConnection = (HttpURLConnection) new URL(
                "http://site.trajano.net/ZaWorld").openConnection();
        urlConnection.connect();
        assertEquals(200, urlConnection.getResponseCode());
        assertEquals("http://site.trajano.net/ZaWorld/", urlConnection.getURL()
                .toExternalForm());
        urlConnection.disconnect();

    }
}
