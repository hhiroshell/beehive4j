package jp.gr.java_conf.hhayakawa_jp.beehive_client;

import java.net.HttpCookie;

final class BeehiveCredential {

    private final HttpCookie session;
    private final String antiCsrfToken;

    BeehiveCredential(HttpCookie session, String antiCsrfToken) {
        super();
        this.session = session;
        this.antiCsrfToken = antiCsrfToken;
    }

    HttpCookie getSession() {
        return session;
    }

    String getAnticsrf() {
        return antiCsrfToken;
    }

}
