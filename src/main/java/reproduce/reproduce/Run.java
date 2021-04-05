package reproduce.reproduce;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.util.concurrent.TimeUnit;

public class Run {

    private static final Logger LOGGER = LogManager.getLogger(Run.class);

    static void run() {
        new Run().doRun();
    }

    void doRun() {
        System.out.println("Log4j initialization complete");
        LOGGER.info("Info enabled");
        LOGGER.debug("Debugging enabled");

        var properties = new String[] {
                "log4j2.contextSelector", "Log4jContextSelector",
                "log4j2.asyncLoggerWaitStrategy", "AsyncLogger.WaitStrategy",
                "log4j2.asyncLoggerConfigWaitStrategy", "AsyncLoggerConfig.WaitStrategy"};
        for (String property : properties) {
            LOGGER.info("{}: {}", property, System.getProperty(property));
        }

        while (true) {
            LOGGER.info("Running");
            LOGGER.debug("Running and debugging");
            try {
                TimeUnit.SECONDS.sleep(5L);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                break;
            }
            dumpThreads();
        }
    }

    private void dumpThreads() {
        ThreadInfo[] threadInfo = ManagementFactory.getThreadMXBean().dumpAllThreads(true, true);
        for (ThreadInfo thread : threadInfo) {
            LOGGER.debug("Found thread {}, stack:", thread.getThreadName());
            for (StackTraceElement stackTrace : thread.getStackTrace()) {
                LOGGER.debug(stackTrace);
            }
        }
    }

}
