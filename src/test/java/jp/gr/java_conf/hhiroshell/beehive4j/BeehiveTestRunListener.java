package jp.gr.java_conf.hhiroshell.beehive4j;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.runner.Description;
import org.junit.runner.notification.RunListener;

public class BeehiveTestRunListener extends RunListener {

    @Override
    public void testRunStarted(Description description) throws Exception {
        Logger.getRootLogger().setLevel(Level.INFO);
    }

}
